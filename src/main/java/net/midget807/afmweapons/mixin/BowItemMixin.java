package net.midget807.afmweapons.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.midget807.afmweapons.item.afmw.arrow.FrostArrowItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BowItem.class)
public class BowItemMixin {
    /*@Inject(method = "onStoppedUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ArrowItem;createArrow(Lnet/minecraft/world/World;Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/LivingEntity;)Lnet/minecraft/entity/projectile/PersistentProjectileEntity;", shift = At.Shift.AFTER), slice = @Slice(from = @At(value = "HEAD"), to = @At(value = "FIELD", target = "Lnet/minecraft/enchantment/Enchantments;POWER:Lnet/minecraft/enchantment/Enchantment;")))
    private void afmw$addCustomArrows(ItemStack stack, World world, LivingEntity user, int remainingUseTicks, CallbackInfo ci, @Local LocalRef<PersistentProjectileEntity> projectileEntityLocalRef) {
        PlayerEntity player = ((PlayerEntity) user);
        ItemStack itemStack = player.getProjectileType(stack);
        FrostArrowItem frostArrowItem = (FrostArrowItem) (itemStack.getItem() instanceof FrostArrowItem ? itemStack.getItem() : Items.ARROW);
        projectileEntityLocalRef.set(frostArrowItem.createArrow(world, itemStack, player));
    }*/
    /*@WrapOperation(method = "onStoppedUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ArrowItem;createArrow(Lnet/minecraft/world/World;Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/LivingEntity;)Lnet/minecraft/entity/projectile/PersistentProjectileEntity;"))
    public PersistentProjectileEntity afmw$changeArrow(ArrowItem instance, World world, ItemStack stack, LivingEntity shooter, Operation<PersistentProjectileEntity> original) {
        if (shooter instanceof PlayerEntity && !world.isClient) {
            ItemStack itemStack = shooter.getProjectileType(stack);
            FrostArrowItem frostArrowItem = (FrostArrowItem) (itemStack.getItem() instanceof FrostArrowItem ? itemStack.getItem() : Items.ARROW);
            return original.call(frostArrowItem, world, stack, shooter);
        }
        return original.call(instance, world, stack, shooter);
    }*/
}
