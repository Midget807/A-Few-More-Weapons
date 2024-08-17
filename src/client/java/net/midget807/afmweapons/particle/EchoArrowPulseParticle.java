package net.midget807.afmweapons.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.function.Consumer;

public class EchoArrowPulseParticle extends SpriteBillboardParticle {
    private int delay;
    protected EchoArrowPulseParticle(ClientWorld clientWorld, double x, double y, double z, int delay) {
        super(clientWorld, x, y, z, 0.0, 0.0, 0.0);
        this.scale = 0.85f;
        this.delay = delay;
        this.maxAge = 30;
        this.gravityStrength = 0.0f;
        this.velocityX = 0.0f;
        this.velocityY = 0.0f;
        this.velocityZ = 0.0f;
    }

    @Override
    public float getSize(float tickDelta) {
        return this.scale * MathHelper.clamp((this.age + tickDelta) / this.maxAge * 0.75f, 0.0f, 1.0f);
    }

    @Override
    public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
        if (this.delay <= 0) {
            this.alpha = 1.0F - MathHelper.clamp(((float)this.age + tickDelta) / (float)this.maxAge, 0.0F, 1.0F);
            this.buildGeometry(vertexConsumer, camera, tickDelta, quaternion -> quaternion.mul(new Quaternionf().rotationYXZ((float) -Math.PI, 1.0472F, 0.0F)));
        }
    }
    private void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta, Consumer<Quaternionf> rotator) {
        Vec3d vec3d = camera.getPos();
        float f = (float)(MathHelper.lerp((double)tickDelta, this.prevPosX, this.x) - vec3d.getX());
        float g = (float)(MathHelper.lerp((double)tickDelta, this.prevPosY, this.y) - vec3d.getY());
        float h = (float)(MathHelper.lerp((double)tickDelta, this.prevPosZ, this.z) - vec3d.getZ());
        Quaternionf quaternionf = new Quaternionf().setAngleAxis(0.0F, new Vector3f(0.5F, 0.5F, 0.5F).normalize().x(), new Vector3f(0.5F, 0.5F, 0.5F).normalize().y(), new Vector3f(0.5F, 0.5F, 0.5F).normalize().z());
        rotator.accept(quaternionf);
        quaternionf.transform(new Vector3f(-1.0f, -1.0f, 0.0f));
        Vector3f[] vector3fs = new Vector3f[]{
                new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)
        };
        float i = this.getSize(tickDelta);

        for (int j = 0; j < 4; j++) {
            Vector3f vector3f = vector3fs[j];
            vector3f.rotate(quaternionf);
            vector3f.mul(i);
            vector3f.add(f, g, h);
        }

        int j = this.getBrightness(tickDelta);
        this.vertex(vertexConsumer, vector3fs[0], this.getMaxU(), this.getMaxV(), j);
        this.vertex(vertexConsumer, vector3fs[1], this.getMaxU(), this.getMinV(), j);
        this.vertex(vertexConsumer, vector3fs[2], this.getMinU(), this.getMinV(), j);
        this.vertex(vertexConsumer, vector3fs[3], this.getMinU(), this.getMaxV(), j);
    }
    private void vertex(VertexConsumer vertexConsumer, Vector3f pos, float u, float v, int light) {
        vertexConsumer.vertex((double)pos.x(), (double)pos.y(), (double)pos.z()).texture(u, v).color(this.red, this.green, this.blue, this.alpha).light(light).next();
    }

    @Override
    protected int getBrightness(float tint) {
        return 240;
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }
    @Override
    public void tick() {
        if (this.delay > 0) {
            this.delay--;
        } else {
            super.tick();
        }
    }
    public static class Factory implements ParticleFactory<EchoArrowPulseParticleEffect> {
        private final SpriteProvider spriteProvider;
        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(EchoArrowPulseParticleEffect echoArrowPulseParticleEffect, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            EchoArrowPulseParticle echoArrowPulseParticle = new EchoArrowPulseParticle(world, x, y, z, echoArrowPulseParticleEffect.getDelay());
            echoArrowPulseParticle.setSprite(this.spriteProvider);
            echoArrowPulseParticle.setAlpha(1.0f);
            return echoArrowPulseParticle;
        }
    }
}
