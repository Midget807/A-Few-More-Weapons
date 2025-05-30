package net.midget807.afmweapons.mixin.client;

import net.midget807.afmweapons.effect.ModEffects;
import net.midget807.afmweapons.effect.afmw.client.ClientPlayerConcussAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {

    @Shadow @Final private MinecraftClient client;

    @Shadow protected abstract void renderPortalOverlay(DrawContext context, float nauseaStrength);

    //Concussed Effect - Nausea
    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;hasStatusEffect(Lnet/minecraft/entity/effect/StatusEffect;)Z"))
    public void afmw$renderConcussed(DrawContext context, float tickDelta, CallbackInfo ci) {
        float concussedIntensity = 0.0f;
        float prevConcussedIntensity = 0.0f;
        if (this.client.player instanceof ClientPlayerConcussAccessor) {
            concussedIntensity = ((ClientPlayerConcussAccessor) this.client.player).afmw$getConcussedIntensity();
            prevConcussedIntensity = ((ClientPlayerConcussAccessor) this.client.player).afmw$getPrevConcussedIntensity();
        }
        float gConcussed = MathHelper.lerp(tickDelta, prevConcussedIntensity, concussedIntensity);
        if (gConcussed > 0.0F && !this.client.player.hasStatusEffect(ModEffects.CONCUSSED)) {
            this.renderPortalOverlay(context, gConcussed);
        }
    }

}
