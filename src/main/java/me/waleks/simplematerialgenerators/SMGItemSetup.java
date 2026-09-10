package me.waleks.simplematerialgenerators;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.waleks.simplematerialgenerators.items.BrokenGenerator;
import me.waleks.simplematerialgenerators.items.GeneratorMultiblock;
import me.waleks.simplematerialgenerators.items.MaterialGenerator;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public final class SMGItemSetup {

    private SMGItemSetup() {}

    public static void setup(@Nonnull SimpleMaterialGenerators plugin) {
        plugin.getLogger().info("[SMG] Setting up legacy and modern material generators");
        new GeneratorMultiblock(SMGItems.SMG_ITEM_CATEGORY, SMGItems.SMG_GENERATOR_MULTIBLOCK).register(plugin);

        registerBaseGenerators(plugin);
        registerLegacyModifiedGenerators(plugin);
        registerModernGenerators(plugin);

        new BrokenGenerator(
            SMGItems.SMG_ITEM_CATEGORY,
            SMGItems.SMG_BROKEN_GENERATOR,
            RecipeType.NULL,
            new ItemStack[9]
        ).register(plugin);
    }

    private static void registerBaseGenerators(SimpleMaterialGenerators plugin) {
        register(plugin, SMGItems.SMG_GENERATOR_COBBLESTONE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
            new ItemStack(Material.SMOOTH_STONE), new ItemStack(Material.SMOOTH_STONE), new ItemStack(Material.SMOOTH_STONE),
            new ItemStack(Material.LAVA_BUCKET), new ItemStack(Material.IRON_PICKAXE), new ItemStack(Material.WATER_BUCKET),
            new ItemStack(Material.SMOOTH_STONE), new ItemStack(Material.SMOOTH_STONE), new ItemStack(Material.SMOOTH_STONE)
        }, 4, new ItemStack(Material.COBBLESTONE), "cobblestone");

        broken(plugin, SMGItems.SMG_GENERATOR_STONE_BROKEN, RecipeType.SMELTERY, SMGItems.SMG_GENERATOR_COBBLESTONE);
        register(plugin, SMGItems.SMG_GENERATOR_STONE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
            new ItemStack(Material.STONE), SlimefunItems.SOLDER_INGOT, new ItemStack(Material.STONE),
            SlimefunItems.SOLDER_INGOT, SMGItems.SMG_GENERATOR_STONE_BROKEN, SlimefunItems.SOLDER_INGOT,
            new ItemStack(Material.STONE), SlimefunItems.SOLDER_INGOT, new ItemStack(Material.STONE)
        }, 8, new ItemStack(Material.STONE), "stone");

        broken(plugin, SMGItems.SMG_GENERATOR_SMOOTH_STONE_BROKEN, RecipeType.SMELTERY, SMGItems.SMG_GENERATOR_STONE);
        register(plugin, SMGItems.SMG_GENERATOR_SMOOTH_STONE, RecipeType.SMELTERY, new ItemStack[] {
            new ItemStack(Material.SMOOTH_STONE), SlimefunItems.STEEL_INGOT, new ItemStack(Material.SMOOTH_STONE),
            new ItemStack(Material.IRON_BARS), SMGItems.SMG_GENERATOR_SMOOTH_STONE_BROKEN, new ItemStack(Material.IRON_BARS),
            new ItemStack(Material.SMOOTH_STONE), SlimefunItems.STEEL_INGOT, new ItemStack(Material.SMOOTH_STONE)
        }, 12, new ItemStack(Material.SMOOTH_STONE), "smooth-stone");

        broken(plugin, SMGItems.SMG_GENERATOR_GRAVEL_BROKEN, RecipeType.GRIND_STONE, SMGItems.SMG_GENERATOR_COBBLESTONE);
        register(plugin, SMGItems.SMG_GENERATOR_GRAVEL, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
            new ItemStack(Material.STONE_BRICKS), SlimefunItems.BILLON_INGOT, new ItemStack(Material.STONE_BRICKS),
            SlimefunItems.BILLON_INGOT, SMGItems.SMG_GENERATOR_GRAVEL_BROKEN, SlimefunItems.BILLON_INGOT,
            new ItemStack(Material.STONE_BRICKS), SlimefunItems.BILLON_INGOT, new ItemStack(Material.STONE_BRICKS)
        }, 6, new ItemStack(Material.GRAVEL), "gravel");

        broken(plugin, SMGItems.SMG_GENERATOR_SAND_BROKEN, RecipeType.ORE_CRUSHER, SMGItems.SMG_GENERATOR_GRAVEL);
        register(plugin, SMGItems.SMG_GENERATOR_SAND, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
            new ItemStack(Material.SANDSTONE), SlimefunItems.DURALUMIN_INGOT, new ItemStack(Material.SANDSTONE),
            SlimefunItems.DURALUMIN_INGOT, SMGItems.SMG_GENERATOR_SAND_BROKEN, SlimefunItems.DURALUMIN_INGOT,
            new ItemStack(Material.SANDSTONE), SlimefunItems.DURALUMIN_INGOT, new ItemStack(Material.SANDSTONE)
        }, 8, new ItemStack(Material.SAND), "sand");

        register(plugin, SMGItems.SMG_GENERATOR_GLASS, RecipeType.SMELTERY, new ItemStack[] {
            SlimefunItems.BRONZE_INGOT, SlimefunItems.GOLD_8K, SlimefunItems.BRONZE_INGOT,
            SlimefunItems.BRASS_INGOT, SMGItems.SMG_GENERATOR_SAND, SlimefunItems.BRASS_INGOT,
            null, null, null
        }, 12, new ItemStack(Material.GLASS), "glass");

        register(plugin, SMGItems.SMG_GENERATOR_NETHERRACK, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
            SlimefunItems.SOLDER_INGOT, SlimefunItems.SOLDER_INGOT, SlimefunItems.SOLDER_INGOT,
            SMGItems.SMG_GENERATOR_STONE, new ItemStack(Material.LAVA_BUCKET), SMGItems.SMG_GENERATOR_STONE,
            SMGItems.SMG_GENERATOR_STONE, SlimefunItems.COMPOSTER, SMGItems.SMG_GENERATOR_STONE
        }, 6, new ItemStack(Material.NETHERRACK), "netherrack");

        register(plugin, SMGItems.SMG_GENERATOR_SOUL_SAND, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
            SlimefunItems.STEEL_INGOT, new ItemStack(Material.SPIDER_EYE), SlimefunItems.STEEL_INGOT,
            new ItemStack(Material.ROTTEN_FLESH), new ItemStack(Material.WATER_BUCKET), new ItemStack(Material.BONE),
            SMGItems.SMG_GENERATOR_SAND, SlimefunItems.COMPOSTER, SMGItems.SMG_GENERATOR_SAND
        }, 8, new ItemStack(Material.SOUL_SAND), "soul-sand");
    }

    private static void registerLegacyModifiedGenerators(SimpleMaterialGenerators plugin) {
        SlimefunItemStack core = SMGItems.SMG_GENERATOR_COBBLESTONE;
        legacy(plugin, SMGItems.SMG_GENERATOR_ANDESITE, Material.ANDESITE, Material.ANDESITE, 60, "andesite", core);
        legacy(plugin, SMGItems.SMG_GENERATOR_DIORITE, Material.DIORITE, Material.DIORITE, 60, "diorite", core);
        legacy(plugin, SMGItems.SMG_GENERATOR_GRANITE, Material.GRANITE, Material.GRANITE, 60, "granite", core);
        legacy(plugin, SMGItems.SMG_GENERATOR_COAL, Material.COAL_BLOCK, Material.COAL, 200, "coal", core);
        legacy(plugin, SMGItems.SMG_GENERATOR_IRON, Material.IRON_BLOCK, Material.IRON_INGOT, 250, "iron", core);
        legacy(plugin, SMGItems.SMG_GENERATOR_GOLD, Material.GOLD_BLOCK, Material.GOLD_INGOT, 300, "gold", core);
        legacy(plugin, SMGItems.SMG_GENERATOR_REDSTONE, Material.REDSTONE_BLOCK, Material.REDSTONE, 250, "redstone", core);
        legacy(plugin, SMGItems.SMG_GENERATOR_LAPIS, Material.LAPIS_BLOCK, Material.LAPIS_LAZULI, 250, "lapis", core);
        legacy(plugin, SMGItems.SMG_GENERATOR_DIAMOND, Material.DIAMOND_BLOCK, Material.DIAMOND, 900, "diamond", core);
        legacy(plugin, SMGItems.SMG_GENERATOR_EMERALD, Material.EMERALD_BLOCK, Material.EMERALD, 1200, "emerald", core);
        legacy(plugin, SMGItems.SMG_GENERATOR_QUARTZ, Material.QUARTZ_BLOCK, Material.QUARTZ, 300, "quartz", core);
        legacy(plugin, SMGItems.SMG_GENERATOR_ENDSTONE, Material.END_STONE, Material.END_STONE, 200, "endstone", core);
        legacy(plugin, SMGItems.SMG_GENERATOR_OBSIDIAN, Material.OBSIDIAN, Material.OBSIDIAN, 400, "obsidian", core);
        legacy(plugin, SMGItems.SMG_GENERATOR_CLAY, Material.CLAY, Material.CLAY_BALL, 70, "clay", core);
        legacy(plugin, SMGItems.SMG_GENERATOR_BLAZE_ROD, Material.BLAZE_ROD, Material.BLAZE_ROD, 1000, "blaze-rod", core);
        legacy(plugin, SMGItems.SMG_GENERATOR_GHAST_TEAR, Material.GHAST_TEAR, Material.GHAST_TEAR, 1600, "ghast-tear", core);
        legacy(plugin, SMGItems.SMG_GENERATOR_ENDER_PEARL, Material.ENDER_PEARL, Material.ENDER_PEARL, 800, "ender-pearl", core);
        legacy(plugin, SMGItems.SMG_GENERATOR_BONE, Material.BONE, Material.BONE, 200, "bone", core);
    }

    private static void registerModernGenerators(SimpleMaterialGenerators plugin) {
        SlimefunItemStack core = SMGItems.SMG_GENERATOR_COBBLESTONE;
        legacy(plugin, SMGItems.SMG_GENERATOR_WOOD, Material.OAK_LOG, Material.OAK_LOG, 100, "wood", core);
        legacy(plugin, SMGItems.SMG_GENERATOR_COPPER, Material.COPPER_BLOCK, Material.COPPER_INGOT, 275, "copper", core);
        legacy(plugin, SMGItems.SMG_GENERATOR_TUFF, Material.TUFF, Material.TUFF, 60, "tuff", core);
        legacy(plugin, SMGItems.SMG_GENERATOR_CALCITE, Material.CALCITE, Material.CALCITE, 90, "calcite", core);
        legacy(plugin, SMGItems.SMG_GENERATOR_DRIPSTONE, Material.DRIPSTONE_BLOCK, Material.POINTED_DRIPSTONE, 100, "dripstone", core);
        legacy(plugin, SMGItems.SMG_GENERATOR_RESIN, Material.RESIN_BLOCK, Material.RESIN_CLUMP, 200, "resin", core);
        legacy(plugin, SMGItems.SMG_GENERATOR_SULFUR, Material.SULFUR, Material.SULFUR, 180, "sulfur", core);
        legacy(plugin, SMGItems.SMG_GENERATOR_CINNABAR, Material.CINNABAR, Material.CINNABAR, 240, "cinnabar", core);
    }

    private static void legacy(SimpleMaterialGenerators plugin, SlimefunItemStack item, Material ingredient, Material output, int rate, String key, SlimefunItemStack core) {
        register(plugin, item, RecipeType.ENHANCED_CRAFTING_TABLE, surround(new ItemStack(ingredient), core), rate, new ItemStack(output), key);
    }

    private static void register(SimpleMaterialGenerators plugin, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, int defaultRate, ItemStack output, String key) {
        new MaterialGenerator(plugin, SMGItems.SMG_ITEM_CATEGORY, item, recipeType, recipe, defaultRate, key)
            .setItem(output)
            .register(plugin);
    }

    private static void broken(SimpleMaterialGenerators plugin, SlimefunItemStack broken, RecipeType recipeType, ItemStack source) {
        new BrokenGenerator(SMGItems.SMG_ITEM_CATEGORY, broken, recipeType, new ItemStack[] { source, null, null, null, null, null, null, null, null }).register(plugin);
    }

    private static ItemStack[] surround(ItemStack ingredient, ItemStack center) {
        return new ItemStack[] {
            ingredient, ingredient, ingredient,
            ingredient, center, ingredient,
            ingredient, ingredient, ingredient
        };
    }
}
