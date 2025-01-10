package net.midget807.afmweapons.component.afmw;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.midget807.afmweapons.component.ModComponents;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.nbt.NbtCompound;

public class MagicArrowComponent implements AutoSyncedComponent {
    private final PersistentProjectileEntity magicArrow;
    private int ticksInAir = 0;

    public MagicArrowComponent(PersistentProjectileEntity magicArrow) {
        this.magicArrow = magicArrow;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        this.ticksInAir = tag.getInt("TicksInAir");
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putInt("TicksInAir", this.ticksInAir);
    }
    public void sync() {
        ModComponents.MAGIC_ARROW_COMPONENT.sync(this.magicArrow);
    }

}
