package net.midget807.afmweapons.entity.afmw;

import net.midget807.afmweapons.entity.ModEntities;
import net.midget807.afmweapons.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.EndermiteEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class WarpArrowEntity extends PersistentProjectileEntity {
    public WarpArrowEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public WarpArrowEntity(World world, double x, double y, double z) {
        super(ModEntities.WARP_ARROW_ENTITY_TYPE, x, y, z, world);
    }

    public WarpArrowEntity(World world, LivingEntity owner) {
        super(ModEntities.WARP_ARROW_ENTITY_TYPE, owner, world);
    }

    @Override
    protected ItemStack asItemStack() {
        return new ItemStack(ModItems.WARP_ARROW);
    }
    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        entityHitResult.getEntity().damage(this.getDamageSources().thrown(this, this.getOwner()), 0.0f);
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        for (int i = 0; i < 32; ++i) {
            this.getWorld().addParticle(ParticleTypes.PORTAL, this.getX(), this.getY() + this.random.nextDouble() * 2.0, this.getZ(), this.random.nextGaussian(), 0.0, this.random.nextGaussian());
        }
        if (!this.getWorld().isClient && !this.isRemoved()) {
            Entity entity = this.getOwner();
            if (entity instanceof ServerPlayerEntity) {
                ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)entity;
                if (serverPlayerEntity.networkHandler.isConnectionOpen() && serverPlayerEntity.getWorld() == this.getWorld() && !serverPlayerEntity.isSleeping()) {
                    if (entity.hasVehicle()) {
                        serverPlayerEntity.requestTeleportAndDismount(this.getX(), this.getY(), this.getZ());
                    } else {
                        entity.requestTeleport(this.getX(), this.getY(), this.getZ());
                    }
                    entity.onLanding();
                    entity.damage(this.getDamageSources().fall(), 5.0f);
                }
            } else if (entity != null) {
                entity.requestTeleport(this.getX(), this.getY(), this.getZ());
                entity.onLanding();
            }
            this.discard();
        }
    }

    @Override
    public void tick() {
        Entity entity = this.getOwner();
        if (entity instanceof ServerPlayerEntity && !entity.isAlive() && this.getWorld().getGameRules().getBoolean(GameRules.ENDER_PEARLS_VANISH_ON_DEATH)) {
            this.discard();
        } else {
            super.tick();
        }
    }

    @Override
    @Nullable
    public Entity moveToWorld(ServerWorld destination) {
        Entity entity = this.getOwner();
        if (entity != null && entity.getWorld().getRegistryKey() != destination.getRegistryKey()) {
            this.setOwner(null);
        }
        return super.moveToWorld(destination);
    }

}
