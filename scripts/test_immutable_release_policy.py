#!/usr/bin/env python3
"""Fail closed on SMG release publication policy regressions, without GitHub access."""
from pathlib import Path
import unittest

ROOT = Path(__file__).resolve().parents[1]
WORKFLOW = ROOT / ".github/workflows/maven.yml"
STEP = "      - name: Publish distinct immutable version tag only\n"


def verify_policy(workflow: str) -> None:
    if workflow.count(STEP) != 1:
        raise ValueError("release step must be unique")
    before, release = workflow.split(STEP, 1)
    if "test_immutable_release_policy.py' -v" not in before:
        raise ValueError("policy suite must run on every build")
    if "    branches: ['**']" not in before or "    tags: ['v*']" not in before:
        raise ValueError("existing build triggers must remain")
    if not release.startswith("        if: startsWith(github.ref, 'refs/tags/v')\n"):
        raise ValueError("only exact version tags may enter publication")
    required = (
        'TAG="v${ARTIFACT_VERSION}"',
        'test "$GITHUB_REF" = "refs/tags/${TAG}"',
        'JAR="dist/${OUTPUT_NAME}"',
        'test -s "$JAR"',
        'if gh release view "$TAG" --repo "$GITHUB_REPOSITORY" >/dev/null 2>&1; then',
        'exit 1',
        'fi',
        'gh release create "$TAG" "$JAR" --verify-tag --repo "$GITHUB_REPOSITORY"',
    )
    for value in required:
        if value not in release:
            raise ValueError(f"missing required fail-closed release guard: {value}")
    if any(value in release for value in ("gh release upload", "--clobber", "gh release edit", "workflow_dispatch", "default_branch")):
        raise ValueError("branch/manual or existing-release mutation is forbidden")
    check = release.index(required[4])
    reject = release.index("exit 1", check)
    end = release.index("fi", reject)
    create = release.index(required[7], end)
    if not check < reject < end < create:
        raise ValueError("existing release must be rejected before new creation")


class SMGImmutableReleasePolicyTest(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        cls.workflow = WORKFLOW.read_text(encoding="utf-8")

    def test_current_policy(self):
        verify_policy(self.workflow)

    def mutate(self, before: str, after: str):
        self.assertEqual(1, self.workflow.count(before))
        with self.assertRaises(ValueError):
            verify_policy(self.workflow.replace(before, after, 1))

    def test_main_push_cannot_publish(self):
        self.mutate(
            "if: startsWith(github.ref, 'refs/tags/v')",
            "if: github.ref_name == github.event.repository.default_branch",
        )

    def test_manual_dispatch_cannot_publish(self):
        self.mutate(
            "if: startsWith(github.ref, 'refs/tags/v')",
            "if: github.event_name == 'workflow_dispatch' || startsWith(github.ref, 'refs/tags/v')",
        )

    def test_tag_must_match_canonical_version(self):
        self.mutate('test "$GITHUB_REF" = "refs/tags/${TAG}"',
                    'test "$GITHUB_REF" != "refs/tags/${TAG}"')

    def test_existing_release_check_cannot_be_inverted(self):
        self.mutate(
            'if gh release view "$TAG" --repo "$GITHUB_REPOSITORY" >/dev/null 2>&1; then',
            'if ! gh release view "$TAG" --repo "$GITHUB_REPOSITORY" >/dev/null 2>&1; then',
        )

    def test_existing_release_rejection_is_required(self):
        self.mutate("            exit 1\n", "            true\n")

    def test_existing_release_upload_is_forbidden(self):
        self.mutate(
            'gh release create "$TAG" "$JAR" --verify-tag --repo "$GITHUB_REPOSITORY"',
            'gh release upload "$TAG" "$JAR" --clobber --repo "$GITHUB_REPOSITORY"',
        )

    def test_tag_creation_must_not_be_implicit(self):
        self.mutate(
            'gh release create "$TAG" "$JAR" --verify-tag --repo "$GITHUB_REPOSITORY"',
            'gh release create "$TAG" "$JAR" --repo "$GITHUB_REPOSITORY"',
        )

    def test_jar_must_exist(self):
        self.mutate('test -s "$JAR"', 'test -n "$JAR"')

    def test_ci_policy_suite_must_execute(self):
        self.mutate("test_immutable_release_policy.py' -v", "test_immutable_release_policy.py' --skip")


if __name__ == "__main__":
    unittest.main()
