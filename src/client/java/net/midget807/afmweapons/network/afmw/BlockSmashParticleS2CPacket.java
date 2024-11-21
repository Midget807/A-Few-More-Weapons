package net.midget807.afmweapons.network.afmw;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class BlockSmashParticleS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        float radius = buf.readFloat();
        BlockPos blockPos = buf.readBlockPos();
        Vec3d pos = new Vec3d(blockPos.getX(), blockPos.getY(), blockPos.getZ());
        Vec3d particlePos = pos.add(pos.normalize().multiply(radius));
        Quaternionf rotation = new Quaternionf().setAngleAxis(0.0f, 0.0, 1.0, 0.0);
        rotation.transform(new Vector3f((float) particlePos.x, (float) particlePos.y, (float) particlePos.z));
        for (int i = 0; i < 19; i++) {
            if (client.world != null) {
                BlockState state = client.world.getBlockState(new BlockPos(new Vec3i((int) particlePos.x, (int) particlePos.y, (int) particlePos.z)));
                client.particleManager.addBlockBreakParticles(blockPos, state);
            }
            rotation.mul(new Quaternionf().rotateZ((float) (Math.PI / 10))); //Pi / 10 == 18deg
        }

    }
}
