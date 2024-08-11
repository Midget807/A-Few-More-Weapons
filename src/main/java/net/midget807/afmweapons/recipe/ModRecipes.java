package net.midget807.afmweapons.recipe;

import net.midget807.afmweapons.AFMWMain;
import net.midget807.afmweapons.recipe.afmw.FletchingTransformRecipe;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModRecipes {
    public static void registerRecipes() {
        Registry.register(Registries.RECIPE_SERIALIZER, AFMWMain.id(FletchingTransformRecipe.Serializer.ID),
                FletchingTransformRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, AFMWMain.id(FletchingTransformRecipe.Type.ID),
                FletchingTransformRecipe.Type.INSTANCE);

    }
}
