package net.midget807.afmweapons.recipe.afmw;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.midget807.afmweapons.recipe.ModRecipes;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.world.World;

import java.util.stream.Stream;

public class FletchingTransformRecipe implements FletchingTableRecipe {
    public final Identifier id;
    public final Ingredient arrow;
    public final Ingredient addition1;
    public final Ingredient addition2;
    public final ItemStack result;

    public FletchingTransformRecipe(Identifier id, Ingredient arrow, Ingredient addition1, Ingredient addition2, ItemStack result) {
        this.id = id;
        this.arrow = arrow;
        this.addition1 = addition1;
        this.addition2 = addition2;
        this.result = result;
    }

    @Override
    public boolean matches(Inventory inventory, World world) {
        return this.arrow.test(inventory.getStack(0)) && this.addition1.test(inventory.getStack(1)) && this.addition2.test(inventory.getStack(2));
    }

    @Override
    public ItemStack craft(Inventory inventory, DynamicRegistryManager registryManager) {
        return result.copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getOutput(DynamicRegistryManager registryManager) {
        return this.result.getItem().getDefaultStack();
    }

    @Override
    public Identifier getId() {
        return this.id;
    }

    public Ingredient getArrow() {
        return arrow;
    }

    public Ingredient getAddition1() {
        return addition1;
    }

    public Ingredient getAddition2() {
        return addition2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.FLETCHING_TRANSFORM_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.FLETCHING_RECIPE_TYPE;
    }

    @Override
    public boolean isEmpty() {
        return Stream.of(this.arrow, this.addition1).anyMatch(Ingredient::isEmpty);
    }

    @Override
    public boolean testArrow(ItemStack stack) {
        return this.arrow.test(stack);
    }

    @Override
    public boolean testAddition1(ItemStack stack) {
        return this.addition1.test(stack);
    }

    @Override
    public boolean testAddition2(ItemStack stack) {
        return this.addition2.test(stack);
    }

    public static class Serializer implements RecipeSerializer<FletchingTransformRecipe> {

        @Override
        public FletchingTransformRecipe read(Identifier id, JsonObject json) {
            Ingredient ingredient1 = Ingredient.fromJson(JsonHelper.getElement(json, "arrow"));
            Ingredient ingredient2 = Ingredient.fromJson(JsonHelper.getElement(json, "addition_1"));
            Ingredient ingredient3 = Ingredient.fromJson(JsonHelper.getElement(json, "addition_2"));
            ItemStack itemStack = ShapedRecipe.outputFromJson(JsonHelper.getObject(json, "result"));
            return new FletchingTransformRecipe(id, ingredient1, ingredient2, ingredient3, itemStack.getItem().getDefaultStack());
        }

        @Override
        public FletchingTransformRecipe read(Identifier id, PacketByteBuf buf) {
            Ingredient ingredient1 = Ingredient.fromPacket(buf);
            Ingredient ingredient2 = Ingredient.fromPacket(buf);
            Ingredient ingredient3 = Ingredient.fromPacket(buf);
            ItemStack itemStack = buf.readItemStack();
            return new FletchingTransformRecipe(id, ingredient1, ingredient2, ingredient3, itemStack.getItem().getDefaultStack());
        }

        @Override
        public void write(PacketByteBuf buf, FletchingTransformRecipe recipe) {
            recipe.arrow.write(buf);
            recipe.addition1.write(buf);
            recipe.addition2.write(buf);
            buf.writeItemStack(recipe.result.getItem().getDefaultStack());
        }
    }
}
