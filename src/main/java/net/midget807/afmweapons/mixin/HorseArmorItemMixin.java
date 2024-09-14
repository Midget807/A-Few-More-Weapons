package net.midget807.afmweapons.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(HorseArmorItem.class)
public abstract class HorseArmorItemMixin extends Item {

    public HorseArmorItemMixin(Settings settings) {
        super(settings);
    }
    @ModifyReturnValue(method = "getBonus", at = @At("RETURN"))
    private int afmw$buffArmor(int original) {
        return (int) (original * 1.5);
    }
}
