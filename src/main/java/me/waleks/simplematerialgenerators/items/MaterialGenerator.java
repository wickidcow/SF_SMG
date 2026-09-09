package me.waleks.simplematerialgenerators.items;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class MaterialGenerator extends SlimefunItem {

    private static final ConcurrentMap<BlockKey, Integer> GENERATOR_PROGRESS = new ConcurrentHashMap<>();

    private int rate = 2;
    private ItemStack output;

    @ParametersAreNonnullByDefault
    public MaterialGenerator(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public void preRegister() {
        addItemHandler(new BlockTicker() {
            @Override
            @SuppressWarnings("deprecation")
            @ParametersAreNonnullByDefault
            public void tick(Block block, SlimefunItem item, Config data) {
                MaterialGenerator.this.tick(block);
            }

            @Override
            public boolean isSynchronized() {
                return true;
            }
        });
    }

    public void tick(@Nonnull Block block) {
        Block targetBlock = block.getRelative(BlockFace.UP);
        if (targetBlock.getType() != Material.CHEST) {
            GENERATOR_PROGRESS.remove(BlockKey.of(block));
            return;
        }

        BlockState state = targetBlock.getState();
        if (!(state instanceof InventoryHolder holder)) {
            return;
        }

        Inventory inventory = holder.getInventory();
        if (inventory.firstEmpty() == -1 || output == null) {
            return;
        }

        BlockKey key = BlockKey.of(block);
        int progress = GENERATOR_PROGRESS.merge(key, 1, Integer::sum);
        if (progress >= rate) {
            GENERATOR_PROGRESS.put(key, 0);
            inventory.addItem(output.clone());
        }
    }

    public final MaterialGenerator setItem(@Nonnull Material material) {
        this.output = new ItemStack(material);
        return this;
    }

    public final MaterialGenerator setRate(int rateTicks) {
        this.rate = Math.max(rateTicks, 2);
        return this;
    }

    private record BlockKey(UUID world, int x, int y, int z) {
        private static BlockKey of(Block block) {
            return new BlockKey(block.getWorld().getUID(), block.getX(), block.getY(), block.getZ());
        }
    }
}
