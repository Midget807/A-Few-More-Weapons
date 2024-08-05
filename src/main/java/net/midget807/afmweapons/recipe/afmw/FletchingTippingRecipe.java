package net.midget807.afmweapons.recipe.afmw;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.world.World;

public class FletchingTippingRecipe implements Recipe<Inventory> {
    private final Potion potion;
    private final Ingredient arrow;
    public final ItemStack result;
    public FletchingTippingRecipe(Potion potion, Ingredient arrow, ItemStack result) {
        this.potion = potion;
        this.arrow = arrow;
        this.result = result;
    }
    @Override
    public boolean matches(Inventory inventory, World world) {
        return this.arrow.test(inventory.getStack(1));
    }

    @Override
    public ItemStack craft(Inventory inventory, DynamicRegistryManager registryManager) {
        return null;
    }

    @Override
    public boolean fits(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getResult(DynamicRegistryManager registryManager) {
        return null;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return null;
    }

    @Override
    public RecipeType<?> getType() {
        return null;
    }
}
