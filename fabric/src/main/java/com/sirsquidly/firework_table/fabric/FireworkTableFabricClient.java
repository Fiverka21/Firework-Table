package com.sirsquidly.firework_table.fabric;

import com.sirsquidly.firework_table.client.FireworkTableClient;
import net.fabricmc.api.ClientModInitializer;

public final class FireworkTableFabricClient implements ClientModInitializer {
    @Override public void onInitializeClient() { FireworkTableClient.init(); }
}
