package net.midget807.afmweapons.mixin;

import net.midget807.afmweapons.item.afmw.HalberdItem;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {

    @Inject(method = "getSweepingMultiplier", at = @At("TAIL"), cancellable = true)
    private static void afmw$ignoreSweepingMultiplier(LivingEntity entity, CallbackInfoReturnable<Float> cir) {
        float initValue = cir.getReturnValue();
        if (entity.getMainHandStack().getItem() instanceof HalberdItem) {
            cir.setReturnValue(((HalberdItem) entity.getActiveItem().getItem()).getAttackDamage());
        }
    }
}
