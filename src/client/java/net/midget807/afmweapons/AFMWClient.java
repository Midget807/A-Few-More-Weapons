package net.midget807.afmweapons;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.midget807.afmweapons.entity.ModEntities;
import net.midget807.afmweapons.rendering.FrostArrowEntityRenderer;
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
		ModModelPredicateProviderRegistry.registerModModelPredicatesProviders();
		HandledScreens.register(ModScreenHandlers.FLETCHING_TABLE_SCREEN_HANDLER, FletchingScreen::new);
	}


}