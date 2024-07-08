package net.midget807.afmweapons.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.midget807.afmweapons.AFMWMain;
import net.midget807.afmweapons.item.ModItems;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(RecipeExporter exporter) {

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

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.NETHERITE_LONGSWORD, 1)
                .pattern("  I")
                .pattern("ID ")
                .pattern("SI ")
                .input('S',Items.STICK)
                .input('D',Items.DIAMOND_SWORD)
                .input('I',Items.NETHERITE_INGOT)
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .criterion(hasItem(Items.DIAMOND_SWORD), conditionsFromItem(Items.DIAMOND_SWORD))
                .criterion(hasItem(Items.NETHERITE_INGOT), conditionsFromItem(Items.NETHERITE_INGOT))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.NETHERITE_LONGSWORD)));
    }
}
