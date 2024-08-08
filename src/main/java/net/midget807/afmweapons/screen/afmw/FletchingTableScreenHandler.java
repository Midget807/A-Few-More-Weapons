package net.midget807.afmweapons.screen.afmw;

import net.midget807.afmweapons.recipe.afmw.FletchingTableRecipe;
import net.midget807.afmweapons.recipe.afmw.FletchingTransformRecipe;
import net.midget807.afmweapons.screen.ModScreenHandlers;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.SmithingRecipe;
import net.minecraft.screen.ForgingScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.ForgingSlotsManager;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.OptionalInt;

public class FletchingTableScreenHandler extends ForgingScreenHandler {
    public static final int RESULT_SLOT_ID = 3;
    public static final int ADDITION_2_SLOT_ID = 2;
    public static final int ADDITION_1_SLOT_ID = 1;
    public static final int ARROW_INPUT_SLOT_ID = 0;
    private final World world;
    @Nullable
    private RecipeEntry<FletchingTransformRecipe> currentRecipe;
    private final List<RecipeEntry<FletchingTransformRecipe>> recipes;

    public FletchingTableScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
    }
    public FletchingTableScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(ModScreenHandlers.FLETCHING_TABLE_SCREEN_HANDLER, syncId, playerInventory, context);
        this.world = playerInventory.player.getWorld();
        this.recipes = this.world.getRecipeManager().listAllOfType(FletchingTransformRecipe.Type.INSTANCE);
    }

    @Override
    protected ForgingSlotsManager getForgingSlotsManager() {
        return ForgingSlotsManager.create()
                .input(ARROW_INPUT_SLOT_ID, 26, 48, stack -> this.recipes.stream()
                        .anyMatch(recipe -> (recipe.value())
                                .testArrow(stack)))
                .input(ADDITION_1_SLOT_ID, 54, 48, stack -> this.recipes.stream()
                        .anyMatch(recipe -> (recipe.value())
                                .testAddition1(stack)))
                .input(ADDITION_2_SLOT_ID, 82, 48, stack -> this.recipes.stream()
                        .anyMatch(recipe -> (recipe.value())
                                .testAddition2(stack)))
                .output(RESULT_SLOT_ID, 135, 48)
                .build();
    }

    @Override
    protected boolean canUse(BlockState state) {
        return state.isOf(Blocks.FLETCHING_TABLE);
    }

    @Override
    protected boolean canTakeOutput(PlayerEntity player, boolean present) {
        return this.currentRecipe != null && this.currentRecipe.value().matches(this.input, this.world);
    }

    @Override
    protected void onTakeOutput(PlayerEntity player, ItemStack stack) {
        stack.onCraft(player.getWorld(), player, stack.getCount());
        this.output.unlockLastRecipe(player, this.getInputStacks());
        this.decrementStack(0);
        this.decrementStack(1);
        this.decrementStack(2);
        this.context.run((world1, pos) -> world1.syncWorldEvent(WorldEvents.SMITHING_TABLE_USED, pos, 0));
    }

    private void decrementStack(int slot) {
        ItemStack itemStack = this.input.getStack(slot);
        if (!itemStack.isEmpty()) {
            itemStack.decrement(1);
            this.input.setStack(slot, itemStack);
        }
    }

    private List<ItemStack> getInputStacks() {
        return List.of(this.input.getStack(0), this.input.getStack(1), this.input.getStack(2));
    }

    @Override
    public void updateResult() {
        List<RecipeEntry<FletchingTransformRecipe>> list = this.world.getRecipeManager().getAllMatches(FletchingTransformRecipe.Type.INSTANCE, this.input, this.world);
        if (list.isEmpty()) {
            this.output.setStack(0, ItemStack.EMPTY);
        } else {
            RecipeEntry<FletchingTransformRecipe> recipeEntry = list.get(0);
            ItemStack itemStack = recipeEntry.value().craft(this.input, this.world.getRegistryManager());
            if (itemStack.isItemEnabled(this.world.getEnabledFeatures())) {
                this.currentRecipe = recipeEntry;
                this.output.setLastRecipe(recipeEntry);
                this.output.setStack(0, itemStack);
            }
        }
    }

    @Override
    public int getSlotFor(ItemStack stack) {
        return this.getQuickMoveSlot(stack).orElse(0);
    }
    private static OptionalInt getQuickMoveSlot(FletchingTransformRecipe recipe, ItemStack stack) {
        if (recipe.testArrow(stack)) {
            return OptionalInt.of(0);
        }
        if (recipe.testAddition1(stack)) {
            return OptionalInt.of(1);
        }
        if (recipe.testAddition2(stack)) {
            return OptionalInt.of(2);
        }
        return OptionalInt.empty();
    }

    public OptionalInt getQuickMoveSlot(ItemStack stack) {
        return this.recipes.stream().flatMapToInt(recipe -> getQuickMoveSlot(recipe.value(), stack).stream()).filter(slot -> !this.getSlot(slot).hasStack()).findFirst();
    }
}
