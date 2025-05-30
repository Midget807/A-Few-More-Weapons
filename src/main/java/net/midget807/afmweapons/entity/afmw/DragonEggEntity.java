package net.midget807.afmweapons.entity.afmw;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.midget807.afmweapons.effect.ModEffects;
import net.midget807.afmweapons.entity.ModEntities;
import net.midget807.afmweapons.item.ModItems;
import net.midget807.afmweapons.sound.ModSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.TitleFadeS2CPacket;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class DragonEggEntity extends ThrownItemEntity {
    public DragonEggEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public DragonEggEntity(double x, double y, double z, World world) {
        super(ModEntities.FRIED_EGG_ENTITY_TYPE, x, y, z, world);
    }

    public DragonEggEntity(LivingEntity livingEntity, World world) {
        super(ModEntities.FRIED_EGG_ENTITY_TYPE, livingEntity, world);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.FRIED_EGG;
    }
    @Environment(EnvType.CLIENT)
    private ParticleEffect getParticleParameters() {
        ItemStack itemStack = this.getItem();
        return (ParticleEffect)(itemStack.isEmpty() ? ParticleTypes.PORTAL : new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack));
    }
    @Environment(EnvType.CLIENT)
    public void handleStatus(byte status) {
        if (status == 3) {
            ParticleEffect particleEffect = this.getParticleParameters();
            for (int i = 0; i < 8; ++i) {
                this.getWorld().addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0d, 0.0d, 0.0d);
            }
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.damage(this.getDamageSources().thrown(this, this.getOwner()), 0.5F);
            livingEntity.teleport(livingEntity.getX(), -68, livingEntity.getZ());
            if (livingEntity instanceof ServerPlayerEntity serverPlayer) {
                serverPlayer.getServerWorld().playSoundFromEntity(null, serverPlayer, ModSoundEvents.SAD_TROMBONE, SoundCategory.HOSTILE, 1.0f, 1.0f);
                serverPlayer.networkHandler.sendPacket(new TitleS2CPacket(Text.literal("haha L bozo").formatted(Formatting.BOLD).formatted(Formatting.RED)));
                serverPlayer.networkHandler.sendPacket(new TitleFadeS2CPacket(10, 60, 10));
            }
        }
        this.discard();
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        if (!this.getWorld().isClient) {
            this.dropStack(new ItemStack(ModItems.FRIED_EGG, 1));
            this.kill();
        }
    }
}
