package net.midget807.afmweapons.item.afmw.client;

import net.minecraft.client.item.TooltipData;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

public class TripleShotTooltipData implements TooltipData {
    private final DefaultedList<ItemStack> inventory;
    private final int tripleShotOccupancy;
    public TripleShotTooltipData(DefaultedList<ItemStack> inventory, int tripleShotOccupancy) {
        this.inventory = inventory;
        this.tripleShotOccupancy = tripleShotOccupancy;
    }
    public DefaultedList<ItemStack> getInventory() {
        return this.inventory;
    }
    public int getTripleShotOccupancy() {
        return this.tripleShotOccupancy;
    }
}
