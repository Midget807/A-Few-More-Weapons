package net.midget807.afmweapons.sound;

import net.midget807.afmweapons.AFMWMain;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public class ModSoundEvents {
    public static final SoundEvent SAD_TROMBONE = registerSoundEvent("sad_trombone");
    public static final SoundEvent LONGSWORD_BLOCK_NORMAL = registerSoundEvent("longsword_block_normal");
    public static final SoundEvent LONGSWORD_BLOCK_NETHERITE = registerSoundEvent("longsword_block_netherite");
    public static SoundEvent registerSoundEvent(String name) {
        return Registry.register(Registries.SOUND_EVENT, AFMWMain.id(name), SoundEvent.of(AFMWMain.id(name)));
    }
    public static void registerModSoundEvents() {
        AFMWMain.LOGGER.info("Registering AFMW Sound Events");
    }
}
