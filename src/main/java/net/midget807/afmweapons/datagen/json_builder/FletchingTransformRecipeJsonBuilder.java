package net.midget807.afmweapons.datagen.json_builder;

import com.google.gson.JsonObject;
import net.midget807.afmweapons.AFMWMain;
import net.midget807.afmweapons.recipe.ModRecipes;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.CriterionMerger;
import net.minecraft.advancement.criterion.CriterionConditions;
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.item.Item;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class FletchingTransformRecipeJsonBuilder {
    private final Ingredient arrow;
    private final Ingredient addition1;
    private final Ingredient addition2;
    private final Item result;
    private final Advancement.Builder advancement = Advancement.Builder.createUntelemetered();
    private final RecipeSerializer<?> serializer;
    private final RecipeCategory category;

    public FletchingTransformRecipeJsonBuilder(RecipeCategory recipeCategory, RecipeSerializer serializer, Ingredient arrow, Ingredient addition1, Ingredient addition2, Item result) {
        this.category = recipeCategory;
        this.serializer = serializer;
        this.arrow = arrow;
        this.addition1 = addition1;
        this.addition2 = addition2;
        this.result = result;
    }

    public static FletchingTransformRecipeJsonBuilder createWithOneAddition(Ingredient arrow, Ingredient addition1, RecipeCategory category, Item result) {
        return new FletchingTransformRecipeJsonBuilder(category, ModRecipes.FLETCHING_TRANSFORM_SERIALIZER, arrow, addition1, Ingredient.EMPTY, result);
    }
    public static FletchingTransformRecipeJsonBuilder createWithTwoAdditions(Ingredient arrow, Ingredient addition1, Ingredient addition2, RecipeCategory category, Item result) {
        return new FletchingTransformRecipeJsonBuilder(category, ModRecipes.FLETCHING_TRANSFORM_SERIALIZER, arrow, addition1, addition2, result);
    }
    public FletchingTransformRecipeJsonBuilder criterion(String name, CriterionConditions criterion) {
        this.advancement.criterion(name, criterion);
        return this;
    }
    public void offerTo(Consumer<RecipeJsonProvider> exporter, String recipeId) {
        this.offerTo(exporter, AFMWMain.id(recipeId));
    }
    public void offerTo(Consumer<RecipeJsonProvider> exporter, Identifier recipeId) {
        this.validate(recipeId);
        this.advancement
                        .parent(CraftingRecipeJsonBuilder.ROOT)
                                .criterion("has_the_recipe", RecipeUnlockedCriterion.create(recipeId))
                                        .rewards(AdvancementRewards.Builder.recipe(recipeId))
                                                .criteriaMerger(CriterionMerger.OR);
        exporter.accept(new FletchingTransformRecipeJsonProvider(recipeId, this.serializer, this.arrow, this.addition1, this.addition2, this.result, this.advancement, recipeId.withPrefixedPath("recipes/" + this.category.getName() + "/")));
    }

    private void validate(Identifier recipeId) {
        if (this.advancement.getCriteria().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe: " + recipeId);
        }
    }
    public record FletchingTransformRecipeJsonProvider(Identifier id, RecipeSerializer<?> serializer, Ingredient arrow, Ingredient addition1, Ingredient addition2, Item result, Advancement.Builder advancement, Identifier advancementId) implements RecipeJsonProvider {

        @Override
        public void serialize(JsonObject json) {
            json.add("arrow", this.arrow.toJson());
            json.add("addition_1", this.addition1.toJson());
            json.add("addition_2", this.addition2.toJson());
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("item", Registries.ITEM.getId(this.result).toString());
            json.add("result", jsonObject);
        }

        @Override
        public Identifier getRecipeId() {
            return this.id;
        }

        @Override
        public RecipeSerializer<?> getSerializer() {
            return this.serializer;
        }

        @Override
        public @Nullable JsonObject toAdvancementJson() {
            return this.advancement.toJson();
        }

        @Override
        public @Nullable Identifier getAdvancementId() {
            return this.advancementId;
        }
    }
}
