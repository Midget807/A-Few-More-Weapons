package net.midget807.afmweapons.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.midget807.afmweapons.item.ModItems;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.WOODEN_HALBERD, Models.GENERATED);
        itemModelGenerator.register(ModItems.STONE_HALBERD, Models.GENERATED);
        itemModelGenerator.register(ModItems.IRON_HALBERD, Models.GENERATED);
        itemModelGenerator.register(ModItems.GOLD_HALBERD, Models.GENERATED);
        itemModelGenerator.register(ModItems.DIAMOND_HALBERD, Models.GENERATED);
        itemModelGenerator.register(ModItems.NETHERITE_HALBERD, Models.GENERATED);

        itemModelGenerator.register(ModItems.WOODEN_LONGSWORD, Models.GENERATED);
        itemModelGenerator.register(ModItems.STONE_LONGSWORD, Models.GENERATED);
        itemModelGenerator.register(ModItems.IRON_LONGSWORD, Models.GENERATED);
        itemModelGenerator.register(ModItems.GOLDEN_LONGSWORD, Models.GENERATED);
        itemModelGenerator.register(ModItems.DIAMOND_LONGSWORD, Models.GENERATED);
        itemModelGenerator.register(ModItems.NETHERITE_LONGSWORD, Models.GENERATED);

        itemModelGenerator.register(ModItems.FRIED_EGG, Models.GENERATED);

    }
}
