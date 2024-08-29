package net.midget807.afmweapons.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ArrowUtil {
    public static final String FROST_ARROW_LEVEL_KEY = "Level";
    public static final String FROST_ARROW_DURATION_KEY = "FreezeDuration";

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

    public static final String EXPLOSIVE_ARROW_POWER_KEY = "ExplosionPower";
    public static int getExplosiveArrowExplosionPower(ItemStack stack) {
        return getExplosiveArrowExplosionPowerNbt(stack.getNbt());
    }

    public static int getExplosiveArrowExplosionPowerNbt(NbtCompound nbtCompound) {
        if (nbtCompound == null) {
            return 0;
        }
        return nbtCompound.getInt(EXPLOSIVE_ARROW_POWER_KEY);
    }
    public static ItemStack setExplosiveArrow(ItemStack stack, int explosionPower) {
        if (explosionPower == 0) {
            stack.removeSubNbt(EXPLOSIVE_ARROW_POWER_KEY);
        } else {
            stack.getOrCreateNbt().putInt(EXPLOSIVE_ARROW_POWER_KEY, explosionPower);
        }
        return stack;
    }

    public static final String RICOCHET_ARROW_BOUNCE_KEY = "Bounces";
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

    public static final String MAGIC_ARROW_FLIGHT_TIME_KEY = "FlightDuration";
    public static int getMagicArrowFlightTime(ItemStack stack) {
        return getMagicArrowFlightTimeNbt(stack.getNbt());
    }

    public static int getMagicArrowFlightTimeNbt(NbtCompound nbtCompound) {
        if (nbtCompound == null) {
            return 0;
        }
        return nbtCompound.getInt(MAGIC_ARROW_FLIGHT_TIME_KEY);
    }
    public static ItemStack setMagicArrow(ItemStack stack, int flightTime) {
        if (flightTime == 0) {
            stack.removeSubNbt(MAGIC_ARROW_FLIGHT_TIME_KEY);
        } else {
            stack.getOrCreateNbt().putInt(MAGIC_ARROW_FLIGHT_TIME_KEY, flightTime);
        }
        return stack;
    }

    public static final String ECHO_ARROW_AGE_KEY = "GroundAge";
    public static final String ECHO_ARROW_PULSING_KEY = "Pulsing";
    public static int getEchoArrowAge(ItemStack stack) {
        return getEchoArrowAgeNbt(stack.getNbt());
    }

    public static int getEchoArrowAgeNbt(NbtCompound nbtCompound) {
        if (nbtCompound == null) {
            return 0;
        }
        return nbtCompound.getInt(ECHO_ARROW_AGE_KEY);
    }

    public static boolean getEchoArrowShouldPulse(ItemStack stack) {
        return getEchoArrowShouldPulseNbt(stack.getNbt());
    }

    public static boolean getEchoArrowShouldPulseNbt(NbtCompound nbtCompound) {
        if (nbtCompound == null) {
            return false;
        }
        return nbtCompound.getBoolean(ECHO_ARROW_PULSING_KEY);
    }
    public static ItemStack setEchoArrow(ItemStack stack, int age, boolean pulsing) {
        if (age == 0) {
            stack.removeSubNbt(ECHO_ARROW_AGE_KEY);
        } else {
            stack.getOrCreateNbt().putInt(ECHO_ARROW_AGE_KEY, age);
        }
        stack.getOrCreateNbt().putBoolean(ECHO_ARROW_PULSING_KEY, pulsing);
        return stack;
    }
}
