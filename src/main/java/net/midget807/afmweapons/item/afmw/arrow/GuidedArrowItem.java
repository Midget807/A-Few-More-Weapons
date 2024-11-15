package net.midget807.afmweapons.item.afmw.arrow;

import net.midget807.afmweapons.entity.afmw.GuidedArrowEntity;
import net.midget807.afmweapons.util.ArrowUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GuidedArrowItem extends ArrowItem {
    public GuidedArrowItem(Settings settings) {
        super(settings);
    }

    @Override
    public PersistentProjectileEntity createArrow(World world, ItemStack stack, LivingEntity shooter) {
        GuidedArrowEntity guidedArrowEntity = new GuidedArrowEntity(world, shooter);
        guidedArrowEntity.initFromStack(stack);
        return guidedArrowEntity;
    }

    @Override
    public ItemStack getDefaultStack() {
        return ArrowUtil.setGuidedArrow(super.getDefaultStack(), 40);
    }
}
