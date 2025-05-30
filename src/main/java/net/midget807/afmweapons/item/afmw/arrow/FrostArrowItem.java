package net.midget807.afmweapons.item.afmw.arrow;

import net.midget807.afmweapons.entity.afmw.FrostArrowEntity;
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

public class FrostArrowItem extends ArrowItem {
    public FrostArrowItem(Settings settings) {
        super(settings);
    }

    @Override
    public PersistentProjectileEntity createArrow(World world, ItemStack stack, LivingEntity shooter) {
         FrostArrowEntity frostArrowEntity = new FrostArrowEntity(world, shooter);
         frostArrowEntity.initFromStack(stack);
         return frostArrowEntity;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item.afmweapons.frost_arrow.desc").formatted(Formatting.GRAY));
        tooltip.add(ScreenTexts.EMPTY);
        tooltip.add(Text.translatable("item.afmweapons.custom_arrow_loadable").formatted(Formatting.GRAY));
        /*tooltip.add(Text.literal("Level: " + ArrowUtil.getFrostArrowLevel(stack)));
        tooltip.add(Text.literal("Duration: " + ArrowUtil.getFrostArrowDuration(stack)));*/
    }

    @Override
    public ItemStack getDefaultStack() {
        return ArrowUtil.setFrostArrow(super.getDefaultStack(), 1, 240);
    }
}
