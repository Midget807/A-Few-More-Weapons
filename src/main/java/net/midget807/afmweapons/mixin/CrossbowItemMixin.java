package net.midget807.afmweapons.mixin;

import net.midget807.afmweapons.enchantment.ModEnchantments;
import net.midget807.afmweapons.item.afmw.client.TripleShotTooltipData;
import net.minecraft.client.item.TooltipData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Optional;
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
        }
    }

    @Inject(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/CrossbowItem;setCharged(Lnet/minecraft/item/ItemStack;Z)V", shift = At.Shift.AFTER), cancellable = true)
    private void afmw$tripleShot(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        ItemStack crossbow = user.getStackInHand(hand);
        ItemStack projectile = user.getProjectileType(crossbow);

        if (afmw$hasTripleShot(crossbow) && afmw$numberOfShots >= 0) {
            afmw$numberOfShots--;
            if (!user.isCreative()) {
                projectile.split(1);
                if (projectile.isEmpty() && user instanceof PlayerEntity) {
                    user.getInventory().removeOne(projectile);
                }
            }
            loadProjectile(user, crossbow, user.getProjectileType(crossbow), true, false);
            setCharged(crossbow,true);
            cir.setReturnValue(TypedActionResult.consume(crossbow));
        }
    }
    @Override
    public Optional<TooltipData> getTooltipData(ItemStack stack) {
        DefaultedList<ItemStack> defaultedList = DefaultedList.of();
        getInsertedStacks(stack).forEach(defaultedList::add);
        return Optional.of(new TripleShotTooltipData(defaultedList, getCrossbowOccupancy(stack)));
    }

    @Unique
    private static int getCrossbowOccupancy(ItemStack stack) {
        return getInsertedStacks(stack).mapToInt(itemstack -> getItemOccupancy(itemstack) * itemstack.getCount()).sum();
    }

    @Unique
    private static int getItemOccupancy(ItemStack itemstack) {
        if (itemstack.isOf(Items.CROSSBOW)) {
            return 4 + getCrossbowOccupancy(itemstack);
        } else {
            return 64 / itemstack.getMaxCount();
        }
    }

    @Unique
    private static Stream<ItemStack> getInsertedStacks(ItemStack stack) {
        NbtCompound nbtCompound = stack.getNbt();
        if (nbtCompound == null) {
            return Stream.empty();
        } else {
            NbtList nbtList = nbtCompound.getList("TripleShotItems", NbtElement.COMPOUND_TYPE);
            return nbtList.stream().map(NbtCompound.class::cast).map(ItemStack::fromNbt);
        }
    }

}
