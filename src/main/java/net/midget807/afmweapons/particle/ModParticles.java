package net.midget807.afmweapons.particle;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.midget807.afmweapons.AFMWMain;
import net.midget807.afmweapons.item.ModItemGroups;
import net.midget807.afmweapons.particle.afmw.PublicDefaultParticleType;
import net.minecraft.item.ItemGroups;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class ModParticles {
    public static final DefaultParticleType ECHO_ARROW_PULSE_PARTICLE_TYPE = registerDefaultParticleType("echo_arrow_pulse", true);


    public static DefaultParticleType registerDefaultParticleType(String name, boolean alwaysShow) {
        return Registry.register(Registries.PARTICLE_TYPE, AFMWMain.id(name), new PublicDefaultParticleType(alwaysShow));
    }

    public static void registerModParticles() {
        AFMWMain.LOGGER.info("Registering AFMW Particles");
    }
}
