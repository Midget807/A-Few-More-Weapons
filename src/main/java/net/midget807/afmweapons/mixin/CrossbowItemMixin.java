package net.midget807.afmweapons.mixin;

import net.midget807.afmweapons.enchantment.ModEnchantments;
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
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

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

    /*
        @Inject(method = "shoot", at = @At("HEAD"),cancellable = true)
        private static void afmw$tripleShot(World world, LivingEntity shooter, Hand hand, ItemStack crossbow, ItemStack projectile, float soundPitch, boolean creative, float speed, float divergence, float simulated, CallbackInfo ci) {
            if (!world.isClient) {
                int tripleShotLevel = EnchantmentHelper.getLevel(ModEnchantments.TRIPLE_SHOT, crossbow);
                if (tripleShotLevel > 0) {
                    ProjectileEntity projectileEntity;
                    projectileEntity = createArrow(world, shooter, crossbow, projectile);
                    world.spawnEntity(projectileEntity);
                }
            }
        }
        */
    @Unique
    private static boolean afmw$hasTripleShot(LivingEntity shooter, ItemStack crossbow) {
        if (shooter.getStackInHand(shooter.preferredHand).isOf(Items.CROSSBOW)) {
            int i = EnchantmentHelper.getLevel(ModEnchantments.TRIPLE_SHOT, crossbow);
            return i != 0;
        } else {
            return false;
        }
    }
    @Unique
    private static int afmw$numberOfShots;

    @Inject(method = "use", at = @At(value = "HEAD"), cancellable = true)
    private void afmw$tripleShot(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        ItemStack stack = user.getStackInHand(hand);
        user.sendMessage(Text.literal("Level Test 1: " + EnchantmentHelper.getLevel(ModEnchantments.TRIPLE_SHOT, stack)));
        if (afmw$hasTripleShot(user, stack)) {
            user.sendMessage(Text.literal("Level Test 2: " + EnchantmentHelper.getLevel(ModEnchantments.TRIPLE_SHOT, stack)));
            setCharged(stack, true);
            cir.setReturnValue(TypedActionResult.consume(stack));
        }
    }

}
