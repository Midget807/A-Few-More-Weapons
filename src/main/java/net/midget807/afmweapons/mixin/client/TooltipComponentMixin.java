package net.midget807.afmweapons.mixin.client;

import net.midget807.afmweapons.item.afmw.client.TripleShotTooltipComponent;
import net.midget807.afmweapons.item.afmw.client.TripleShotTooltipData;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.item.TooltipData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TooltipComponent.class)
public interface TooltipComponentMixin {
    @Inject(method = "of(Lnet/minecraft/client/item/TooltipData;)Lnet/minecraft/client/gui/tooltip/TooltipComponent;", at = @At("HEAD"), cancellable = true)
    private static void afmw$addTripleShotTooltipComponent(TooltipData data, CallbackInfoReturnable<TooltipComponent> cir) {
        if (data instanceof TripleShotTooltipData) {
            cir.setReturnValue(new TripleShotTooltipComponent((TripleShotTooltipData) data));
        } else {
            throw new IllegalArgumentException("Unknown TooltipComponent");
        }
    }

}
