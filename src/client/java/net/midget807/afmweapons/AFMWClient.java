package net.midget807.afmweapons;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.midget807.afmweapons.entity.ModEntities;
import net.midget807.afmweapons.entity.afmw.GuidedArrowEntity;
import net.midget807.afmweapons.network.ModClientMessages;
import net.midget807.afmweapons.particle.EchoArrowPulseParticle;
import net.midget807.afmweapons.particle.ModParticles;
import net.midget807.afmweapons.rendering.*;
import net.midget807.afmweapons.screen.FletchingScreen;
import net.midget807.afmweapons.screen.ModScreenHandlers;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.entity.ArrowEntityRenderer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

public class AFMWClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		EntityRendererRegistry.register(ModEntities.FRIED_EGG_ENTITY_TYPE, FlyingItemEntityRenderer::new);
		EntityRendererRegistry.register(ModEntities.FROST_ARROW_ENTITY_TYPE, FrostArrowEntityRenderer::new);
		EntityRendererRegistry.register(ModEntities.EXPLOSIVE_ARROW_ENTITY_TYPE, ExplosiveArrowEntityRenderer::new);
		EntityRendererRegistry.register(ModEntities.RICOCHET_ARROW_ENTITY_TYPE, RicochetArrowEntityRenderer::new);
		EntityRendererRegistry.register(ModEntities.WARP_ARROW_ENTITY_TYPE, WarpArrowEntityRenderer::new);
		EntityRendererRegistry.register(ModEntities.MAGIC_ARROW_ENTITY_TYPE, MagicArrowEntityRenderer::new);
		EntityRendererRegistry.register(ModEntities.ECHO_ARROW_ENTITY_TYPE, EchoArrowEntityRenderer::new);
		EntityRendererRegistry.register(ModEntities.GUIDED_ARROW_ENTITY_TYPE, GuidedArrowEntityRenderer::new);
		ModModelPredicateProviderRegistry.registerModModelPredicatesProviders();
		HandledScreens.register(ModScreenHandlers.FLETCHING_TABLE_SCREEN_HANDLER, FletchingScreen::new);
		ParticleFactoryRegistry.getInstance().register(ModParticles.ECHO_ARROW_PULSE_PARTICLE_TYPE, EchoArrowPulseParticle.Factory::new);
		ModClientMessages.registerS2CPackets();
	}


}