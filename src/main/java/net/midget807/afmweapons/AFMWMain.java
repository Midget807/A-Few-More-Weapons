package net.midget807.afmweapons;

import net.fabricmc.api.ModInitializer;
import net.midget807.afmweapons.block.ModBlocks;
import net.midget807.afmweapons.block.entity.ModBlockEntities;
import net.midget807.afmweapons.enchantment.ModEnchantmentEffects;
import net.midget807.afmweapons.effect.ModEffects;
import net.midget807.afmweapons.enchantment.ModEnchantments;
import net.midget807.afmweapons.entity.ModDamageTypes;
import net.midget807.afmweapons.entity.ModEntities;
import net.midget807.afmweapons.event.ModEvents;
import net.midget807.afmweapons.item.ModFoodComponents;
import net.midget807.afmweapons.item.ModItemGroups;
import net.midget807.afmweapons.item.ModItems;
import net.midget807.afmweapons.particle.ModParticles;
import net.midget807.afmweapons.rea.ReachEntityAttributes;
import net.midget807.afmweapons.recipe.ModRecipes;
import net.midget807.afmweapons.screen.ModScreenHandlers;
import net.midget807.afmweapons.sound.ModSoundEvents;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AFMWMain implements ModInitializer {
	public static Identifier id(String path) {
		return Identifier.of(Mod_ID, path);
	}
	public static final String Mod_ID = "afmweapons";
    public static final Logger LOGGER = LoggerFactory.getLogger("afmweapons");

	@Override
	public void onInitialize() {
		LOGGER.info("wassup");

		ModItems.registerModItems();
		ModItemGroups.registerModItemGroups();
		ModEnchantments.registerModEnchantments();
		ModEnchantmentEffects.registerModEnchantmentEffect();
		ModEffects.registerModEffects();
		ModEntities.registerModEntities();
		ModDamageTypes.registerModDamageTypes();
		ModBlocks.registerModBlocks();
		ModScreenHandlers.registerModScreenHandlers();
		ModBlockEntities.registerModBlockEntities();
		ModRecipes.registerRecipes();
		ModParticles.registerModParticles();
		ModEvents.registerModEvents();
		ModSoundEvents.registerModSoundEvents();
		ModFoodComponents.registerModFoodComponents();
		ReachEntityAttributes.registerAFMWEntityAttribute();
	}
}