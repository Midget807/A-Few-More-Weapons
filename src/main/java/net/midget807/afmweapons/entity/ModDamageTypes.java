package net.midget807.afmweapons.entity;

import net.midget807.afmweapons.AFMWMain;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public class ModDamageTypes {
    public static final RegistryKey<DamageType> SIZZLE = registerDamageType("sizzle");
    public static RegistryKey<DamageType> registerDamageType(String name) {
        return RegistryKey.of(RegistryKeys.DAMAGE_TYPE, AFMWMain.id(name));
    }
    public static void registerModDamageTypes() {
        AFMWMain.LOGGER.info("Registering AFMW Damage Types");
    }
}
