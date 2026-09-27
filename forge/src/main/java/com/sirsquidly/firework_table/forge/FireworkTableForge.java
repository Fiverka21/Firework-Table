package com.sirsquidly.firework_table.forge;

import com.sirsquidly.firework_table.FireworkTable;
import com.sirsquidly.firework_table.client.FireworkTableClient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(FireworkTable.MOD_ID)
public final class FireworkTableForge {
    public FireworkTableForge() {
        FireworkTable.init();
        if (FMLEnvironment.dist == Dist.CLIENT) FireworkTableClient.init();
    }
}
