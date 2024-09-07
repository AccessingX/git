package org.attomicron.item;

import de.tr7zw.nbtapi.NBT;
import de.tr7zw.nbtapi.iface.ReadWriteNBT;
import org.attomicron.item.format.LoreFormatter;
import org.attomicron.item.format.NameFormatter;
import org.attomicron.utilities.collection.TextColorizedList;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public final class ItemRefresher {

    private ItemRefresher(){}

    public static ItemStack refresh(ItemStack itemStack) {
        return refresh(itemStack, null);
    }

    public static ItemStack refresh(ItemStack itemStack, Player player) {
        Item item = ItemRegistrant.from(itemStack);
        if (item == null) {
            return itemStack;
        }

        NBT.modify(itemStack, readWriteItemNBT -> {
            ReadWriteNBT itemNBT = readWriteItemNBT
                    .getCompound(Item.ITEM_IDENTIFIER);

            ItemName name = new ItemName(item.getRawName());
            for (NameFormatter nameFormatter : item.nameFormatters) {
                if (player != null) {
                    if (nameFormatter.canFormat(player)) {
                        nameFormatter
                                .format(itemStack, item, itemNBT, name);
                    }
                } else {
                    nameFormatter
                            .format(itemStack, item, itemNBT, name);
                }
            }

            TextColorizedList lore = new TextColorizedList();
            for (LoreFormatter loreFormatter : item.loreFormatters) {
                if (player != null) {
                    if (loreFormatter.canFormat(player)) {
                        loreFormatter
                                .format(itemStack, item, itemNBT, lore);
                    }
                } else {
                    loreFormatter
                            .format(itemStack, item, itemNBT, lore);
                }
            }

            readWriteItemNBT.modifyMeta((nbt, itemMeta) -> {
                itemMeta.setLore(lore);
                itemMeta.setDisplayName(name.getValue());
            });
        });

        return itemStack.clone();
    }

}
