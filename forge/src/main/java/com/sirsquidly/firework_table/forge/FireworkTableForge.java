package com.sirsquidly.firework_table.forge;

import com.sirsquidly.firework_table.FireworkTable;
import com.sirsquidly.firework_table.client.FireworkTableClient;
import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.eventbus.api.IEventBus;

@Mod(FireworkTable.MOD_ID)
public final class FireworkTableForge {
    public FireworkTableForge() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        EventBuses.registerModEventBus(
                FireworkTable.MOD_ID,
                modEventBus
        );
        FireworkTable.init();
        if (FMLEnvironment.dist == Dist.CLIENT) {
            modEventBus.addListener(this::clientSetup);
        }
    }

    private void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(FireworkTableClient::init);
    }
}
