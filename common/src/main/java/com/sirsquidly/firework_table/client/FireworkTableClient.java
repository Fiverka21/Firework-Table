package com.sirsquidly.firework_table.client;

import com.sirsquidly.firework_table.registry.ModMenus;
import dev.architectury.registry.menu.MenuRegistry;

public final class FireworkTableClient {
    private FireworkTableClient() {}
    public static void init() { MenuRegistry.registerScreenFactory(ModMenus.FIREWORK_TABLE.get(), FireworkTableScreen::new); }
}
