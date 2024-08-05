package net.midget807.afmweapons.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.midget807.afmweapons.component.afmw.LongswordComponent;
import net.midget807.afmweapons.datagen.ModItemTagProvider;
import net.midget807.afmweapons.item.afmw.HalberdItem;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.mob.DrownedEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Shadow public abstract void disableShield(boolean sprinting);

    @Shadow public abstract boolean isInvulnerableTo(DamageSource damageSource);

    @Inject(method = "takeShieldHit", at = @At("HEAD"))
    protected void afmw$halberdDisableShield(LivingEntity attacker, CallbackInfo ci) {
        if (attacker.getMainHandStack().getItem() instanceof HalberdItem) {
            this.disableShield(true);
        }
    }

    // === Credit: From Amarite mod ===
    @WrapOperation(method = "applyDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;modifyAppliedDamage(Lnet/minecraft/entity/damage/DamageSource;F)F"))
    private float afmw$longswordBlock(PlayerEntity entity, DamageSource source, float amount, Operation<Float> original) {
        float base = (Float)original.call(entity, source, amount);
        LongswordComponent longswordComponent = LongswordComponent.get(entity);
        if (!source.isIn(DamageTypeTags.BYPASSES_EFFECTS)) {
            if (longswordComponent.isBlocking()) {
                Vec3d damagePos = source.getPosition();
                Vec3d rotVec;
                Vec3d difference;
                double angle;
                ServerWorld serverWorld;
                World var13;
                if (damagePos != null) {
                    rotVec = this.getRotationVector();
                    difference = damagePos.relativize(this.getEyePos()).normalize();
                    angle = difference.dotProduct(rotVec);
                    if (angle < -1.0 || angle > 1.0) {
                        return base;
                    }

                    if (angle < -0.35) {
                        var13 = this.getEntityWorld();
                        if (var13 instanceof ServerWorld) {
                            serverWorld = ((ServerWorld) var13);
                            serverWorld.playSoundFromEntity((PlayerEntity) null, this, SoundEvents.BLOCK_ANVIL_PLACE, SoundCategory.HOSTILE, 0.5F /*TODO add longsword block sound and adjust volume*/, 1.0F + this.getEntityWorld().random.nextFloat() * 0.4F);
                        }
                        return base / 2.0F;
                    }
                } else if (source.isOf(DamageTypes.FALL)) {
                    rotVec = this.getRotationVector();
                    difference = new Vec3d(0.0, 1.0f, 0.0);
                    angle = difference.dotProduct(rotVec);
                    if (angle< -0.35) {
                        var13 = this.getEntityWorld();
                        if (var13 instanceof ServerWorld) {
                            serverWorld = ((ServerWorld) var13);
                            serverWorld.playSoundFromEntity((PlayerEntity) null, this, SoundEvents.BLOCK_ANVIL_PLACE, SoundCategory.HOSTILE, 0.5F /*TODO add longsword block sound and adjust volume*/, 1.0F + this.getEntityWorld().random.nextFloat() * 0.4F);
                        }
                        return base / 0.6F;
                    }

                }
            }
        }
        return base;
    }

    // ================================

    @Inject(method = "createPlayerAttributes", at = @At("HEAD"), cancellable = true)
    private static void afmw$initGenericKnockback(CallbackInfoReturnable<DefaultAttributeContainer.Builder> cir) {
        cir.setReturnValue(createLivingAttributes()
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.1F)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED)
                .add(EntityAttributes.GENERIC_LUCK)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK)
        );
    }
    @Inject(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;takeKnockback(DDD)V"), cancellable = true)
    private void afmw$addKBAttributeToCalc(Entity target, CallbackInfo ci) {
        int i = 0;
        i += EnchantmentHelper.getKnockback(this);
        ((LivingEntity) target).takeKnockback(
                (i + this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_KNOCKBACK)) * 0.5,
                (double) MathHelper.sin(this.getYaw() * (float) (Math.PI / 180.0)),
                (double)(-MathHelper.cos(this.getYaw() * (float) (Math.PI / 180.0))));

    }

    //May be implemented

    @Inject(
            method = "attack",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;isSprinting()Z",
                    shift = At.Shift.AFTER
            ),
            slice = @Slice(
                    from = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/PlayerEntity;fallDistance:F"),
                    to = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getFireAspect(Lnet/minecraft/entity/LivingEntity;)I")
            )
    )
    private void afmw$halberdIgnoresCrit(Entity target, CallbackInfo ci, @Local(ordinal = 2) LocalBooleanRef bl3) {
        if (this.getStackInHand(Hand.MAIN_HAND).isIn(ModItemTagProvider.HALBERDS)) {
            bl3.set(true);
        }
    }

    /*@ModifyVariable(
            method = "attack",
            at = @At("STORE"),
            slice = @Slice(
                    from = @At(value = "FIELD", target = "Lnet/minecraft/entity/attribute/EntityAttributes;GENERIC_ATTACK_DAMAGE:Lnet/minecraft/entity/attribute/EntityAttribute;", shift = At.Shift.BY, by = 2),
                    to = @At(value = "FIELD", target = "Lnet/minecraft/entity/EntityGroup;DEFAULT:Lnet/minecraft/entity/EntityGroup;")
            ),
            ordinal = 0
    )
    private float afmw$impaling(float value, Entity target) {
        if (target instanceof DrownedEntity) {
            return 2.5F;
        } else {
            return value;
        }
    }*/
}
