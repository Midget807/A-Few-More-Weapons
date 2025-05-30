package net.midget807.afmweapons.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.DrownedEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TridentEntity.class)
public class TridentEntityMixin {
    @Shadow private ItemStack tridentStack;

    @Inject(
            method = "onEntityHit",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/TridentEntity;getOwner()Lnet/minecraft/entity/Entity;"),
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "Lnet/minecraft/util/hit/EntityHitResult;getEntity()Lnet/minecraft/entity/Entity;"),
                    to = @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/TridentEntity;getOwner()Lnet/minecraft/entity/Entity;")
            )
    )
    private void afmw$impalingFix(EntityHitResult entityHitResult, CallbackInfo ci, @Local(ordinal = 0) LocalFloatRef f) {
        Entity entity = entityHitResult.getEntity();
        if (entity instanceof DrownedEntity || (entity instanceof LivingEntity && entity.isWet())) {
            f.set(f.get() + EnchantmentHelper.getAttackDamage(this.tridentStack, EntityGroup.AQUATIC));
        }
    }
}
