package net.midget807.afmweapons;

import net.fabricmc.api.ModInitializer;

import net.midget807.afmweapons.block.ModBlocks;
import net.midget807.afmweapons.block.entity.ModBlockEntities;
import net.midget807.afmweapons.effect.ModEffects;
import net.midget807.afmweapons.enchantment.ModEnchantments;
import net.midget807.afmweapons.entity.ModEntities;
import net.midget807.afmweapons.event.ModEvents;
import net.midget807.afmweapons.item.ModFoodComponents;
import net.midget807.afmweapons.item.ModItemGroups;
import net.midget807.afmweapons.item.ModItems;
import net.midget807.afmweapons.particle.ModParticles;
import net.midget807.afmweapons.recipe.ModRecipes;
import net.midget807.afmweapons.screen.ModScreenHandlers;
import net.midget807.afmweapons.sound.ModSoundEvents;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AFMWMain implements ModInitializer {
	public static final String MOD_ID = "afmweapons";

	public static Identifier id(String path) {
		return new Identifier(MOD_ID, path);
	}

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("YOOOOO");

		ModBlocks.registerModBlocks();
		ModBlockEntities.registerModBlockEntities();
		ModEffects.registerModEffects();
		ModEnchantments.registerModEnchantments();
		ModEntities.registerModEntities();
		ModEvents.registerModEvents();
		ModItems.registerModItems();
		ModItemGroups.registerModItemGroups();
		ModFoodComponents.registerModFoodComponents();
		ModParticles.registerModParticles();
		ModRecipes.registerModRecipes();
		ModScreenHandlers.registerModScreenHandlers();
		ModSoundEvents.registerModSoundEvents();

	}
}