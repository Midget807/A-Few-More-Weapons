package net.midget807.afmweapons.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.midget807.afmweapons.cca.afmw.LongswordComponent;
import net.midget807.afmweapons.cca.afmw.LanceComponent;
import net.midget807.afmweapons.datagen.ModItemTagProvider;
import net.midget807.afmweapons.item.ModItems;
import net.midget807.afmweapons.item.afmw.HalberdItem;
import net.midget807.afmweapons.sound.ModSoundEvents;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Shadow public abstract void disableShield(boolean sprinting);

    @Shadow public abstract boolean isInvulnerableTo(DamageSource damageSource);

    @Shadow public abstract boolean isPlayer();

    @Shadow public abstract void remove(RemovalReason reason);

    @Shadow public abstract ItemCooldownManager getItemCooldownManager();
    @Shadow public abstract void takeShieldHit(LivingEntity attacker);
    @Shadow protected abstract void applyDamage(DamageSource source, float amount);

    @Inject(method = "takeShieldHit", at = @At("HEAD"))
    protected void afmw$halberdDisableShield(LivingEntity attacker, CallbackInfo ci) {
        if (attacker.getMainHandStack().getItem() instanceof HalberdItem) {
            this.disableShield(true);
        }
    }

    // === Credit: From Amarite mod ===
    @WrapOperation(method = "applyDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;modifyAppliedDamage(Lnet/minecraft/entity/damage/DamageSource;F)F"))
    private float afmw$swordsBlock(PlayerEntity entity, DamageSource source, float amount, Operation<Float> original) {
        float base = (Float) original.call(entity, source, amount);
        LongswordComponent longswordComponent = LongswordComponent.get(entity);
        //ClaymoreComponent claymoreComponent = ClaymoreComponent.get(entity);
        if (entity.getMainHandStack().isIn(ModItemTagProvider.LONGSWORDS)) {
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
                                if (entity.getMainHandStack().isOf(ModItems.WOODEN_LONGSWORD)) {
                                    serverWorld.playSoundFromEntity((PlayerEntity) null, this, ModSoundEvents.LONGSWORD_BLOCK_NORMAL, SoundCategory.HOSTILE, 1.0F, 1.5F + this.getEntityWorld().random.nextFloat() * 0.4F);
                                } else if (entity.getMainHandStack().isOf(ModItems.NETHERITE_LONGSWORD)) {
                                    serverWorld.playSoundFromEntity((PlayerEntity) null, this, ModSoundEvents.LONGSWORD_BLOCK_NETHERITE, SoundCategory.HOSTILE, 0.5F, 0.5F + this.getEntityWorld().random.nextFloat() * 0.2F);
                                } else {
                                    serverWorld.playSoundFromEntity((PlayerEntity) null, this, ModSoundEvents.LONGSWORD_BLOCK_NORMAL, SoundCategory.HOSTILE, 1.0F, 3.0F + this.getEntityWorld().random.nextFloat() * 0.4F);
                                }
                            }
                            return base / 2.0F;
                        }
                    } else if (source.isOf(DamageTypes.FALL)) {
                        rotVec = this.getRotationVector();
                        difference = new Vec3d(0.0, 1.0f, 0.0);
                        angle = difference.dotProduct(rotVec);
                        if (angle < -0.35) {
                            var13 = this.getEntityWorld();
                            if (var13 instanceof ServerWorld) {
                                serverWorld = ((ServerWorld) var13);
                                if (entity.getMainHandStack().isOf(ModItems.WOODEN_LONGSWORD)) {
                                    serverWorld.playSoundFromEntity((PlayerEntity) null, this, ModSoundEvents.LONGSWORD_BLOCK_NORMAL, SoundCategory.HOSTILE, 1.0F, 1.5F + this.getEntityWorld().random.nextFloat() * 0.4F);
                                } else if (entity.getMainHandStack().isOf(ModItems.NETHERITE_LONGSWORD)) {
                                    serverWorld.playSoundFromEntity((PlayerEntity) null, this, ModSoundEvents.LONGSWORD_BLOCK_NETHERITE, SoundCategory.HOSTILE, 0.5F, 0.5F + this.getEntityWorld().random.nextFloat() * 0.2F);
                                } else {
                                    serverWorld.playSoundFromEntity((PlayerEntity) null, this, ModSoundEvents.LONGSWORD_BLOCK_NORMAL, SoundCategory.HOSTILE, 1.0F, 3.0F + this.getEntityWorld().random.nextFloat() * 0.4F);
                                }
                            }
                            return base / 0.6F;
                        }

                    }
                }
            }
        } /*else if (entity.getMainHandStack().isIn(ModItemTagProvider.CLAYMORES)) {
            if (!source.isIn(DamageTypeTags.BYPASSES_SHIELD)) {
                if (claymoreComponent.isBlocking()) {
                    Vec3d damagePos = source.getPosition();
                    Vec3d rotVec;
                    Vec3d difference;
                    double angle;
                    ServerWorld serverWorld;
                    World world;
                    if (damagePos != null) {
                        rotVec = this.getRotationVector();
                        difference = damagePos.relativize(this.getEyePos()).normalize();
                        angle = difference.dotProduct(rotVec);
                        if (angle < -0.8 || angle > 0.8) {
                            return base;
                        }
                        if (angle < -0.35) {
                            world = this.getEntityWorld();
                            if (world instanceof ServerWorld) {
                                serverWorld = (ServerWorld) world;
                                if (entity.getMainHandStack().isOf(ModItems.WOODEN_LONGSWORD)) {// TODO: 9/09/2024 use same sound but maybe deeper and change lang
                                    serverWorld.playSoundFromEntity((PlayerEntity) null, this, ModSoundEvents.LONGSWORD_BLOCK_NORMAL, SoundCategory.HOSTILE, 1.0F, 1.5F + this.getEntityWorld().random.nextFloat() * 0.4F);
                                } else if (entity.getMainHandStack().isOf(ModItems.NETHERITE_CLAYMORE)) {
                                    serverWorld.playSoundFromEntity((PlayerEntity) null, this, ModSoundEvents.LONGSWORD_BLOCK_NETHERITE, SoundCategory.HOSTILE, 0.5F, 0.5F + this.getEntityWorld().random.nextFloat() * 0.2F);
                                } else {
                                    serverWorld.playSoundFromEntity((PlayerEntity) null, this, ModSoundEvents.LONGSWORD_BLOCK_NORMAL, SoundCategory.HOSTILE, 1.0F, 3.0F + this.getEntityWorld().random.nextFloat() * 0.4F);
                                }
                            }
                            claymoreComponent.setParryCharge(400);
                            claymoreComponent.setHasBlocked(true);
                            claymoreComponent.setDamageBlocked(base);
                            claymoreComponent.sync();
                            return 0.0f;
                        }
                    }
                }
            }
        }*/
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

    @Inject(method = "tickRiding", at = @At("HEAD"))
    private void afmw$ridingHorse(CallbackInfo ci) {
        LanceComponent lanceComponent = LanceComponent.get((PlayerEntity) this.getVehicle().getFirstPassenger());
        if (this.getVehicle() instanceof HorseEntity) {
            if (this.getMainHandStack().isIn(ModItemTagProvider.LANCES) && !lanceComponent.isRidingHorse()) {
                lanceComponent.setRidingHorse(true);
            }
        } else if (lanceComponent.isRidingHorse()) {
            lanceComponent.setRidingHorse(false);
        }
    }

    //Claymore ==================================
    /*@WrapOperation(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getAttributeValue(Lnet/minecraft/entity/attribute/EntityAttribute;)D"))
    private double afmw$parryDamage(PlayerEntity player, EntityAttribute entityAttribute, Operation<Double> original) {
        ClaymoreComponent claymoreComponent = ClaymoreComponent.get(player);
        if (claymoreComponent.isHasBlocked() && claymoreComponent.canParry()) {
            claymoreComponent.setHasParried(true);
            claymoreComponent.setHasBlocked(false);
            claymoreComponent.sync();
            return claymoreComponent.getParryDamage();
        } else {
            return original.call(player, entityAttribute);
        }
    }*/

}
