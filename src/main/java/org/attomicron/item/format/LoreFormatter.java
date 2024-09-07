package org.attomicron.item.format;

import de.tr7zw.nbtapi.iface.ReadableNBT;
import org.attomicron.item.Item;
import org.attomicron.utilities.collection.TextColorizedList;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class LoreFormatter {

    private final String name;

    public LoreFormatter(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean canFormat(Player player) {
        return true;
    }

    public abstract void format(ItemStack itemStack, Item item, ReadableNBT nbt, TextColorizedList lore);

}
