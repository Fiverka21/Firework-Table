package com.sirsquidly.firework_table.neoforge;

import com.sirsquidly.firework_table.FireworkTable;
import com.sirsquidly.firework_table.client.FireworkTableClient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.bus.api.IEventBus;

@Mod(FireworkTable.MOD_ID)
public final class FireworkTableNeoForge {
    public FireworkTableNeoForge(IEventBus modEventBus) {
        FireworkTable.init();
        if (FMLEnvironment.dist == Dist.CLIENT) modEventBus.addListener(this::clientSetup);
    }

    private void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(FireworkTableClient::init);
    }
}
