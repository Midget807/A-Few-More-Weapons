package net.midget807.afmweapons.recipe.afmw;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.world.World;

import java.util.stream.Stream;

public class FletchingTransformRecipe implements FletchingTableRecipe {
    public final Ingredient arrow;
    public final Ingredient addition1;
    public final Ingredient addition2;
    public final ItemStack result;

    public FletchingTransformRecipe(Ingredient arrow, Ingredient addition1, Ingredient addition2, ItemStack result) {
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
    public ItemStack getResult(DynamicRegistryManager registryManager) {
        return this.result;
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
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    @Override
    public boolean isEmpty() {
        return Stream.of(this.arrow, this.addition1, this.addition2).anyMatch(Ingredient::isEmpty);
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

    public static class Type implements RecipeType<FletchingTransformRecipe> {
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID = "fletching_transform";
    }
    public static class Serializer implements RecipeSerializer<FletchingTransformRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final String ID = "fletching_transform";
        public static final Codec<FletchingTransformRecipe> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                (Ingredient.ALLOW_EMPTY_CODEC.fieldOf("arrow")).forGetter(recipe -> recipe.arrow),
                (Ingredient.ALLOW_EMPTY_CODEC.fieldOf("addition1")).forGetter(recipe -> recipe.addition1),
                (Ingredient.ALLOW_EMPTY_CODEC.fieldOf("addition2")).forGetter(recipe -> recipe.addition2),
                (RecipeCodecs.CRAFTING_RESULT.fieldOf("result")).forGetter(recipe -> recipe.result)
        ).apply(instance, FletchingTransformRecipe::new));
        @Override
        public Codec<FletchingTransformRecipe> codec() {
            return CODEC;
        }

        @Override
        public FletchingTransformRecipe read(PacketByteBuf buf) {
            Ingredient ingredient1 = Ingredient.fromPacket(buf);
            Ingredient ingredient2 = Ingredient.fromPacket(buf);
            Ingredient ingredient3 = Ingredient.fromPacket(buf);
            ItemStack itemStack = buf.readItemStack();
            return new FletchingTransformRecipe(ingredient1, ingredient2, ingredient3, itemStack);
        }

        @Override
        public void write(PacketByteBuf buf, FletchingTransformRecipe recipe) {
            recipe.arrow.write(buf);
            recipe.addition1.write(buf);
            recipe.addition2.write(buf);
            buf.writeItemStack(recipe.result);
        }
    }
}
