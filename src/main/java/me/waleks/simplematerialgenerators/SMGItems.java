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
        "&aGenerators output only to a chest", "&adirectly above the generator."
    );

    public static final SlimefunItemStack SMG_GENERATOR_COBBLESTONE = generator(
        "SMG_GENERATOR_COBBLESTONE", Material.COBBLESTONE, "&7Cobblestone Generator", 4);
    public static final SlimefunItemStack SMG_GENERATOR_STONE_BROKEN = broken(
        "SMG_GENERATOR_STONE_BROKEN", Material.STONE, "&7Stone Generator");
    public static final SlimefunItemStack SMG_GENERATOR_STONE = generator(
        "SMG_GENERATOR_STONE", Material.STONE, "&7Stone Generator", 8);
    public static final SlimefunItemStack SMG_GENERATOR_SMOOTH_STONE_BROKEN = broken(
        "SMG_GENERATOR_SMOOTH_STONE_BROKEN", Material.SMOOTH_STONE, "&7Smooth Stone Generator");
    public static final SlimefunItemStack SMG_GENERATOR_SMOOTH_STONE = generator(
        "SMG_GENERATOR_SMOOTH_STONE", Material.SMOOTH_STONE, "&7Smooth Stone Generator", 12);
    public static final SlimefunItemStack SMG_GENERATOR_GRAVEL_BROKEN = broken(
        "SMG_GENERATOR_GRAVEL_BROKEN", Material.GRAVEL, "&7Gravel Generator");
    public static final SlimefunItemStack SMG_GENERATOR_GRAVEL = generator(
        "SMG_GENERATOR_GRAVEL", Material.ANDESITE, "&7Gravel Generator", 6);
    public static final SlimefunItemStack SMG_GENERATOR_SAND_BROKEN = broken(
        "SMG_GENERATOR_SAND_BROKEN", Material.SAND, "&eSand Generator");
    public static final SlimefunItemStack SMG_GENERATOR_SAND = generator(
        "SMG_GENERATOR_SAND", Material.SANDSTONE, "&eSand Generator", 8);
    public static final SlimefunItemStack SMG_GENERATOR_GLASS = generator(
        "SMG_GENERATOR_GLASS", Material.GLASS, "&fGlass Generator", 12);
    public static final SlimefunItemStack SMG_GENERATOR_NETHERRACK = generator(
        "SMG_GENERATOR_NETHERRACK", Material.NETHERRACK, "&cNetherrack Generator", 6);
    public static final SlimefunItemStack SMG_GENERATOR_SOUL_SAND = generator(
        "SMG_GENERATOR_SOUL_SAND", Material.SOUL_SAND, "&8Soul Sand Generator", 8);

    private static SlimefunItemStack generator(String id, Material material, String name, int ticks) {
        return new SlimefunItemStack(id, material, name, "&6Rate: &e" + ticks + " ticks", "", "&9&oSimpleMaterialGenerators");
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
