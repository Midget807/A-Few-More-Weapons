package net.midget807.afmweapons.mixin;

import net.midget807.afmweapons.item.afmw.HalberdItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
    @Shadow public abstract void attack(Entity target);

    @Shadow public abstract void disableShield(boolean sprinting);

    @Inject(method = "takeShieldHit", at = @At("HEAD"))
    protected void halberdDisableShield(LivingEntity attacker, CallbackInfo ci) {
        if (attacker.getMainHandStack().getItem() instanceof HalberdItem) {
            this.disableShield(true);
        }
    }
}
