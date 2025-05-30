package net.midget807.afmweapons.entity.afmw;

import net.midget807.afmweapons.entity.ModEntities;
import net.midget807.afmweapons.item.ModItems;
import net.midget807.afmweapons.util.ArrowUtil;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class MagicArrowEntity extends NoClipArrowEntity {
    private int flightDuration;
    public MagicArrowEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public MagicArrowEntity(World world, double x, double y, double z) {
        super(ModEntities.MAGIC_ARROW_ENTITY_TYPE, x, y, z, world);
    }

    public MagicArrowEntity(World world, LivingEntity owner) {
        super(ModEntities.MAGIC_ARROW_ENTITY_TYPE, owner, world);
    }

    public void initFromStack(ItemStack stack) {
        this.flightDuration = ArrowUtil.getMagicArrowFlightTime(stack);
        this.setVelocity(this.getVelocity().multiply(0.5));
    }
    @Override
    protected ItemStack asItemStack() {
        ItemStack itemStack = new ItemStack(ModItems.MAGIC_ARROW);
        ArrowUtil.setMagicArrow(itemStack, flightDuration);
        return itemStack;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.getWorld().isClient) {
            //server shit
            if (this.flightDuration == 0) {
                this.discardMagicArrow();
            } else {
                --this.flightDuration;
            }
        }
        if (this.getWorld().isClient) {
            // client shit
            this.spawnParticles(4);
        }
    }


    private void discardMagicArrow() {
        if (this.getWorld().isClient) {
            this.spawnDiscardParticles(15);
        }
        this.discard();
    }

    private void spawnDiscardParticles(int amount) {
        for (int i = 0; i < amount; ++i) {
            this.getWorld().addParticle(ParticleTypes.GLOW, this.getX(), this.getY(), this.getZ(), 0.1, 0.05, 0.1);
        }
    }


    private void spawnParticles(int amount) {
        for (int i = 0; i < amount; ++i) {
            this.getWorld().addParticle(ParticleTypes.GLOW, this.getX(), this.getY(), this.getZ(), MathHelper.nextBetween(random, -0.5f, 0.5f) * 0.083333336f * 0.05, 0.05f, MathHelper.nextBetween(random, -0.5f, 0.5f) * 0.083333336f * 0.05);
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        float f = (float)this.getVelocity().length();
        int i = MathHelper.ceil(MathHelper.clamp((double)f * this.damage, 0.0, 2.147483647E9));
        List<Entity> entities = entity.getEntityWorld().getEntitiesByClass(Entity.class, entity.getBoundingBox().expand(1.0f), EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR);
        Entity entity2 = this.getOwner();
        DamageSource damageSource;
        if (entity2 == null) {
            damageSource = this.getDamageSources().arrow(this, this);
        } else {
            damageSource = this.getDamageSources().arrow(this, entity2);
            if (entity2 instanceof LivingEntity) {
                ((LivingEntity)entity2).onAttacking(entity);
            }
        }
        boolean bl = entity.getType() == EntityType.ENDERMAN;
        int j = entity.getFireTicks();
        if (this.isOnFire() && !bl) {
            entity.setOnFireFor(5);
        }
        this.applyDamage(entity, damageSource, i, bl, entity2, j);
        for (Entity target : entities) {
            this.applyDamage(target, damageSource, i, bl, entity2, j);
        }


    }

    private void applyDamage(Entity entity, DamageSource damageSource, int damage, boolean isEnderman, Entity owner, int fireTicks) {
        if (entity.damage(damageSource, damage)) {
            if (isEnderman) {
                return;
            }
            if (entity instanceof LivingEntity livingEntity) {
                if (this.punch > 0) {
                    double d = Math.max(0.0, 1.0 - livingEntity.getAttributeValue(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE));
                    Vec3d vec3d = this.getVelocity().multiply(1.0, 0.0, 1.0).normalize().multiply(this.punch * 0.6 * d);
                    if (vec3d.lengthSquared() > 0.0) {
                        livingEntity.addVelocity(vec3d.x, 0.1, vec3d.z);
                    }
                }

                if (!this.getWorld().isClient && owner instanceof LivingEntity) {
                    EnchantmentHelper.onUserDamaged(livingEntity, owner);
                    EnchantmentHelper.onTargetDamaged((LivingEntity) owner, livingEntity);
                }
            }
            this.playSound(this.sound, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
        } else {
            entity.setFireTicks(fireTicks);
        }
    }


    @Override
    public void onPlayerCollision(PlayerEntity player) {

    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("FlightDuration", NbtElement.NUMBER_TYPE)) {
            this.flightDuration = ArrowUtil.getMagicArrowFlightTimeNbt(nbt);
        }
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        if (this.flightDuration != 0) {
            nbt.putInt("FlightDuration", flightDuration);
        }
    }

    @Override
    public boolean isNoClip() {
        return true;
    }
}
