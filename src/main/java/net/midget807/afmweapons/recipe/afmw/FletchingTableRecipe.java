package net.midget807.afmweapons.recipe.afmw;

import net.minecraft.block.Blocks;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;

public interface FletchingTableRecipe extends Recipe<Inventory> {
    @Override
    default public ItemStack createIcon() {
        return new ItemStack(Blocks.FLETCHING_TABLE);
    }

    public boolean testArrow(ItemStack var1);

    public boolean testAddition1(ItemStack var1);

    public boolean testAddition2(ItemStack var1);
}
