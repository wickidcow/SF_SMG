package me.waleks.simplematerialgenerators.commands;

import javax.annotation.Nonnull;
import me.waleks.simplematerialgenerators.SimpleMaterialGenerators;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

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
                sender.sendMessage(Component.text("You do not have permission to reload SMG.", NamedTextColor.RED));
                return true;
            }

            plugin.safeReloadConfig();
            sender.sendMessage(Component.text(
                "SMG configuration reloaded. Generator rate/enabled changes are active now; guide lore refreshes after a restart.",
                NamedTextColor.GREEN
            ));
            return true;
        }

        sender.sendMessage(Component.text("Usage: /" + label + " reload", NamedTextColor.YELLOW));
        return true;
    }
}
