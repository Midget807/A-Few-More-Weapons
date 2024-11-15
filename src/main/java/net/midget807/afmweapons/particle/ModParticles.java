package net.midget807.afmweapons.particle;

import net.midget807.afmweapons.AFMWMain;
import net.midget807.afmweapons.particle.afmw.PublicDefaultParticleType;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModParticles {
    public static final DefaultParticleType ECHO_ARROW_PULSE_PARTICLE_TYPE =
            registerParticle("echo_arrow_pulse", true);

    private static DefaultParticleType registerParticle(String name, boolean alwaysShow) {
        return Registry.register(Registries.PARTICLE_TYPE, AFMWMain.id(name), new PublicDefaultParticleType(alwaysShow));
    }

    public static void registerModParticles() {
        AFMWMain.LOGGER.info("Registering AFMW Particles");
    }
}
