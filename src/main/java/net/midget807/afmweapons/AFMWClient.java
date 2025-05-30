package net.midget807.afmweapons;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.midget807.afmweapons.entity.ModEntities;
import net.midget807.afmweapons.entity.afmw.client.*;
import net.midget807.afmweapons.item.afmw.client.ModModelPredicateProviderRegistry;
import net.midget807.afmweapons.particle.ModParticles;
import net.midget807.afmweapons.particle.afmw.client.EchoArrowPulseParticle;
import net.midget807.afmweapons.screen.ModScreenHandlers;
import net.midget807.afmweapons.screen.afmw.client.FletchingScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

public class AFMWClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntities.FRIED_EGG_ENTITY_TYPE, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.FROST_ARROW_ENTITY_TYPE, FrostArrowEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.EXPLOSIVE_ARROW_ENTITY_TYPE, ExplosiveArrowEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.RICOCHET_ARROW_ENTITY_TYPE, RicochetArrowEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.WARP_ARROW_ENTITY_TYPE, WarpArrowEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.MAGIC_ARROW_ENTITY_TYPE, MagicArrowEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.ECHO_ARROW_ENTITY_TYPE, EchoArrowEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.SEEKING_ARROW_ENTITY_TYPE, SeekingArrowEntityRenderer::new);
        ModModelPredicateProviderRegistry.registerModModelPredicatesProviders();
        HandledScreens.register(ModScreenHandlers.FLETCHING_TABLE_SCREEN_HANDLER, FletchingScreen::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.ECHO_ARROW_PULSE_PARTICLE_TYPE, EchoArrowPulseParticle.Factory::new);
    }
}
