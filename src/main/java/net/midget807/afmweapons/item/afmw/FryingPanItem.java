package net.midget807.afmweapons.item.afmw;

import net.midget807.afmweapons.entity.afmw.FriedEggEntity;
import net.midget807.afmweapons.item.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
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

import java.util.List;

public class FryingPanItem extends Item {
    public FryingPanItem(Settings settings) {
        super(settings);
    }
    public static AttributeModifiersComponent createAttributeModifier(ToolMaterial material, int baseAttackDamage, float attackSpeed, float attackKnockback, float reach, float attackRange) {
        return AttributeModifiersComponent.builder()
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE,
                        new EntityAttributeModifier(
                                BASE_ATTACK_DAMAGE_MODIFIER_ID, (baseAttackDamage + material.getAttackDamage()), EntityAttributeModifier.Operation.ADD_VALUE
                        ),
                        AttributeModifierSlot.MAINHAND
                )
                .add(EntityAttributes.GENERIC_ATTACK_SPEED,
                        new EntityAttributeModifier(
                                BASE_ATTACK_DAMAGE_MODIFIER_ID, attackSpeed, EntityAttributeModifier.Operation.ADD_VALUE
                        ),
                        AttributeModifierSlot.MAINHAND
                )
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK,
                        new EntityAttributeModifier(
                                ModItems.ATTACK_KNOCKBACK_MODIFIER_ID, attackKnockback, EntityAttributeModifier.Operation.ADD_VALUE
                        ),
                        AttributeModifierSlot.MAINHAND
                )
                .add(EntityAttributes.PLAYER_BLOCK_INTERACTION_RANGE,
                        new EntityAttributeModifier(
                                ModItems.REACH_MODIFIER_ID, reach, EntityAttributeModifier.Operation.ADD_VALUE
                        ),
                        AttributeModifierSlot.MAINHAND
                )
                .add(EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE,
                        new EntityAttributeModifier(
                                ModItems.ATTACK_RANGE_MODIFIER_ID, attackRange, EntityAttributeModifier.Operation.ADD_VALUE
                        ),
                        AttributeModifierSlot.MAINHAND
                )
                .build();
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.NONE;
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity entity) {
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
            int useTime = this.getMaxUseTime(stack, playerEntity) - remainingUseTicks;
            if (useTime >= 10 && !world.isClient && (user.getStackInHand(Hand.OFF_HAND).isOf(Items.EGG) || user.getStackInHand(Hand.OFF_HAND).isOf(Items.DRAGON_EGG))) {
                stack.damage(1, playerEntity, playerEntity.getEquippedStack(EquipmentSlot.MAINHAND) == ModItems.FRYING_PAN.getDefaultStack() ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
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
        return true;
    }

    @Override
    public void postDamageEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(1, attacker, EquipmentSlot.MAINHAND);
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if (state.getHardness(world, pos) != 0.0) {
            stack.damage(2, miner, EquipmentSlot.MAINHAND);
        }
        return true;
    }

    @Override
    public int getEnchantability() {
        return 1;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("item.afmweapons.frying_pan.desc").formatted(Formatting.GRAY));
        super.appendTooltip(stack, context, tooltip, type);

    }
}
