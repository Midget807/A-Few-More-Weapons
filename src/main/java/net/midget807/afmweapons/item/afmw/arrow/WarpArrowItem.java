package net.midget807.afmweapons.item.afmw.arrow;

import net.midget807.afmweapons.entity.afmw.WarpArrowEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class WarpArrowItem extends ArrowItem {
    public WarpArrowItem(Settings settings) {
        super(settings);
    }

    @Override
    public PersistentProjectileEntity createArrow(World world, ItemStack stack, LivingEntity shooter) {
        return new WarpArrowEntity(world, shooter);
    }
}
