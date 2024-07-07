package net.midget807.afmweapons.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.midget807.afmweapons.AFMWMain;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;

public class ModItemGroups {
    public static final ItemGroup AFMW_GROUP = registerItemGroup("main", FabricItemGroup.builder()
            .displayName(Text.translatable("itemgroup.afmweapons.main"))
            .icon(() -> new ItemStack(ModItems.NETHERITE_HALBERD))
            .entries((displayContext, entries) -> {
                entries.add(ModItems.NETHERITE_HALBERD);
                entries.add(ModItems.NETHERITE_LONGSWORD);
            }).build());
    public static ItemGroup registerItemGroup(String name, ItemGroup group) {
        return Registry.register(Registries.ITEM_GROUP, AFMWMain.id(name), group);
    }
    public static void registerModItemGroups() {
        AFMWMain.LOGGER.info("Registering AFMW Item Groups");
    }
}
