package net.midget807.afmweapons.enchantment;

import net.midget807.afmweapons.AFMWMain;
import net.midget807.afmweapons.enchantment.afmw.ConcussEnchantment;
import net.midget807.afmweapons.enchantment.afmw.DeflectionEnchantment;
import net.midget807.afmweapons.enchantment.afmw.TripleShotEnchantment;
import net.midget807.afmweapons.enchantment.afmw.VindictiveEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModEnchantments {
    public static final Enchantment TRIPLE_SHOT = registerEnchantment("triple_shot", new TripleShotEnchantment(Enchantment.Rarity.RARE, new EquipmentSlot[]{EquipmentSlot.MAINHAND}));
    public static final Enchantment CONCUSS = registerEnchantment("concuss", new ConcussEnchantment(Enchantment.Rarity.VERY_RARE, new EquipmentSlot[]{EquipmentSlot.MAINHAND}));
    public static final Enchantment DEFLECTION = registerEnchantment("deflection", new DeflectionEnchantment(Enchantment.Rarity.UNCOMMON, new EquipmentSlot[]{EquipmentSlot.MAINHAND}));
    public static final Enchantment VINDICTIVE = registerEnchantment("vindictive", new VindictiveEnchantment(Enchantment.Rarity.RARE, new EquipmentSlot[]{EquipmentSlot.MAINHAND}));
    public static Enchantment registerEnchantment(String name, Enchantment enchantment) {
        return Registry.register(Registries.ENCHANTMENT, AFMWMain.id(name),enchantment);
    }
    public static void registerModEnchantments() {
        AFMWMain.LOGGER.info("Registering AFMW Enchantments");
    }
}
