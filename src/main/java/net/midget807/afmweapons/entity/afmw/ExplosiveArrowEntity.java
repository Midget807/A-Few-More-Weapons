package net.midget807.afmweapons.entity.afmw;

import net.midget807.afmweapons.entity.ModEntities;
import net.midget807.afmweapons.item.ModItems;
import net.midget807.afmweapons.item.afmw.arrow.ExplosiveArrowItem;
import net.midget807.afmweapons.util.ArrowUtil;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;

public class ExplosiveArrowEntity extends PersistentProjectileEntity {
    private int explosionPower;

    public ExplosiveArrowEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public ExplosiveArrowEntity(World world, double x, double y, double z) {
        super(ModEntities.EXPLOSIVE_ARROW_ENTITY_TYPE, x, y, z, world);
    }

    public ExplosiveArrowEntity(World world, LivingEntity owner) {
        super(ModEntities.EXPLOSIVE_ARROW_ENTITY_TYPE, owner, world);
    }
    public void initFromStack(ItemStack stack) {
        this.explosionPower = ArrowUtil.getExplosiveArrowExplosionPower(stack);
    }

    @Override
    protected void onHit(LivingEntity target) {
        super.onHit(target);
        if (target != this.getOwner()) {
            if (!this.getWorld().isClient) {
                this.getWorld().createExplosion(this, this.getX(), this.getY(), this.getZ(), explosionPower, false, World.ExplosionSourceType.NONE);
            }
            this.discard();
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        if (!this.getWorld().isClient) {
            this.getWorld().createExplosion(this, this.getX(), this.getY(), this.getZ(), explosionPower, false, World.ExplosionSourceType.NONE);
        }
        this.discard();

    }

    @Override
    protected ItemStack asItemStack() {
        ItemStack itemStack = new ItemStack(ModItems.EXPLOSIVE_ARROW);
        ArrowUtil.setExplosiveArrow(itemStack, explosionPower);
        return itemStack;
    }
}
