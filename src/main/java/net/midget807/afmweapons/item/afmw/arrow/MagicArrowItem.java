package net.midget807.afmweapons.item.afmw.arrow;

import net.midget807.afmweapons.entity.afmw.MagicArrowEntity;
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

public class MagicArrowItem extends ArrowItem {
    public MagicArrowItem(Settings settings) {
        super(settings);
    }

    @Override
    public PersistentProjectileEntity createArrow(World world, ItemStack stack, LivingEntity shooter) {
        MagicArrowEntity magicArrowEntity = new MagicArrowEntity(world, shooter);
        magicArrowEntity.initFromStack(stack);
        magicArrowEntity.setPierceLevel((byte) 1000);
        return magicArrowEntity;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item.afmweapons.magic_arrow.desc").formatted(Formatting.GRAY));
        tooltip.add(ScreenTexts.EMPTY);
        tooltip.add(Text.translatable("item.afmweapons.custom_arrow_loadable").formatted(Formatting.GRAY));
    }

    @Override
    public ItemStack getDefaultStack() {
        return ArrowUtil.setMagicArrow(super.getDefaultStack(), 20);
    }
}
