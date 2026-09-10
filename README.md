<div align="center">

# SF_SMG — Slimefun Legacy
### Maintained SimpleMaterialGenerators for modern Paper servers

SimpleMaterialGenerators adds compact passive generators for common building materials while preserving the original broken/repaired progression model.

[![Build](https://github.com/wickidcow/SF_SMG/actions/workflows/maven.yml/badge.svg)](https://github.com/wickidcow/SF_SMG/actions/workflows/maven.yml)
[![Slimefun Legacy](https://img.shields.io/badge/Slimefun-Legacy-6bd425)](https://github.com/wickidcow/Slimefun-Legacy)
[![Paper](https://img.shields.io/badge/Server-Paper%2026.2-blue)](https://papermc.io/)
[![Java](https://img.shields.io/badge/Build-Java%2025-orange)](https://adoptium.net/)
[![License](https://img.shields.io/badge/License-GPLv3-blue)](https://github.com/wickidcow/Slimefun-Legacy/blob/master/LICENSE)

[Releases](https://github.com/wickidcow/SF_SMG/releases) · [Builds](https://github.com/wickidcow/SF_SMG/actions) · [Issues](https://github.com/wickidcow/SF_SMG/issues)

</div>

> [!IMPORTANT]
> **SF_SMG is an unofficial, independently maintained downstream fork of SimpleMaterialGenerators.** It is maintained by `wickidcow` for [AlbionMC.com](https://albionmc.com) and the wider Slimefun community. It is not an official release of the original SMG project, the original Slimefun project, Slimefun United, or the SlimefunGuguProject.
>
> **NOT AN OFFICIAL MINECRAFT PRODUCT. NOT APPROVED BY OR ASSOCIATED WITH MOJANG OR MICROSOFT.**

---
## What is SF_SMG?

SMG adds passive Slimefun generators for common building materials. Generators output into a chest directly above the machine and retain the original progression through broken and repaired generator tiers.

The maintained fork removes the obsolete direct CS-CoreLib dependency, ports item groups and recipes to the current Slimefun API, and uses a concurrent location-keyed progress map so separate Folia regions cannot race shared generator state.

---
## Download and build

Release JARs use the maintained Slimefun-addon naming convention:

`SF_SMG1.0.1.jar`

The project builds with **Java 25** while targeting **Java 21 bytecode**. The production compatibility baseline is **Slimefun Legacy 4.1.48 on Paper 26.2**. Development builds are available from GitHub Actions and versioned release JARs are published on the Releases page.

---
## Compatibility

Primary target: **Slimefun Legacy**.

Compatibility is also validated against Slimefun United, SlimefunGuguProject/Slimefun4, and original Slimefun4-compatible APIs. Paper is the primary server family; Purpur, Folia and Leaf are compatibility targets.

This maintained fork avoids external Slimefun utility-library dependencies. The remaining BlockTicker compatibility surface is supplied by Slimefun itself so the addon can remain usable across compatible Slimefun implementations.

---
## Credits and project lineage

Original SimpleMaterialGenerators project and authorship belong to **waleks** and its contributors. Modern compatibility, concurrency hardening, and Slimefun Legacy integration are maintained by **wickidcow**.

This fork exists to preserve and maintain that work for current servers—not to replace the original developers or claim their work as its own. Upstream authorship, copyright notices, and license obligations remain respected.

---
## Independence, trademarks and non-affiliation

**NOT AN OFFICIAL MINECRAFT PRODUCT. NOT APPROVED BY OR ASSOCIATED WITH MOJANG OR MICROSOFT.**

SF_SMG and this maintenance fork are independent community projects. They are not affiliated with, endorsed by, sponsored by, approved by, or operated by Mojang Studios or Microsoft Corporation. References to Minecraft, Slimefun, Paper, upstream projects, companies, products, or communities are for identification, compatibility, attribution, and interoperability only.

Minecraft, Mojang Studios, Microsoft, and other third-party names, logos, brands, and trademarks remain the property of their respective owners. No sponsorship, partnership, ownership, or endorsement is claimed or implied.

---
## License

SF_SMG is distributed under the [GNU General Public License v3.0](https://github.com/wickidcow/Slimefun-Legacy/blob/master/LICENSE), consistent with the GPLv3 license retained by this maintained fork.

Upstream authorship and copyright remain with the original SimpleMaterialGenerators authors and contributors. Copyright in later modifications remains with the contributors who authored those changes.
