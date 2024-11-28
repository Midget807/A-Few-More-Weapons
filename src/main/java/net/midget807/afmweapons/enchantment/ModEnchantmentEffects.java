package net.midget807.afmweapons.enchantment;

import com.mojang.serialization.MapCodec;
import net.midget807.afmweapons.AFMWMain;
import net.midget807.afmweapons.enchantment.afmw.ConcussEnchantmentEffect;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModEnchantmentEffects {

    public static final MapCodec<? extends EnchantmentEntityEffect> CONCUSS_EFFECT = registerEntityEffect("concuss", ConcussEnchantmentEffect.CODEC);
    private static MapCodec<? extends EnchantmentEntityEffect> registerEntityEffect(String name, MapCodec<? extends EnchantmentEntityEffect> codec) {
        return Registry.register(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, AFMWMain.id(name), codec);
    }

    public static void registerModEnchantmentEffect() {
        AFMWMain.LOGGER.info("Registering Mod Enchantment Effects");
    }
}
