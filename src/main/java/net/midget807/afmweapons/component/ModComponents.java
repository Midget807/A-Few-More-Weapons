package net.midget807.afmweapons.component;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import net.midget807.afmweapons.AFMWMain;
import net.midget807.afmweapons.component.afmw.LongswordComponent;
import net.minecraft.entity.player.PlayerEntity;

public final class ModComponents implements EntityComponentInitializer {

    public static final ComponentKey<LongswordComponent> LONGSWORD_COMPONENT = ComponentRegistry.getOrCreate(AFMWMain.id("longsword_component"), LongswordComponent.class);
    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.beginRegistration(PlayerEntity.class, LONGSWORD_COMPONENT).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(LongswordComponent::new);
    }
}