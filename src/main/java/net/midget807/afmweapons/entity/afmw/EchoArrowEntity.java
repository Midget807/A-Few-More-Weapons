package net.midget807.afmweapons.entity.afmw;

import net.midget807.afmweapons.entity.ModEntities;
import net.midget807.afmweapons.item.ModItems;
import net.midget807.afmweapons.particle.ModParticles;
import net.midget807.afmweapons.util.ArrowUtil;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;

public class EchoArrowEntity extends PersistentProjectileEntity {
    private static final int ECHO_RADIUS = 16;
    private static int MAX_PULSE_TIME = 0;
    public int maxPulses;
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
        this.maxPulses = ArrowUtil.getEchoArrowMaxPulses(stack);
        MAX_PULSE_TIME = maxPulses * (2 * 20 + 40);
    }

    @Override
    protected ItemStack asItemStack() {
        ItemStack itemStack = new ItemStack(ModItems.ECHO_ARROW);
        ArrowUtil.setEchoArrow(itemStack, this.maxPulses);
        return itemStack;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getWorld().isClient) {
            //client
            if (!inGround) {
                spawnParticles(2);
            } else if (this.inGroundTime < MAX_PULSE_TIME) {
                if (this.inGroundTime % (4 * 20) == 0) {
                    this.runPulseParticle();
                }
            }
        } else {
            //server
            if (this.inGroundTime < MAX_PULSE_TIME && inGround) {
                if (this.inGroundTime % (4 * 20) == 0) {
                    this.runPulse();
                }
            }
        }
    }
    private void runPulseParticle() {
        /*if (this.getWorld() instanceof ServerWorld serverWorld) {
            for (ServerPlayerEntity player : serverWorld.getServer().getPlayerManager().getPlayerList()) {
                serverWorld.spawnParticles(player, ModParticles.ECHO_ARROW_PULSE_PARTICLE_TYPE, true, this.getX(), this.getY() + this.getBoundingBox().maxY, this.getZ(), 1, 0.0, 0.0, 0.0, 0);
            }
        }*/
        this.getWorld().addParticle(ModParticles.ECHO_ARROW_PULSE_PARTICLE_TYPE, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
    }

    private void spawnParticles(int amount) {
        for (int i = 0; i < amount; ++i) {
            this.getWorld().addParticle(ParticleTypes.SCULK_CHARGE_POP, this.getX(), this.getY(), this.getZ(), 0, -0.1, 0);
        }
    }

    private void runPulse() {
        List<LivingEntity> entities = this.getWorld().getEntitiesByClass(LivingEntity.class, this.getBoundingBox().expand(ECHO_RADIUS), EntityPredicates.VALID_ENTITY);
        for (LivingEntity livingEntity : entities) {
            if (livingEntity != this.getOwner()) {
                livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 2 * 20));
            }
        }
    }

    @Override
    public void onPlayerCollision(PlayerEntity player) {
        if (this.getWorld().isClient || !this.inGround && !this.isNoClip() || this.shake > 0) {
            return;
        }
        if (this.tryPickup(player)) {
            if (!player.isCreative()) {
                player.sendPickup(this, 1);
            }
            this.discard();
        }
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("MaxPulses",NbtElement.NUMBER_TYPE)) {
            this.maxPulses = ArrowUtil.getEchoArrowMaxPulsesNbt(nbt);
        }
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("MaxPulses", this.maxPulses);
    }
}
