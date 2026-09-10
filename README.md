# SF_SMG

Maintained SimpleMaterialGenerators fork for modern Slimefun servers.

SMG adds compact passive generators for common building materials. Generators output into a chest directly above the machine and retain the original progression through broken and repaired generator tiers.

## Compatibility

Primary Slimefun targets:
- Slimefun Legacy
- Slimefun United

Additional compatibility targets:
- SlimefunGuguProject/Slimefun4
- Original Slimefun4-compatible API implementations

Server software:
- Paper
- Purpur
- Folia
- Leaf

Minecraft target: **1.21.11+**. Builds use Java 25 with Java 21 bytecode.

The maintained fork removes the old direct CS-CoreLib dependency and ports item groups, recipes and item classes to the current Slimefun API. The remaining BlockTicker compatibility interface is supplied by Slimefun itself so the addon can stay usable across both modern and older-compatible Slimefun forks.

Generator state uses a concurrent location-keyed progress map so separate Folia regions cannot race the shared progress store.

## Dependency policy

This maintained fork avoids external Slimefun utility-library dependencies. Gugu support comes only through the common Slimefun addon API.

## Maintenance

Original project by waleks. Modern maintenance and compatibility work are provided by `wickidcow`.
