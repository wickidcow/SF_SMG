<div align="center">

# SF_SMG — Slimefun Legacy
### Simple Material Generators, preserved and modernized

[![Build](https://github.com/wickidcow/SF_SMG/actions/workflows/maven.yml/badge.svg)](https://github.com/wickidcow/SF_SMG/actions/workflows/maven.yml)
[![License](https://img.shields.io/badge/License-GPLv3-blue)](https://github.com/wickidcow/Slimefun-Legacy/blob/master/LICENSE)

</div>

> [!IMPORTANT]
> SF_SMG is an unofficial, independently maintained downstream fork. **NOT AN OFFICIAL MINECRAFT PRODUCT. NOT APPROVED BY OR ASSOCIATED WITH MOJANG OR MICROSOFT.**

## Preserved Albion behavior

The maintained line keeps the working expanded generator set from the Albion `MODIFIED v0.3` build, `/smg reload`, configurable generator enable/rate values, and output into any `InventoryHolder` above the generator.

It also includes additive modern generators for Wood, Copper, Tuff, Calcite, Dripstone, Resin, Sulfur and Cinnabar.

## 1.0.3 runtime hardening

Generator progress remains stored in a concurrency-safe location-keyed cache, but stale entries are now explicitly removed when:

- the generator block is broken;
- its chunk unloads;
- its world unloads;
- the plugin disables.

This does not change generation rates or item IDs. It simply keeps long-running servers from retaining progress entries for machines that no longer exist.

Release JAR: `SF_SMG1.0.3.jar`

Built with Java 25 targeting Java 21 bytecode. Slimefun Legacy is the primary target; shared API compatibility is retained for Slimefun United, SlimefunGuguProject/Slimefun4 and original Slimefun4-compatible implementations. Paper is primary, with Purpur, Folia and Leaf as compatibility targets.

No direct GuizhanLib dependency is used.

## License and attribution

The maintained distribution is released under the [GNU General Public License v3.0](https://github.com/wickidcow/Slimefun-Legacy/blob/master/LICENSE). Upstream authorship and copyright remain with their original authors and contributors.
