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
    public static final Item POLE = registerItem("pole", new Item(new FabricItemSettings()));
    public static final Item NETHERITE_HALBERD = registerItem("netherite_halberd", new HalberdItem(ToolMaterials.NETHERITE, 5, -3.0f, new FabricItemSettings().maxCount(1).fireproof()));
    public static final Item WOODEN_LONGSWORD = registerItem("wooden_longsword", new LongswordItem(ToolMaterials.WOOD, 3, -2.7f, new FabricItemSettings().maxCount(1)));
    public static final Item STONE_LONGSWORD = registerItem("stone_longsword", new LongswordItem(ToolMaterials.STONE, 3, -2.7f, new FabricItemSettings().maxCount(1)));
    public static final Item IRON_LONGSWORD = registerItem("iron_longsword", new LongswordItem(ToolMaterials.IRON, 3, -2.7f, new FabricItemSettings().maxCount(1)));
    public static final Item GOLDEN_LONGSWORD = registerItem("golden_longsword", new LongswordItem(ToolMaterials.GOLD, 3, -2.7f, new FabricItemSettings().maxCount(1)));
    public static final Item DIAMOND_LONGSWORD = registerItem("diamond_longsword", new LongswordItem(ToolMaterials.DIAMOND, 3, -2.7f, new FabricItemSettings().maxCount(1)));
    public static final Item NETHERITE_LONGSWORD = registerItem("netherite_longsword", new LongswordItem(ToolMaterials.NETHERITE, 3, -2.7f, new FabricItemSettings().maxCount(1).fireproof()));

    public static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, AFMWMain.id(name), item);
    }
    public static void registerModItems() {
        AFMWMain.LOGGER.info("Registering AFMW Items");
    }
}
