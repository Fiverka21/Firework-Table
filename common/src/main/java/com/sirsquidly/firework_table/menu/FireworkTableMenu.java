package com.sirsquidly.firework_table.menu;

import com.sirsquidly.firework_table.config.Config;
import com.sirsquidly.firework_table.registry.ModMenus;
import net.minecraft.core.BlockPos;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.FireworkExplosion;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.level.Level;

public final class FireworkTableMenu extends AbstractContainerMenu {
    public enum FireworkTab { DYES, EXPLOSION }
    public enum FireworkShape { SMALL, LARGE, STAR, CREEPER, BURST }

    public final SimpleContainer input = new SimpleContainer(18) { @Override public void setChanged() { super.setChanged(); FireworkTableMenu.this.slotsChanged(this); } };
    public final SimpleContainer result = new SimpleContainer(1);
    private final ContainerLevelAccess access;
    private final ContainerData data = new ContainerData() {
        private final int[] values = new int[4];
        @Override public int get(int i) { return values[i]; }
        @Override public void set(int i, int value) { values[i] = value; }
        @Override public int getCount() { return values.length; }
    };

    public FireworkTableMenu(int id, Inventory playerInventory, FriendlyByteBuf buf) {
        this(id, playerInventory, buf.readBlockPos());
    }

    public FireworkTableMenu(int id, Inventory playerInventory, BlockPos pos) {
        super(ModMenus.FIREWORK_TABLE.get(), id);
        this.access = ContainerLevelAccess.create(playerInventory.player.level(), pos);
        addDataSlots(data);

        addSlot(new Slot(input, 0, 13, 15) { @Override public boolean mayPlace(ItemStack stack) { return stack.is(Items.GUNPOWDER); } });
        for (int i = 0; i < 8; i++) addDyeSlot(i, 53 + (i % 4) * 18, 15 + (i / 4) * 18);
        for (int i = 0; i < 8; i++) addDyeSlot(8 + i, 53 + (i % 4) * 18, 63 + (i / 4) * 18);
        addSlot(new Slot(result, 0, 142, 67) {
            @Override public boolean mayPlace(ItemStack stack) { return false; }
            @Override public ItemStack remove(int amount) { return super.remove(amount); }
            @Override public void onTake(Player player, ItemStack stack) { input.removeItem(0, 1); for (int i = 1; i < 18; i++) input.removeItem(i, 1); super.onTake(player, stack); }
        });
        addPlayerInventory(playerInventory);
        updateOutput();
    }

    private void addDyeSlot(int index, int x, int y) {
        addSlot(new Slot(input, index + 1, x, y) {
            @Override public boolean mayPlace(ItemStack stack) {
                return currentTab() == FireworkTab.DYES
                        && stack.getItem() instanceof DyeItem
                        && !isDyeSlotDisabled(index);
            }
            @Override public boolean isActive() { return currentTab() == FireworkTab.DYES; }
        });
    }

    private void addPlayerInventory(Inventory inventory) {
        for (int row = 0; row < 3; row++) for (int column = 0; column < 9; column++) addSlot(new Slot(inventory, column + row * 9 + 9, 8 + column * 18, 105 + row * 18));
        for (int column = 0; column < 9; column++) addSlot(new Slot(inventory, column, 8 + column * 18, 163));
    }

    public FireworkTab currentTab() { return FireworkTab.values()[data.get(0)]; }
    public FireworkShape currentShape() { return FireworkShape.values()[data.get(1)]; }
    public boolean flickEnabled() { return data.get(2) == 1; }
    public boolean trailEnabled() { return data.get(3) == 1; }

    @Override public boolean clickMenuButton(Player player, int id) {
        if (id > 0 && id <= 5) data.set(1, id - 1);
        else if (id == 101) data.set(2, data.get(2) == 0 ? 1 : 0);
        else if (id == 102) data.set(3, data.get(3) == 0 ? 1 : 0);
        else if (id == -1) data.set(0, 0);
        else if (id == -2) data.set(0, 1);
        else return false;
        updateOutput();
        return true;
    }

    @Override public void slotsChanged(Container container) { super.slotsChanged(container); updateOutput(); }
    private void updateOutput() {
        if (!input.getItem(0).is(Items.GUNPOWDER)) { result.setItem(0, ItemStack.EMPTY); return; }
        int[] colors = collectColors(1, 8), fades = collectColors(9, 16);
        if (colors.length == 0 || hasLockedItems()) { result.setItem(0, ItemStack.EMPTY); return; }
        ItemStack output = new ItemStack(Items.FIREWORK_STAR);
        FireworkExplosion explosion = new FireworkExplosion(
                FireworkExplosion.Shape.byId(data.get(1)),
                new IntArrayList(colors),
                new IntArrayList(fades),
                trailEnabled(),
                flickEnabled());
        output.set(DataComponents.FIREWORK_EXPLOSION, explosion);
        result.setItem(0, output);
    }
    private boolean hasLockedItems() { for (int i = 1; i <= 8; i++) if (isDyeSlotDisabled(i - 1) && !input.getItem(i).isEmpty()) return true; return false; }
    private int[] collectColors(int first, int last) { int[] colors = new int[last - first + 1]; int count = 0; for (int i = first; i <= last; i++) { ItemStack stack = input.getItem(i); if (stack.getItem() instanceof DyeItem dye) colors[count++] = dye.getDyeColor().getFireworkColor(); } return java.util.Arrays.copyOf(colors, count); }
    public int complexity() { return Config.enableComplexityLimits ? (data.get(1) != 0 ? 1 : 0) + data.get(2) + data.get(3) : 0; }
    public boolean isDyeSlotDisabled(int index) { return index >= 8 - complexity() && index < 8; }

    @Override public ItemStack quickMoveStack(Player player, int index) {
        Slot slot = slots.get(index); if (!slot.hasItem()) return ItemStack.EMPTY;
        ItemStack copy = slot.getItem().copy();
        if (index == 17) {
            if (!moveItemStackTo(slot.getItem(), 18, slots.size(), true)) return ItemStack.EMPTY;
            slot.onTake(player, copy);
        } else if (index < 17) {
            if (!moveItemStackTo(slot.getItem(), 18, slots.size(), true)) return ItemStack.EMPTY;
        } else if (!moveItemStackTo(slot.getItem(), 0, 17, false)) return ItemStack.EMPTY;
        if (slot.getItem().isEmpty()) slot.set(ItemStack.EMPTY); else slot.setChanged();
        return copy;
    }
    @Override public boolean stillValid(Player player) { return stillValid(access, player, com.sirsquidly.firework_table.registry.ModBlocks.FIREWORK_TABLE.get()); }
    @Override public void removed(Player player) { super.removed(player); if (!player.level().isClientSide) clearContainer(player, input); }
}
