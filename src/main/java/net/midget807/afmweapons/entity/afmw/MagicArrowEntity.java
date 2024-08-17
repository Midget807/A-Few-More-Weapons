package net.midget807.afmweapons.entity.afmw;

import net.midget807.afmweapons.entity.ModEntities;
import net.midget807.afmweapons.item.ModItems;
import net.midget807.afmweapons.util.ArrowUtil;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class MagicArrowEntity extends PersistentProjectileEntity {
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
            this.spawnParticles(2);
        }
    }


    private void discardMagicArrow() {
        if (this.getWorld().isClient) {
            this.spawnDiscardParticles(10);
        }
        this.discard();
    }

    private void spawnDiscardParticles(int amount) {
        for (int i = 0; i < amount; ++i) {
            this.getWorld().addParticle(ParticleTypes.GLOW, this.getX(), this.getY(), this.getZ(), 0.05, 0.05, 0.05);
        }
    }


    private void spawnParticles(int amount) {
        for (int i = 0; i < amount; ++i) {
            this.getWorld().addParticle(ParticleTypes.GLOW, this.getX(), this.getY(), this.getZ(), MathHelper.nextBetween(random, -1.0f, 1.0f) * 0.083333336f, 0.05f, MathHelper.nextBetween(random, -1.0f, 1.0f) * 0.083333336f);
        }
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        if (hitResult.getType() == HitResult.Type.ENTITY) {
            EntityHitResult entityHitResult = ((EntityHitResult) hitResult);
            this.onEntityHit(entityHitResult);
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        entityHitResult.getEntity().damage(this.getDamageSources().arrow(this, this.getOwner()), (float) this.getDamage() * 4);
        this.setVelocity(this.getVelocity().multiply(1.0));
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
    }

    @Override
    protected void tryCheckBlockCollision() {
    }

    @Override
    public void onPlayerCollision(PlayerEntity player) {
        if (this.getWorld().isClient || !this.inGround && !this.isNoClip() || this.shake > 0) {
            return;
        }
        if (this.tryPickup(player) && flightDuration == 0) {
            player.sendPickup(this, 1);
            this.discard();
        }
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
}
