package net.midget807.afmweapons.datagen.json_builder;

import com.google.gson.JsonObject;
import net.midget807.afmweapons.recipe.afmw.FletchingTransformRecipe;
import net.minecraft.advancement.*;
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.item.Item;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

public class FletchingTransformRecipeJsonBuilder {
    private final Ingredient arrow;
    private final Ingredient addition1;
    private final Ingredient addition2;
    private final Item result;
    private final Map<String, AdvancementCriterion<?>> criteria = new LinkedHashMap<>();
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
        return new FletchingTransformRecipeJsonBuilder(category, FletchingTransformRecipe.Serializer.INSTANCE, arrow, addition1, Ingredient.EMPTY, result);
    }
    public static FletchingTransformRecipeJsonBuilder createWithTwoAdditions(Ingredient arrow, Ingredient addition1, Ingredient addition2, RecipeCategory category, Item result) {
        return new FletchingTransformRecipeJsonBuilder(category, FletchingTransformRecipe.Serializer.INSTANCE, arrow, addition1, addition2, result);
    }
    public FletchingTransformRecipeJsonBuilder criterion(String name, AdvancementCriterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }
    public void offerTo(RecipeExporter exporter, String recipeId) {
        this.offerTo(exporter, Identifier.of(recipeId));
    }
    public void offerTo(RecipeExporter exporter, Identifier recipeId) {
        this.validate(recipeId);
        Advancement.Builder builder = exporter.getAdvancementBuilder().criterion("has_the_recipe", RecipeUnlockedCriterion.create(recipeId)).rewards(AdvancementRewards.Builder.recipe(recipeId)).criteriaMerger(AdvancementRequirements.CriterionMerger.OR);
        this.criteria.forEach(builder::criterion);
        exporter.accept(new FletchingTransformRecipeJsonProvider(recipeId, this.serializer, this.arrow, this.addition1, this.addition2, this.result, builder.build(recipeId.withPrefixedPath("recipes/" + this.category.getName() +"/"))));
    }

    private void validate(Identifier recipeId) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe: " + recipeId);
        }
    }
    public record FletchingTransformRecipeJsonProvider(Identifier id, RecipeSerializer<?> serializer, Ingredient arrow, Ingredient addition1, Ingredient addition2, Item result, AdvancementEntry advancement) implements RecipeJsonProvider {

        @Override
        public void serialize(JsonObject json) {
            json.add("arrow", this.arrow.toJson(true));
            json.add("addition1", this.addition1.toJson(true));
            json.add("addition2", this.addition2.toJson(true));
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("item", Registries.ITEM.getId(this.result).toString());
            json.add("result", jsonObject);
        }
    }
}
