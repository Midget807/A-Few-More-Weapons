package net.midget807.afmweapons.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.function.Consumer;

public class EchoArrowPulseParticle extends SpriteBillboardParticle {
    private int delay;
    public EchoArrowPulseParticle(ClientWorld clientWorld, double x, double y, double z, int delay) {
        super(clientWorld, x, y, z, 0.0, 0.0, 0.0);
        this.scale = 0.85f;
        this.maxAge = 20;
        this.gravityStrength = 0.0f;
        this.delay = delay;
    }
    /*

    @Override
    public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
        this.buildGeometry(vertexConsumer, camera, tickDelta, quaternion -> quaternion.mul(new Quaternionf().rotationX(0.0f)));
    }

    private void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta, Consumer<Quaternionf> rotator) {
        Vec3d vec3d = camera.getPos();
    }*/


    @Override
    protected int getBrightness(float tint) {
        return 240;
    }



    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public float getSize(float tickDelta) {
        return this.scale * MathHelper.clamp((this.age + tickDelta) / (this.maxAge * 0.75f), 0.0f, 1.0f);
    }

    @Override
    public void tick() {
        super.tick();
    }
    public static class Factory implements ParticleFactory<EchoArrowPulseParticleEffect> {
        private final SpriteProvider spriteProvider;
        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(EchoArrowPulseParticleEffect echoArrowPulseParticleEffect, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            EchoArrowPulseParticle particle = new EchoArrowPulseParticle(world, x, y, z, echoArrowPulseParticleEffect.getDelay());
            particle.setSprite(this.spriteProvider);
            particle.setAlpha(1.0f);
            return particle;
        }
    }
}
