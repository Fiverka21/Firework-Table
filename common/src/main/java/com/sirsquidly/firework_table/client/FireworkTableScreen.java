package com.sirsquidly.firework_table.client;

import com.sirsquidly.firework_table.FireworkTable;
import com.sirsquidly.firework_table.menu.FireworkTableMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public final class FireworkTableScreen extends AbstractContainerScreen<FireworkTableMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(FireworkTable.MOD_ID, "textures/gui/firework_table.png");

    public FireworkTableScreen(FireworkTableMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        imageWidth = 176;
        imageHeight = 200;
    }

    @Override protected void init() {
        super.init();
        addRenderableWidget(Button.builder(Component.literal("Dyes"), b -> press(-1)).bounds(leftPos + 32, topPos + 67, 40, 18).build());
        addRenderableWidget(Button.builder(Component.literal("Effects"), b -> press(-2)).bounds(leftPos + 32, topPos + 87, 50, 18).build());
        int[] ids = {1, 2, 3, 4, 5};
        String[] labels = {"S", "L", "★", "C", "B"};
        for (int i = 0; i < ids.length; i++) {
            int id = ids[i];
            addRenderableWidget(Button.builder(Component.literal(labels[i]), b -> press(id)).bounds(leftPos + 51 + (i % 4) * 16, topPos + 28 + (i / 4) * 16, 15, 15).build());
        }
        addRenderableWidget(Button.builder(Component.literal("F"), b -> press(101)).bounds(leftPos + 51, topPos + 73, 15, 15).build());
        addRenderableWidget(Button.builder(Component.literal("T"), b -> press(102)).bounds(leftPos + 67, topPos + 73, 15, 15).build());
    }

    private void press(int id) { if (minecraft.gameMode != null) minecraft.gameMode.handleInventoryButtonClick(menu.containerId, id); }

    @Override protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        graphics.blit(TEXTURE, leftPos, topPos, 0, 0, imageWidth, imageHeight);
    }

    @Override protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
        graphics.drawString(font, title, (imageWidth - font.width(title)) / 2, 4, 0x404040, false);
        graphics.drawString(font, playerInventoryTitle, 8, imageHeight - 94, 0x404040, false);
    }
}
