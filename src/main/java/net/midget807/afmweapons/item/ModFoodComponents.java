package net.midget807.afmweapons.item;

import net.midget807.afmweapons.AFMWMain;
import net.minecraft.item.FoodComponent;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModFoodComponents {
    public static final FoodComponent FRIED_EGG = new FoodComponent.Builder().hunger(2).saturationModifier(0.1f).build();

    public static void registerModFoodComponents() {
        AFMWMain.LOGGER.info("Registering AFMW Food Components");
    }

}
