package net.midget807.afmweapons.item.afmw;

import com.google.common.collect.Multimap;
import net.midget807.afmweapons.datagen.ModBlockTagProvider;
import net.midget807.afmweapons.item.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LanceItem extends ToolItem {
    public LanceItem(ToolMaterial material, Settings settings) {
        super(material, settings.component(DataComponentTypes.TOOL, createToolComponent()));
    }

    private static ToolComponent createToolComponent() {
        return new ToolComponent(
                List.of(ToolComponent.Rule.of(ModBlockTagProvider.LANCE_EFFICIENT, 15.0f)), 1.0f, 2
        );
    }
    public static AttributeModifiersComponent createAttributeModifier(ToolMaterial material, int baseAttackDamage, float attackSpeed, float reach, float attackRange) {
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
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return !miner.isCreative();
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return true;
    }

    @Override
    public void postDamageEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(1, attacker, EquipmentSlot.MAINHAND);
    }
    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if (state.getHardness(world, pos) != 0.0 && !state.isIn(ModBlockTagProvider.LANCE_EFFICIENT)) {
            stack.damage(2, miner, EquipmentSlot.MAINHAND);
        }
        return true;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("item.afmweapons.lance.desc").formatted(Formatting.GRAY));
        tooltip.add(ScreenTexts.EMPTY);
        tooltip.add(Text.translatable("item.afmweapons.lance.attack_multiplier.head").formatted(Formatting.GRAY));
        if (stack.isOf(ModItems.IRON_LANCE)) {
            tooltip.add(Text.translatable("item.afmweapons.lance.attack_multiplier.6").formatted(Formatting.BLUE));
        } else if (stack.isOf(ModItems.GOLDEN_LANCE)) {
            tooltip.add(Text.translatable("item.afmweapons.lance.attack_multiplier.4").formatted(Formatting.BLUE));
        } else if (stack.isOf(ModItems.DIAMOND_LANCE)) {
            tooltip.add(Text.translatable("item.afmweapons.lance.attack_multiplier.7").formatted(Formatting.BLUE));
        } else {
            tooltip.add(Text.translatable("item.afmweapons.lance.attack_multiplier.8").formatted(Formatting.BLUE));
        }
        super.appendTooltip(stack, context, tooltip, type);
    }
}
