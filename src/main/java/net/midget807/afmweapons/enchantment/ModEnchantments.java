package net.midget807.afmweapons.enchantment;

import net.midget807.afmweapons.AFMWMain;
import net.midget807.afmweapons.datagen.ModItemTagProvider;
import net.midget807.afmweapons.enchantment.afmw.DeflectionEnchantment;
import net.midget807.afmweapons.enchantment.afmw.VindictiveEnchantment;
import net.midget807.afmweapons.enchantment.afmw.ConcussEnchantmentEffect;
import net.midget807.afmweapons.enchantment.afmw.TripleShotEnchantment;
import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.EnchantmentEffectTarget;
import net.minecraft.enchantment.effect.value.AddEnchantmentEffect;
import net.minecraft.enchantment.effect.value.SetEnchantmentEffect;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.EnchantmentTags;
import net.minecraft.registry.tag.ItemTags;

public class ModEnchantments {
    public static final RegistryKey<Enchantment> TRIPLE_SHOT = RegistryKey.of(RegistryKeys.ENCHANTMENT, AFMWMain.id("triple_shot"));
    public static final RegistryKey<Enchantment> CONCUSS = RegistryKey.of(RegistryKeys.ENCHANTMENT, AFMWMain.id("concuss"));
    public static final RegistryKey<Enchantment> DEFLECTION = RegistryKey.of(RegistryKeys.ENCHANTMENT, AFMWMain.id("deflection"));
    public static final RegistryKey<Enchantment> VINDICTIVE = RegistryKey.of(RegistryKeys.ENCHANTMENT, AFMWMain.id("vindictive"));
    public static void bootstrap(Registerable<Enchantment> registerable) {
        var enchantments = registerable.getRegistryLookup(RegistryKeys.ENCHANTMENT);
        var items = registerable.getRegistryLookup(RegistryKeys.ITEM);

        registerEnchantment(registerable, TRIPLE_SHOT, Enchantment.builder(
                Enchantment.definition(
                        items.getOrThrow(ItemTags.CROSSBOW_ENCHANTABLE),
                        2,
                        1,
                        Enchantment.constantCost(35),
                        Enchantment.constantCost(50),
                        4,
                        AttributeModifierSlot.MAINHAND
                )).exclusiveSet(
                enchantments.getOrThrow(EnchantmentTags.CROSSBOW_EXCLUSIVE_SET)
                )
        );

        registerEnchantment(registerable, CONCUSS, Enchantment.builder(Enchantment.definition(
                items.getOrThrow(ModItemTagProvider.FRYING_PAN_ENCHANTABLE),
                5,
                2,
                Enchantment.leveledCost(5, 7),
                Enchantment.leveledCost(25, 9),
                2,
                AttributeModifierSlot.MAINHAND
                )).addEffect(
                EnchantmentEffectComponentTypes.POST_ATTACK,
                EnchantmentEffectTarget.ATTACKER,
                EnchantmentEffectTarget.VICTIM,
                new ConcussEnchantmentEffect(EnchantmentLevelBasedValue.linear(2.0f))
                )
        );
        // TODO: 29/11/2024 Fix enchantment data like weight and min & max level
        registerEnchantment(registerable, DEFLECTION, Enchantment.builder(
                        Enchantment.definition(
                                items.getOrThrow(ModItemTagProvider.LONGSWORD_ENCHANTABLE),
                                2,
                                1,
                                Enchantment.constantCost(35),
                                Enchantment.constantCost(50),
                                4,
                                AttributeModifierSlot.MAINHAND
                        ))
        );
        registerEnchantment(registerable, VINDICTIVE, Enchantment.builder(
                        Enchantment.definition(
                                items.getOrThrow(ModItemTagProvider.CLAYMORE_ENCHANTABLE),
                                2,
                                1,
                                Enchantment.constantCost(35),
                                Enchantment.constantCost(50),
                                4,
                                AttributeModifierSlot.MAINHAND
                        ))
        );
    }
    public static void registerEnchantment(Registerable<Enchantment> registry, RegistryKey<Enchantment> key, Enchantment.Builder builder) {
        registry.register(key, builder.build(key.getValue()));
    }
    public static void registerModEnchantments() {
        AFMWMain.LOGGER.info("Registering AFMW Enchantments");
    }
}
