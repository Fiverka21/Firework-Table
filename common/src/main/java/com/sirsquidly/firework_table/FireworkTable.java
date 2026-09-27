package com.sirsquidly.firework_table;

import com.sirsquidly.firework_table.menu.FireworkTableMenu;
import com.sirsquidly.firework_table.registry.ModBlocks;
import com.sirsquidly.firework_table.registry.ModItems;
import com.sirsquidly.firework_table.registry.ModMenus;

public final class FireworkTable {
    public static final String MOD_ID = "firework_table";
    public static final String NAME = "Firework Table";
    private static boolean initialized;

    private FireworkTable() {}

    public static void init() {
        if (initialized) return;
        initialized = true;
        ModBlocks.BLOCKS.register();
        ModItems.ITEMS.register();
        ModMenus.MENUS.register();
    }
}
