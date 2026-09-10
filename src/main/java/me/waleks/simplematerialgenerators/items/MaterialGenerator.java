package me.waleks.simplematerialgenerators.items;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.waleks.simplematerialgenerators.SimpleMaterialGenerators;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class MaterialGenerator extends SlimefunItem {

    private static final ConcurrentMap<BlockKey, Integer> GENERATOR_PROGRESS = new ConcurrentHashMap<>();
    private static final List<MaterialGenerator> GENERATORS = new CopyOnWriteArrayList<>();

    private final SimpleMaterialGenerators plugin;
    private final String configKey;
    private final int defaultRate;
    private volatile int rate;
    private volatile boolean enabled;
    private ItemStack output;

    @ParametersAreNonnullByDefault
    public MaterialGenerator(SimpleMaterialGenerators plugin, ItemGroup itemGroup, SlimefunItemStack item,
                             RecipeType recipeType, ItemStack[] recipe, int defaultRate, String configKey) {
        super(itemGroup, item, recipeType, recipe);
        this.plugin = plugin;
        this.configKey = configKey;
        this.defaultRate = Math.max(defaultRate, 2);
        refreshSettings();
        GENERATORS.add(this);
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
        BlockKey key = BlockKey.of(block);
        if (!enabled || output == null) {
            GENERATOR_PROGRESS.remove(key);
            return;
        }

        BlockState state = block.getRelative(BlockFace.UP).getState();
        if (!(state instanceof InventoryHolder holder)) {
            GENERATOR_PROGRESS.remove(key);
            return;
        }

        Inventory inventory = holder.getInventory();
        int progress = GENERATOR_PROGRESS.merge(key, 1, Integer::sum);
        if (progress < rate) {
            return;
        }

        if (inventory.addItem(output.clone()).isEmpty()) {
            GENERATOR_PROGRESS.put(key, 0);
        } else {
            GENERATOR_PROGRESS.put(key, rate);
        }
    }

    public final MaterialGenerator setItem(@Nonnull ItemStack item) {
        this.output = item.clone();
        return this;
    }

    public final void refreshSettings() {
        String path = "generators." + configKey;
        enabled = plugin.getConfig().getBoolean(path + ".enabled", true);
        rate = Math.max(plugin.getConfig().getInt(path + ".rate", defaultRate), 2);
    }

    public static void refreshAll() {
        for (MaterialGenerator generator : GENERATORS) {
            generator.refreshSettings();
        }
    }

    public static void clearProgress(Block block) {
        GENERATOR_PROGRESS.remove(BlockKey.of(block));
    }

    public static void clearProgress(Chunk chunk) {
        UUID world = chunk.getWorld().getUID();
        int chunkX = chunk.getX();
        int chunkZ = chunk.getZ();
        GENERATOR_PROGRESS.keySet().removeIf(key -> key.world.equals(world)
            && (key.x >> 4) == chunkX && (key.z >> 4) == chunkZ);
    }

    public static void clearProgress(World world) {
        UUID worldId = world.getUID();
        GENERATOR_PROGRESS.keySet().removeIf(key -> key.world.equals(worldId));
    }

    public static void clearAllProgress() {
        GENERATOR_PROGRESS.clear();
    }

    private record BlockKey(UUID world, int x, int y, int z) {
        private static BlockKey of(Block block) {
            return new BlockKey(block.getWorld().getUID(), block.getX(), block.getY(), block.getZ());
        }
    }
}
