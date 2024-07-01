package net.midget807.afmweapons.mixin;

import net.midget807.afmweapons.item.afmw.HalberdItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

    @Shadow public abstract void disableShield(boolean sprinting);

    @Inject(method = "takeShieldHit", at = @At("HEAD"))
    protected void afmw$halberdDisableShield(LivingEntity attacker, CallbackInfo ci) {
        if (attacker.getMainHandStack().getItem() instanceof HalberdItem) {
            this.disableShield(true);
        }
    }

}
