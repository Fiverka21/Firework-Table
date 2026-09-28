package com.sirsquidly.firework_table.neoforge;

import com.sirsquidly.firework_table.FireworkTable;
import com.sirsquidly.firework_table.client.FireworkTableScreen;
import com.sirsquidly.firework_table.registry.ModMenus;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@Mod(FireworkTable.MOD_ID)
public final class FireworkTableNeoForge {
    public FireworkTableNeoForge(IEventBus modEventBus) {
        FireworkTable.init();
        if (FMLEnvironment.dist == Dist.CLIENT) modEventBus.addListener(this::registerScreens);
    }

    private void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenus.FIREWORK_TABLE.get(), FireworkTableScreen::new);
    }
}
