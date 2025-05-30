package net.midget807.afmweapons.item.afmw.arrow;

import net.midget807.afmweapons.entity.afmw.RicochetArrowEntity;
import net.midget807.afmweapons.util.ArrowUtil;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RicochetArrowItem extends ArrowItem {
    public RicochetArrowItem(Settings settings) {
        super(settings);
    }

    @Override
    public PersistentProjectileEntity createArrow(World world, ItemStack stack, LivingEntity shooter) {
        RicochetArrowEntity ricochetArrowEntity = new RicochetArrowEntity(world, shooter);
        ricochetArrowEntity.initFromStack(stack);
        return ricochetArrowEntity;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item.afmweapons.ricochet_arrow.desc").formatted(Formatting.GRAY));
        tooltip.add(ScreenTexts.EMPTY);
        tooltip.add(Text.translatable("item.afmweapons.custom_arrow_loadable").formatted(Formatting.GRAY));
    }

    @Override
    public ItemStack getDefaultStack() {
        return ArrowUtil.setRicochetArrow(super.getDefaultStack(), 2);
    }
}
