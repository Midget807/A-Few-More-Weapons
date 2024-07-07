package net.midget807.afmweapons;

import net.fabricmc.api.ModInitializer;
import net.midget807.afmweapons.enchantment.ModEnchantments;
import net.midget807.afmweapons.item.ModItemGroups;
import net.midget807.afmweapons.item.ModItems;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AFMWMain implements ModInitializer {
	public static Identifier id(String path) {
		return new Identifier(AFMWMain.Mod_ID, path);
	}
	public static final String Mod_ID = "afmweapons";
    public static final Logger LOGGER = LoggerFactory.getLogger("afmweapons");

	@Override
	public void onInitialize() {
		LOGGER.info("wassup");

		ModItems.registerModItems();
		ModItemGroups.registerModItemGroups();
		ModEnchantments.registerModEnchantments();
	}
}