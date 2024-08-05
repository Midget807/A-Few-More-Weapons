package net.midget807.afmweapons.entity.afmw;

import net.midget807.afmweapons.entity.ModEntities;
import net.midget807.afmweapons.item.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class FrostArrowEntity extends PersistentProjectileEntity {
    private static final String LEVEL_KEY = "Level";
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
    public void initFromStack(ItemStack stack) {
        this.level = getItemLevel(stack);
    }

    public int getItemLevel(ItemStack stack) {
        return getLevel(stack.getNbt());
    }

    private int getLevel(@Nullable NbtCompound compound) {
        if (compound == null) {
            return 0;
        }
        return compound.getInt(LEVEL_KEY);
    }
    public void setLevel(ItemStack stack, int level) {
        if (level == 0 || level > 3) {
            stack.removeSubNbt(LEVEL_KEY);
        } else {
            stack.getOrCreateNbt().putInt(LEVEL_KEY, level);
        }
    }

    @Override
    protected ItemStack asItemStack() {
        return new ItemStack(ModItems.FROST_ARROW);
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        if (this.isTouchingWater()) {
            this.getWorld().setBlockState(this.getBlockPos(), Blocks.FROSTED_ICE.getDefaultState());
            this.kill();
        } else {
            Vec3d vec3d = blockHitResult.getPos().subtract(this.getX(), this.getY(), this.getZ());
            this.setVelocity(vec3d);
            Vec3d vec3d2 = vec3d.normalize().multiply(0.05f);
            this.setPos(this.getX() - vec3d2.x, this.getY() - vec3d2.y, this.getZ() - vec3d2.z);
            this.playSound(this.getSound(), 1.0f, 1.2f / (this.random.nextFloat() * 0.2f + 0.9f));
            this.inGround = true;
            this.shake = 7;
            this.setCritical(false);
            this.setPierceLevel((byte)0);
            this.setSound(SoundEvents.ENTITY_ARROW_HIT);
            this.setShotFromCrossbow(false);
        }
    }
}
