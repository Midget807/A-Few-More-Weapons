package net.midget807.afmweapons.component.afmw;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.midget807.afmweapons.component.ModComponents;
import net.midget807.afmweapons.datagen.ModItemTagProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import org.jetbrains.annotations.NotNull;

public class LanceComponent implements AutoSyncedComponent {
    private final PlayerEntity player;
    private boolean ridingHorse = false;

    public LanceComponent(PlayerEntity player) {
        this.player = player;
    }
    public static LanceComponent get(@NotNull PlayerEntity player) {
        return ModComponents.LANCE_COMPONENT.get(player);
    }
    private void sync() {
        ModComponents.LANCE_COMPONENT.sync(this.player);
    }
    public boolean isRidingHorse() {
        if (!this.player.getWorld().isClient && (!this.player.getMainHandStack().isIn(ModItemTagProvider.LANCES))) {
            this.setRidingHorse(false);
        }
        return this.ridingHorse;
    }

    public void setRidingHorse(boolean ridingHorse) {
        this.ridingHorse = ridingHorse;
        this.sync();
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        this.ridingHorse = tag.getBoolean("isRidingHorse");
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putBoolean("isRidingHorse", this.ridingHorse);
    }
}
