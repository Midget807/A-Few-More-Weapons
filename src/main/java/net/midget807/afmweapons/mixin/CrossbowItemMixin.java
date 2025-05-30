package net.midget807.afmweapons.mixin;

import net.midget807.afmweapons.enchantment.ModEnchantments;
import net.midget807.afmweapons.item.afmw.client.TripleShotTooltipData;
import net.minecraft.client.item.TooltipData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
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
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;
import java.util.function.Predicate;
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
        if (projectile.isEmpty()) {
            return false;
        } else {
            boolean bl = creative && projectile.getItem() instanceof ArrowItem;
            ItemStack itemStack;
            if (!bl && !creative && !simulated) {
                itemStack = projectile.split(1);
                if (projectile.isEmpty() && shooter instanceof PlayerEntity) {
                    ((PlayerEntity)shooter).getInventory().removeOne(projectile);
                }
            } else {
                itemStack = projectile.copy();
            }

            putProjectile(crossbow, itemStack);
            return true;
        }
    }

    @Shadow
    private static void putProjectile(ItemStack crossbow, ItemStack projectile) {
    }

    @Shadow public abstract Predicate<ItemStack> getProjectiles();


    @Shadow private boolean charged;

    @Shadow private boolean loaded;

    @Shadow
    public static boolean isCharged(ItemStack stack) {
        NbtCompound nbtCompound = stack.getNbt();
        return nbtCompound != null && nbtCompound.getBoolean("Charged");
    }

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
            removeAmountFromTripleShot(stack, 0);
        }
    }

    @Redirect(method = "loadProjectiles", at = @At(value = "INVOKE", target ="Lnet/minecraft/entity/LivingEntity;getProjectileType(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/item/ItemStack;"))
    private static ItemStack afmw$loadInsertedProjectile(LivingEntity shooter, ItemStack crossbow) {
        DefaultedList<ItemStack> defaultedList = DefaultedList.of();
        getInsertedStacks(crossbow).forEach(defaultedList::add);
        if (!defaultedList.isEmpty()) {
            return defaultedList.get(0);
        } else {
            return shooter.getProjectileType(crossbow);
        }
    }

    @Inject(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/TypedActionResult;fail(Ljava/lang/Object;)Lnet/minecraft/util/TypedActionResult;"), cancellable = true)
    private void afmw$loadFirstShot(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        ItemStack crossbow = user.getStackInHand(hand);
        DefaultedList<ItemStack> defaultedList = DefaultedList.of();
        getInsertedStacks(crossbow).forEach(defaultedList::add);
        if (!defaultedList.isEmpty()) {
            if (!isCharged(crossbow)) {
                this.charged = false;
                this.loaded = false;
                user.setCurrentHand(hand);
            }
            cir.setReturnValue(TypedActionResult.consume(crossbow));
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
            if (!user.isCreative()) {
                if (!defaultedList.isEmpty()) {
                    if (afmw$numberOfShots == 2 && !defaultedList.get(1).isEmpty()) {
                        removeAmountFromTripleShot(crossbow, 1);
                    } else if (afmw$numberOfShots == 1 && !defaultedList.get(2).isEmpty()) {
                        removeAmountFromTripleShot(crossbow, 2);
                    }
                } else {
                    projectile.split(1);
                    if (projectile.isEmpty() && user instanceof PlayerEntity) {
                        user.getInventory().removeOne(projectile);
                    }
                }
            }
            loadProjectile(user, crossbow, projectile, true, false);
            setCharged(crossbow,true);
            cir.setReturnValue(TypedActionResult.consume(crossbow));
        }
    }

    //Triple Shot Storage ===


    @Unique
    private static void removeAmountFromTripleShot(ItemStack crossbow, int index) {
        NbtCompound crossbowNbt = crossbow.getOrCreateNbt();
        if (crossbowNbt.contains(ITEMS_KEY)) {
            NbtList nbtList = crossbowNbt.getList(ITEMS_KEY, NbtElement.COMPOUND_TYPE);
            if (!nbtList.isEmpty()) {
                NbtCompound stackNbt = nbtList.getCompound(index);
                ItemStack stack = ItemStack.fromNbt(stackNbt);
                stack.decrement(1);
                stack.writeNbt(stackNbt);
                nbtList.remove(stackNbt);
                nbtList.add(index, stackNbt);
            }
        }
    }

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

    //Crossbow Stack clicks Arrow Stack
    @Override
    public boolean onStackClicked(ItemStack crossbowStack, Slot slot, ClickType clickType, PlayerEntity player) {
        DefaultedList<ItemStack> defaultedList = DefaultedList.of();
        getInsertedStacks(crossbowStack).forEach(defaultedList::add);
        if (clickType != ClickType.RIGHT) {
            return false;
        } else {
            ItemStack slotStack = slot.getStack();
            if (slotStack.isEmpty()) {
                this.playRemoveOneSound(player);
                removeFirstStack(crossbowStack).ifPresent(removedStack -> addToCrossbow(crossbowStack, slot.insertStack(removedStack)));
            } else if (slotStack.getItem().canBeNested() && slotStack.isIn(ItemTags.ARROWS) && afmw$hasTripleShot(crossbowStack) && defaultedList.size() < 3) {

                int j = addToCrossbow(crossbowStack, slotStack);
                if (j > 0) {
                    slotStack.decrement(j);
                    this.playInsertSound(player);
                }
            }
            return true;
        }
    }

    @Unique
    private static int getI(ItemStack itemStack, DefaultedList<ItemStack> defaultedList) {
        int i;
        if (!defaultedList.isEmpty()) {
            if (itemStack.isOf(defaultedList.get(0).getItem()) && itemStack.getName() == defaultedList.get(0).getName()) {
                i = (64 - defaultedList.get(0).getCount());
            } else if (defaultedList.size() > 1 && itemStack.isOf(defaultedList.get(1).getItem()) && itemStack.getName() == defaultedList.get(1).getName()) {
                i = (64 - defaultedList.get(1).getCount());
            } else if (defaultedList.size() > 2 && itemStack.isOf(defaultedList.get(2).getItem()) && itemStack.getName() == defaultedList.get(2).getName()) {
                i = (64 - defaultedList.get(2).getCount());
            } else {
                i = itemStack.getCount();
            }
        } else {
            i = itemStack.getCount();
        }
        return i;
    }

    //Arrow Stack Clicks Crossbow Stack
    @Override
    public boolean onClicked(ItemStack crossbowStack, ItemStack arrowStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        DefaultedList<ItemStack> defaultedList = DefaultedList.of();
        getInsertedStacks(crossbowStack).forEach(defaultedList::add);
        if (clickType == ClickType.RIGHT && slot.canTakePartial(player)) {
            if (arrowStack.isEmpty()) {
                removeFirstStack(crossbowStack).ifPresent(itemStack -> {
                    this.playRemoveOneSound(player);
                    cursorStackReference.set(itemStack);
                });
            } else if (arrowStack.isIn(ItemTags.ARROWS) && afmw$hasTripleShot(crossbowStack) && defaultedList.size() < 3){
                int i = addToCrossbow(crossbowStack, arrowStack);
                if (i > 0) {
                    this.playInsertSound(player);
                    arrowStack.decrement(i);
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
        if (!stack.isEmpty() && stack.getItem().canBeNested() && stack.isIn(ItemTags.ARROWS) && afmw$hasTripleShot(crossbow) && defaultedList.size() <= 3) {
            NbtCompound nbtCompound = crossbow.getOrCreateNbt();
            if (!nbtCompound.contains(ITEMS_KEY)) {
                nbtCompound.put(ITEMS_KEY, new NbtList());
            }
            NbtList nbtList = nbtCompound.getList(ITEMS_KEY, NbtElement.COMPOUND_TYPE);
            Optional<NbtCompound> optional = canMergeStack(stack, nbtList);
            if (optional.isPresent()) {
                NbtCompound nbtCompound2 = (NbtCompound) optional.get();
                ItemStack stackInCrossbow = ItemStack.fromNbt(nbtCompound2);
                int k = Math.min(64 - stackInCrossbow.getCount(), stack.getCount());
                if (k == 0) {
                    return 0;
                }
                stackInCrossbow.increment(k);
                stackInCrossbow.writeNbt(nbtCompound2);
                nbtList.remove(nbtCompound2);
                nbtList.add(0, nbtCompound2);
                return  k;
            } else {
                int k  = stack.getCount();
                if (k == 0) {
                    return 0;
                }
                ItemStack itemStack2 = stack.copyWithCount(k);
                NbtCompound nbtCompound3 = new NbtCompound();
                itemStack2.writeNbt(nbtCompound3);
                nbtList.add(0, nbtCompound3);
                return k;
            }
        } else {
            return 0;
        }
    }

    @Unique
    private static int getK(ItemStack stack, DefaultedList<ItemStack> defaultedList) {
        int k;
        if (!defaultedList.isEmpty()) {
            if (stack.isOf(defaultedList.get(0).getItem()) && stack.getName() == defaultedList.get(0).getName()) {
                k = Math.min((64 - defaultedList.get(0).getCount()), stack.getCount());
            } else if (defaultedList.size() > 1 && stack.isOf(defaultedList.get(1).getItem()) && stack.getName() == defaultedList.get(1).getName()) {
                k = Math.min((64 - defaultedList.get(1).getCount()), stack.getCount());
            }  else if (defaultedList.size() > 2 && stack.isOf(defaultedList.get(2).getItem()) && stack.getName() == defaultedList.get(2).getName()) {
                k = Math.min((64 - defaultedList.get(2).getCount()), stack.getCount());
            } else {
                k = 0;
            }
        } else {
            k = stack.getCount();
        }
        return k;
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
