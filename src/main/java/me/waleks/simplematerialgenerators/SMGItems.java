package me.waleks.simplematerialgenerators;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public final class SMGItems {

    private SMGItems() {}

    public static final ItemGroup SMG_ITEM_CATEGORY = new ItemGroup(
        new NamespacedKey(SimpleMaterialGenerators.getInstance(), "simple_material_generators"),
        namedItem(Material.SMOOTH_STONE, "&9Simple Material Generators")
    );

    public static final SlimefunItemStack SMG_GENERATOR_MULTIBLOCK = new SlimefunItemStack(
        "SMG_GENERATOR_MULTIBLOCK", Material.BEDROCK, "&9Generator Multiblock", "",
        "&dBuild any generator from this addon", "&dusing this multiblock pattern.",
        "&aGenerators output to an inventory", "&adirectly above the generator."
    );

    public static final SlimefunItemStack SMG_GENERATOR_COBBLESTONE = generator("SMG_GENERATOR_COBBLESTONE", Material.COBBLESTONE, "&7Cobblestone Generator", "cobblestone", 4);
    public static final SlimefunItemStack SMG_GENERATOR_STONE_BROKEN = broken("SMG_GENERATOR_STONE_BROKEN", Material.STONE, "&7Stone Generator");
    public static final SlimefunItemStack SMG_GENERATOR_STONE = generator("SMG_GENERATOR_STONE", Material.STONE, "&7Stone Generator", "stone", 8);
    public static final SlimefunItemStack SMG_GENERATOR_SMOOTH_STONE_BROKEN = broken("SMG_GENERATOR_SMOOTH_STONE_BROKEN", Material.SMOOTH_STONE, "&7Smooth Stone Generator");
    public static final SlimefunItemStack SMG_GENERATOR_SMOOTH_STONE = generator("SMG_GENERATOR_SMOOTH_STONE", Material.SMOOTH_STONE, "&7Smooth Stone Generator", "smooth-stone", 12);
    public static final SlimefunItemStack SMG_GENERATOR_GRAVEL_BROKEN = broken("SMG_GENERATOR_GRAVEL_BROKEN", Material.GRAVEL, "&7Gravel Generator");
    public static final SlimefunItemStack SMG_GENERATOR_GRAVEL = generator("SMG_GENERATOR_GRAVEL", Material.ANDESITE, "&7Gravel Generator", "gravel", 6);
    public static final SlimefunItemStack SMG_GENERATOR_SAND_BROKEN = broken("SMG_GENERATOR_SAND_BROKEN", Material.SAND, "&eSand Generator");
    public static final SlimefunItemStack SMG_GENERATOR_SAND = generator("SMG_GENERATOR_SAND", Material.SANDSTONE, "&eSand Generator", "sand", 8);
    public static final SlimefunItemStack SMG_GENERATOR_GLASS = generator("SMG_GENERATOR_GLASS", Material.GLASS, "&fGlass Generator", "glass", 12);
    public static final SlimefunItemStack SMG_GENERATOR_NETHERRACK = generator("SMG_GENERATOR_NETHERRACK", Material.NETHERRACK, "&cNetherrack Generator", "netherrack", 6);
    public static final SlimefunItemStack SMG_GENERATOR_SOUL_SAND = generator("SMG_GENERATOR_SOUL_SAND", Material.SOUL_SAND, "&8Soul Sand Generator", "soul-sand", 8);

    // IDs restored from the working Albion MODIFIED v0.3 build.
    public static final SlimefunItemStack SMG_GENERATOR_ANDESITE = generator("SMG_GENERATOR_ANDESITE", Material.ANDESITE, "&eAndesite Generator", "andesite", 60);
    public static final SlimefunItemStack SMG_GENERATOR_DIORITE = generator("SMG_GENERATOR_DIORITE", Material.DIORITE, "&eDiorite Generator", "diorite", 60);
    public static final SlimefunItemStack SMG_GENERATOR_GRANITE = generator("SMG_GENERATOR_GRANITE", Material.GRANITE, "&eGranite Generator", "granite", 60);
    public static final SlimefunItemStack SMG_GENERATOR_COAL = generator("SMG_GENERATOR_COAL", Material.OAK_LOG, "&eCoal Generator", "coal", 200);
    public static final SlimefunItemStack SMG_GENERATOR_IRON = generator("SMG_GENERATOR_IRON", Material.IRON_BLOCK, "&eIron Generator", "iron", 250);
    public static final SlimefunItemStack SMG_GENERATOR_GOLD = generator("SMG_GENERATOR_GOLD", Material.GOLD_BLOCK, "&eGold Generator", "gold", 300);
    public static final SlimefunItemStack SMG_GENERATOR_REDSTONE = generator("SMG_GENERATOR_REDSTONE", Material.REDSTONE_BLOCK, "&eRedstone Generator", "redstone", 250);
    public static final SlimefunItemStack SMG_GENERATOR_LAPIS = generator("SMG_GENERATOR_LAPIS", Material.LAPIS_BLOCK, "&eLapis Generator", "lapis", 250);
    public static final SlimefunItemStack SMG_GENERATOR_DIAMOND = generator("SMG_GENERATOR_DIAMOND", Material.DIAMOND_BLOCK, "&bDiamond Generator", "diamond", 900);
    public static final SlimefunItemStack SMG_GENERATOR_EMERALD = generator("SMG_GENERATOR_EMERALD", Material.EMERALD_BLOCK, "&aEmerald Generator", "emerald", 1200);
    public static final SlimefunItemStack SMG_GENERATOR_QUARTZ = generator("SMG_GENERATOR_QUARTZ", Material.QUARTZ_BLOCK, "&fQuartz Generator", "quartz", 300);
    public static final SlimefunItemStack SMG_GENERATOR_ENDSTONE = generator("SMG_GENERATOR_ENDSTONE", Material.END_STONE, "&eEnd Stone Generator", "endstone", 200);
    public static final SlimefunItemStack SMG_GENERATOR_OBSIDIAN = generator("SMG_GENERATOR_OBSIDIAN", Material.OBSIDIAN, "&5Obsidian Generator", "obsidian", 400);
    public static final SlimefunItemStack SMG_GENERATOR_CLAY = generator("SMG_GENERATOR_CLAY", Material.TERRACOTTA, "&6Clay Generator", "clay", 70);
    public static final SlimefunItemStack SMG_GENERATOR_BLAZE_ROD = generator("SMG_GENERATOR_BLAZE_ROD", Material.NETHER_BRICKS, "&6Blaze Rod Generator", "blaze-rod", 1000);
    public static final SlimefunItemStack SMG_GENERATOR_GHAST_TEAR = generator("SMG_GENERATOR_GHAST_TEAR", Material.WHITE_WOOL, "&fGhast Tear Generator", "ghast-tear", 1600);
    public static final SlimefunItemStack SMG_GENERATOR_ENDER_PEARL = generator("SMG_GENERATOR_ENDER_PEARL", Material.OBSIDIAN, "&5Ender Pearl Generator", "ender-pearl", 800);
    public static final SlimefunItemStack SMG_GENERATOR_BONE = generator("SMG_GENERATOR_BONE", Material.BONE_BLOCK, "&fBone Generator", "bone", 200);
    public static final SlimefunItemStack SMG_BROKEN_GENERATOR = broken("SMG_BROKEN_GENERATOR", Material.CRACKED_STONE_BRICKS, "&8Broken Generator");

    // New modern-Minecraft generators. These use new IDs and do not replace legacy content.
    public static final SlimefunItemStack SMG_GENERATOR_WOOD = generator("SMG_GENERATOR_WOOD", Material.OAK_LOG, "&6Wood Generator", "wood", 100);
    public static final SlimefunItemStack SMG_GENERATOR_COPPER = generator("SMG_GENERATOR_COPPER", Material.COPPER_BLOCK, "&6Copper Generator", "copper", 275);
    public static final SlimefunItemStack SMG_GENERATOR_TUFF = generator("SMG_GENERATOR_TUFF", Material.TUFF, "&7Tuff Generator", "tuff", 60);
    public static final SlimefunItemStack SMG_GENERATOR_CALCITE = generator("SMG_GENERATOR_CALCITE", Material.CALCITE, "&fCalcite Generator", "calcite", 90);
    public static final SlimefunItemStack SMG_GENERATOR_DRIPSTONE = generator("SMG_GENERATOR_DRIPSTONE", Material.DRIPSTONE_BLOCK, "&6Dripstone Generator", "dripstone", 100);
    public static final SlimefunItemStack SMG_GENERATOR_RESIN = generator("SMG_GENERATOR_RESIN", Material.RESIN_BLOCK, "&6Resin Generator", "resin", 200);
    public static final SlimefunItemStack SMG_GENERATOR_SULFUR = generator("SMG_GENERATOR_SULFUR", Material.SULFUR, "&eSulfur Generator", "sulfur", 180);
    public static final SlimefunItemStack SMG_GENERATOR_CINNABAR = generator("SMG_GENERATOR_CINNABAR", Material.CINNABAR, "&cCinnabar Generator", "cinnabar", 240);

    public static int configuredRate(String key, int defaultRate) {
        return Math.max(SimpleMaterialGenerators.getInstance().getConfig().getInt("generators." + key + ".rate", defaultRate), 2);
    }

    private static SlimefunItemStack generator(String id, Material material, String name, String key, int defaultRate) {
        int rate = configuredRate(key, defaultRate);
        return new SlimefunItemStack(
            id,
            material,
            name,
            "&6Rate: &e" + rate + " ticks",
            "&7Config: generators." + key,
            "",
            "&9&oSimpleMaterialGenerators"
        );
    }

    private static SlimefunItemStack broken(String id, Material material, String name) {
        return new SlimefunItemStack(id, material, name + " &8(Broken)", "&8Needs to be repaired", "", "&9&oSimpleMaterialGenerators");
    }

    private static ItemStack namedItem(Material material, String name) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
            item.setItemMeta(meta);
        }
        return item;
    }
}
