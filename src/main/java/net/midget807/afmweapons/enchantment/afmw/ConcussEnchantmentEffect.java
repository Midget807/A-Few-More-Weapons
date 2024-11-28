package net.midget807.afmweapons.enchantment.afmw;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

public record ConcussEnchantmentEffect(EnchantmentLevelBasedValue duration) implements EnchantmentEntityEffect {
    public static final MapCodec<ConcussEnchantmentEffect> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(EnchantmentLevelBasedValue.CODEC.fieldOf("duration").forGetter(concussEnchantmentEffect -> concussEnchantmentEffect.duration))
                    .apply(instance, ConcussEnchantmentEffect::new)
    );

    @Override
    public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity user, Vec3d pos) {
        if (user instanceof LivingEntity livingEntity) {
            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, (int) this.duration.getValue(level), 0));
            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, (int) this.duration.getValue(level), 0));
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return CODEC;
    }
}
