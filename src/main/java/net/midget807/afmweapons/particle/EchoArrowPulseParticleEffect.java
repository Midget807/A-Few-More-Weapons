package net.midget807.afmweapons.particle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.ShriekParticleEffect;
import net.minecraft.registry.Registries;

import java.util.Locale;

public class EchoArrowPulseParticleEffect implements ParticleEffect {

    public static final Codec<EchoArrowPulseParticleEffect> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            (Codec.INT.fieldOf("delay")).forGetter(particleEffect -> particleEffect.delay)
    ).apply(instance, EchoArrowPulseParticleEffect::new));
    public static final ParticleEffect.Factory<EchoArrowPulseParticleEffect> FACTORY = new ParticleEffect.Factory<EchoArrowPulseParticleEffect>() {
        @Override
        public EchoArrowPulseParticleEffect read(ParticleType<EchoArrowPulseParticleEffect> type, StringReader reader) throws CommandSyntaxException {
            reader.expect(' ');
            int i = reader.readInt();
            return new EchoArrowPulseParticleEffect(i);
        }

        @Override
        public EchoArrowPulseParticleEffect read(ParticleType<EchoArrowPulseParticleEffect> type, PacketByteBuf buf) {
            return new EchoArrowPulseParticleEffect(buf.readVarInt());
        }
    };
    private final int delay;

    public EchoArrowPulseParticleEffect(int delay) {
        this.delay = delay;
    }

    @Override
    public ParticleType<?> getType() {
        return ModParticles.ECHO_ARROW_PULSE_PARTICLE_TYPE;
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeVarInt(this.delay);
    }

    @Override
    public String asString() {
        return String.format(Locale.ROOT, "%s %d", Registries.PARTICLE_TYPE.getId(this.getType()), this.delay);
    }

    public int getDelay() {
        return this.delay;
    }
}
