package com.sirsquidly.firework_table.registry;

import com.sirsquidly.firework_table.FireworkTable;
import com.sirsquidly.firework_table.block.FireworkTableBlock;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

public final class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(FireworkTable.MOD_ID, Registries.BLOCK);
    public static final RegistrySupplier<Block> FIREWORK_TABLE = BLOCKS.register("firework_table", () ->
            new FireworkTableBlock(BlockBehaviour.Properties.of().strength(2.5F).requiresCorrectToolForDrops()));
    private ModBlocks() {}
}
