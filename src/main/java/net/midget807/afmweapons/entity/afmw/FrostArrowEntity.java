package net.midget807.afmweapons.entity.afmw;

import net.midget807.afmweapons.effect.ModEffects;
import net.midget807.afmweapons.entity.ModEntities;
import net.midget807.afmweapons.item.ModItems;
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
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class FrostArrowEntity extends PersistentProjectileEntity {
    private int duration = 240;
    private int level = 1;
    public FrostArrowEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }
    public FrostArrowEntity(World world, double x, double y, double z) {
        super(ModEntities.FROST_ARROW_ENTITY_TYPE, x, y, z, world);
    }
    public FrostArrowEntity(World world, LivingEntity owner) {
        super(ModEntities.FROST_ARROW_ENTITY_TYPE, owner, world);
    }

    @Override
    protected ItemStack asItemStack() {
        return new ItemStack(ModItems.FROST_ARROW);
    }

    @Override
    public void tick() {
        if (this.isTouchingWater()) {
            freezeWater(this, this.getWorld(), this.getBlockPos(), level);
            this.discard();
        } else {
            super.tick();
        }
    }

    public void freezeWater(Entity entity, World world, BlockPos blockPos, int level) {
        BlockState blockState = Blocks.FROSTED_ICE.getDefaultState();
        int spreadOffset = Math.min(16, level);
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (BlockPos blockPos2 : BlockPos.iterate(blockPos.add(-spreadOffset, 0, -spreadOffset), blockPos.add(spreadOffset, 0, spreadOffset))) {
            BlockState blockState3 = world.getBlockState(blockPos2);
            mutable.set(blockPos2.getX(), blockPos2.getY(), blockPos2.getZ());
            BlockState blockState2 = world.getBlockState(mutable);
            if (!blockState2.isAir() || (blockState3 != FrostedIceBlock.getMeltedState() || !blockState.canPlaceAt(world, blockPos2) || !world.canPlace(blockState, blockPos2, ShapeContext.absent())))continue;
            world.setBlockState(blockPos2, blockState);
            world.scheduleBlockTick(blockPos2, Blocks.FROSTED_ICE, MathHelper.nextInt(entity.getEntityWorld().random, 60, 120));
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
        if (nbt.contains("FreezeDuration")) {
            this.duration = nbt.getInt("FreezeDuration");
        }
        if (nbt.contains("Level")) {
            this.duration = nbt.getInt("Level");
        }
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("FreezeDuration", this.duration);
        nbt.putInt("Level", this.level);
    }
}
