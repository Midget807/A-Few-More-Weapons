package net.midget807.afmweapons.particle.afmw.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.function.Consumer;

public class EchoArrowPulseParticle extends ExplosionLargeParticle {
    public static final Vector3f ROTATION_VECTOR = new Vector3f(0.5f, 0.5f, 0.5f).normalize();
    public static final Vector3f TRANSFORMATION_VECTOR = new Vector3f(-1.0f, -1.0f, 0.0f);
    public EchoArrowPulseParticle(ClientWorld world, double x, double y, double z, double d, SpriteProvider spriteProvider) {
        super(world, x, y, z, d, spriteProvider);
        this.maxAge = 24;
        this.scale = 2.0f;
        this.gravityStrength = 0.0f;
        this.velocityX = 0.0f;
        this.velocityY = 0.0f;
        this.velocityZ = 0.0f;
        this.red = 1.0f;
        this.green = 1.0f;
        this.blue = 1.0f;
        this.setSpriteForAge(spriteProvider);
    }

    @Override
    public float getSize(float tickDelta) {
        float d = ((float) this.age + tickDelta) / (float) this.maxAge;
        return this.scale * MathHelper.clamp(d, 0.0f, 1.0f);
    }

    @Override
    public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
        this.alpha = 1.0f - MathHelper.clamp(((float) this.age + tickDelta) / (float) this.maxAge, 0.0f, 1.0f);
        this.buildGeometry(vertexConsumer, camera, tickDelta, (quaternion) -> {
            quaternion.mul(new Quaternionf().rotateX((float) -Math.PI * 0.5f));
            quaternion.mul(new Quaternionf().rotateY((float) -Math.PI));
        });
        this.buildGeometry(vertexConsumer, camera, tickDelta, (quaternion) -> {
            quaternion.mul(new Quaternionf().rotateX((float) Math.PI * 0.5f));
            quaternion.mul(new Quaternionf().rotateY((float) Math.PI));
        });
    }

    @Override
    public void tick() {
        super.tick();
    }

    private void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta, Consumer<Quaternionf> rotator) {
        Vec3d cameraPos = camera.getPos();
        float f = (float)(MathHelper.lerp(tickDelta, this.prevPosX, this.x) - cameraPos.getX());
        float g = (float)(MathHelper.lerp(tickDelta, this.prevPosY, this.y) - cameraPos.getY());
        float h = (float)(MathHelper.lerp(tickDelta, this.prevPosZ, this.z) - cameraPos.getZ());
        Quaternionf quaternionf = new Quaternionf().setAngleAxis(0.0f, ROTATION_VECTOR.x(), ROTATION_VECTOR.y(), ROTATION_VECTOR.z());
        rotator.accept(quaternionf);
        quaternionf.transform(TRANSFORMATION_VECTOR);
        Vector3f[] vector3fs = new Vector3f[]{
                new Vector3f(-1.0f, -1.0f, 0.0f),
                new Vector3f(-1.0f, 1.0f, 0.0f),
                new Vector3f(1.0f, 1.0f, 0.0f),
                new Vector3f(1.0f, -1.0f, 0.0f)
        };
        float size = this.getSize(tickDelta);
        int brightness;
        for (brightness = 0; brightness < 4; ++brightness) {
            Vector3f vector3f = vector3fs[brightness];
            vector3f.rotate(quaternionf);
            vector3f.mul(size);
            vector3f.add(f, g, h);
        }
        brightness = this.getBrightness(tickDelta);
        this.vertex(vertexConsumer, vector3fs[0], this.getMaxU(), this.getMaxV(), brightness);
        this.vertex(vertexConsumer, vector3fs[1], this.getMaxU(), this.getMinV(), brightness);
        this.vertex(vertexConsumer, vector3fs[2], this.getMinU(), this.getMinV(), brightness);
        this.vertex(vertexConsumer, vector3fs[3], this.getMinU(), this.getMaxV(), brightness);
    }

    private void vertex(VertexConsumer vertexConsumer, Vector3f pos, float u, float v, int light) {
        vertexConsumer.vertex((double) pos.x(), (double) pos.y(), (double) pos.z()).texture(u, v).color(this.red, this.green, this.blue, this.alpha).light(light).next();
    }

    @Override
    public int getBrightness(float tint) {
        return 240;
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }
    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;
        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }
        @Nullable
        @Override
        public Particle createParticle(DefaultParticleType particleType, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new EchoArrowPulseParticle(world, x, y, z, velocityX, this.spriteProvider);
        }
    }
}
