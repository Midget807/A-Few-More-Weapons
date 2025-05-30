package net.midget807.afmweapons.mixin;

import net.midget807.afmweapons.datagen.ModItemTagProvider;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.item.Vanishable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.function.Predicate;

@Mixin(BowItem.class)
public class BowItemMixin extends RangedWeaponItem implements Vanishable {
    @Unique
    private static final Predicate<ItemStack> AFMW_BOW_PROJECTILES = stack -> stack.isIn(ModItemTagProvider.AFMW_BOW_PROJECTILES);
    public BowItemMixin(Settings settings) {
        super(settings);
    }

    @Override
    public Predicate<ItemStack> getProjectiles() {
        return AFMW_BOW_PROJECTILES;
    }

    @Override
    public int getRange() {
        return 15;
    }
}
