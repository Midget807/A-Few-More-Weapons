package net.midget807.afmweapons.mixin.client;

import net.midget807.afmweapons.component.afmw.LongswordComponent;
import net.midget807.afmweapons.datagen.ModItemTagProvider;
import net.midget807.afmweapons.item.ModItems;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {

    // === Credit: From Amarite Mod ===

    public PlayerEntityRendererMixin(EntityRendererFactory.Context ctx, PlayerEntityModel<AbstractClientPlayerEntity> model, float shadowRadius) {
        super(ctx, model, shadowRadius);
    }

    @Inject(method = "getArmPose", at = @At("HEAD"), cancellable = true)
    private static void afmw$swordPoses(AbstractClientPlayerEntity player, Hand hand, CallbackInfoReturnable<BipedEntityModel.ArmPose> cir) {
        ItemStack main = player.getMainHandStack();
        LongswordComponent longswordComponent = LongswordComponent.get(player);
        if (main.isIn(ModItemTagProvider.LONGSWORDS) || main.isIn(ModItemTagProvider.HALBERDS)) {
            boolean blocking = longswordComponent.isBlocking();
            if (hand == Hand.MAIN_HAND) {
                if (blocking) {
                    cir.setReturnValue(BipedEntityModel.ArmPose.BOW_AND_ARROW);
                } else {
                    cir.setReturnValue(BipedEntityModel.ArmPose.BLOCK);
                }
            } else if (blocking) {
                cir.setReturnValue(BipedEntityModel.ArmPose.BOW_AND_ARROW);
            } else {
                cir.setReturnValue(BipedEntityModel.ArmPose.CROSSBOW_CHARGE);
            }
        }
    }

    // ================================

    @Inject(method = "getArmPose", at = @At("HEAD"), cancellable = true)
    private static void afmw$fryingPanPose(AbstractClientPlayerEntity player, Hand hand, CallbackInfoReturnable<BipedEntityModel.ArmPose> cir) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.isOf(ModItems.FRYING_PAN)) {
            if (player.isUsingItem()) {
                cir.setReturnValue(BipedEntityModel.ArmPose.THROW_SPEAR);
            } else {
                cir.setReturnValue(BipedEntityModel.ArmPose.BLOCK);
            }
        }
    }
}
