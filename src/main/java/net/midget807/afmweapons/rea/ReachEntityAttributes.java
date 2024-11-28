package net.midget807.afmweapons.rea;

import net.midget807.afmweapons.AFMWMain;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public final class ReachEntityAttributes {
    /*
    * REACH attribute is replaced with PLAYER_BLOCK_INTERACTION_RANGE
    * */
    public static Identifier id(String path) {
        return Identifier.of(AFMW_REA_ID, path);
    }
    public static final String AFMW_REA_ID = AFMWMain.Mod_ID + "-rea";
    //public static final RegistryEntry<EntityAttribute> REACH = registryEntryREA("reach", make("reach", 0.0, -1024.0, 1024.0));
    public static final RegistryEntry<EntityAttribute> ATTACK_RANGE = registryEntryREA("reach", make("reach", 0.0, -1024.0, 1024.0));
    /*public static double getReachDistance(final LivingEntity entity, final double baseReachDistance) {
        @Nullable final var reachDistance = entity.getAttributeInstance(REACH);
        return (reachDistance != null) ? (baseReachDistance + reachDistance.getValue()) : baseReachDistance;
    }
    public static double getSquaredReachDistance(final LivingEntity entity, final double sqBaseReachDistance) {
        final var reachDistance = getReachDistance(entity, Math.sqrt(sqBaseReachDistance));
        return reachDistance * reachDistance;
    }*/
    public static double getAttackRange(final LivingEntity entity, final double baseAttackRange) {
        @Nullable final var attackRange = entity.getAttributeInstance(ATTACK_RANGE);
        return (attackRange != null) ? (baseAttackRange + attackRange.getValue()) : baseAttackRange;
    }
    public static double getSquaredAttackRange(final LivingEntity entity, final double sqBaseAttackRange) {
        final var attackRange = getAttackRange(entity, Math.sqrt(sqBaseAttackRange));
        return attackRange * attackRange;
    }

    /*public static List<PlayerEntity> getPlayersWithinReach(final World world, final int x, final int y, final int z, final double baseReachDistance) {
        return getPlayersWithinReach(player -> true, world, x, y, z, baseReachDistance);
    }

    public static List<PlayerEntity> getPlayersWithinReach(final Predicate<PlayerEntity> viewerPredicate, final World world, final int x, final int y, final int z, final double baseReachDistance) {
        final List<PlayerEntity> playersWithinReach = new ArrayList<>(0);
        for (final PlayerEntity player : world.getPlayers()) {
            final var reach = getReachDistance(player, baseReachDistance);
            final var dx = (x + 0.5) - player.getX();
            final var dy = (y + 0.5) - player.getY();
            final var dz = (z + 0.5) - player.getZ();
            if (((dx * dx) + (dy * dy) + (dz * dz)) <= (reach * reach)) {
                playersWithinReach.add(player);
            }
        }
        return playersWithinReach;
    }*/
    public static boolean isWithinAttackRange(final PlayerEntity player, final Entity entity) {
        return player.squaredDistanceTo(entity) <= getSquaredAttackRange(player, 64.0);
    }

    private static EntityAttribute make(String name, final double base, final double min, final double max) {
        return new ClampedEntityAttribute("attribute.name.generic." + AFMW_REA_ID + "." + name, base, min, max);
    }
    public static RegistryEntry<EntityAttribute> registryEntryREA(String path, EntityAttribute attribute) {
        return Registry.registerReference(Registries.ATTRIBUTE, ReachEntityAttributes.id(path), attribute);
    }
    public static void registerAFMWEntityAttribute() {
        AFMWMain.LOGGER.info("Registering Reach Entity Attributes for AFMW");
    }
}
