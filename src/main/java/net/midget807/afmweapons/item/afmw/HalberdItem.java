package net.midget807.afmweapons.item.afmw;

import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.Vanishable;

public class HalberdItem extends SwordItem implements Vanishable {
    public HalberdItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }
}
