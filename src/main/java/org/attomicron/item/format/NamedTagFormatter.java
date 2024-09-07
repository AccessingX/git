package org.attomicron.item.format;

import de.tr7zw.nbtapi.iface.ReadWriteNBT;
import org.attomicron.item.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class NamedTagFormatter {

    private final String name;

    public NamedTagFormatter(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean canFormat(Player player) {
        return true;
    }

    public abstract void format(ItemStack itemStack, Item item, ReadWriteNBT nbt);

}
