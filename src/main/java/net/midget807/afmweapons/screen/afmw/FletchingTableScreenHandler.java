package net.midget807.afmweapons.screen.afmw;

import net.midget807.afmweapons.recipe.ModRecipes;
import net.midget807.afmweapons.recipe.afmw.FletchingTableRecipe;
import net.midget807.afmweapons.recipe.afmw.FletchingTransformRecipe;
import net.midget807.afmweapons.screen.ModScreenHandlers;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ForgingScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.ForgingSlotsManager;
import net.minecraft.screen.slot.Slot;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

public class FletchingTableScreenHandler extends ForgingScreenHandler {
    public static final int RESULT_SLOT_ID = 3;
    public static final int ADDITION_2_SLOT_ID = 2;
    public static final int ADDITION_1_SLOT_ID = 1;
    public static final int ARROW_INPUT_SLOT_ID = 0;
    private final World world;
    @Nullable
    private FletchingTableRecipe currentRecipe;
    private final List<FletchingTableRecipe> recipes;

    public FletchingTableScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
    }
    public FletchingTableScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(ModScreenHandlers.FLETCHING_TABLE_SCREEN_HANDLER, syncId, playerInventory, context);
        this.world = playerInventory.player.getWorld();
        this.recipes = this.world.getRecipeManager().listAllOfType(ModRecipes.FLETCHING_RECIPE_TYPE);
    }

    @Override
    protected ForgingSlotsManager getForgingSlotsManager() {
        return ForgingSlotsManager.create()
                .input(ARROW_INPUT_SLOT_ID, 26, 48, stack -> this.recipes.stream()
                        .anyMatch(recipe -> (recipe.testArrow(stack))))
                .input(ADDITION_1_SLOT_ID, 54, 48, stack -> this.recipes.stream()
                        .anyMatch(recipe -> (recipe.testAddition1(stack))))
                .input(ADDITION_2_SLOT_ID, 82, 48, stack -> this.recipes.stream()
                        .anyMatch(recipe -> (recipe.testAddition2(stack))))
                .output(RESULT_SLOT_ID, 135, 48)
                .build();
    }

    @Override
    protected boolean canUse(BlockState state) {
        return state.isOf(Blocks.FLETCHING_TABLE);
    }

    @Override
    protected boolean canTakeOutput(PlayerEntity player, boolean present) {
        return this.currentRecipe != null && this.currentRecipe.matches(this.input, this.world);
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
        List<FletchingTableRecipe> list = this.world.getRecipeManager().getAllMatches(ModRecipes.FLETCHING_RECIPE_TYPE, this.input, this.world);
        if (list.isEmpty()) {
            this.output.setStack(0, ItemStack.EMPTY);
        } else {
            FletchingTransformRecipe recipeEntry = (FletchingTransformRecipe) list.get(0);
            ItemStack itemStack = recipeEntry.craft(this.input, this.world.getRegistryManager());
            if (itemStack.isItemEnabled(this.world.getEnabledFeatures())) {
                this.currentRecipe = recipeEntry;
                this.output.setLastRecipe(recipeEntry);
                this.output.setStack(0, itemStack.getItem().getDefaultStack());
            }
        }
    }

    @Override
    public int getSlotFor(ItemStack stack) {
        return (Integer)((Optional)this.recipes.stream().map((recipe) -> getQuickMoveSlot(recipe, stack)).filter(Optional::isPresent).findFirst().orElse(Optional.of(0))).get();
    }


    public Optional<Integer> getQuickMoveSlot(FletchingTableRecipe recipe, ItemStack stack) {
        if (recipe.testArrow(stack)) {
            return Optional.of(0);
        } else if (recipe.testAddition1(stack)) {
            return Optional.of(1);
        } else {
            return recipe.testAddition2(stack) ? Optional.of(2) : Optional.empty();
        }
    }

    @Override
    public boolean canInsertIntoSlot(ItemStack stack, Slot slot) {
        return slot.inventory != this.output && super.canInsertIntoSlot(stack, slot);
    }

    @Override
    protected boolean isValidIngredient(ItemStack stack) {
        return this.recipes.stream().map(recipe -> getQuickMoveSlot(recipe, stack)).anyMatch(Optional::isPresent);
    }
}
