package net.midget807.afmweapons.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.midget807.afmweapons.AFMWMain;
import net.midget807.afmweapons.entity.afmw.*;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModEntities {
    public static final EntityType<FriedEggEntity> FRIED_EGG_ENTITY_TYPE =
            Registry.register(Registries.ENTITY_TYPE, AFMWMain.id("fried_egg"),
                    FabricEntityTypeBuilder.<FriedEggEntity>create(SpawnGroup.MISC, FriedEggEntity::new)
                            .dimensions(EntityDimensions.fixed(0.5f, 0.5f))
                            .trackRangeBlocks(4).trackedUpdateRate(10)
                            .build());
    public static final EntityType<DragonEggEntity> DRAGON_EGG_ENTITY_TYPE =
            Registry.register(Registries.ENTITY_TYPE, AFMWMain.id("fried_dragon_egg"),
                    FabricEntityTypeBuilder.<DragonEggEntity>create(SpawnGroup.MISC, DragonEggEntity::new)
                            .dimensions(EntityDimensions.fixed(1.0f, 1.0f))
                            .trackRangeBlocks(4).trackedUpdateRate(5)
                            .build());

    public static final EntityType<FrostArrowEntity> FROST_ARROW_ENTITY_TYPE =
            Registry.register(Registries.ENTITY_TYPE, AFMWMain.id("frost_arrow"),
                    FabricEntityTypeBuilder.<FrostArrowEntity>create(SpawnGroup.MISC, FrostArrowEntity::new)
                            .dimensions(EntityDimensions.fixed(0.5f, 0.5f))
                            .trackRangeBlocks(4).trackedUpdateRate(10)
                            .build());
    public static final EntityType<ExplosiveArrowEntity> EXPLOSIVE_ARROW_ENTITY_TYPE =
            Registry.register(Registries.ENTITY_TYPE, AFMWMain.id("explosive_arrow"),
                    FabricEntityTypeBuilder.<ExplosiveArrowEntity>create(SpawnGroup.MISC, ExplosiveArrowEntity::new)
                            .dimensions(EntityDimensions.fixed(0.5f, 0.5f))
                            .trackRangeBlocks(4).trackedUpdateRate(20)
                            .build());
    public static final EntityType<RicochetArrowEntity> RICOCHET_ARROW_ENTITY_TYPE =
            Registry.register(Registries.ENTITY_TYPE, AFMWMain.id("ricochet_arrow"),
                    FabricEntityTypeBuilder.<RicochetArrowEntity>create(SpawnGroup.MISC, RicochetArrowEntity::new)
                            .dimensions(EntityDimensions.fixed(0.5f, 0.5f))
                            .trackRangeBlocks(4).trackedUpdateRate(10)
                            .build());
    public static final EntityType<WarpArrowEntity> WARP_ARROW_ENTITY_TYPE =
            Registry.register(Registries.ENTITY_TYPE, AFMWMain.id("warp_arrow"),
                    FabricEntityTypeBuilder.<WarpArrowEntity>create(SpawnGroup.MISC, WarpArrowEntity::new)
                            .dimensions(EntityDimensions.fixed(0.5f, 0.5f))
                            .trackRangeBlocks(4).trackedUpdateRate(10)
                            .build());
    public static final EntityType<MagicArrowEntity> MAGIC_ARROW_ENTITY_TYPE =
            Registry.register(Registries.ENTITY_TYPE, AFMWMain.id("magic_arrow"),
                    FabricEntityTypeBuilder.<MagicArrowEntity>create(SpawnGroup.MISC, MagicArrowEntity::new)
                            .dimensions(EntityDimensions.fixed(0.5f, 0.5f))
                            .trackRangeBlocks(4).trackedUpdateRate(2)
                            .build());
    public static final EntityType<EchoArrowEntity> ECHO_ARROW_ENTITY_TYPE =
            Registry.register(Registries.ENTITY_TYPE, AFMWMain.id("echo_arrow"),
                    FabricEntityTypeBuilder.<EchoArrowEntity>create(SpawnGroup.MISC, EchoArrowEntity::new)
                            .dimensions(EntityDimensions.fixed(0.5f, 0.5f))
                            .trackRangeBlocks(4).trackedUpdateRate(10)
                            .build());
    public static final EntityType<SeekingArrowEntity> SEEKING_ARROW_ENTITY_TYPE =
            Registry.register(Registries.ENTITY_TYPE, AFMWMain.id("seeking_arrow"),
                    FabricEntityTypeBuilder.<SeekingArrowEntity>create(SpawnGroup.MISC, SeekingArrowEntity::new)
                            .dimensions(EntityDimensions.fixed(0.5f, 0.5f))
                            .trackRangeBlocks(4).trackedUpdateRate(10)
                            .build());
    public static void registerModEntities() {
        AFMWMain.LOGGER.info("Registering AFMW Entities");
    }
}
