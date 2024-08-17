package net.midget807.afmweapons.entity.afmw;

import net.midget807.afmweapons.entity.ModEntities;
import net.midget807.afmweapons.item.ModItems;
import net.midget807.afmweapons.util.ArrowUtil;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class RicochetArrowEntity extends PersistentProjectileEntity {
    public int bounces;
    private boolean shouldSpawnParticle;
    private static final float VELOCITY_MODIFIER = 0.75f;
    private int ticksSinceBounce;
    public RicochetArrowEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public RicochetArrowEntity(World world, double x, double y, double z) {
        super(ModEntities.RICOCHET_ARROW_ENTITY_TYPE, x, y, z, world);
    }

    public RicochetArrowEntity(World world, LivingEntity owner) {
        super(ModEntities.RICOCHET_ARROW_ENTITY_TYPE, owner, world);
    }
    public void initFromStack(ItemStack stack) {
        this.bounces = ArrowUtil.getRicochetArrowBounces(stack);
    }

    @Override
    protected ItemStack asItemStack() {
        ItemStack itemStack = new ItemStack(ModItems.RICOCHET_ARROW);
        ArrowUtil.setRicochetArrow(itemStack, bounces);
        return itemStack;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.inGround && this.inGroundTime != 0 && this.inGroundTime >= 600) {
            this.getWorld().sendEntityStatus(this, (byte) 0);
        }
        if (this.getWorld().isClient && this.shouldSpawnParticle) {
            spawnParticles(2);
            this.ticksSinceBounce++;
        } else {
            this.shouldSpawnParticle = false;
            this.ticksSinceBounce = 0;
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        Direction direction = blockHitResult.getSide();
        Vec3d vec3d = this.getVelocity();
        if (bounces > 0) {
            if (direction == Direction.UP || direction == Direction.DOWN) {
                this.setVelocity(vec3d.x * VELOCITY_MODIFIER, -vec3d.y * VELOCITY_MODIFIER, vec3d.z * VELOCITY_MODIFIER);
                this.bounces--;
                this.shouldSpawnParticle = true;
            }
            if (direction == Direction.EAST || direction == Direction.WEST) {
                this.setVelocity(-vec3d.x * 0.8, vec3d.y * 0.8, vec3d.z * 0.8);
                this.bounces--;
                this.shouldSpawnParticle = true;
            }
            if (direction == Direction.NORTH || direction == Direction.SOUTH) {
                this.setVelocity(vec3d.x * 0.8, vec3d.y * 0.8, -vec3d.z * 0.8);
                this.bounces--;
                this.shouldSpawnParticle = true;
            }
        } else {
            super.onBlockHit(blockHitResult);
            this.bounces = 2;
        }
    }
    public void spawnParticles(int amount) {
        for (int i = 0; i < amount; ++i) {
            this.getWorld().addParticle(ParticleTypes.CRIT, this.getX(), this.getY(), this.getZ(), 0.05, 0.05, 0.05);
        } // TODO: 12/08/2024 fix particle spawning
    }

    @Override
    public void onPlayerCollision(PlayerEntity player) {
        if (this.getWorld().isClient || !this.inGround && !this.isNoClip() || this.shake > 0) {
            return;
        }
        if (this.tryPickup(player)) {
            if (!player.isCreative()) {
                player.sendPickup(this, 1);
            } // TODO: 12/08/2024 Fix item pickup
            this.discard();
        }

    }

    public int setBounces(int bounces) {
        return this.bounces = bounces;
    }


    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("Bounces", NbtElement.NUMBER_TYPE)) {
            this.bounces = ArrowUtil.getRicochetArrowBouncesNbt(nbt);
        }
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        if (this.bounces != 0) {
            nbt.putInt("Bounces", bounces);
        }
    }
}
