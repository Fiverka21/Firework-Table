package com.sirsquidly.firework_table.fabric;

import com.sirsquidly.firework_table.FireworkTable;
import com.sirsquidly.firework_table.client.FireworkTableClient;
import net.fabricmc.api.ModInitializer;

public final class FireworkTableFabric implements ModInitializer {
    @Override public void onInitialize() { FireworkTable.init(); }

}
