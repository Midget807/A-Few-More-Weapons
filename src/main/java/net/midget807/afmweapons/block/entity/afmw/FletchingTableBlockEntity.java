package net.midget807.afmweapons.block.entity.afmw;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.midget807.afmweapons.block.entity.ModBlockEntities;
import net.midget807.afmweapons.network.ModMessages;
import net.midget807.afmweapons.recipe.afmw.FletchingTippingRecipe;
import net.midget807.afmweapons.screen.afmw.FletchingTableScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PotionItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class FletchingTableBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(6, ItemStack.EMPTY);
    private static final int INPUT_SLOT_1 = 0;
    private static final int FLUID_ITEM_SLOT_1 = 1;
    private static final int OUTPUT_SLOT_1 = 2;
    private static final int INPUT_SLOT_2 = 3;
    private static final int FLUID_ITEM_SLOT_2 = 4;
    private static final int OUTPUT_SLOT_2 = 5;
    private int fluidLevel1 = 3;
    private final int DEFAULT_FLUID_LEVEL_1 = 3;
    public static final String FLUID_STORAGE_1_KEY = "FluidStorage1";
    public static final String FLUID_VARIANT_1_KEY = "FluidVariant1";
    public static final String FLUID_STORAGE_2_KEY = "FluidStorage2";
    public static final String FLUID_VARIANT_2_KEY = "FluidVariant2";
    public FletchingTableBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FLETCHING_TABLE_BLOCK_ENTITY, pos, state);
    }

    @Override
    public void markDirty() {
        if(!world.isClient()) {
            PacketByteBuf data = PacketByteBufs.create();
            data.writeInt(inventory.size());
            for(int i = 0; i < inventory.size(); i++) {
                data.writeItemStack(inventory.get(i));
            }
            data.writeBlockPos(getPos());

            for(ServerPlayerEntity player : PlayerLookup.tracking((ServerWorld) world, getPos())) {
                ServerPlayNetworking.send(player, ModMessages.ITEM_SYNC, data);
            }
        }

        super.markDirty();
    }

    public void setInventory(DefaultedList<ItemStack> list) {
        for(int i = 0; i < list.size(); i++) {
            this.inventory.set(i, list.get(i));
        }
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("screen.afmweapons.fletching_table");
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return this.inventory;
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new FletchingTableScreenHandler(syncId, playerInventory, this);
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        fillUpOnFluid1();
        fillUpOnFluid2();
        /*if (canInsertIntoOutputSlot() && hasRecipe()) {
            markDirty();
            craftItem();
            extractFluid();
        }*/
    }

    /*private void craftItem() {
        Optional<RecipeEntry<FletchingTippingRecipe>> recipe = getCurrentRecipe();

        this.removeStack(INPUT_SLOT_1, 1);

        this.setStack(OUTPUT_SLOT_1, new ItemStack(recipe.get().value().getResult(null).getItem(),
                this.getStack(OUTPUT_SLOT_1).getCount() + recipe.get().value().getResult(null).getCount()));
    }

    private boolean hasRecipe() {
        Optional<RecipeEntry<GemEmpoweringRecipe>> recipe = getCurrentRecipe();

        if (recipe.isEmpty()) {
            return false;
        }
        ItemStack output = recipe.get().value().getResult(null);

        return canInsertAmountIntoOutputSlot(output.getCount())
                && canInsertItemIntoOutputSlot(output) && hasEnoughFluidToCraft();
    }*/

    private boolean hasEnoughFluidToCraft() {
        return this.fluidStorage1.amount >= 1000;
    }


    private boolean canInsertItemIntoOutputSlot(ItemStack output) {
        return this.getStack(OUTPUT_SLOT_1).isEmpty() || this.getStack(OUTPUT_SLOT_1).getItem() == output.getItem();
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        return this.getStack(OUTPUT_SLOT_1).getMaxCount() >= this.getStack(OUTPUT_SLOT_1).getCount() + count;
    }

    private boolean canInsertIntoOutputSlot() {
        return this.getStack(OUTPUT_SLOT_1).isEmpty() ||
                this.getStack(OUTPUT_SLOT_1).getCount() < this.getStack(OUTPUT_SLOT_1).getMaxCount();
    }

    private void fillUpOnFluid1() {
        if (hasFluidSourceItemInFluidSlot1(FLUID_ITEM_SLOT_1)) {
            transferItemFluidToTank1(FLUID_ITEM_SLOT_1);
        }
    }

    private void transferItemFluidToTank1(int fluidItemSlot1) {
        try(Transaction transaction = Transaction.openOuter()) {
            this.fluidStorage1.insert(FluidVariant.of(Fluids.WATER),
                    (FluidConstants.BOTTLE / 27), transaction);
            transaction.commit();

            this.setStack(fluidItemSlot1, new ItemStack(Items.GLASS_BOTTLE));
        }
    }

    private boolean hasFluidSourceItemInFluidSlot1(int fluidSlot1) {
        return this.getStack(fluidSlot1).getItem() == PotionUtil.setPotion(Items.POTION.getDefaultStack(), Potions.WATER).getItem();
    }
    private void fillUpOnFluid2() {
        if (hasFluidSourceItemInFluidSlot2(FLUID_ITEM_SLOT_2)) {
            transferItemFluidToTank2(FLUID_ITEM_SLOT_2);
        }
    }

    private void transferItemFluidToTank2(int fluidItemSlot2) {
    }

    private boolean hasFluidSourceItemInFluidSlot2(int fluidSlot2) {
        return false;
    }

    private void extractFluid() {
        try (Transaction transaction = Transaction.openOuter()) {
            this.fluidStorage1.extract(FluidVariant.of(Fluids.WATER), 1000, transaction);
            transaction.commit();
        }
    }


    public final SingleVariantStorage<FluidVariant> fluidStorage1 = new SingleVariantStorage<FluidVariant>() {
        @Override
        protected FluidVariant getBlankVariant() {
            return FluidVariant.blank();
        }

        @Override
        protected long getCapacity(FluidVariant variant) {
            return (FluidConstants.BOTTLE / 27) * 3;
        }

        @Override
        protected void onFinalCommit() {
            markDirty();
            getWorld().updateListeners(pos, getCachedState(), getCachedState(), 3);
        }
    };
    public final SingleVariantStorage<FluidVariant> fluidStorage2 = new SingleVariantStorage<FluidVariant>() {
        @Override
        protected FluidVariant getBlankVariant() {
            return FluidVariant.blank();
        }

        @Override
        protected long getCapacity(FluidVariant variant) {
            return (FluidConstants.BOTTLE / 27) * 3;
        }

        @Override
        protected void onFinalCommit() {
            markDirty();
            getWorld().updateListeners(pos, getCachedState(), getCachedState(), 3);
        }
    };

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction side) {
        return side == Direction.UP;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction side) {
        return side == Direction.DOWN;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.put(FLUID_VARIANT_1_KEY, fluidStorage1.variant.toNbt());
        nbt.putLong(FLUID_STORAGE_1_KEY, fluidStorage1.amount);
        nbt.put(FLUID_VARIANT_2_KEY, fluidStorage2.variant.toNbt());
        nbt.putLong(FLUID_STORAGE_2_KEY, fluidStorage2.amount);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        Inventories.readNbt(nbt, inventory);
        fluidStorage1.variant = FluidVariant.fromNbt((NbtCompound) nbt.get(FLUID_VARIANT_1_KEY));
        fluidStorage1.amount = nbt.getLong(FLUID_STORAGE_1_KEY);
        fluidStorage2.variant = FluidVariant.fromNbt((NbtCompound) nbt.get(FLUID_VARIANT_2_KEY));
        fluidStorage2.amount = nbt.getLong(FLUID_STORAGE_2_KEY);
        super.readNbt(nbt);
    }
}
