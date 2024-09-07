package org.attomicron.item;

import de.tr7zw.nbtapi.NBT;
import de.tr7zw.nbtapi.iface.ReadableNBT;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public final class ItemRegistrant {

    private final static Map<Integer, Item> items = new HashMap<>();

    public static Item register(Item item) {
        if (items.containsKey(item.getId())) {
            throw new RuntimeException("Item already registered");
        }
        items.put(item.getId(), item);
        return item;
    }

    public static Item get(int id) {
        return items.get(id);
    }

    public static boolean isRegistered(int id) {
        return items.containsKey(id);
    }

    public static boolean isRegistered(Item item) {
        return isRegistered(item.getId());
    }

    public static Item from(ItemStack item) {
        AtomicInteger id = new AtomicInteger(-1);
        NBT.get(item, readableItemNBT -> {
            ReadableNBT nbt = readableItemNBT.getCompound(Item.ITEM_IDENTIFIER);
            if (nbt == null) return;
            id.set(nbt.getInteger("id"));
        });

        if (id.get() == -1) {
            return null;
        }

        return get(id.get());
    }

    public static Map<Integer, Item> getItems() {
        return items;
    }

}
