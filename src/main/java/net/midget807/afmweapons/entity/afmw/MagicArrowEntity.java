package net.midget807.afmweapons.entity.afmw;

import net.midget807.afmweapons.component.ModComponents;
import net.midget807.afmweapons.entity.ModEntities;
import net.midget807.afmweapons.item.ModItems;
import net.midget807.afmweapons.util.ArrowUtil;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
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
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.List;

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
        //this.setNoClip(true);
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
    protected void onCollision(HitResult hitResult) {
        if (hitResult.getType() == HitResult.Type.ENTITY) {
            EntityHitResult entityHitResult = ((EntityHitResult) hitResult);
            this.onEntityHit(entityHitResult);
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        float f = (float)this.getVelocity().length();
        int i = MathHelper.ceil(MathHelper.clamp((double)f * this.getDamage(), 0.0, 2.147483647E9));
        List<LivingEntity> livingEntities = entity.getEntityWorld().getEntitiesByClass(LivingEntity.class, new Box(entity.getBlockPos()), EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR);
        for (LivingEntity livingEntity : livingEntities) {
            livingEntity.damage(this.getDamageSources().arrow(this, this.getOwner()), i);
        }
        this.setVelocity(this.getVelocity().multiply(1.0));
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        ModComponents.MAGIC_ARROW_COMPONENT.maybeGet(this).ifPresent((magicArrowComponent -> {
            BlockState state = this.getWorld().getBlockState(blockHitResult.getBlockPos());
            state.onProjectileHit(this.getWorld(), state, blockHitResult, this);
            double distance = 0.0;
            Vec3d start = this.getPos();
            Vec3d end;
            for (end = start.add(this.getVelocity().multiply(0.125).normalize()); distance < 4.0; end.add(this.getVelocity().multiply(0.125).normalize())) {
                HitResult hitResult = this.getWorld().raycast(new RaycastContext(start, end, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this));
                if (hitResult.getType() == HitResult.Type.MISS) {
                    break;
                }
                distance = this.getPos().distanceTo(hitResult.getPos());
                start = end;
            }
            if (distance <= 3.1) {
                if (!this.getWorld().isClient) {
                    Vec3d target = this.getPos().add(this.getVelocity());
                    EntityHitResult entityHitResult = this.getEntityCollision(this.getPos(), target.add(this.getVelocity()));
                    if (entityHitResult != null) {
                        this.onEntityHit(entityHitResult);
                    }
                    this.getWorld().emitGameEvent(GameEvent.TELEPORT, this.getPos(), GameEvent.Emitter.of(this));
                    this.teleport(end.getX(), end.getY(), end.getZ());
                } else {
                    for (int i = 0; i < 6; ++i) {
                        this.getWorld().addParticle(ParticleTypes.GLOW, blockHitResult.getPos().getX() + MathHelper.nextDouble(this.random, (-this.getWidth() / 2.0f), (this.getWidth() / 2.0f)), blockHitResult.getPos().getY() + MathHelper.nextDouble(this.random, (-this.getWidth() / 2.0f), (this.getWidth() / 2.0f)), blockHitResult.getPos().getZ() + MathHelper.nextDouble(this.random, (-this.getWidth() / 2.0f), (this.getWidth() / 2.0f)), 0.0, 0.0, 0.0);
                        this.getWorld().addParticle(ParticleTypes.GLOW, end.getX() + MathHelper.nextDouble(this.random, (-this.getWidth() / 2.0f), (this.getWidth() / 2.0f)), end.getY() + MathHelper.nextDouble(this.random, (-this.getWidth() / 2.0f), (this.getWidth() / 2.0f)), end.getZ() + MathHelper.nextDouble(this.random, (-this.getWidth() / 2.0f), (this.getWidth() / 2.0f)), 0.0, 0.0, 0.0);
                    }
                }

            }
        }));
        super.onBlockHit(blockHitResult);
    }
    /*
    @Override
    protected void tryCheckBlockCollision() {
    }*/

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
}
