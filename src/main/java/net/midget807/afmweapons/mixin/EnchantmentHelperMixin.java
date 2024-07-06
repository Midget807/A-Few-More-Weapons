package net.midget807.afmweapons.mixin;

import net.midget807.afmweapons.item.afmw.HalberdItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {

    @Inject(method = "getSweepingMultiplier", at = @At("HEAD"), cancellable = true)
    private static void afmw$ignoreSweepingMultiplier(LivingEntity entity, CallbackInfoReturnable<Float> cir) {
        if (entity.getMainHandStack().getItem() instanceof HalberdItem) {
            cir.setReturnValue(1.0F);
        }
    }
}
