package net.midget807.afmweapons.event;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.midget807.afmweapons.datagen.ModItemTagProvider;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.util.ActionResult;

public class ModEvents {
    public static void registerModEvents() {
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (player.getMainHandStack().isIn(ModItemTagProvider.HALBERDS) && !player.isSpectator()) {
                player.fallDistance = 0.0f;
            }
            return ActionResult.PASS;
        });
    }
}
