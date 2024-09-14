package net.midget807.afmweapons.particle;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.midget807.afmweapons.AFMWMain;
import net.midget807.afmweapons.item.ModItemGroups;
import net.minecraft.item.ItemGroups;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class ModParticles {
    public static final ParticleType<EchoArrowPulseParticleEffect> ECHO_ARROW_PULSE_PARTICLE_TYPE = registerParticleType("echo_arrow_pulse", true, EchoArrowPulseParticleEffect.FACTORY, type -> EchoArrowPulseParticleEffect.CODEC);

    public static <T extends ParticleEffect> ParticleType<T> registerParticleType(String name, boolean alwaysShow, ParticleEffect.Factory<T> factory, final Function<ParticleType<T>, Codec<T>> codecGetter) {
        return Registry.register(Registries.PARTICLE_TYPE, AFMWMain.id(name), new ParticleType<T>(alwaysShow, factory) {
            @Override
            public Codec<T> getCodec() {
                return codecGetter.apply(this);
            }
        });
    }
    public static DefaultParticleType registerDefaultParticleType(String name, DefaultParticleType particleType) {
        return Registry.register(Registries.PARTICLE_TYPE, AFMWMain.id(name), particleType);
    }

    public static void registerModParticles() {
        AFMWMain.LOGGER.info("Registering AFMW Particles");
    }
}
