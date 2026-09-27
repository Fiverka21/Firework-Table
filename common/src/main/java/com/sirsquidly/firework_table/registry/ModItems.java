package com.sirsquidly.firework_table.registry;

import com.sirsquidly.firework_table.FireworkTable;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

public final class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(FireworkTable.MOD_ID, Registries.ITEM);
    public static final RegistrySupplier<Item> FIREWORK_TABLE = ITEMS.register("firework_table", () ->
            new BlockItem(ModBlocks.FIREWORK_TABLE.get(), new Item.Properties()));
    private ModItems() {}
}
