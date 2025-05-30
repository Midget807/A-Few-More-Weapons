package net.midget807.afmweapons.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.authlib.GameProfile;
import net.midget807.afmweapons.datagen.ModItemTagProvider;
import net.midget807.afmweapons.effect.ModEffects;
import net.midget807.afmweapons.effect.afmw.client.ClientPlayerConcussAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends AbstractClientPlayerEntity implements ClientPlayerConcussAccessor {
    @Shadow @Final protected MinecraftClient client;

    @Shadow public abstract void closeHandledScreen();

    public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    // === Credit: From Amarite Mod ===

    @WrapOperation(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingItem()Z"))
    private boolean amarite$noMovementSlowWhenSwordBlocking(ClientPlayerEntity player, Operation<Boolean> original) {
        return original.call(player) && !player.getActiveItem().isIn(ModItemTagProvider.LONGSWORDS);
    }
    // ================================

    //Concussed Effect - Nausea


    @Override
    public float afmw$getConcussedIntensity() {
        return concussedIntensity;
    }

    @Override
    public float afmw$getPrevConcussedIntensity() {
        return prevConcussedIntensity;
    }

    @Unique
    public float prevConcussedIntensity;
    @Unique
    public float concussedIntensity;

    @Inject(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;updateNausea()V"), cancellable = true)
    public void afmw$updateConcussed(CallbackInfo ci) {
        this.updateConcussed();
    }

    @Unique
    private void updateConcussed() {
        this.prevConcussedIntensity = this.concussedIntensity;
        float f = 0.0f;
        if (this.inNetherPortal) {
            if (this.client.currentScreen != null && !this.client.currentScreen.shouldPause() && !(this.client.currentScreen instanceof DeathScreen)) {
                if (this.client.currentScreen instanceof HandledScreen) {
                    this.closeHandledScreen();
                }
                this.client.setScreen(null);
            }
            if (this.concussedIntensity == 0.0f) {
                this.client.getSoundManager().play(PositionedSoundInstance.ambient(SoundEvents.BLOCK_PORTAL_TRIGGER, this.random.nextFloat() * 0.4f + 0.8f, 0.25f));
            }
            f = 0.0125f;
            this.inNetherPortal = false;
        } else if (this.hasStatusEffect(ModEffects.CONCUSSED) && !this.getStatusEffect(ModEffects.CONCUSSED).isDurationBelow(60)) {
            f = 0.0066666667f;
        } else if (this.concussedIntensity > 0.0f) {
            f = -0.05f;
        }
        this.concussedIntensity = MathHelper.clamp(this.concussedIntensity + f, 0.0f, 1.0f);
        this.tickPortalCooldown();
    }
    @Inject(method = "removeStatusEffectInternal", at = @At("HEAD"), cancellable = true)
    public void afmw$removeConcussedInternal(StatusEffect type, CallbackInfoReturnable<StatusEffectInstance> cir) {
        if (type == ModEffects.CONCUSSED) {
            this.prevConcussedIntensity = 0.0f;
            this.concussedIntensity = 0.0f;
        }
    }

}






