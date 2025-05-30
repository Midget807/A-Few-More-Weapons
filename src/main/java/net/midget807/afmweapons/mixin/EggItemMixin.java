package net.midget807.afmweapons.mixin;

import net.midget807.afmweapons.item.ModItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EggItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EggItem.class)
public class EggItemMixin {
    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void afmw$ignoreEggThrow(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        ItemStack itemStack = user.getStackInHand(Hand.MAIN_HAND);
        if (itemStack.isOf(ModItems.FRYING_PAN)) {
            cir.cancel();
        }
    }
}
