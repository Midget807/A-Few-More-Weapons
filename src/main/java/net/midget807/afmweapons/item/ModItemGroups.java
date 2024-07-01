package net.midget807.afmweapons.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.midget807.afmweapons.AFMWMain;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;

public class ModItemGroups {
    public static final ItemGroup AFMW_GROUP = Registry.register(Registries.ITEM_GROUP,
            AFMWMain.id("main"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.afmweapons.main"))
                    .icon(() -> new ItemStack(ModItems.HALBERD))
                    .entries((displayContext, entries) -> {
                        entries.add(ModItems.HALBERD);
                    }).build());
    public static void registerModItemGroups() {
        AFMWMain.LOGGER.info("Registering AFMW Item Groups");
    }
}
