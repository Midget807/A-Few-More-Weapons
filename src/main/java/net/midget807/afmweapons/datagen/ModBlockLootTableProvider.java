package net.midget807.afmweapons.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.midget807.afmweapons.block.ModBlocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.resource.featuretoggle.FeatureFlags;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ModBlockLootTableProvider extends FabricBlockLootTableProvider {
    private static final Set<Item> EXPLOSION_IMMUNE = (Set<Item>) Stream.of(
            ModBlocks.TOASTED_DRAGON_EGG,
            ModBlocks.BAKED_DRAGON_EGG
    ).map(ItemConvertible::asItem).collect(Collectors.toSet());
    public ModBlockLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        this.addDrop(ModBlocks.TOASTED_DRAGON_EGG);
        this.addDrop(ModBlocks.BAKED_DRAGON_EGG);
    }
}
