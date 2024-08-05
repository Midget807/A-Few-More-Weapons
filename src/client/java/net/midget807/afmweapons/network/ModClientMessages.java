package net.midget807.afmweapons.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.midget807.afmweapons.network.afmw.ItemStackSyncS2CPacket;

public class ModClientMessages {
    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(ModMessages.ITEM_SYNC, ItemStackSyncS2CPacket::receive);
    }
}
