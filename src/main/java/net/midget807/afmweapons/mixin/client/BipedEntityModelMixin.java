package net.midget807.afmweapons.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.midget807.afmweapons.datagen.ModItemTagProvider;
import net.midget807.afmweapons.item.ModItems;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BipedEntityModel.class)
public abstract class BipedEntityModelMixin<T extends LivingEntity> extends AnimalModel<T> {

    // === Credit: From Amarite Mod ===

    @Shadow protected abstract Arm getPreferredArm(T entity);

    @Shadow protected abstract ModelPart getArm(Arm arm);

    @Shadow @Final public ModelPart head;

    @Shadow @Final public ModelPart body;

    @WrapOperation(method = "setAngles(Lnet/minecraft/entity/LivingEntity;FFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;isUsingItem()Z", ordinal = 0))
    public boolean amarite$dualBlock(LivingEntity livingEntity, Operation<Boolean> original) {
        return livingEntity.getActiveItem().isIn(ModItemTagProvider.LONGSWORDS) ? false : (Boolean) original.call(livingEntity);
    }
    @Inject(method = "animateArms", at = @At("TAIL"), cancellable = true)
    protected void amarite$twoHandedHolding(T entity, float animationProgress, CallbackInfo ci) {
        ItemStack stack = entity.getMainHandStack();
        if ((stack.isIn(ModItemTagProvider.LONGSWORDS) || stack.isIn(ModItemTagProvider.HALBERDS)) && !(this.handSwingProgress <= 0.0F)) {
            Arm arm = this.getPreferredArm(entity) == Arm.RIGHT ? Arm.LEFT : Arm.RIGHT;
            ModelPart modelPart = this.getArm(arm);
            float f = 1.0F - this.handSwingProgress;
            f *= f;
            f *= f;
            f = 1.0F - f;
            float g = MathHelper.sin(f * MathHelper.PI);
            float h = MathHelper.sin(this.handSwingProgress * MathHelper.PI) * -(this.head.pitch - 0.7F) * 0.75F;
            modelPart.pitch -= g * 1.2F + h;
            modelPart.yaw += this.body.yaw * 2.0F;
            modelPart.roll += MathHelper.sin(this.handSwingProgress * MathHelper.PI) * -0.4F;
        }
    }
    // ================================

}
