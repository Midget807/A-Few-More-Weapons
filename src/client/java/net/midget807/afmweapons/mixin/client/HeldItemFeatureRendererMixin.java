package net.midget807.afmweapons.mixin.client;

import net.fabricmc.loader.impl.lib.sat4j.core.Vec;
import net.midget807.afmweapons.datagen.ModItemTagProvider;
import net.midget807.afmweapons.item.ModItems;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.ModelWithArms;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector2f;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemFeatureRenderer.class)
public abstract class HeldItemFeatureRendererMixin<T extends LivingEntity, M extends EntityModel<T> & ModelWithArms> extends FeatureRenderer<T, M> {
    // === Credit: From Amarite Mod ===
    public HeldItemFeatureRendererMixin(FeatureRendererContext<T, M> context) {
        super(context);
    }

    @Inject(method = "renderItem", at = @At("HEAD"), cancellable = true)
    protected void amarite$twoHandedHolding(LivingEntity entity, ItemStack stack, ModelTransformationMode transformationMode, Arm arm, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        ItemStack main = entity.getMainHandStack();
        boolean mainArm = entity.getMainArm() == arm;
        if ((main.isIn(ModItemTagProvider.LONGSWORDS) || main.isIn(ModItemTagProvider.HALBERDS)) && !mainArm) {
            matrices.push();
            ((ModelWithArms)this.getContextModel()).setArmAngle(arm, matrices);
            matrices.pop();
            ci.cancel();
        } else {
            boolean leftArm = arm == Arm.LEFT;
        }
    }

    // ================================
}
