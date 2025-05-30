package net.midget807.afmweapons.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.midget807.afmweapons.effect.ModEffects;
import net.midget807.afmweapons.effect.afmw.client.ClientPlayerConcussAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.BufferBuilderStorage;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.joml.Math;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin implements AutoCloseable {
    @Shadow @Final
    MinecraftClient client;

    @Shadow @Final private BufferBuilderStorage buffers;
    @Shadow private int ticks;

    @Unique
    private static final Identifier CONCUSSED_OVERLAY = new Identifier("textures/misc/nausea.png");


    //Concussed Effect - Nausea
    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;lerp(FFF)F"))
    public void afmw$renderConcussed(float tickDelta, long startTime, boolean tick, CallbackInfo ci) {
        float concussedIntensity = 0.0f;
        float prevConcussedIntensity = 0.0f;
        if (this.client.player instanceof ClientPlayerConcussAccessor) {
            concussedIntensity = ((ClientPlayerConcussAccessor) this.client.player).afmw$getConcussedIntensity();
            prevConcussedIntensity = ((ClientPlayerConcussAccessor) this.client.player).afmw$getPrevConcussedIntensity();
        }
        float fConcussed = MathHelper.lerp(tickDelta, prevConcussedIntensity, concussedIntensity);
        float gConcussed = this.client.options.getDistortionEffectScale().getValue().floatValue();
        if (fConcussed > 0.0f && this.client.player.hasStatusEffect(ModEffects.CONCUSSED) && gConcussed < 1.0f) {
            this.renderConcussed(new DrawContext(this.client, this.buffers.getEntityVertexConsumers()), fConcussed * (1.0f - gConcussed));
        }
    }
    @Inject(method = "renderWorld", at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/GameRenderer;viewDistance:F"))
    private void afmw$renderWorldConcussed(float tickDelta, long limitTime, MatrixStack matrices, CallbackInfo ci, @Local LocalRef<MatrixStack> localRef) {
        float concussedIntensity = 0.0f;
        float prevConcussedIntensity = 0.0f;
        if (this.client.player instanceof ClientPlayerConcussAccessor) {
            concussedIntensity = ((ClientPlayerConcussAccessor) this.client.player).afmw$getConcussedIntensity();
            prevConcussedIntensity = ((ClientPlayerConcussAccessor) this.client.player).afmw$getPrevConcussedIntensity();
        }
        float fConcussed = this.client.options.getDistortionEffectScale().getValue().floatValue();
        float gConcussed = MathHelper.lerp(tickDelta, prevConcussedIntensity, concussedIntensity) * fConcussed * fConcussed;
        if (gConcussed > 0.0f) {
            int i = this.client.player.hasStatusEffect(ModEffects.CONCUSSED) ? 7 : 20;
            float h = 5.0f / (gConcussed * gConcussed + 5.0f) - gConcussed * 0.04f;
            h *= h;
            RotationAxis rotationAxis = RotationAxis.of(new Vector3f(0.0f, MathHelper.SQUARE_ROOT_OF_TWO / 2.0f, MathHelper.SQUARE_ROOT_OF_TWO / 2.0f));
            MatrixStack matrixStack = new MatrixStack();
            matrixStack.multiply(rotationAxis.rotationDegrees(((float) this.ticks + tickDelta) * (float) i));
            matrixStack.scale(1.0f / h, 1.0f, 1.0f);
            float j = - ((float) this.ticks + tickDelta) * (float) i;
            matrixStack.multiply(rotationAxis.rotationDegrees(j));
            localRef.set(matrixStack);
        }

    }

    @Unique
    private void renderConcussed(DrawContext context, float distortionStrength) {
        int i = context.getScaledWindowWidth();
        int j = context.getScaledWindowHeight();
        context.getMatrices().push();
        float f = MathHelper.lerp(distortionStrength, 2.0F, 1.0F);
        context.getMatrices().translate((float)i / 2.0F, (float)j / 2.0F, 0.0F);
        context.getMatrices().scale(f, f, f);
        context.getMatrices().translate((float)(-i) / 2.0F, (float)(-j) / 2.0F, 0.0F);
        float g = 0.2F * distortionStrength;
        float h = 0.4F * distortionStrength;
        float k = 0.2F * distortionStrength;
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ONE, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ONE);
        context.setShaderColor(g, h, k, 1.0F);
        context.drawTexture(CONCUSSED_OVERLAY, 0, 0, -90, 0.0F, 0.0F, i, j, i, j);
        context.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        context.getMatrices().pop();
    }
}
