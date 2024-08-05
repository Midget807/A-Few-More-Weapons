package net.midget807.afmweapons.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.midget807.afmweapons.AFMWMain;
import net.midget807.afmweapons.block.entity.afmw.FletchingTableBlockEntity;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModBlockEntities {
    public static final BlockEntityType<FletchingTableBlockEntity> FLETCHING_TABLE_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, AFMWMain.id("fletching_table_block_entity"),
                    FabricBlockEntityTypeBuilder.create(FletchingTableBlockEntity::new, Blocks.FLETCHING_TABLE).build(null));
    public static void registerModBlockEntities() {
        AFMWMain.LOGGER.info("Registering AFMW Block Entities");
    }
}
