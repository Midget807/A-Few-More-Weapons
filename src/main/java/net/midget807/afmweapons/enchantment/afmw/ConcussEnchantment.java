package net.midget807.afmweapons.enchantment.afmw;

import net.midget807.afmweapons.item.ModItems;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;

public class ConcussEnchantment extends Enchantment {
    public ConcussEnchantment(Rarity rarity, EquipmentSlot[] slotTypes) {
        super(rarity, EnchantmentTarget.WEAPON, slotTypes);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public int getMinLevel() {
        return 20;
    }

    @Override
    public int getMaxPower(int level) {
        return 50;
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return stack.isOf(ModItems.FRYING_PAN);
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        if (target instanceof LivingEntity) {
            ((LivingEntity) target).addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, (level * level) * 20 + 60, 0));
            ((LivingEntity) target).addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, (level * level) * 20 + 60, 0));
            // TODO: 26/07/2024 - replace with concussed effect
        }
        super.onTargetDamaged(user, target, level);
    }
}
