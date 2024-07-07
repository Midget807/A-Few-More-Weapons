package net.midget807.afmweapons.enchantment.afmw;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class TripleShotEnchantment extends Enchantment {
    public TripleShotEnchantment(Rarity rarity, EquipmentSlot[] slotTypes) {
        super(rarity, EnchantmentTarget.CROSSBOW, slotTypes);
    }
}
