package net.midget807.afmweapons.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.midget807.afmweapons.AFMWMain;
import net.midget807.afmweapons.entity.afmw.FriedEggEntity;
import net.midget807.afmweapons.entity.afmw.FrostArrowEntity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModEntities {
    public static final EntityType<FriedEggEntity> FRIED_EGG_ENTITY_TYPE =
            Registry.register(Registries.ENTITY_TYPE, AFMWMain.id("fried_egg"),
                    FabricEntityTypeBuilder.<FriedEggEntity>create(SpawnGroup.MISC, FriedEggEntity::new)
                            .dimensions(EntityDimensions.fixed(0.25f, 0.25f))
                            .trackRangeBlocks(4).trackedUpdateRate(10)
                            .build());

    public static final EntityType<FrostArrowEntity> FROST_ARROW_ENTITY_TYPE =
            Registry.register(Registries.ENTITY_TYPE, AFMWMain.id("frost_arrow"),
                    FabricEntityTypeBuilder.<FrostArrowEntity>create(SpawnGroup.MISC, FrostArrowEntity::new)
                            .dimensions(EntityDimensions.fixed(0.25f, 0.25f))
                            .trackRangeBlocks(4).trackedUpdateRate(20)
                            .build());
    public static void registerModEntities() {
        AFMWMain.LOGGER.info("Registering AFMW Entities");
    }
}
