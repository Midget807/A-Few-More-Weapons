package net.midget807.afmweapons.item;

import net.midget807.afmweapons.AFMWMain;
import net.minecraft.component.type.FoodComponent;

public class ModFoodComponents {
    public static final FoodComponent FRIED_EGG = new FoodComponent.Builder().nutrition(2).saturationModifier(0.1f).build();

    public static void registerModFoodComponents() {
        AFMWMain.LOGGER.info("Registering AFMW Food Components");
    }

}
