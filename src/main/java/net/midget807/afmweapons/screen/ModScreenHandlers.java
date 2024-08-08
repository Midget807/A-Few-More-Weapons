package net.midget807.afmweapons.screen;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.midget807.afmweapons.AFMWMain;
import net.midget807.afmweapons.screen.afmw.FletchingTableScreenHandler;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;

public class ModScreenHandlers {
    public static final ScreenHandlerType<FletchingTableScreenHandler> FLETCHING_TABLE_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, AFMWMain.id("fletching_table_screen_handler"),
                    new ScreenHandlerType<>(FletchingTableScreenHandler::new, FeatureFlags.VANILLA_FEATURES));
    public static void registerModScreenHandlers() {
        AFMWMain.LOGGER.info("Registering AFMW Screen Handlers");
    }
}
