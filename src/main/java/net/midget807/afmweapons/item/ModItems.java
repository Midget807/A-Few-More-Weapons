package net.midget807.afmweapons.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.midget807.afmweapons.AFMWMain;
import net.midget807.afmweapons.item.afmw.HalberdItem;
import net.midget807.afmweapons.item.afmw.LongswordItem;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModItems {
    public static final Item HALBERD = registerItem("halberd", new HalberdItem(ToolMaterials.NETHERITE, 5, -3.0f, new FabricItemSettings()));
    public static final Item LONGSWORD = registerItem("longsword", new LongswordItem(ToolMaterials.NETHERITE, 3, -2.5f, new FabricItemSettings()));

    public static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, AFMWMain.id(name), item);
    }
    public static void registerModItems() {
        AFMWMain.LOGGER.info("Registering AFMW Items");
    }
}
