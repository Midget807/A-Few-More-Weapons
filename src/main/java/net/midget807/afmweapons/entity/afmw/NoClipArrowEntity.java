package net.midget807.afmweapons.entity.afmw;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public abstract class NoClipArrowEntity extends PersistentProjectileEntity {
    public NoClipArrowEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public NoClipArrowEntity(EntityType<? extends PersistentProjectileEntity> type, double x, double y, double z, World world) {
        super(type, x, y, z, world);
    }

    public NoClipArrowEntity(EntityType<? extends PersistentProjectileEntity> type, LivingEntity owner, World world) {
        super(type, owner, world);
    }

    @Override
    public void tick() {
        this.baseTick();
        if (!this.shot) {
            this.emitGameEvent(GameEvent.PROJECTILE_SHOOT, this.getOwner());
            this.shot = true;
        }

        if (!this.leftOwner) {
            this.leftOwner = this.shouldLeaveOwner();
        }
        boolean bl = this.isNoClip();
        Vec3d vec3d = this.getVelocity();
        if (this.prevPitch == 0.0F && this.prevYaw == 0.0F) {
            double d = vec3d.horizontalLength();
            this.setYaw((float)(MathHelper.atan2(vec3d.x, vec3d.z) * 180.0F / (float)Math.PI));
            this.setPitch((float)(MathHelper.atan2(vec3d.y, d) * 180.0F / (float)Math.PI));
            this.prevYaw = this.getYaw();
            this.prevPitch = this.getPitch();
        }

        BlockPos blockPos = this.getBlockPos();
        BlockState blockState = this.getWorld().getBlockState(blockPos);
        if (!blockState.isAir() && !bl) {
            VoxelShape voxelShape = blockState.getCollisionShape(this.getWorld(), blockPos);
            if (!voxelShape.isEmpty()) {
                Vec3d vec3d2 = this.getPos();

                for (Box box : voxelShape.getBoundingBoxes()) {
                    if (box.offset(blockPos).contains(vec3d2)) {
                        this.inGround = true;
                        break;
                    }
                }
            }
        }

        if (this.shake > 0) {
            this.shake--;
        }

        if (this.isTouchingWaterOrRain() || blockState.isOf(Blocks.POWDER_SNOW)) {
            this.extinguish();
        }

        if (this.inGround && !bl) {
            if (this.inBlockState != blockState && this.shouldFall()) {
                this.fall();
            } else if (!this.getWorld().isClient) {
                this.age();
            }

            this.inGroundTime++;
        } else {
            this.inGroundTime = 0;
            Vec3d vec3d3 = this.getPos();
            Vec3d vec3d2 = vec3d3.add(vec3d);
            HitResult hitResult = this.getWorld().raycast(new RaycastContext(vec3d3, vec3d2, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this));
            if (hitResult.getType() != HitResult.Type.MISS) {
                vec3d2 = hitResult.getPos();
            }

            if (!this.isRemoved()) {
                EntityHitResult entityHitResult = this.getEntityCollision(vec3d3, vec3d2);

                if (entityHitResult != null) {
                    hitResult = entityHitResult;
                    this.onEntityHit(entityHitResult);
                }

                if (hitResult != null && hitResult.getType() == HitResult.Type.ENTITY) {
                    Entity entity = ((EntityHitResult)hitResult).getEntity();
                    Entity entity2 = this.getOwner();
                    if (entity instanceof PlayerEntity && entity2 instanceof PlayerEntity && !((PlayerEntity)entity2).shouldDamagePlayer((PlayerEntity)entity)) {
                        hitResult = null;
                        entityHitResult = null;
                    }
                }

                if (hitResult != null) {
                    if (!bl) {
                        this.onCollision(hitResult);
                        this.velocityDirty = true;
                    } else if (hitResult.getType() == HitResult.Type.ENTITY) {
                        this.onEntityHit((EntityHitResult) hitResult);
                    }
                }
                hitResult = null;
            }

            vec3d = this.getVelocity();
            double e = vec3d.x;
            double f = vec3d.y;
            double g = vec3d.z;
            if (this.isCritical()) {
                for (int i = 0; i < 4; i++) {
                    this.getWorld().addParticle(ParticleTypes.CRIT, this.getX() + e * i / 4.0, this.getY() + f * i / 4.0, this.getZ() + g * i / 4.0, -e, -f + 0.2, -g);
                }
            }

            double h = this.getX() + e;
            double j = this.getY() + f;
            double k = this.getZ() + g;
            double l = vec3d.horizontalLength();
            /*if (bl) {
                this.setYaw((float)(MathHelper.atan2(-e, -g) * 180.0F / (float)Math.PI));
            } else {
                this.setYaw((float)(MathHelper.atan2(e, g) * 180.0F / (float)Math.PI));
            }*/

            this.setPitch((float)(MathHelper.atan2(f, l) * 180.0F / (float)Math.PI));
            this.setPitch(updateRotation(this.prevPitch, this.getPitch()));
            this.setYaw(updateRotation(this.prevYaw, this.getYaw()));
            float m = 0.99F;
            float n = 0.05F;
            if (this.isTouchingWater()) {
                for (int o = 0; o < 4; o++) {
                    float p = 0.25F;
                    this.getWorld().addParticle(ParticleTypes.BUBBLE, h - e * 0.25, j - f * 0.25, k - g * 0.25, e, f, g);
                }

                m = this.getDragInWater();
            }

            this.setVelocity(vec3d.multiply(m));
            if (!this.hasNoGravity() && !bl) {
                Vec3d vec3d4 = this.getVelocity();
                this.setVelocity(vec3d4.x, vec3d4.y - 0.05F, vec3d4.z);
            }

            this.setPosition(h, j, k);
            this.checkBlockCollision();
        }
    }
}
