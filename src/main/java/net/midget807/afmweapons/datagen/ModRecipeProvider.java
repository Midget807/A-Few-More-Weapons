package net.midget807.afmweapons.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.midget807.afmweapons.block.ModBlocks;
import net.midget807.afmweapons.datagen.json_builder.FletchingTransformRecipeJsonBuilder;
import net.midget807.afmweapons.item.ModItems;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output) {
        super(output);
    }


    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        offerBlasting(exporter, List.of(Items.DRAGON_EGG), RecipeCategory.MISC, ModBlocks.TOASTED_DRAGON_EGG.asItem(), 0.0f, 72000, "dragon_egg");
        offerBlasting(exporter, List.of(ModBlocks.TOASTED_DRAGON_EGG.asItem()), RecipeCategory.MISC, ModBlocks.BAKED_DRAGON_EGG.asItem(), 0.0f, 72000, "dragon_egg");


        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.POLE, 1)
                .pattern("  S")
                .pattern(" S ")
                .pattern("S  ")
                .input('S', Items.STICK)
                .criterion(FabricRecipeProvider.hasItem(Items.STICK), FabricRecipeProvider.conditionsFromItem(Items.STICK))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.POLE)));



        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.WOODEN_LONGSWORD, 1)
                .pattern("  I")
                .pattern("ID ")
                .pattern("SI ")
                .input('S',Items.STICK)
                .input('D',Items.WOODEN_SWORD)
                .input('I', ItemTags.PLANKS)
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .criterion(hasItem(Items.WOODEN_SWORD), conditionsFromItem(Items.WOODEN_SWORD))
                .criterion("has_planks", conditionsFromTag(ItemTags.PLANKS))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.WOODEN_LONGSWORD)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.STONE_LONGSWORD, 1)
                .pattern("  I")
                .pattern("ID ")
                .pattern("SI ")
                .input('S',Items.STICK)
                .input('D',Items.STONE_SWORD)
                .input('I',ItemTags.STONE_TOOL_MATERIALS)
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .criterion(hasItem(Items.STONE_SWORD), conditionsFromItem(Items.STONE_SWORD))
                .criterion("has_cobblestone", conditionsFromTag(ItemTags.STONE_TOOL_MATERIALS))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.STONE_LONGSWORD)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.IRON_LONGSWORD, 1)
                .pattern("  I")
                .pattern("ID ")
                .pattern("SI ")
                .input('S',Items.STICK)
                .input('D',Items.IRON_SWORD)
                .input('I',Items.IRON_INGOT)
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .criterion(hasItem(Items.IRON_SWORD), conditionsFromItem(Items.IRON_SWORD))
                .criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.IRON_LONGSWORD)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.GOLDEN_LONGSWORD, 1)
                .pattern("  I")
                .pattern("ID ")
                .pattern("SI ")
                .input('S',Items.STICK)
                .input('D',Items.GOLDEN_SWORD)
                .input('I',Items.GOLD_INGOT)
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .criterion(hasItem(Items.GOLDEN_SWORD), conditionsFromItem(Items.GOLDEN_SWORD))
                .criterion(hasItem(Items.GOLD_INGOT), conditionsFromItem(Items.GOLD_INGOT))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.GOLDEN_LONGSWORD)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.DIAMOND_LONGSWORD, 1)
                .pattern("  I")
                .pattern("ID ")
                .pattern("SI ")
                .input('S',Items.STICK)
                .input('D',Items.DIAMOND_SWORD)
                .input('I',Items.DIAMOND)
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .criterion(hasItem(Items.DIAMOND_SWORD), conditionsFromItem(Items.DIAMOND_SWORD))
                .criterion(hasItem(Items.DIAMOND), conditionsFromItem(Items.DIAMOND))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.DIAMOND_LONGSWORD)));


        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.WOODEN_HALBERD, 1)
                .pattern("II")
                .pattern("IA")
                .pattern("P ")
                .input('P',ModItems.POLE)
                .input('A',Items.WOODEN_AXE)
                .input('I', ItemTags.PLANKS)
                .criterion(hasItem(ModItems.POLE), conditionsFromItem(ModItems.POLE))
                .criterion(hasItem(Items.WOODEN_AXE), conditionsFromItem(Items.WOODEN_AXE))
                .criterion("has_planks", conditionsFromTag(ItemTags.PLANKS))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.WOODEN_HALBERD)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.STONE_HALBERD, 1)
                .pattern("II")
                .pattern("IA")
                .pattern("P ")
                .input('P',ModItems.POLE)
                .input('A',Items.STONE_AXE)
                .input('I',ItemTags.STONE_TOOL_MATERIALS)
                .criterion(hasItem(ModItems.POLE), conditionsFromItem(ModItems.POLE))
                .criterion(hasItem(Items.STONE_AXE), conditionsFromItem(Items.STONE_AXE))
                .criterion("has_cobblestone", conditionsFromTag(ItemTags.STONE_TOOL_MATERIALS))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.STONE_HALBERD)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.IRON_HALBERD, 1)
                .pattern("II")
                .pattern("IA")
                .pattern("P ")
                .input('P',ModItems.POLE)
                .input('A',Items.IRON_AXE)
                .input('I',Items.IRON_INGOT)
                .criterion(hasItem(ModItems.POLE), conditionsFromItem(ModItems.POLE))
                .criterion(hasItem(Items.IRON_AXE), conditionsFromItem(Items.IRON_AXE))
                .criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.IRON_HALBERD)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.GOLDEN_HALBERD, 1)
                .pattern("II")
                .pattern("IA")
                .pattern("P ")
                .input('P',ModItems.POLE)
                .input('A',Items.GOLDEN_AXE)
                .input('I',Items.GOLD_INGOT)
                .criterion(hasItem(ModItems.POLE), conditionsFromItem(ModItems.POLE))
                .criterion(hasItem(Items.GOLDEN_AXE), conditionsFromItem(Items.GOLDEN_AXE))
                .criterion(hasItem(Items.GOLD_INGOT), conditionsFromItem(Items.GOLD_INGOT))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.GOLDEN_HALBERD)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.DIAMOND_HALBERD, 1)
                .pattern("II")
                .pattern("IA")
                .pattern("P ")
                .input('P',ModItems.POLE)
                .input('A',Items.DIAMOND_AXE)
                .input('I',Items.DIAMOND)
                .criterion(hasItem(ModItems.POLE), conditionsFromItem(ModItems.POLE))
                .criterion(hasItem(Items.DIAMOND_AXE), conditionsFromItem(Items.DIAMOND_AXE))
                .criterion(hasItem(Items.DIAMOND), conditionsFromItem(Items.DIAMOND))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.DIAMOND_HALBERD)));

        offerNetheriteUpgradeRecipe(exporter, ModItems.DIAMOND_LONGSWORD, RecipeCategory.COMBAT, ModItems.NETHERITE_LONGSWORD);
        offerNetheriteUpgradeRecipe(exporter, ModItems.DIAMOND_HALBERD, RecipeCategory.COMBAT, ModItems.NETHERITE_HALBERD);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.FRYING_PAN, 1)
                .pattern(" I ")
                .pattern("IBI")
                .pattern("WI ")
                .input('B', Items.IRON_BLOCK)
                .input('I', Items.IRON_INGOT)
                .input('W', Items.STICK)
                .criterion(hasItem(Items.IRON_BLOCK), conditionsFromItem(Items.IRON_BLOCK))
                .criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.FRYING_PAN)));

        offerOneAdditionFletchingRecipe(exporter, Items.ARROW, Items.GLOWSTONE_DUST, RecipeCategory.COMBAT, Items.SPECTRAL_ARROW);
        offerOneAdditionFletchingRecipe(exporter, Items.ARROW, Items.ICE, RecipeCategory.COMBAT, ModItems.FROST_ARROW);
        offerOneAdditionFletchingRecipe(exporter, Items.ARROW, Items.FIRE_CHARGE, RecipeCategory.COMBAT, ModItems.EXPLOSIVE_ARROW);
        /**
         * Json builder does not like the repeated slimeballs due to double instanced criterion of Slime balls causing error
        offerTwoAdditionFletchingRecipe(exporter, Items.ARROW, Items.SLIME_BALL, Items.SLIME_BALL, RecipeCategory.COMBAT, ModItems.RICOCHET_ARROW);
         */
        offerOneAdditionFletchingRecipe(exporter, Items.ARROW, Items.ENDER_PEARL, RecipeCategory.COMBAT, ModItems.WARP_ARROW);
        offerTwoAdditionFletchingRecipe(exporter, Items.SPECTRAL_ARROW, Items.LAPIS_LAZULI, Items.ENDER_PEARL, RecipeCategory.COMBAT, ModItems.MAGIC_ARROW);
        offerOneAdditionFletchingRecipe(exporter, Items.SPECTRAL_ARROW, Items.ECHO_SHARD, RecipeCategory.COMBAT, ModItems.ECHO_ARROW);

    }

    public static void offerOneAdditionFletchingRecipe(Consumer<RecipeJsonProvider> exporter, Item arrow, Item addition1, RecipeCategory category, Item result) {
        FletchingTransformRecipeJsonBuilder.createWithOneAddition(Ingredient.ofItems(arrow), Ingredient.ofItems(addition1), category, result)
                .criterion(hasItem(arrow), conditionsFromItem(arrow))
                .criterion(hasItem(addition1), conditionsFromItem(addition1))
                .offerTo(exporter, getRecipeName(result));
    }
    public static void offerTwoAdditionFletchingRecipe(Consumer<RecipeJsonProvider> exporter, Item arrow, Item addition1, Item addition2, RecipeCategory category, Item result) {
        FletchingTransformRecipeJsonBuilder.createWithTwoAdditions(Ingredient.ofItems(arrow), Ingredient.ofItems(addition1), Ingredient.ofItems(addition2), category, result)
                .criterion(hasItem(arrow), conditionsFromItem(arrow))
                .criterion(hasItem(addition1), conditionsFromItem(addition1))
                .criterion(hasItem(addition2), conditionsFromItem(addition2))
                .offerTo(exporter, getRecipeName(result));
    }
}
