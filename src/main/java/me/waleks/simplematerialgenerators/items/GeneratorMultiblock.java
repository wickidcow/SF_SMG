package me.waleks.simplematerialgenerators.items;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.NotPlaceable;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import javax.annotation.Nonnull;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GeneratorMultiblock extends SimpleSlimefunItem<ItemUseHandler> implements NotPlaceable {

    public GeneratorMultiblock(ItemGroup itemGroup, SlimefunItemStack item) {
        super(itemGroup, item, RecipeType.MULTIBLOCK, new ItemStack[] {
            null, null, null,
            null, new ItemStack(Material.CHEST), null,
            null, namedItem(Material.BEDROCK, "Any SMG generator"), null
        });
    }

    @Nonnull
    @Override
    public ItemUseHandler getItemHandler() {
        return event -> {
            event.cancel();
            event.getPlayer().sendMessage(Component.text(
                "This is a guide item. Place an actual SMG generator beneath a chest."
            ));
        };
    }

    private static ItemStack namedItem(Material material, String name) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.displayName(Component.text(name));
            item.setItemMeta(meta);
        }
        return item;
    }
}
