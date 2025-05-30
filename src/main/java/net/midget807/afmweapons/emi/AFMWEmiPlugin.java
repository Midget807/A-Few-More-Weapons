package net.midget807.afmweapons.emi;

import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import net.midget807.afmweapons.AFMWMain;
import net.midget807.afmweapons.recipe.ModRecipes;
import net.midget807.afmweapons.recipe.afmw.FletchingTableRecipe;
import net.midget807.afmweapons.recipe.afmw.FletchingTransformRecipe;
import net.minecraft.client.MinecraftClient;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Items;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

@EmiEntrypoint
public class AFMWEmiPlugin implements EmiPlugin {
    public static final Identifier FLETCHING_SPRITE_SHEET = AFMWMain.id("textures/gui/container/fletching_table_gui.png");
    public static final EmiStack FLETCHING_STATION = EmiStack.of(Items.FLETCHING_TABLE);
    public static final EmiRecipeCategory FLETCHING_CATEGORY = new EmiRecipeCategory(
            AFMWMain.id("fletching_station"), FLETCHING_STATION, new EmiTexture(FLETCHING_SPRITE_SHEET, 0, 0, 16, 16)
    );

    @Override
    public void register(EmiRegistry emiRegistry) {
        emiRegistry.addCategory(FLETCHING_CATEGORY);
        emiRegistry.addWorkstation(FLETCHING_CATEGORY, FLETCHING_STATION);
        RecipeManager manager = emiRegistry.getRecipeManager();

        for (FletchingTableRecipe recipe : getRecipes(emiRegistry, ModRecipes.FLETCHING_RECIPE_TYPE)) {
            if (recipe instanceof FletchingTransformRecipe ftr) {
                addRecipeSafe(
                        emiRegistry,
                        () -> new FletchingRecipeEmiRecipe(
                                EmiIngredient.of(ftr.getArrow()),
                                EmiIngredient.of(ftr.getAddition1()),
                                EmiIngredient.of(ftr.getAddition2()),
                                EmiStack.of(ftr.getOutput(MinecraftClient.getInstance().world.getRegistryManager())),
                                ftr.getId()
                        ),
                        recipe
                );
            }
        }
    }

    private static <C extends Inventory, T extends Recipe<C>> Iterable<T> getRecipes(EmiRegistry emiRegistry, RecipeType<T> type) {
        return emiRegistry.getRecipeManager().listAllOfType(type).stream()::iterator;
    }

    private static void addRecipeSafe(EmiRegistry emiRegistry, Supplier<EmiRecipe> supplier, Recipe<?> recipe) {
        try {
            emiRegistry.addRecipe(supplier.get());
        } catch (Throwable e) {
            AFMWMain.LOGGER.warn("Exception thrown when parsing vanilla recipe {}", recipe.getId());
            AFMWMain.LOGGER.error("{}", String.valueOf(e));
        }
    }
}
