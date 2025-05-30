package net.midget807.afmweapons.item.afmw;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import net.midget807.afmweapons.enchantment.ModEnchantments;
import net.midget807.afmweapons.entity.afmw.FriedEggEntity;
import net.midget807.afmweapons.item.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class FryingPanItem extends Item implements Vanishable {
    private final Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;
    protected static final UUID ATTACK_REACH_MODIFIER_ID = UUID.fromString("009ca970-a477-4c89-bfc7-eea9b5113297");
    protected static final UUID REACH_MODIFIER_ID = UUID.fromString("73a1efbb-9199-4a50-86c4-b1f64034d448");
    protected static final UUID ATTACK_KNOCKBACK_MODIFIER_ID = UUID.fromString("9e510382-5951-4ad2-b00b-dc05fc1d908f");
    public FryingPanItem(ToolMaterial toolMaterial, float attackDmg, float attackSpd, Settings settings) {
        super(settings);
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", toolMaterial.getAttackDamage() + attackDmg, EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", attackSpd, EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, new EntityAttributeModifier(ATTACK_KNOCKBACK_MODIFIER_ID, "Weapon modifier", 2.5f, EntityAttributeModifier.Operation.ADDITION));
        builder.put(ReachEntityAttributes.ATTACK_RANGE, new EntityAttributeModifier(ATTACK_REACH_MODIFIER_ID, "Weapon modifier", -0.25f, EntityAttributeModifier.Operation.ADDITION));
        builder.put(ReachEntityAttributes.REACH, new EntityAttributeModifier(REACH_MODIFIER_ID, "Weapon modifier", -1.0f, EntityAttributeModifier.Operation.ADDITION));
        this.attributeModifiers = builder.build();
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.NONE;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 72000;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (itemStack.getDamage() >= itemStack.getMaxDamage() - 1) {
            return TypedActionResult.fail(itemStack);
        } else if (user.getStackInHand(Hand.OFF_HAND).isOf(Items.EGG)) {
            user.setCurrentHand(hand);
            return TypedActionResult.consume(itemStack);
        } else {
            return TypedActionResult.pass(itemStack);
        }
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (user instanceof PlayerEntity playerEntity) {
            if (!world.isClient) {
                SoundEvent soundEvent = SoundEvents.BLOCK_CAMPFIRE_CRACKLE;
                world.playSoundFromEntity(null, playerEntity, soundEvent, SoundCategory.PLAYERS, 1.0f, 1.0f);

            }
        }
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (user instanceof PlayerEntity playerEntity) {
            int useTime = this.getMaxUseTime(stack) - remainingUseTicks;
            if (useTime >= 10 && !world.isClient && (user.getStackInHand(Hand.OFF_HAND).isOf(Items.EGG) || user.getStackInHand(Hand.OFF_HAND).isOf(Items.DRAGON_EGG))) {
                stack.damage(1, playerEntity, player -> player.sendToolBreakStatus(user.getActiveHand()));
                FriedEggEntity friedEggEntity = new FriedEggEntity(user, world);
                friedEggEntity.setItem(new ItemStack(ModItems.FRIED_EGG));
                friedEggEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, 1.5f, 0.5f);
                world.spawnEntity(friedEggEntity);
                if (!playerEntity.isCreative()) {
                    user.getStackInHand(Hand.OFF_HAND).decrement(1);
                }
                playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
                SoundEvent soundEvent = SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE;
                world.playSoundFromEntity(null, playerEntity, soundEvent, SoundCategory.PLAYERS, 0.5f, 1.0f);
            }


        }
    }
    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.getWorld().isClient) {
            SoundEvent soundEvent = SoundEvents.BLOCK_ANVIL_PLACE;
            attacker.getWorld().playSoundFromEntity(null, attacker, soundEvent, SoundCategory.PLAYERS, 0.5f, 0.7f);
        }
        stack.damage(1, attacker, entity -> entity.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
        return true;
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if (state.getHardness(world, pos) != 0.0) {
            stack.damage(2, miner, entity -> entity.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
        }
        return true;
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(ItemStack stack, EquipmentSlot slot) {
        return slot == EquipmentSlot.MAINHAND ? this.attributeModifiers : super.getAttributeModifiers(slot);
    }

    @Override
    public int getEnchantability() {
        return 1;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item.afmweapons.frying_pan.desc").formatted(Formatting.GRAY));
        super.appendTooltip(stack, world, tooltip, context);

    }
}
