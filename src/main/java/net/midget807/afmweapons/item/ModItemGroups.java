package net.midget807.afmweapons.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.midget807.afmweapons.AFMWMain;
import net.midget807.afmweapons.enchantment.ModEnchantments;
import net.midget807.afmweapons.enchantment.afmw.ConcussEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;

import java.util.EnumSet;
import java.util.Set;

public class ModItemGroups {
    public static final ItemGroup AFMW_GROUP = registerItemGroup("main", FabricItemGroup.builder()
            .displayName(Text.translatable("itemgroup.afmweapons.main"))
            .icon(() -> new ItemStack(ModItems.NETHERITE_HALBERD))
            .entries((displayContext, entries) -> {
                entries.add(ModItems.POLE);

                entries.add(ModItems.WOODEN_HALBERD);
                entries.add(ModItems.STONE_HALBERD);
                entries.add(ModItems.IRON_HALBERD);
                entries.add(ModItems.GOLDEN_HALBERD);
                entries.add(ModItems.DIAMOND_HALBERD);
                entries.add(ModItems.NETHERITE_HALBERD);

                entries.add(ModItems.WOODEN_LONGSWORD);
                entries.add(ModItems.STONE_LONGSWORD);
                entries.add(ModItems.IRON_LONGSWORD);
                entries.add(ModItems.GOLDEN_LONGSWORD);
                entries.add(ModItems.DIAMOND_LONGSWORD);
                entries.add(ModItems.NETHERITE_LONGSWORD);

                entries.add(ModItems.FRYING_PAN);
                entries.add(ModItems.FRIED_EGG);
                /*
                Set<EnchantmentTarget> set = EnumSet.allOf(EnchantmentTarget.class);
                displayContext.lookup().getOptionalWrapper(RegistryKeys.ENCHANTMENT).ifPresent(wrapper -> {
                    addAFMWEnchantedBooks(entries, wrapper, set, ItemGroup.StackVisibility.PARENT_TAB_ONLY);
                });
                */
                entries.add(EnchantedBookItem.forEnchantment(new EnchantmentLevelEntry(ModEnchantments.TRIPLE_SHOT, 1)));
                entries.add(EnchantedBookItem.forEnchantment(new EnchantmentLevelEntry(ModEnchantments.CONCUSS, 3)));
                entries.add(ModItems.FROST_ARROW.getDefaultStack());
                entries.add(ModItems.EXPLOSIVE_ARROW.getDefaultStack());
                entries.add(ModItems.RICOCHET_ARROW.getDefaultStack());
                entries.add(ModItems.WARP_ARROW);
                entries.add(ModItems.MAGIC_ARROW.getDefaultStack());
                entries.add(ModItems.ECHO_ARROW.getDefaultStack());
            }).build());

    private static void addAFMWEnchantedBooks(ItemGroup.Entries entries, RegistryWrapper.Impl<Enchantment> wrapper, Set<EnchantmentTarget> set, ItemGroup.StackVisibility stackVisibility) {
        wrapper.streamEntries().map(RegistryEntry::value).filter(enchantment -> set.contains(enchantment.target)).map(enchantment -> EnchantedBookItem.forEnchantment(new EnchantmentLevelEntry(enchantment, enchantment.getMaxLevel()))).forEach(stack -> entries.add(stack, stackVisibility));
    }
    private static void addItemsToFoodGroup(FabricItemGroupEntries entries) {
        entries.addAfter(Items.MILK_BUCKET, ModItems.FRIED_EGG);
    }
    public static ItemGroup registerItemGroup(String name, ItemGroup group) {
        return Registry.register(Registries.ITEM_GROUP, AFMWMain.id(name), group);
    }
    public static void registerModItemGroups() {
        AFMWMain.LOGGER.info("Registering AFMW Item Groups");
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(ModItemGroups::addItemsToFoodGroup);
    }
}
