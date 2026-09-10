package me.waleks.simplematerialgenerators.commands;

import me.waleks.simplematerialgenerators.SimpleMaterialGenerators;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import javax.annotation.Nonnull;

public final class SMGReloadCommand implements CommandExecutor {

    private final SimpleMaterialGenerators plugin;

    public SMGReloadCommand(SimpleMaterialGenerators plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(
        @Nonnull CommandSender sender,
        @Nonnull Command command,
        @Nonnull String label,
        @Nonnull String[] args
    ) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("smg.reload")) {
                sender.sendMessage(ChatColor.RED + "You do not have permission to reload SMG.");
                return true;
            }

            plugin.safeReloadConfig();
            sender.sendMessage(ChatColor.GREEN + "SMG configuration reloaded. Generator rate/enabled changes are active now; guide lore refreshes after a restart.");
            return true;
        }

        sender.sendMessage(ChatColor.YELLOW + "Usage: /" + label + " reload");
        return true;
    }
}
