package net.midget807.afmweapons.recipe.afmw;

import net.midget807.afmweapons.recipe.ModRecipes;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;

public interface FletchingTableRecipe extends Recipe<Inventory> {
    default RecipeType<?> getType() {
        return ModRecipes.FLETCHING_RECIPE_TYPE;
    }
    @Override
    default public ItemStack createIcon() {
        return new ItemStack(Blocks.FLETCHING_TABLE);
    }

    public boolean testArrow(ItemStack var1);

    public boolean testAddition1(ItemStack var1);

    public boolean testAddition2(ItemStack var1);
}
