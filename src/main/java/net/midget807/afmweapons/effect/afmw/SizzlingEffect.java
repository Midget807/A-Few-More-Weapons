package net.midget807.afmweapons.effect.afmw;

import net.midget807.afmweapons.entity.ModDamageTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public class SizzlingEffect extends StatusEffect {
    public SizzlingEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (!entity.getWorld().isClient) {
            SoundEvent soundEvent = SoundEvents.ENTITY_PLAYER_HURT_ON_FIRE;
            entity.getWorld().playSoundFromEntity(null, entity, soundEvent, SoundCategory.PLAYERS, 1.0f, 1.0f);
        }
        DamageSource damageSource = new DamageSource(entity.getWorld().getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(ModDamageTypes.SIZZLE));
        entity.damage(damageSource, 1.0f);
    }


    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        int i = 25 >> amplifier;
        return i == 0 || duration % i == 0;
    }
}
