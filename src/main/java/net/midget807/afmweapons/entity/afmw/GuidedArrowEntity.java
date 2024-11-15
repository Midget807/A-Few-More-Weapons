package net.midget807.afmweapons.entity.afmw;

import net.midget807.afmweapons.entity.ModEntities;
import net.midget807.afmweapons.item.ModItems;
import net.midget807.afmweapons.util.ArrowUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class GuidedArrowEntity extends PersistentProjectileEntity {
    private int flightDuration;
    @Nullable
    private Entity target;
    @Nullable
    private UUID targetUuid;
    public GuidedArrowEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public GuidedArrowEntity(World world, double x, double y, double z) {
        super(ModEntities.GUIDED_ARROW_ENTITY_TYPE, x, y, z, world);
    }

    public GuidedArrowEntity(World world, LivingEntity owner) {
        super(ModEntities.GUIDED_ARROW_ENTITY_TYPE, owner, world);
    }

    public void initFromStack(ItemStack stack) {
        this.flightDuration = ArrowUtil.getGuidedArrowFlightTime(stack);
    }
    @Override
    protected ItemStack asItemStack() {
        ItemStack itemStack = new ItemStack(ModItems.GUIDED_ARROW);
        ArrowUtil.setGuidedArrow(itemStack, this.flightDuration);
        return itemStack;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getWorld().isClient) {
            this.spawnParticle(2);
        } else {
            if (this.flightDuration == 0) {
                this.discard();
            } else {
                --this.flightDuration;
            }

            this.getClosestValidTarget();

            // TODO: 15/11/2024 Debug
            if (target != null) {
                this.sendMessage(Text.literal("Target: " + target.getName()));
            }



        }
    }

    private void getClosestValidTarget() { // TODO: 15/11/2024 Make work
        Vec3d rotVec = this.getRotationVector();
        Box box = new Box(
                this.getX() - 2, this.getY() - 2, this.getZ() - 2,
                this.getX() + 2, this.getY() + 2, this.getZ() + 2
        );
        List<LivingEntity> entities = this.getWorld().getEntitiesByClass(LivingEntity.class, box, (livingEntity) -> {
            return livingEntity.canHit() && livingEntity != this.getOwner() && (!(livingEntity instanceof TameableEntity) || !((TameableEntity) livingEntity).isTamed());
        });
        double maxAngle = 0.3;
        for (Entity target : entities) {
            Vec3d distVec = target.getPos().subtract(this.getPos());
            double dotProduct = distVec.normalize().dotProduct(rotVec);
            if (dotProduct > maxAngle) {
                this.target = target;
                maxAngle = dotProduct;
            }
        }
    }

    public void spawnParticle(int amount) {
        this.getWorld().addParticle(ParticleTypes.DRAGON_BREATH, true, this.getX(), this.getY(), this.getZ(), 0, -0.1, 0);
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        this.getWorld().addParticle(ParticleTypes.REVERSE_PORTAL, true, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
        this.discard();
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);/*
        if (this.target != null) {
            nbt.putUuid("Target", this.target.getUuid());
        }*/
        if (flightDuration != 0) {
            nbt.putInt("FlightDuration", this.flightDuration);
        }
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);/*
        if (nbt.contains("Target")) {
            this.targetUuid = nbt.getUuid("Target");
        }*/
        if (nbt.contains("FlightDuration", NbtElement.NUMBER_TYPE)) {
            this.flightDuration = ArrowUtil.getGuidedArrowFlightTimeNbt(nbt);
        }
    }
}
