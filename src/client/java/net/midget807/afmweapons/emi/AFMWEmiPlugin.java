package net.midget807.afmweapons.emi;

import com.google.common.collect.Maps;
import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import net.midget807.afmweapons.AFMWMain;
import net.midget807.afmweapons.recipe.afmw.FletchingTableRecipe;
import net.midget807.afmweapons.recipe.afmw.FletchingTransformRecipe;
import net.minecraft.client.MinecraftClient;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Items;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;

import java.util.Map;

@EmiEntrypoint
public class AFMWEmiPlugin implements EmiPlugin {
    public static final Identifier FLETCHING_SPRITE_SHEET = AFMWMain.id("textures/gui/container/fletching_table_gui.png");
    public static final EmiStack FLETCHING_STATION = EmiStack.of(Items.FLETCHING_TABLE);
    public static final EmiRecipeCategory FLETCHING_CATEGORY =
            new EmiRecipeCategory(AFMWMain.id("fletching_station"), FLETCHING_STATION, new EmiTexture(FLETCHING_SPRITE_SHEET, 0, 0, 16, 16));

    @Override
    public void register(EmiRegistry registry) {
        registry.addCategory(FLETCHING_CATEGORY);
        registry.addWorkstation(FLETCHING_CATEGORY, FLETCHING_STATION);
        for (FletchingTableRecipe recipe : getRecipe(registry, FletchingTransformRecipe.Type.INSTANCE)) {
            MinecraftClient client = MinecraftClient.getInstance();
            getRecipeIds();
            if (recipe instanceof FletchingTransformRecipe ftr) {
                registry.addRecipe(new FletchingRecipeEmiRecipe(
                        EmiIngredient.of(ftr.getArrow()),
                        EmiIngredient.of(ftr.getAddition1()),
                        EmiIngredient.of(ftr.getAddition2()),
                        EmiStack.of(recipe.getResult(client.world.getRegistryManager())),
                        AFMWMain.id(FletchingTransformRecipe.Type.ID + "/" + getId(ftr))
                        )
                );
            }
        }
    }
    public static Map<Recipe<?>, Identifier> recipeIds = Map.of();
    public static void getRecipeIds() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world != null) {
            RecipeManager manager = client.world.getRecipeManager();
            recipeIds = Maps.newIdentityHashMap();
            if (manager != null) {
                for (RecipeEntry<?> entry : manager.values()) {
                    recipeIds.put(entry.value(), entry.id());
                }
            }
        }
    }
    public static String getId(Recipe<?> recipe) {
        return recipeIds.get(recipe).getPath();
    }

    private static <C extends Inventory, T extends Recipe<C>> Iterable<T> getRecipe(EmiRegistry registry, RecipeType<T> type) {
        return registry.getRecipeManager().listAllOfType(type).stream().map(e -> e.value())::iterator;
    }
}
