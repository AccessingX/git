package org.attomicron.utilities.collection;

import org.bukkit.ChatColor;

import java.util.ArrayList;

public final class TextColorizedList extends ArrayList<String> {

    @Override
    public boolean add(String string) {
        return super.add(ChatColor.translateAlternateColorCodes('&', string));
    }
}
