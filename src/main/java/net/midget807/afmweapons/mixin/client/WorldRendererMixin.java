package net.midget807.afmweapons.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.midget807.afmweapons.effect.ModEffects;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {
    @Inject(method = "hasBlindnessOrDarkness", at = @At("RETURN"), cancellable = true)
    private void afmw$hasConcussedToo(Camera camera, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(!(camera.getFocusedEntity() instanceof LivingEntity livingEntity) ? cir.getReturnValue() : livingEntity.hasStatusEffect(ModEffects.CONCUSSED));
    }

}
