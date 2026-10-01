package me.waleks.simplematerialgenerators.items;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import me.waleks.simplematerialgenerators.SimpleMaterialGenerators;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;

/** Real generator tick/cleanup methods with a mocked plugin boundary and Bukkit test world. */
class MaterialGeneratorTest {
    private ServerMock server;
    private YamlConfiguration config;
    private MaterialGenerator generator;
    private Block block;

    @BeforeEach void setUp() {
        server = MockBukkit.mock();
        var plugin = mock(SimpleMaterialGenerators.class);
        config = new YamlConfiguration();
        when(plugin.getConfig()).thenReturn(config);
        var stack = mock(SlimefunItemStack.class);
        when(stack.getItemId()).thenReturn("SMG_GENERATOR_COBBLESTONE");
        generator = new MaterialGenerator(plugin, mock(ItemGroup.class), stack, mock(RecipeType.class), new ItemStack[9], 4, "cobblestone");
        generator.setItem(new ItemStack(Material.COBBLESTONE));
        block = server.addSimpleWorld("generator-world").getBlockAt(-17, 70, -17);
        block.setType(Material.STONE);
        block.getRelative(BlockFace.UP).setType(Material.CHEST);
        MaterialGenerator.clearAllProgress();
    }

    @AfterEach void tearDown() { MaterialGenerator.clearAllProgress(); MockBukkit.unmock(); }

    private Inventory target(Block source) {
        return ((InventoryHolder) source.getRelative(BlockFace.UP).getState()).getInventory();
    }
    private void tick(Block source, int count) { for (int i = 0; i < count; i++) generator.tick(source); }
    private int count(Inventory inventory) {
        int result = 0;
        for (ItemStack item : inventory.getStorageContents()) if (item != null && !item.getType().isAir()) result += item.getAmount();
        return result;
    }

    @Test void configuredCadenceAndOutputRemainExact() {
        tick(block, 3); assertEquals(0, count(target(block)));
        tick(block, 1); assertEquals(1, count(target(block)));
        tick(block, 36); assertEquals(10, count(target(block)));
        assertEquals("SMG_GENERATOR_COBBLESTONE", generator.getId());
    }

    @Test void oneThousandTicksDoNotChangeProductionRate() {
        tick(block, 1000); assertEquals(250, count(target(block)));
    }

    @Test void fullTargetRetainsReadyProgressUntilSpaceExists() {
        var inv = target(block);
        for (int i = 0; i < inv.getSize(); i++) inv.setItem(i, new ItemStack(Material.DIRT, 64));
        tick(block, 12); assertEquals(inv.getSize() * 64, count(inv));
        inv.setItem(0, null); tick(block, 1);
        assertEquals(Material.COBBLESTONE, inv.getItem(0).getType());
        assertEquals(1, inv.getItem(0).getAmount());
    }

    @Test void absentTargetClearsProgressWithoutCreatingItems() {
        tick(block, 3); block.getRelative(BlockFace.UP).setType(Material.AIR); tick(block, 1);
        block.getRelative(BlockFace.UP).setType(Material.CHEST); tick(block, 3);
        assertEquals(0, count(target(block))); tick(block, 1); assertEquals(1, count(target(block)));
    }

    @Test void disabledGeneratorClearsProgress() {
        tick(block, 3); config.set("generators.cobblestone.enabled", false); generator.refreshSettings(); tick(block, 1);
        config.set("generators.cobblestone.enabled", true); generator.refreshSettings(); tick(block, 3);
        assertEquals(0, count(target(block))); tick(block, 1); assertEquals(1, count(target(block)));
    }

    @Test void minimumRateRemainsTwoTicks() {
        config.set("generators.cobblestone.rate", -100); generator.refreshSettings();
        tick(block, 1); assertEquals(0, count(target(block)));
        tick(block, 1); assertEquals(1, count(target(block)));
    }

    @Test void outputTemplateIsDetachedAndMetadataSurvives() {
        var output = new ItemStack(Material.DIAMOND);
        var meta = output.getItemMeta();
        var key = NamespacedKey.fromString("old:opaque_count");
        meta.getPersistentDataContainer().set(key, PersistentDataType.LONG, 9007199254740993L);
        output.setItemMeta(meta); var expected = output.clone();
        generator.setItem(output); output.setAmount(7); tick(block, 4);
        assertEquals(expected, target(block).getItem(0));
    }

    @Test void blockCleanupDoesNotResetNeighbors() {
        Block other = block.getRelative(BlockFace.EAST); other.getRelative(BlockFace.UP).setType(Material.CHEST);
        tick(block, 3); tick(other, 3); MaterialGenerator.clearProgress(block);
        tick(block, 1); tick(other, 1);
        assertEquals(0, count(target(block))); assertEquals(1, count(target(other)));
    }

    @Test void chunkCleanupHandlesNegativeCoordinatesAndPreservesOtherChunks() {
        Block other = block.getWorld().getBlockAt(32, 70, 32); other.getRelative(BlockFace.UP).setType(Material.CHEST);
        tick(block, 3); tick(other, 3); MaterialGenerator.clearProgress(block.getChunk());
        tick(block, 1); tick(other, 1);
        assertEquals(0, count(target(block))); assertEquals(1, count(target(other)));
    }

    @Test void worldCleanupPreservesOtherWorldsAtSameCoordinates() {
        Block other = server.addSimpleWorld("other-world").getBlockAt(-17, 70, -17); other.getRelative(BlockFace.UP).setType(Material.CHEST);
        tick(block, 3); tick(other, 3); MaterialGenerator.clearProgress(block.getWorld());
        tick(block, 1); tick(other, 1);
        assertEquals(0, count(target(block))); assertEquals(1, count(target(other)));
    }

    @Test void globalCleanupResetsTransientProgress() {
        tick(block, 3); MaterialGenerator.clearAllProgress(); tick(block, 1);
        assertEquals(0, count(target(block)));
    }
}
