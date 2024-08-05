package net.midget807.afmweapons.item.afmw.arrow;

import net.midget807.afmweapons.entity.afmw.FrostArrowEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class FrostArrowItem extends ArrowItem {
    private final int level;
    public FrostArrowItem(Settings settings, int level) {
        super(settings);
        this.level = level;
    }

    @Override
    public PersistentProjectileEntity createArrow(World world, ItemStack stack, LivingEntity shooter) {
        FrostArrowEntity frostArrowEntity = new FrostArrowEntity(world, shooter);
        frostArrowEntity.setLevel(stack, level);
        frostArrowEntity.initFromStack(stack);
        return frostArrowEntity;
    }
}
