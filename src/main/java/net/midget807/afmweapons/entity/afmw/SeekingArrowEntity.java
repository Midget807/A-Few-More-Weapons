package net.midget807.afmweapons.entity.afmw;

import net.midget807.afmweapons.entity.ModEntities;
import net.midget807.afmweapons.item.ModItems;
import net.midget807.afmweapons.util.ArrowUtil;
import net.minecraft.block.BlockState;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.List;

public class SeekingArrowEntity extends PersistentProjectileEntity {
    private int flightDuration;
    private LivingEntity target = null;
    public SeekingArrowEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public SeekingArrowEntity(double x, double y, double z, World world) {
        super(ModEntities.SEEKING_ARROW_ENTITY_TYPE, x, y, z, world);
    }

    public SeekingArrowEntity(LivingEntity owner, World world) {
        super(ModEntities.SEEKING_ARROW_ENTITY_TYPE, owner, world);
    }
    public void initFromStack(ItemStack stack) {
        this.flightDuration = ArrowUtil.getSeekingArrowFlightTime(stack);
        this.setVelocity(this.getVelocity().multiply(0.5));
    }

    @Override
    protected ItemStack asItemStack() {
        ItemStack itemStack = new ItemStack(ModItems.SEEKING_ARROW);
        ArrowUtil.setSeekingArrow(itemStack, flightDuration);
        return itemStack;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.getWorld().isClient) {
            //server shit
            if (this.flightDuration == 0) {
                this.discardSeekingArrow();
            } else {
                --this.flightDuration;
            }
        }
        if (this.getWorld().isClient) {
            // client shit
            this.spawnParticles(2);
        }
        if (this.flightDuration % 5 == 0) {
            this.target = this.ping();
        }
        if (this.target != null) {
            this.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, target.getPos());
        }
    }

    private LivingEntity ping() {
        List<LivingEntity> entities = this.getWorld().getEntitiesByClass(LivingEntity.class, this.getBoundingBox().expand(15), EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR);
        return this.getWorld().getClosestEntity(entities, TargetPredicate.createAttackable().setPredicate(livingEntity -> {
            if (livingEntity instanceof PlayerEntity player) {
                return !player.isCreative();
            }
            return !livingEntity.isSpectator();
        }), null, this.getX(), this.getY(), this.getZ());
    }

    private void discardSeekingArrow() {
        if (this.getWorld().isClient) {
            this.spawnDiscardParticles(4);
        }
        this.discard();
    }

    private void spawnDiscardParticles(int amount) {
        for (int i = 0; i < amount; ++i) {
            this.getWorld().addParticle(ParticleTypes.PORTAL, this.getX(), this.getY(), this.getZ(), 0.1, 0.05, 0.1);
        }
    }

    private void spawnParticles(int amount) {
        for (int i = 0; i < amount; ++i) {
            this.getWorld().addParticle(ParticleTypes.REVERSE_PORTAL, this.getX(), this.getY(), this.getZ(), MathHelper.nextBetween(random, -0.5f, 0.5f) * 0.083333336f * 0.05, 0.05f, MathHelper.nextBetween(random, -0.5f, 0.5f) * 0.083333336f * 0.05);
        }
    }

    @Override
    public void onPlayerCollision(PlayerEntity player) {

    }

    @Override
    protected void onBlockCollision(BlockState state) {
        this.discardSeekingArrow();
    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }
}
