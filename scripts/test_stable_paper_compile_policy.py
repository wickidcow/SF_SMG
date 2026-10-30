#!/usr/bin/env python3
"""Preserve bounded stable Paper 26.3 source compile and Java21 release baseline."""
from pathlib import Path
import unittest

WORKFLOW = Path(__file__).resolve().parents[1] / ".github/workflows/maven.yml"
START = "      - name: Compile supported Paper API lines\n"
END = "      - name: Full baseline tests and independent reproducible build\n"
STABLE_LOOP = 'for API in 1.21.11-R0.1-SNAPSHOT 26.2.build.112-stable "[26.3.build,26.4.build)"; do'


def verify(workflow: str) -> None:
    if workflow.count(START) != 1 or workflow.count(END) != 1:
        raise ValueError("source compatibility and release baseline steps must remain")
    prefix, remainder = workflow.split(START, 1)
    lane, suffix = remainder.split(END, 1)
    if "test_stable_paper_compile_policy.py' -v" not in prefix:
        raise ValueError("policy tests must run in CI")
    if STABLE_LOOP not in lane:
        raise ValueError("minimum, 26.2 and bounded stable 26.3 API lines are required")
    if any(x in lane for x in ("26.3.build.140-beta", "alpha", "-rc-", "[26.3.build,)")):
        raise ValueError("pre-release or unbounded Paper 26.3 API is not acceptable")
    if 'mvn -B -ntp -DskipTests "-Dpaper.version=$API" clean package' not in lane:
        raise ValueError("actual source compilation must execute for each API")
    if "mvn -B -ntp -Dpaper.version=1.21.11-R0.1-SNAPSHOT clean verify" not in suffix:
        raise ValueError("Java21 universal baseline and JUnit verification must remain")
    if 'cmp "target/$OUTPUT_NAME" "dist/$OUTPUT_NAME"' not in suffix:
        raise ValueError("independent artifact reproducibility must remain")
    if "if: startsWith(github.ref, 'refs/tags/v')" not in suffix:
        raise ValueError("tag-only immutable release guard must remain")


class StablePaperCompilePolicyTest(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        cls.workflow = WORKFLOW.read_text(encoding="utf-8")

    def test_current_policy(self):
        verify(self.workflow)

    def mutate(self, old: str, new: str):
        self.assertEqual(1, self.workflow.count(old))
        with self.assertRaises(ValueError):
            verify(self.workflow.replace(old, new, 1))

    def test_beta_replacement_is_rejected(self):
        self.mutate(STABLE_LOOP,
                    'for API in 1.21.11-R0.1-SNAPSHOT 26.2.build.112-stable 26.3.build.140-beta; do')

    def test_unbounded_range_is_rejected(self):
        self.mutate(STABLE_LOOP,
                    'for API in 1.21.11-R0.1-SNAPSHOT 26.2.build.112-stable "[26.3.build,)"; do')

    def test_minimum_12111_must_remain(self):
        self.mutate(STABLE_LOOP,
                    'for API in 26.2.build.112-stable "[26.3.build,26.4.build)"; do')

    def test_262_must_remain(self):
        self.mutate(STABLE_LOOP,
                    'for API in 1.21.11-R0.1-SNAPSHOT "[26.3.build,26.4.build)"; do')

    def test_actual_compile_must_remain(self):
        self.mutate('mvn -B -ntp -DskipTests "-Dpaper.version=$API" clean package',
                    'echo "$API compilation passed"')

    def test_independent_jar_comparison_must_remain(self):
        self.mutate('cmp "target/$OUTPUT_NAME" "dist/$OUTPUT_NAME"',
                    'echo reproducibility skipped')

    def test_immutable_publisher_must_remain(self):
        self.mutate("if: startsWith(github.ref, 'refs/tags/v')",
                    "if: github.event_name == 'workflow_dispatch'")

    def test_policy_tests_must_run(self):
        self.mutate("test_stable_paper_compile_policy.py' -v",
                    "test_stable_paper_compile_policy.py' --skip")


if __name__ == "__main__":
    unittest.main()
