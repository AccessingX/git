package org.attomicron.item;

import de.tr7zw.nbtapi.NBT;
import de.tr7zw.nbtapi.iface.ReadWriteNBT;
import org.attomicron.item.format.LoreFormatter;
import org.attomicron.item.format.NameFormatter;
import org.attomicron.item.format.NamedTagFormatter;
import org.attomicron.utilities.collection.TextColorizedList;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class Item {

    private final int id;

    private final Material material;

    private final String displayName;

    protected final TextColorizedList description = new TextColorizedList();

    protected final List<NameFormatter> nameFormatters = new ArrayList<>();

    protected final List<LoreFormatter> loreFormatters = new ArrayList<>();

    protected final List<NamedTagFormatter> nbtFormatters = new ArrayList<>();

    public static final String ITEM_IDENTIFIER = "XITEMS";

    public Item(int id, String displayName, Material material) {
        this.id = id;
        this.displayName = displayName;
        this.material = material;
    }

    public int getId() {
        return id;
    }

    public String getRawName() {
        return displayName;
    }

    public Material getMaterial() {
        return material;
    }

    public TextColorizedList getDescription() {
        return description;
    }

    public ItemFlag[] getFlags() {
        return ItemFlag.values();
    }

    public ItemStack toItemStack() {
        return toItemStack(1, null);
    }

    public ItemStack toItemStack(Player player) {
        return toItemStack(1, player);
    }

    public ItemStack toItemStack(int count, Player player) {
        ItemStack itemStack = new ItemStack(material);
        itemStack.setAmount(Math.max(1, Math.min(itemStack.getMaxStackSize(), count)));

        NBT.modify(itemStack, nbt -> {
            ReadWriteNBT newNBT = nbt.getOrCreateCompound(ITEM_IDENTIFIER);
            newNBT.setInteger("id", this.id);

            for (NamedTagFormatter nbtFormatter : nbtFormatters) {
                if (player != null) {
                    if (nbtFormatter.canFormat(player)) {
                        nbtFormatter.format(itemStack, this, newNBT);
                    }
                } else {
                    nbtFormatter.format(itemStack, this, newNBT);
                }
            }

            ItemName name = new ItemName(displayName);
            for (NameFormatter nameFormatter : nameFormatters) {
                if (player != null) {
                    if (nameFormatter.canFormat(player)) {
                        nameFormatter.format(itemStack, this, newNBT, name);
                    }
                } else {
                    nameFormatter.format(itemStack, this, newNBT, name);
                }
            }

            TextColorizedList lore = new TextColorizedList();
            for (LoreFormatter loreFormatter : loreFormatters) {
                if (player != null) {
                    if (loreFormatter.canFormat(player)) {
                        loreFormatter.format(itemStack, this, newNBT, lore);
                    }
                } else {
                    loreFormatter.format(itemStack, this, newNBT, lore);
                }
            }

            nbt.modifyMeta((readableNBT, itemMeta) -> {
                itemMeta.addItemFlags(getFlags());
                itemMeta.spigot().setUnbreakable(true); //todo
                itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name.getValue()));

                if (!(lore.isEmpty())) {
                    itemMeta.setLore(lore);
                }
            });
        });
        return itemStack;
    }

}
