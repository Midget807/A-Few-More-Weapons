package net.midget807.afmweapons.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.midget807.afmweapons.datagen.ModItemTagProvider;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {

    // === Credit: From Amarite Mod ===

    @WrapOperation(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingItem()Z"))
    private boolean afmw$noMovementSlowWhenSwordBlocking(ClientPlayerEntity player, Operation<Boolean> original) {
        return original.call(player) && !player.getActiveItem().isIn(ModItemTagProvider.LONGSWORDS);
    }
    // ================================

    //Concussed Effect - Nausea

}
