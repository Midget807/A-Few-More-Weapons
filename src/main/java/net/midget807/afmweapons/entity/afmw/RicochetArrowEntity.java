package net.midget807.afmweapons.entity.afmw;

import net.midget807.afmweapons.entity.ModEntities;
import net.midget807.afmweapons.item.ModItems;
import net.midget807.afmweapons.item.afmw.arrow.util.ArrowUtil;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class RicochetArrowEntity extends PersistentProjectileEntity {
    public int bounces;
    public static final Direction[] DIRECTIONS = Direction.values();
    public RicochetArrowEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public RicochetArrowEntity(World world, double x, double y, double z) {
        super(ModEntities.RICOCHET_ARROW_ENTITY_TYPE, x, y, z, world);
    }

    public RicochetArrowEntity(World world, LivingEntity owner) {
        super(ModEntities.RICOCHET_ARROW_ENTITY_TYPE, owner, world);
    }
    public void initFromStack(ItemStack stack) {
        this.bounces = ArrowUtil.getRicochetArrowBounces(stack);
    }

    @Override
    protected ItemStack asItemStack() {
        ItemStack itemStack = new ItemStack(ModItems.RICOCHET_ARROW);
        ArrowUtil.setRicochetArrow(itemStack, bounces);
        return itemStack;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.inGround && this.inGroundTime != 0 && this.inGroundTime >= 600) {
            this.getWorld().sendEntityStatus(this, (byte) 0);
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        Direction direction = blockHitResult.getSide();
        Vec3d vec3d = this.getVelocity();
        if (bounces > 0) {
            if (direction == Direction.UP || direction == Direction.DOWN) {
                this.setVelocity(vec3d.x, -vec3d.y, vec3d.z);
                this.bounces--;
            }
            if (direction == Direction.EAST || direction == Direction.WEST) {
                this.setVelocity(-vec3d.x, vec3d.y, vec3d.z);
                this.bounces--;
            }
            if (direction == Direction.NORTH || direction == Direction.SOUTH) {
                this.setVelocity(vec3d.x, vec3d.y, -vec3d.z);
                this.bounces--;
            }
        } else {
            super.onBlockHit(blockHitResult);
        }
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("Bounces", NbtElement.NUMBER_TYPE)) {
            this.bounces = ArrowUtil.getRicochetArrowBouncesNbt(nbt);
        }
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        if (this.bounces != 0) {
            nbt.putInt("Bounces", bounces);
        }
    }
}
