package com.sirsquidly.firework_table.registry;

import com.sirsquidly.firework_table.FireworkTable;
import com.sirsquidly.firework_table.menu.FireworkTableMenu;
import dev.architectury.registry.menu.MenuRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;

public final class ModMenus {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(FireworkTable.MOD_ID, Registries.MENU);
    public static final RegistrySupplier<MenuType<FireworkTableMenu>> FIREWORK_TABLE = MENUS.register("firework_table",
            () -> MenuRegistry.ofExtended(FireworkTableMenu::new));
    private ModMenus() {}
}
