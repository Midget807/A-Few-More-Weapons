package net.midget807.afmweapons.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.midget807.afmweapons.AFMWMain;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.DragonEggBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;

public class ModBlocks {
    public static final Block TOASTED_DRAGON_EGG = registerBlock("toasted_dragon_egg", new DragonEggBlock(FabricBlockSettings.copyOf(Blocks.DRAGON_EGG).luminance(state -> 1)));
    public static final Block BAKED_DRAGON_EGG = registerBlock("baked_dragon_egg", new DragonEggBlock(FabricBlockSettings.copyOf(Blocks.DRAGON_EGG).luminance(state -> 0)));

    public static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, AFMWMain.id(name), block);
    }

    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, AFMWMain.id(name), new BlockItem(block, new FabricItemSettings()));
    }

    public static void registerModBlocks() {
        AFMWMain.LOGGER.info("Registering AFMW Blocks");
    }
}
