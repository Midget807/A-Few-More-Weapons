package net.midget807.afmweapons.cca;


import net.midget807.afmweapons.AFMWMain;
import net.midget807.afmweapons.cca.afmw.ClaymoreComponent;
import net.midget807.afmweapons.cca.afmw.LanceComponent;
import net.midget807.afmweapons.cca.afmw.LongswordComponent;
import net.minecraft.entity.player.PlayerEntity;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;

public final class ModComponents implements EntityComponentInitializer {

    public static final ComponentKey<ClaymoreComponent> CLAYMORE_COMPONENT = ComponentRegistry.getOrCreate(AFMWMain.id("claymore_component"), ClaymoreComponent.class);
    public static final ComponentKey<LanceComponent> LANCE_COMPONENT = ComponentRegistry.getOrCreate(AFMWMain.id("lance_component"), LanceComponent.class);
    public static final ComponentKey<LongswordComponent> LONGSWORD_COMPONENT = ComponentRegistry.getOrCreate(AFMWMain.id("longsword_component"), LongswordComponent.class);
    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.beginRegistration(PlayerEntity.class, LONGSWORD_COMPONENT).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(LongswordComponent::new);
        registry.beginRegistration(PlayerEntity.class, LANCE_COMPONENT).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(LanceComponent::new);
        registry.beginRegistration(PlayerEntity.class, CLAYMORE_COMPONENT).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(ClaymoreComponent::new);
    }
}
