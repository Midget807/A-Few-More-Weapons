package net.midget807.afmweapons.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.midget807.afmweapons.AFMWMain;
import net.midget807.afmweapons.item.afmw.FryingPanItem;
import net.midget807.afmweapons.item.afmw.HalberdItem;
import net.midget807.afmweapons.item.afmw.LanceItem;
import net.midget807.afmweapons.item.afmw.LongswordItem;
import net.midget807.afmweapons.item.afmw.arrow.*;
import net.midget807.afmweapons.item.afmw.debug.DebugItem;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModItems {
    public static final Item DEBUGGER = registerItem("debugger", new DebugItem(new FabricItemSettings().maxCount(1)));


    public static final Item POLE = registerItem("pole", new Item(new FabricItemSettings()));
    public static final Item WOODEN_HALBERD = registerItem("wooden_halberd", new HalberdItem(ToolMaterials.WOOD, 6, -3.2f, new FabricItemSettings().maxCount(1)));
    public static final Item STONE_HALBERD = registerItem("stone_halberd", new HalberdItem(ToolMaterials.STONE, 7, -3.2f, new FabricItemSettings().maxCount(1)));
    public static final Item IRON_HALBERD = registerItem("iron_halberd", new HalberdItem(ToolMaterials.IRON, 6, -3.1f, new FabricItemSettings().maxCount(1)));
    public static final Item GOLDEN_HALBERD = registerItem("golden_halberd", new HalberdItem(ToolMaterials.GOLD, 8, -3.0f, new FabricItemSettings().maxCount(1)));
    public static final Item DIAMOND_HALBERD = registerItem("diamond_halberd", new HalberdItem(ToolMaterials.DIAMOND, 5, -3.0f, new FabricItemSettings().maxCount(1)));
    public static final Item NETHERITE_HALBERD = registerItem("netherite_halberd", new HalberdItem(ToolMaterials.NETHERITE, 5, -3.0f, new FabricItemSettings().maxCount(1).fireproof()));
    public static final Item WOODEN_LONGSWORD = registerItem("wooden_longsword", new LongswordItem(ToolMaterials.WOOD, 3, -2.7f, new FabricItemSettings().maxCount(1)));
    public static final Item STONE_LONGSWORD = registerItem("stone_longsword", new LongswordItem(ToolMaterials.STONE, 3, -2.7f, new FabricItemSettings().maxCount(1)));
    public static final Item IRON_LONGSWORD = registerItem("iron_longsword", new LongswordItem(ToolMaterials.IRON, 3, -2.7f, new FabricItemSettings().maxCount(1)));
    public static final Item GOLDEN_LONGSWORD = registerItem("golden_longsword", new LongswordItem(ToolMaterials.GOLD, 3, -2.7f, new FabricItemSettings().maxCount(1)));
    public static final Item DIAMOND_LONGSWORD = registerItem("diamond_longsword", new LongswordItem(ToolMaterials.DIAMOND, 3, -2.7f, new FabricItemSettings().maxCount(1)));
    public static final Item NETHERITE_LONGSWORD = registerItem("netherite_longsword", new LongswordItem(ToolMaterials.NETHERITE, 3, -2.7f, new FabricItemSettings().maxCount(1).fireproof()));
    public static final Item IRON_LANCE = registerItem("iron_lance", new LanceItem(ToolMaterials.IRON, 2, -2.0f, new FabricItemSettings().maxCount(1)));
    public static final Item GOLDEN_LANCE = registerItem("golden_lance", new LanceItem(ToolMaterials.GOLD, 2, -2.0f, new FabricItemSettings().maxCount(1)));
    public static final Item DIAMOND_LANCE = registerItem("diamond_lance", new LanceItem(ToolMaterials.DIAMOND, 2, -2.0f, new FabricItemSettings().maxCount(1)));
    public static final Item NETHERITE_LANCE = registerItem("netherite_lance", new LanceItem(ToolMaterials.NETHERITE, 2, -2.0f, new FabricItemSettings().maxCount(1)));
    public static final Item FRYING_PAN = registerItem("frying_pan", new FryingPanItem(ToolMaterials.IRON, 1, -3.1f, new FabricItemSettings().maxCount(1).maxDamage(250)));
    public static final Item FRIED_EGG = registerItem("fried_egg", new Item(new FabricItemSettings().maxCount(16).food(ModFoodComponents.FRIED_EGG)));
    public static final Item FROST_ARROW = registerItem("frost_arrow", new FrostArrowItem(new FabricItemSettings()));
    public static final Item EXPLOSIVE_ARROW = registerItem("explosive_arrow", new ExplosiveArrowItem(new FabricItemSettings()));
    public static final Item RICOCHET_ARROW = registerItem("ricochet_arrow", new RicochetArrowItem(new FabricItemSettings()));
    public static final Item WARP_ARROW = registerItem("warp_arrow", new WarpArrowItem(new FabricItemSettings()));
    public static final Item MAGIC_ARROW = registerItem("magic_arrow", new MagicArrowItem(new FabricItemSettings()));
    public static final Item ECHO_ARROW = registerItem("echo_arrow", new EchoArrowItem(new FabricItemSettings()));
    public static final Item SEEKING_ARROW = registerItem("seeking_arrow", new SeekingArrowItem(new FabricItemSettings()));

    public static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, AFMWMain.id(name), item);
    }
    public static void registerModItems() {
        AFMWMain.LOGGER.info("Registering AFMW Items");
    }
}
