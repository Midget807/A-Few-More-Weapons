package net.midget807.afmweapons.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.midget807.afmweapons.effect.ModEffects;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LightmapTextureManager.class)
public class LightmapTextureManagerMixin {

    @Shadow @Final private MinecraftClient client;

    @ModifyExpressionValue(method = "getDarknessFactor", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;hasStatusEffect(Lnet/minecraft/entity/effect/StatusEffect;)Z"))
    private boolean afmw$getConcussedToo(boolean original) {
        return (original || this.client.player.hasStatusEffect(ModEffects.CONCUSSED));
    }

}
