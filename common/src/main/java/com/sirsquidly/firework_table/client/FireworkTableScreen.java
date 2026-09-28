package com.sirsquidly.firework_table.client;

import com.sirsquidly.firework_table.FireworkTable;
import com.sirsquidly.firework_table.menu.FireworkTableMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.FireworkExplosion;
import net.minecraft.world.entity.player.Inventory;

public final class FireworkTableScreen extends AbstractContainerScreen<FireworkTableMenu> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(FireworkTable.MOD_ID, "textures/gui/firework_table.png");
    private static final ResourceLocation ICONS = ResourceLocation.fromNamespaceAndPath(FireworkTable.MOD_ID, "textures/gui/firework_table_icons.png");
    private static final ResourceLocation PREVIEW = ResourceLocation.fromNamespaceAndPath(FireworkTable.MOD_ID, "textures/gui/firework_table_preview.png");

    private final IconButton[] explosionButtons = new IconButton[7];

    public FireworkTableScreen(FireworkTableMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        imageWidth = 176;
        imageHeight = 200;
    }

    @Override
    protected void init() {
        super.init();
        addRenderableWidget(new IconButton(leftPos + 32, topPos + 67, 18, 16, 224, 32, -1, false));
        addRenderableWidget(new IconButton(leftPos + 32, topPos + 84, 18, 16, 240, 32, -2, false));

        int[] ids = {1, 2, 3, 4, 5};
        for (int i = 0; i < ids.length; i++) {
            explosionButtons[i] = new IconButton(leftPos + 51 + i % 4 * 15, topPos + 28 + i / 4 * 15,
                    15, 15, 176 + i * 16, 17, ids[i], true);
            addRenderableWidget(explosionButtons[i]);
        }
        explosionButtons[5] = new IconButton(leftPos + 51, topPos + 73, 15, 15, 176, 33, 101, true);
        explosionButtons[6] = new IconButton(leftPos + 66, topPos + 73, 15, 15, 192, 33, 102, true);
        addRenderableWidget(explosionButtons[5]);
        addRenderableWidget(explosionButtons[6]);
        updateExplosionButtons();
    }

    @Override
    protected void containerTick() {
        super.containerTick();
        updateExplosionButtons();
    }

    private void updateExplosionButtons() {
        boolean visible = menu.currentTab() == FireworkTableMenu.FireworkTab.EXPLOSION;
        for (IconButton button : explosionButtons) button.visible = visible;
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        graphics.blit(TEXTURE, leftPos, topPos, 0, 0, imageWidth, imageHeight, 256, 256);
        if (menu.currentTab() == FireworkTableMenu.FireworkTab.DYES) {
            graphics.blit(ICONS, leftPos + 50, topPos + 12, 0, 0, 76, 88, 256, 256);
        } else {
            graphics.blit(ICONS, leftPos + 50, topPos + 12, 80, 96, 76, 88, 256, 256);
            graphics.blit(ICONS, leftPos + 50, topPos + 12, 176, 48, 64, 16, 256, 256);
            graphics.blit(ICONS, leftPos + 50, topPos + 57, 176, 48, 64, 16, 256, 256);
        }
        graphics.blit(ICONS, leftPos + 13, topPos + 15, 176, 80, 16, 16, 256, 256);
        renderPreview(graphics);
    }

    private void renderPreview(GuiGraphics graphics) {
        ItemStack stack = menu.result.getItem(0);
        FireworkExplosion explosion = stack.get(DataComponents.FIREWORK_EXPLOSION);
        if (explosion == null || explosion.colors().isEmpty()) return;
        int shape = Math.max(0, Math.min(4, explosion.shape().getId()));
        for (int i = 0; i < 8; i++) {
            int color = explosion.colors().getInt(i % explosion.colors().size());
            graphics.setColor(((color >> 16) & 255) / 255f, ((color >> 8) & 255) / 255f, (color & 255) / 255f, 1.0f);
            graphics.blit(PREVIEW, leftPos + 130, topPos + 18, i * 40, shape * 40, 40, 40, 512, 512);
        }
        graphics.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
        graphics.drawString(font, title, (imageWidth - font.width(title)) / 2, 4, 0x404040, false);
        graphics.drawString(font, playerInventoryTitle, 8, imageHeight - 94, 0x404040, false);
    }

    private final class IconButton extends Button {
        private final int iconU;
        private final int iconV;
        private final int id;
        private final boolean shapeOrEffect;

        private IconButton(int x, int y, int width, int height, int iconU, int iconV, int id, boolean shapeOrEffect) {
            super(x, y, width, height, Component.empty(), button -> press(id), Button.DEFAULT_NARRATION);
            this.iconU = iconU;
            this.iconV = iconV;
            this.id = id;
            this.shapeOrEffect = shapeOrEffect;
        }

        @Override
        protected void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
            boolean selected = id == -1
                    ? menu.currentTab() == FireworkTableMenu.FireworkTab.DYES
                    : id == -2
                    ? menu.currentTab() == FireworkTableMenu.FireworkTab.EXPLOSION
                    : id >= 1 && id <= 5
                    && menu.currentShape() == FireworkTableMenu.FireworkShape.values()[id - 1]
                    || id == 101 && menu.flickEnabled()
                    || id == 102 && menu.trailEnabled();
            int stateOffset = selected ? 32 : isHovered ? 16 : 0;
            int backgroundU = shapeOrEffect ? 176 : 238;
            int backgroundV = shapeOrEffect ? 1 : 64;
            graphics.blit(ICONS, getX(), getY(), backgroundU, backgroundV + stateOffset, width, height, 256, 256);
            int offset = selected ? -2 : 0;
            graphics.blit(ICONS, getX() + offset, getY() + (shapeOrEffect ? 0 : -1), iconU, iconV, 16, 16, 256, 256);
        }
    }

    private void press(int id) {
        if (minecraft != null && minecraft.gameMode != null) minecraft.gameMode.handleInventoryButtonClick(menu.containerId, id);
    }
}
