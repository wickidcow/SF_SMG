package me.waleks.simplematerialgenerators;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import me.waleks.simplematerialgenerators.commands.SMGReloadCommand;
import me.waleks.simplematerialgenerators.items.MaterialGenerator;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.event.world.WorldUnloadEvent;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SimpleMaterialGenerators extends JavaPlugin implements SlimefunAddon, Listener {

    private static SimpleMaterialGenerators instance;

    @Override
    public void onEnable() {
        setInstance(this);
        saveDefaultConfig();
        SMGItemSetup.setup(this);
        getServer().getPluginManager().registerEvents(this, this);

        PluginCommand command = getCommand("smg");
        if (command != null) {
            command.setExecutor(new SMGReloadCommand(this));
        } else {
            getLogger().warning("The /smg command is missing from plugin.yml.");
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        MaterialGenerator.clearProgress(event.getBlock());
    }

    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent event) {
        MaterialGenerator.clearProgress(event.getChunk());
    }

    @EventHandler
    public void onWorldUnload(WorldUnloadEvent event) {
        MaterialGenerator.clearProgress(event.getWorld());
    }

    public void safeReloadConfig() {
        reloadConfig();
        MaterialGenerator.refreshAll();
    }

    @Override
    public void onDisable() {
        MaterialGenerator.clearAllProgress();
        setInstance(null);
    }

    @Nonnull
    @Override
    public String getBugTrackerURL() {
        return "https://github.com/wickidcow/SF_SMG/issues";
    }

    @Nonnull
    @Override
    public JavaPlugin getJavaPlugin() {
        return this;
    }

    @Nonnull
    public static SimpleMaterialGenerators getInstance() {
        if (instance == null) {
            throw new IllegalStateException("SimpleMaterialGenerators has not finished enabling.");
        }
        return instance;
    }

    private static void setInstance(@Nullable SimpleMaterialGenerators instance) {
        SimpleMaterialGenerators.instance = instance;
    }
}
