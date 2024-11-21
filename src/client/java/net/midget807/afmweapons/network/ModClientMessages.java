package net.midget807.afmweapons.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.midget807.afmweapons.network.afmw.BlockSmashParticleS2CPacket;
import net.minecraft.client.network.ClientPlayNetworkHandler;

public class ModClientMessages {
    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(ModMessages.SPAWN_BLOCK_SMASH_PARTICLE, BlockSmashParticleS2CPacket::receive);
    }
}
