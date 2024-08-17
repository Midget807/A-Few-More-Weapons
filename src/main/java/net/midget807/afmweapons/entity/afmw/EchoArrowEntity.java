package net.midget807.afmweapons.entity.afmw;

import net.midget807.afmweapons.entity.ModEntities;
import net.midget807.afmweapons.item.ModItems;
import net.midget807.afmweapons.util.ArrowUtil;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;

public class EchoArrowEntity extends PersistentProjectileEntity {
    private int groundAge;
    public boolean isPulsing;
    public boolean canPulse = false;
    public int modelAge;
    public EchoArrowEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public EchoArrowEntity(World world, double x, double y, double z) {
        super(ModEntities.ECHO_ARROW_ENTITY_TYPE, x, y, z, world);
    }

    public EchoArrowEntity(World world, LivingEntity owner) {
        super(ModEntities.ECHO_ARROW_ENTITY_TYPE, owner, world);
    }
    public void initFromStack (ItemStack stack) {
        this.groundAge = ArrowUtil.getEchoArrowAge(stack);
        this.canPulse = false;
        this.isPulsing = ArrowUtil.getEchoArrowShouldPulse(stack);

    }

    @Override
    protected ItemStack asItemStack() {
        ItemStack itemStack = new ItemStack(ModItems.ECHO_ARROW);
        ArrowUtil.setEchoArrow(itemStack, this.groundAge, this.isPulsing);
        return itemStack;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.inGround && this.inGroundTime != 0 && this.inGroundTime >= (12 * 20)) {
            this.getWorld().sendEntityStatus(this, (byte) 0);
            this.canPulse = true;
            if (this.getWorld().isClient) {
                //client shit
                this.spawnParticles(2);
            }
        } else {
            this.canPulse = false;
        }
        if (!this.getWorld().isClient) {
            //server shit
            if (this.canPulse) {
                runPulse();
            }
        }
    }

    private void spawnParticles(int amount) {
        for (int i = 0; i < amount; ++i) {
            this.getWorld().addParticle(ParticleTypes.SCULK_CHARGE_POP, this.getX(), this.getY(), this.getZ(), 1, 1, 1);
        }
    }

    private void runPulse() {
        if (groundAge % (4 * 20) == 0) {
            this.isPulsing = true;
        }
        if (isPulsing) {
            List<LivingEntity> entities = this.getWorld().getEntitiesByClass(LivingEntity.class, new Box(this.getBlockPos().add(16, 16, 16)), EntityPredicates.VALID_ENTITY);
            for (LivingEntity livingEntity : entities) {
                livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 2));
            }
            if (this.getWorld().isClient) {
                spawnParticles(10);
            }
            this.isPulsing = false;
        }
    }

    @Override
    public void onPlayerCollision(PlayerEntity player) {
        if (!this.canPulse) {
            super.onPlayerCollision(player);
        }
    }
}
