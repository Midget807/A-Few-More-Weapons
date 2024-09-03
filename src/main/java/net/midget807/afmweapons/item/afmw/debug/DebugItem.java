package net.midget807.afmweapons.item.afmw.debug;

import net.midget807.afmweapons.sound.ModSoundEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.TitleFadeS2CPacket;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class DebugItem extends Item {
    public DebugItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (!world.isClient && user instanceof ServerPlayerEntity serverPlayer) {
            world.playSoundFromEntity(null, user, ModSoundEvents.SAD_TROMBONE, SoundCategory.HOSTILE, 1.0f, 1.0f);
            user.teleport(user.getX(), -70, user.getZ());
            serverPlayer.networkHandler.sendPacket(new TitleS2CPacket(Text.literal("haha L bozo").formatted(Formatting.BOLD).formatted(Formatting.RED)));
            serverPlayer.networkHandler.sendPacket(new TitleFadeS2CPacket(10, 60, 10));
        }
        return TypedActionResult.consume(itemStack);
    }
}
