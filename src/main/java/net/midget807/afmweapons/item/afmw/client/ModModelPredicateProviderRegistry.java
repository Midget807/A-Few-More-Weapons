package net.midget807.afmweapons.item.afmw.client;

import net.midget807.afmweapons.AFMWMain;
import net.midget807.afmweapons.item.ModItems;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;

public class ModModelPredicateProviderRegistry {

    public static void registerModModelPredicatesProviders() {
        AFMWMain.LOGGER.info("Registering AFMW Model Predicate Registries");
        // === Deprecated ===
        ModelPredicateProviderRegistry.register(
                ModItems.FRYING_PAN,
                new Identifier("slinging"),
                (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0F : 0.0F);
        // ==================

    }
}
