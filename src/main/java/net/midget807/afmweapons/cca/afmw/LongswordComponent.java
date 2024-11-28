package net.midget807.afmweapons.cca.afmw;


import net.midget807.afmweapons.cca.ModComponents;
import net.midget807.afmweapons.datagen.ModItemTagProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import org.jetbrains.annotations.NotNull;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;

public class LongswordComponent implements AutoSyncedComponent {

    // === Credit: Methods From Amarite Mod ===
    private final PlayerEntity player;
    private boolean blocking = false;

    public LongswordComponent(PlayerEntity player) {
        this.player = player;
    }
    public static LongswordComponent get(@NotNull PlayerEntity player) {
        return ModComponents.LONGSWORD_COMPONENT.get(player);
    }
    private void sync() {
        ModComponents.LONGSWORD_COMPONENT.sync(this.player);
    }
    public void absorbDamage(ItemStack stack, float base) {
        this.sync();
    }
    public boolean isBlocking() {
        if (!this.player.getWorld().isClient && (!this.player.getMainHandStack().isIn(ModItemTagProvider.LONGSWORDS) || !this.player.isUsingItem())) {
            this.setBlocking(false);
        }
        return this.blocking;
    }

    public void setBlocking(boolean blocking) {
        this.blocking = blocking;
        this.sync();
    }


    @Override
    public void readFromNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
        this.blocking = tag.getBoolean("isBlocking");
    }

    @Override
    public void writeToNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
        tag.putBoolean("isBlocking", this.blocking);
    }
}
