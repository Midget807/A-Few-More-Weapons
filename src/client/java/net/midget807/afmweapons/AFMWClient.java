package net.midget807.afmweapons;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.midget807.afmweapons.entity.ModEntities;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

public class AFMWClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		EntityRendererRegistry.register(ModEntities.FRIED_EGG_ENTITY_TYPE, FlyingItemEntityRenderer::new);
		ModModelPredicateProviderRegistry.registerModModelPredicatesProviders();
	}


}