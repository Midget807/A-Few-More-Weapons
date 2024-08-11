package net.midget807.afmweapons.entity.afmw;

import net.midget807.afmweapons.effect.ModEffects;
import net.midget807.afmweapons.entity.ModEntities;
import net.midget807.afmweapons.item.ModItems;
import net.midget807.afmweapons.item.afmw.arrow.util.ArrowUtil;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FrostArrowEntity extends PersistentProjectileEntity {
    private int duration;
    private int level;
    public FrostArrowEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }
    public FrostArrowEntity(World world, double x, double y, double z) {
        super(ModEntities.FROST_ARROW_ENTITY_TYPE, x, y, z, world);
    }
    public FrostArrowEntity(World world, LivingEntity owner) {
        super(ModEntities.FROST_ARROW_ENTITY_TYPE, owner, world);
    }
    public void initFromStack(ItemStack stack) {
        this.level = ArrowUtil.getFrostArrowLevel(stack);
        this.duration = ArrowUtil.getFrostArrowDuration(stack);
    }

    @Override
    protected ItemStack asItemStack() {
        ItemStack itemStack = new ItemStack(ModItems.FROST_ARROW);
        ArrowUtil.setFrostArrow(itemStack, level, duration);
        return itemStack;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getWorld().isClient) {
            if (this.inGround) {
                if (this.inGroundTime % 5 == 0) {
                    this.spawnParticles(1);
                }
            } else {
                this.spawnParticles(2);
            }
        } else if (this.inGround && this.inGroundTime != 0 && this.inGroundTime >= 600) {
            this.getWorld().sendEntityStatus(this, (byte) 0);
        }
        if (this.isTouchingWater()) {
            freezeWater(this, this.getWorld(), this.getBlockPos(), this.level);
            this.discard();
        }/* else {
            super.tick();
        }*/
    }

    private void spawnParticles(int amount) {
        for (int i = 0; i < amount; ++i) {
            this.getWorld().addParticle(ParticleTypes.SNOWFLAKE, this.getParticleX(0.5), this.getRandomBodyY(), this.getParticleX(0.5), 1, 1,1);
        }
    }

    public void freezeWater(Entity entity, World world, BlockPos blockPos, int level) {
        List<BlockState> list = List.of(Blocks.FROSTED_ICE.getDefaultState(), Blocks.ICE.getDefaultState(), Blocks.PACKED_ICE.getDefaultState());
        BlockPos blockPos2 = world.getBlockState(blockPos.add(0, 1, 0)) == Blocks.WATER.getDefaultState() ? blockPos.add(0, 1, 0) : blockPos;
        switch (level) {
            case 2:
                world.setBlockState(blockPos2, list.get(1));
                break;
            case 3:
                world.setBlockState(blockPos2, list.get(2));
                break;
            default:
                world.setBlockState(blockPos2, list.get(0));
                world.scheduleBlockTick(blockPos2, Blocks.FROSTED_ICE, MathHelper.nextInt(entity.getWorld().getRandom(), 60, 120));
        }

    }

    @Override
    protected void onHit(LivingEntity target) {
        super.onHit(target);
        if (target.canFreeze()) {
            target.setFrozenTicks(duration);
        }
    }


    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("FreezeDuration", NbtElement.NUMBER_TYPE)) {
            this.duration = ArrowUtil.getFrostArrowDurationNbt(nbt);
        }
        if (nbt.contains("Level", NbtElement.NUMBER_TYPE)) {
            this.level = ArrowUtil.getFrostArrowLevelNbt(nbt);
        }
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        if (this.level != 0) {
            nbt.putInt("Level", level);
        }
        if (this.duration != 0) {
            nbt.putInt("FreezeDuration", duration);
        }
    }
}
