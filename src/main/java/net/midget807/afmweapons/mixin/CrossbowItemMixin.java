package net.midget807.afmweapons.mixin;

import net.midget807.afmweapons.enchantment.ModEnchantments;
import net.midget807.afmweapons.item.afmw.client.TripleShotTooltipData;
import net.minecraft.client.item.TooltipData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.potion.PotionUtil;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

@Mixin(CrossbowItem.class)
public abstract class CrossbowItemMixin extends Item {

    public CrossbowItemMixin(Settings settings) {
        super(settings);
    }

    @Shadow
    private static PersistentProjectileEntity createArrow(World world, LivingEntity entity, ItemStack crossbow, ItemStack arrow) {
        ArrowItem arrowItem = (ArrowItem)(arrow.getItem() instanceof ArrowItem ? arrow.getItem() : Items.ARROW);
        PersistentProjectileEntity persistentProjectileEntity = arrowItem.createArrow(world, arrow, entity);
        if (entity instanceof PlayerEntity) {
            persistentProjectileEntity.setCritical(true);
        }

        persistentProjectileEntity.setSound(SoundEvents.ITEM_CROSSBOW_HIT);
        persistentProjectileEntity.setShotFromCrossbow(true);
        int i = EnchantmentHelper.getLevel(Enchantments.PIERCING, crossbow);
        if (i > 0) {
            persistentProjectileEntity.setPierceLevel((byte)i);
        }

        return persistentProjectileEntity;
    }

    @Shadow
    public static void setCharged(ItemStack stack, boolean charged) {
        NbtCompound nbtCompound = stack.getOrCreateNbt();
        nbtCompound.putBoolean("Charged", charged);
    }


    @Shadow
    private static boolean loadProjectile(LivingEntity shooter, ItemStack crossbow, ItemStack projectile, boolean simulated, boolean creative) {
        return false;
    }

    @Shadow
    private static void putProjectile(ItemStack crossbow, ItemStack projectile) {
    }

    @Shadow public abstract Predicate<ItemStack> getProjectiles();

    @Unique
    private static boolean afmw$hasTripleShot(ItemStack crossbow) {
        return EnchantmentHelper.getLevel(ModEnchantments.TRIPLE_SHOT, crossbow) != 0;
    }
    @Unique
    private static int afmw$numberOfShots;
    @Inject(method = "onStoppedUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/CrossbowItem;setCharged(Lnet/minecraft/item/ItemStack;Z)V"))
    private void afmw$loadNumberOfShots(ItemStack stack, World world, LivingEntity user, int remainingUseTicks, CallbackInfo ci) {
        if(afmw$hasTripleShot(stack)) {
            afmw$numberOfShots = 3;
            //Debug
            if (!world.isClient) {
                user.sendMessage(Text.literal("Number of shots: " + afmw$numberOfShots));
            }
            //=====
        }
    }
    @Redirect(method = "loadProjectiles", at = @At(value = "INVOKE", target ="Lnet/minecraft/entity/LivingEntity;getProjectileType(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/item/ItemStack;"))
    private static ItemStack afmw$loadInsertedProjectile(LivingEntity shooter, ItemStack projectile) {
        DefaultedList<ItemStack> defaultedList = DefaultedList.of();
        getInsertedStacks(shooter.getActiveItem()).forEach(defaultedList::add);
        if (!defaultedList.isEmpty()) {
            return defaultedList.get(0);
        } else {
            return shooter.getProjectileType(shooter.getActiveItem());
        }
    }

    @Inject(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/CrossbowItem;setCharged(Lnet/minecraft/item/ItemStack;Z)V", shift = At.Shift.AFTER), cancellable = true)
    private void afmw$tripleShot(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        ItemStack crossbow = user.getStackInHand(hand);
        ItemStack projectile = user.getProjectileType(crossbow);
        DefaultedList<ItemStack> defaultedList = DefaultedList.of();
        getInsertedStacks(crossbow).forEach(defaultedList::add);
        if (!defaultedList.isEmpty()) {
            if (defaultedList.size() >= 2 && afmw$numberOfShots == 3) {
                projectile = defaultedList.get(1);
            } else if (defaultedList.size() >= 3) {
                projectile = defaultedList.get(2);
            } else {
                projectile = user.getProjectileType(crossbow);
            }
        } else {
            projectile = user.getProjectileType(crossbow);
        }

        if (afmw$hasTripleShot(crossbow) && afmw$numberOfShots > 1) {
            if (!world.isClient) {
                afmw$numberOfShots--;
            }
            //Debug
            if (!world.isClient) {
                user.sendMessage(Text.literal("Number of shots: " + afmw$numberOfShots));
            }
            //=====
            if (!user.isCreative()) {
                projectile.split(1);
                if (projectile.isEmpty() && user instanceof PlayerEntity) {
                    user.getInventory().removeOne(projectile);
                }
            }
            loadProjectile(user, crossbow, projectile, true, false);
            setCharged(crossbow,true);
            cir.setReturnValue(TypedActionResult.consume(crossbow));
        }
    }

    //Triple Shot Storage

    @Unique
    private static final String ITEMS_KEY = "TripleShotItems";
    @Unique
    private static final int ITEM_BAR_COLOR = MathHelper.packRgb(0.4F, 0.4F, 1.0F);
    @Unique
    private static final int MAX_STORAGE = 192;
    @Override
    public Optional<TooltipData> getTooltipData(ItemStack stack) {
        if (afmw$hasTripleShot(stack)) {
            DefaultedList<ItemStack> defaultedList = DefaultedList.of();
            getInsertedStacks(stack).forEach(defaultedList::add);
            return Optional.of(new TripleShotTooltipData(defaultedList, getCrossbowOccupancy(stack)));
        } else {
            return Optional.empty();
        }
    }

    @Unique
    private static int getCrossbowOccupancy(ItemStack stack) {
        return getInsertedStacks(stack).mapToInt(itemstack -> getItemOccupancy(itemstack) * itemstack.getCount()).sum();
    }

    @Unique
    private static int getItemOccupancy(ItemStack itemstack) {
        return 1;
    }

    @Unique
    private static Stream<ItemStack> getInsertedStacks(ItemStack stack) {
        NbtCompound nbtCompound = stack.getNbt();
        if (nbtCompound == null) {
            return Stream.empty();
        } else {
            NbtList nbtList = nbtCompound.getList(ITEMS_KEY, NbtElement.COMPOUND_TYPE);
            return nbtList.stream().map(NbtCompound.class::cast).map(ItemStack::fromNbt);
        }
    }
    @Unique
    private static float getAmountFilled(ItemStack stack) {
        return (float) getCrossbowOccupancy(stack) / MAX_STORAGE;
    }
    @Override
    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
        DefaultedList<ItemStack> defaultedList = DefaultedList.of();
        getInsertedStacks(stack).forEach(defaultedList::add);
        if (clickType != ClickType.RIGHT) {
            return false;
        } else {
            ItemStack itemStack = slot.getStack();
            if (itemStack.isEmpty()) {
                this.playRemoveOneSound(player);
                removeFirstStack(stack).ifPresent(removedStack -> addToCrossbow(stack, slot.insertStack(removedStack)));
            } else if (itemStack.getItem().canBeNested() && itemStack.isIn(ItemTags.ARROWS) && afmw$hasTripleShot(stack) && defaultedList.size() < 3) {
                int i = (MAX_STORAGE - getCrossbowOccupancy(stack)) / getItemOccupancy(itemStack);
                int j = addToCrossbow(stack, slot.takeStackRange(itemStack.getCount(), i, player));
                if (j > 0) {
                    this.playInsertSound(player);
                }
            }
            return true;
        }
    }

    @Override
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        DefaultedList<ItemStack> defaultedList = DefaultedList.of();
        getInsertedStacks(stack).forEach(defaultedList::add);
        if (clickType == ClickType.RIGHT && slot.canTakePartial(player)) {
            if (otherStack.isEmpty()) {
                removeFirstStack(stack).ifPresent(itemStack -> {
                    this.playRemoveOneSound(player);
                    cursorStackReference.set(itemStack);
                });
            } else if (otherStack.isIn(ItemTags.ARROWS) && afmw$hasTripleShot(stack) && defaultedList.size() < 3){
                int i = addToCrossbow(stack, otherStack);
                if (i > 0) {
                    this.playInsertSound(player);
                    otherStack.decrement(i);
                }
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        return Math.min(1 + 12 * getCrossbowOccupancy(stack) / MAX_STORAGE, 13);
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        return getCrossbowOccupancy(stack) > 0 && EnchantmentHelper.getLevel(ModEnchantments.TRIPLE_SHOT, stack) != 0;
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        return ITEM_BAR_COLOR;
    }

    @Unique
    private static int addToCrossbow(ItemStack crossbow, ItemStack stack) {
        DefaultedList<ItemStack> defaultedList = DefaultedList.of();
        getInsertedStacks(crossbow).forEach(defaultedList::add);
        if (!stack.isEmpty() && stack.getItem().canBeNested() && stack.isIn(ItemTags.ARROWS) && afmw$hasTripleShot(crossbow) && defaultedList.size() < 3) {
            NbtCompound nbtCompound = crossbow.getOrCreateNbt();
            if (!nbtCompound.contains(ITEMS_KEY)) {
                nbtCompound.put(ITEMS_KEY, new NbtList());
            }
            int i = getCrossbowOccupancy(crossbow);
            int j = getItemOccupancy(stack);
            int k = Math.min(MAX_STORAGE, (64 - i) / j);
            if (k == 0) {
                return 0;
            } else {
                NbtList nbtList = nbtCompound.getList(ITEMS_KEY, NbtElement.COMPOUND_TYPE);
                Optional<NbtCompound> optional = canMergeStack(stack, nbtList);
                if (optional.isPresent()) {
                    NbtCompound nbtCompound2 = (NbtCompound) optional.get();
                    ItemStack itemStack = ItemStack.fromNbt(nbtCompound2);
                    itemStack.increment(k);
                    itemStack.writeNbt(nbtCompound2);
                    nbtList.remove(nbtCompound2);
                    nbtList.add(0, nbtCompound2);
                } else {
                    ItemStack itemStack2 = stack.copyWithCount(k);
                    NbtCompound nbtCompound3 = new NbtCompound();
                    itemStack2.writeNbt(nbtCompound3);
                    nbtList.add(0, nbtCompound3);
                }
                return k;
            }
        } else {
            return 0;
        }
    }

    @Unique
    private static Optional<NbtCompound> canMergeStack(ItemStack stack, NbtList items) {
        return stack.isOf(Items.CROSSBOW) ?
                Optional.empty()
                : items.stream()
                .filter(NbtCompound.class::isInstance)
                .map(NbtCompound.class::cast)
                .filter(item -> ItemStack.canCombine(ItemStack.fromNbt(item), stack))
                .findFirst();
    }

    @Unique
    private static Optional<ItemStack> removeFirstStack(ItemStack stack) {
        NbtCompound nbtCompound = stack.getOrCreateNbt();
        if (!nbtCompound.contains(ITEMS_KEY)) {
            return Optional.empty();
        } else {
            NbtList nbtList = nbtCompound.getList(ITEMS_KEY, NbtElement.COMPOUND_TYPE);
            if (nbtList.isEmpty()) {
                return Optional.empty();
            } else {
                int i = 0;
                NbtCompound nbtCompound2 = nbtList.getCompound(i);
                ItemStack itemStack = ItemStack.fromNbt(nbtCompound2);
                nbtList.remove(i);
                if (nbtList.isEmpty()) {
                    stack.removeSubNbt(ITEMS_KEY);
                }
                return Optional.of(itemStack);
            }
        }

    }

    @Override
    public void onItemEntityDestroyed(ItemEntity entity) {
        ItemUsage.spawnItemContents(entity, getInsertedStacks(entity.getStack()));
    }

    @Unique
    private void playRemoveOneSound(PlayerEntity entity) {
        entity.playSound(SoundEvents.ITEM_BUNDLE_REMOVE_ONE, 0.8f, 0.8f + entity.getWorld().getRandom().nextFloat() * 0.4f);
    }

    @Unique
    private void playInsertSound(PlayerEntity entity) {
        entity.playSound(SoundEvents.ITEM_BUNDLE_INSERT, 0.8F, 0.8F + entity.getWorld().getRandom().nextFloat() * 0.4F);
    }

}
