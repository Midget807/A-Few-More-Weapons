package net.midget807.afmweapons.item.afmw.arrow.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ArrowUtil {
    public static final String FROST_ARROW_LEVEL_KEY = "Level";
    public static final String FROST_ARROW_DURATION_KEY = "FreezeDuration";
    public static final String RICOCHET_ARROW_BOUNCE_KEY = "Bounces";

    public static int getFrostArrowLevel(ItemStack frostArrowStack) {
        return getFrostArrowLevelNbt(frostArrowStack.getNbt());
    }
    public static int getFrostArrowLevelNbt(@Nullable NbtCompound nbtCompound) {
        if (nbtCompound == null) {
            return 0;
        }
        return nbtCompound.getInt(FROST_ARROW_LEVEL_KEY);
    }
    public static int getFrostArrowDuration(ItemStack frostArrowStack) {
        return getFrostArrowDurationNbt(frostArrowStack.getNbt());
    }

    public static int getFrostArrowDurationNbt(NbtCompound nbtCompound) {
        if (nbtCompound == null) {
            return 0;
        }
        return nbtCompound.getInt(FROST_ARROW_DURATION_KEY);
    }
    public static ItemStack setFrostArrow(ItemStack stack, int level, int freezeDuration) {
        if (level == 0) {
            stack.removeSubNbt(FROST_ARROW_LEVEL_KEY);
        } else {
            stack.getOrCreateNbt().putInt(FROST_ARROW_LEVEL_KEY, level);
        }
        if (freezeDuration == 0) {
            stack.removeSubNbt(FROST_ARROW_DURATION_KEY);
        } else{
            stack.getOrCreateNbt().putInt(FROST_ARROW_DURATION_KEY, freezeDuration);
        }
        return stack;
    }
    public static int getRicochetArrowBounces(ItemStack stack) {
        return getRicochetArrowBouncesNbt(stack.getNbt());
    }

    public static int getRicochetArrowBouncesNbt(NbtCompound nbtCompound) {
        if (nbtCompound == null) {
            return 0;
        }
        return nbtCompound.getInt(RICOCHET_ARROW_BOUNCE_KEY);
    }
    public static ItemStack setRicochetArrow(ItemStack stack, int bounces) {
        if (bounces == 0) {
            stack.removeSubNbt(RICOCHET_ARROW_BOUNCE_KEY);
        } else {
            stack.getOrCreateNbt().putInt(RICOCHET_ARROW_BOUNCE_KEY, bounces);
        }
        return stack;
    }

}
