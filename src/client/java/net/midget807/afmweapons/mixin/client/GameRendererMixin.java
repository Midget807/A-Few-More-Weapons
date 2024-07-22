package net.midget807.afmweapons.mixin.client;

import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(GameRenderer.class)
public class GameRendererMixin implements AutoCloseable {
    @Override
    public void close() throws Exception {

    }
    //Concussed Effect - Nausea
}
