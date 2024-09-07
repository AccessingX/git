package org.attomicron;

import org.bukkit.plugin.PluginBase;

public final class ItemLib {

    private static PluginBase plugin;

    public static void boot(PluginBase plugin) {
        if (ItemLib.plugin != null) {
            throw new IllegalStateException("ItemLib already booted!");
        }
        ItemLib.plugin = plugin;
    }

    public static PluginBase plugin() {
        return plugin;
    }

}
