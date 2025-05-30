package net.midget807.afmweapons.effect;

import net.midget807.afmweapons.AFMWMain;
import net.midget807.afmweapons.effect.afmw.EmptyEffect;
import net.midget807.afmweapons.effect.afmw.SizzlingEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModEffects {
    public static final StatusEffect CONCUSSED = registerEffect("concuss", new EmptyEffect(StatusEffectCategory.HARMFUL, 0x373d45));
    public static final StatusEffect SIZZLE = registerEffect("sizzle", new SizzlingEffect(StatusEffectCategory.HARMFUL, 0xfcc33d));
    public static StatusEffect registerEffect(String name, StatusEffect effect) {
        return Registry.register(Registries.STATUS_EFFECT, AFMWMain.id(name), effect);
    }
    public static void registerModEffects() {
        AFMWMain.LOGGER.info("Registering AFMW Effects");
    }
}
