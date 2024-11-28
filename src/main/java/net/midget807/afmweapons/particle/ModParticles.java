package net.midget807.afmweapons.particle;

import net.midget807.afmweapons.AFMWMain;
import net.midget807.afmweapons.particle.afmw.PublicSimpleParticleType;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModParticles {
    public static final SimpleParticleType ENTITY_SMASH_PARTICLE =
            registerParticle("entity_smash", true);
    public static final SimpleParticleType ECHO_ARROW_PULSE_PARTICLE_TYPE =
            registerParticle("echo_arrow_pulse", true);

    private static SimpleParticleType registerParticle(String name, boolean alwaysShow) {
        return Registry.register(Registries.PARTICLE_TYPE, AFMWMain.id(name), new PublicSimpleParticleType(alwaysShow));
    }

    public static void registerModParticles() {
        AFMWMain.LOGGER.info("Registering AFMW Particles");
    }
}
