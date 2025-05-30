package net.midget807.afmweapons.emi;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FletchingRecipeEmiRecipe implements EmiRecipe {
    private final Identifier id;
    public final EmiIngredient arrow;
    public final EmiIngredient addition1;
    public final EmiIngredient addition2;
    public final EmiStack output;

    public FletchingRecipeEmiRecipe(EmiIngredient arrow, EmiIngredient addition1, EmiIngredient addition2, EmiStack output, Identifier id) {
        this.id = id;
        this.arrow = arrow;
        this.addition1 = addition1;
        this.addition2 = addition2;
        this.output = output;
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return AFMWEmiPlugin.FLETCHING_CATEGORY;
    }

    @Override
    public @Nullable Identifier getId() {
        return id;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return List.of(arrow, addition1, addition2);
    }

    @Override
    public List<EmiStack> getOutputs() {
        return List.of(output);
    }

    @Override
    public int getDisplayWidth() {
        return 127;
    }

    @Override
    public int getDisplayHeight() {
        return 18;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addTexture(EmiTexture.EMPTY_ARROW, 75, 1);
        widgets.addSlot(arrow, 0, 0);
        widgets.addSlot(addition1, 28, 0);
        widgets.addSlot(addition2, 56, 0);
        widgets.addSlot(output, 109, 0).recipeContext(this);
    }
}
