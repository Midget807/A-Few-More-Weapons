package net.midget807.afmweapons.recipe;

import net.midget807.afmweapons.AFMWMain;
import net.midget807.afmweapons.recipe.afmw.FletchingTableRecipe;
import net.midget807.afmweapons.recipe.afmw.FletchingTransformRecipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModRecipes {
    public static final RecipeSerializer<FletchingTransformRecipe> FLETCHING_TRANSFORM_SERIALIZER = Registry.register(Registries.RECIPE_SERIALIZER,
            AFMWMain.id("fletching_transform"),
            new FletchingTransformRecipe.Serializer()
    );
    public static final RecipeType<FletchingTableRecipe> FLETCHING_RECIPE_TYPE = Registry.register(Registries.RECIPE_TYPE,
            AFMWMain.id("fletching"),
            new RecipeType<FletchingTableRecipe>() {
                @Override
                public String toString() {
                    return "fletching";
                }
            }
    );

    public static void registerModRecipes() {
        AFMWMain.LOGGER.info("Registering Mod Recipes");
    }
}
