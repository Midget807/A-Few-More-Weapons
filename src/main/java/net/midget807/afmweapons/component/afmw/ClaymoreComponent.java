package net.midget807.afmweapons.component.afmw;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.CommonTickingComponent;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.midget807.afmweapons.component.ModComponents;
import net.midget807.afmweapons.datagen.ModItemTagProvider;
import net.midget807.afmweapons.enchantment.ModEnchantments;
import net.midget807.afmweapons.network.ModMessages;
import net.midget807.afmweapons.particle.ModParticles;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.AbstractDecorationEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.List;

public class ClaymoreComponent implements AutoSyncedComponent, CommonTickingComponent {
    private static final Vector3f ROTATION_VECTOR = new Vector3f(0.0f, 1.0f, 0.0f).normalize();
    private static final Vector3f TRANSFORMATION_VECTOR = new Vector3f(1.0f, 0.0f, 0.0f).normalize().mul(2.5f);
    private final PlayerEntity player;
    public static final int CHARGE_COLOR = 0xB5B5B5;

    public static final Vec3i CHARGE_BAR_COLOR = new Vec3i(181, 181, 181);
    public static final int SMASH_COLOR = 0x5555FF;
    public static final Vec3i SMASH_BAR_COLOR = new Vec3i(85, 85, 255);
    public static final int MAX_CHARGE = 20 * 3;
    public static final int SMASH_TIMEFRAME_COST = MAX_CHARGE / 10; //MAX_CHARGE / however long the timeframe should be in ticks
    private int charge = 0;
    private boolean charging;

    public ClaymoreComponent(PlayerEntity player) {
        this.player = player;
    }
    public static ClaymoreComponent get(@NotNull PlayerEntity player) {
        return ModComponents.CLAYMORE_COMPONENT.get(player);
    }
    public void sync() {
        ModComponents.CLAYMORE_COMPONENT.sync(this.player);
    }

    public boolean isCharging() {
        return this.charging;
    }

    public void setCharging(boolean charging) {
        this.charging = charging;
    }

    @Override
    public void tick() {
        ItemStack stack = this.player.getMainHandStack();
        boolean isLongsword = this.player.isAlive() && !this.player.isDead() && !this.player.isRemoved() && stack.isIn(ModItemTagProvider.LONGSWORDS);
        boolean hasVindictive = EnchantmentHelper.getLevel(ModEnchantments.VINDICTIVE, stack) > 0;
        if (this.isCharging()) {
            if (this.charge < MAX_CHARGE) {
                ++this.charge;
                if (this.charge == MAX_CHARGE) {
                    this.sync();
                }
            }
        } else {
            if (this.charge <= 0 || !isLongsword || hasVindictive /*TODO check if this condition is needed*/) {
                this.charge = 0;
                this.sync();
                return;
            }
            this.charge -= SMASH_TIMEFRAME_COST;
        }
    }

    @Override
    public void serverTick() {
        boolean hasKnockBack = EnchantmentHelper.getLevel(Enchantments.KNOCKBACK, this.player.getMainHandStack()) > 0;
        boolean handSwung = this.player.handSwinging;
        if (this.canSmash() && charge > 0 && handSwung) {
            HitResult hitResult = this.player.raycast(3.0d, 0.0f, false);
            if (hitResult.getType() == HitResult.Type.BLOCK) {
                BlockHitResult blockHitResult = (BlockHitResult) hitResult;
                BlockPos blockPos = blockHitResult.getBlockPos();
                BlockPos blockPos2 = blockPos.offset(blockHitResult.getSide());
                Box box = new Box(blockPos2.getX() - 2.5, 0, blockPos2.getZ() - 2.5, blockPos2.getX() + 2.5, 1, blockPos2.getZ() + 2.5);
                List<Entity> entities = this.player.getWorld().getOtherEntities(this.player, box);
                for (Entity target : entities) {
                    if (!(target instanceof AbstractDecorationEntity)) {
                        if (target instanceof TameableEntity tameableEntity) {
                            if (tameableEntity.isTamed() && !(tameableEntity.getOwner() == this.player)) {
                                target.damage(this.player.getDamageSources().playerAttack(this.player), 6.0f);
                                setBlockSmashVelocity(target);
                            }
                        } else {
                            target.damage(this.player.getDamageSources().playerAttack(this.player), 6.0f);
                            setBlockSmashVelocity(target);
                        }
                    }
                }
                spawnBlockSmashParticle(this.player.getWorld(), blockPos2, 2.5f);
            } else if (hitResult.getType() == HitResult.Type.ENTITY && hasKnockBack) {
                EntityHitResult entityHitResult = (EntityHitResult) hitResult;
                Entity target = entityHitResult.getEntity();
                BlockPos targetPos = target.getBlockPos();
                Box box = new Box(targetPos).expand(1, 0, 1);
                List<Entity> entities = this.player.getWorld().getOtherEntities(this.player, box);
                for (Entity entity : entities) {
                    if (!(entity instanceof AbstractDecorationEntity)) {
                        if (entity instanceof TameableEntity tameableEntity) {
                            if (tameableEntity.isTamed() && !(tameableEntity.getOwner() == this.player)) {
                                entity.damage(this.player.getDamageSources().playerAttack(this.player), 3.0f * EnchantmentHelper.getLevel(Enchantments.KNOCKBACK, this.player.getMainHandStack()));
                                setEntitySmashVelocity(entity, this.player);
                            }
                        } else {
                            entity.damage(this.player.getDamageSources().playerAttack(this.player), 3.0f * EnchantmentHelper.getLevel(Enchantments.KNOCKBACK, this.player.getMainHandStack()));
                            setEntitySmashVelocity(entity, this.player);
                        }
                    }
                } if (this.player.getWorld().isClient) {
                    spawnEntitySmashParticle(this.player.getWorld(), targetPos);
                }
            }
        }

        this.tick();
    }

    @Override
    public void clientTick() {
        this.tick();
    }
    private void setBlockSmashVelocity(Entity target) {
        Vec3d vec3d = new Vec3d(0.0, 1.5, 0.0);
        target.setVelocity(target.getVelocity().add(vec3d));
    }
    private void setEntitySmashVelocity(Entity target, PlayerEntity player) {
        Vec3d playerPos = player.getPos();
        double modP2T = Math.sqrt(target.squaredDistanceTo(playerPos)) / 4.0d; //4.0d == power * 2.0f
        if (modP2T <= 1) {
            double xVec = target.getX() - player.getX();
            double yVec = target.getEyeY() - player.getEyeY();
            double zVec = target.getZ() - player.getZ();
            double modVecDiff = Math.sqrt(xVec * xVec + yVec * yVec + zVec * zVec);
            if (modVecDiff != 0.0) {
                xVec /= modVecDiff;
                yVec /= modVecDiff;
                zVec /= modVecDiff;
                double exposure = Explosion.getExposure(playerPos, target);
                double modP2TExposure = (1.0 - modP2T) * exposure;
                double kbResBl;
                if (target instanceof LivingEntity livingEntity) {
                    kbResBl = ProtectionEnchantment.transformExplosionKnockback(livingEntity, modP2TExposure);
                } else {
                    kbResBl = modP2TExposure;
                }
                xVec *= kbResBl;
                yVec *= kbResBl;
                zVec *= kbResBl;
                Vec3d smashVec = new Vec3d(xVec, yVec, zVec);
                target.setVelocity(target.getVelocity().add(smashVec));
            }
        }
    }
    private void spawnBlockSmashParticle(World world, BlockPos offsetPos, float radius) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBlockPos(offsetPos);
        buf.writeFloat(radius);
        ServerPlayNetworking.send((ServerPlayerEntity) world.getPlayers(), ModMessages.SPAWN_BLOCK_SMASH_PARTICLE, buf);
    }
    private void spawnEntitySmashParticle(World world, BlockPos entityPos) {
        world.addParticle(ModParticles.ENTITY_SMASH_PARTICLE, entityPos.getX(), entityPos.getY(), entityPos.getZ(), 0.0f, 0.0f, 0.0f);
    }
    public boolean canSmash() {
        return this.charge == MAX_CHARGE;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        this.charging = tag.getBoolean("isCharging");
        this.charge = tag.getInt("charge");
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putBoolean("isCharging", this.charging);
        tag.putInt("charge", this.charge);
    }
}
