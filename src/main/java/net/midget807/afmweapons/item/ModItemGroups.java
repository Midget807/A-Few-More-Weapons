package net.midget807.afmweapons.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.midget807.afmweapons.AFMWMain;
import net.midget807.afmweapons.enchantment.ModEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;

import java.util.stream.IntStream;

public class ModItemGroups {
    public static final ItemGroup AFMW_GROUP = registerItemGroup("main", FabricItemGroup.builder()
            .displayName(Text.translatable("itemgroup.afmweapons.main"))
            .icon(() -> new ItemStack(ModItems.ITEMGROUP_ICON))
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
/*
                entries.add(ModItems.IRON_LANCE);
                entries.add(ModItems.GOLDEN_LANCE);
                entries.add(ModItems.DIAMOND_LANCE);
                entries.add(ModItems.NETHERITE_LANCE);
*/

                entries.add(ModItems.FRYING_PAN);
                entries.add(ModItems.FRIED_EGG);

                entries.add(ModItems.FROST_ARROW.getDefaultStack());
                entries.add(ModItems.EXPLOSIVE_ARROW.getDefaultStack());
                entries.add(ModItems.RICOCHET_ARROW.getDefaultStack());
                entries.add(ModItems.WARP_ARROW);
                entries.add(ModItems.MAGIC_ARROW.getDefaultStack());
                entries.add(ModItems.ECHO_ARROW.getDefaultStack());
                //entries.add(ModItems.GUIDED_ARROW.getDefaultStack());

                displayContext.lookup().getOptionalWrapper(RegistryKeys.ENCHANTMENT).ifPresent(registryWrapper -> {
                    addMaxLevelEnchantedBooks(entries, registryWrapper, ItemGroup.StackVisibility.PARENT_TAB_ONLY);
                    addAllLevelEnchantedBooks(entries, registryWrapper, ItemGroup.StackVisibility.SEARCH_TAB_ONLY);
                });

            }).build());

    private static void addAllLevelEnchantedBooks(ItemGroup.Entries entries, RegistryWrapper.Impl<Enchantment> registryWrapper, ItemGroup.StackVisibility stackVisibility) {
        registryWrapper.streamEntries()
                .flatMap(
                        enchantmentEntry -> IntStream.rangeClosed(enchantmentEntry.value().getMinLevel(), enchantmentEntry.value().getMaxLevel())
                                .mapToObj(level -> EnchantedBookItem.forEnchantment(new EnchantmentLevelEntry(enchantmentEntry, level)))
                )
                .forEach(stack -> entries.add(stack, stackVisibility));
    }

    private static void addMaxLevelEnchantedBooks(ItemGroup.Entries entries, RegistryWrapper.Impl<Enchantment> registryWrapper, ItemGroup.StackVisibility stackVisibility) {
        registryWrapper.streamEntries()
                .map(
                        enchantmentEntry -> EnchantedBookItem.forEnchantment(new EnchantmentLevelEntry(enchantmentEntry, enchantmentEntry.value().getMaxLevel()))
                )
                .forEach(stack -> entries.add(stack, stackVisibility));
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
