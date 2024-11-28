package net.midget807.afmweapons.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.midget807.afmweapons.AFMWMain;
import net.midget807.afmweapons.item.afmw.*;
import net.midget807.afmweapons.item.afmw.arrow.*;
import net.midget807.afmweapons.item.afmw.debug.DebugItem;
import net.minecraft.datafixer.fix.HorseArmorFix;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    // TODO: 28/11/2024 Fix weapon stats
    public static final Identifier REACH_MODIFIER_ID = Identifier.ofVanilla("reach");
    public static final Identifier ATTACK_RANGE_MODIFIER_ID = Identifier.ofVanilla("attack_range");
    public static final Identifier ATTACK_KNOCKBACK_MODIFIER_ID = Identifier.ofVanilla("attack_knockback");

    public static final Item DEBUGGER = registerItem("debugger", new DebugItem(new Item.Settings().maxCount(1)));
    public static final Item ITEMGROUP_ICON = registerItem("itemgroup_icon", new Item(new Item.Settings()));


    public static final Item POLE = registerItem("pole", new Item(new Item.Settings()));
    public static final Item WOODEN_HALBERD = registerItem("wooden_halberd", new HalberdItem(ToolMaterials.WOOD, new Item.Settings().maxCount(1).attributeModifiers(HalberdItem.createAttributeModifier(ToolMaterials.WOOD, 6, -3.2f, 2, 1.5f))));
    public static final Item STONE_HALBERD = registerItem("stone_halberd", new HalberdItem(ToolMaterials.STONE, new Item.Settings().maxCount(1).attributeModifiers(HalberdItem.createAttributeModifier(ToolMaterials.STONE, 7, -3.2f, 2, 1.5f))));
    public static final Item IRON_HALBERD = registerItem("iron_halberd", new HalberdItem(ToolMaterials.IRON, new Item.Settings().maxCount(1).attributeModifiers(HalberdItem.createAttributeModifier(ToolMaterials.IRON, 7, -3.2f, 2, 1.5f))));
    public static final Item GOLDEN_HALBERD = registerItem("golden_halberd", new HalberdItem(ToolMaterials.GOLD, new Item.Settings().maxCount(1).attributeModifiers(HalberdItem.createAttributeModifier(ToolMaterials.GOLD, 7, -3.1f, 2, 1.5f))));
    public static final Item DIAMOND_HALBERD = registerItem("diamond_halberd", new HalberdItem(ToolMaterials.DIAMOND, new Item.Settings().maxCount(1).attributeModifiers(HalberdItem.createAttributeModifier(ToolMaterials.DIAMOND, 8, -3.1f, 2, 1.5f))));
    public static final Item NETHERITE_HALBERD = registerItem("netherite_halberd", new HalberdItem(ToolMaterials.NETHERITE, new Item.Settings().maxCount(1).fireproof().attributeModifiers(HalberdItem.createAttributeModifier(ToolMaterials.NETHERITE, 9, -3f, 2, 1.5f))));
    public static final Item WOODEN_LONGSWORD = registerItem("wooden_longsword", new LongswordItem(ToolMaterials.WOOD, new Item.Settings().maxCount(1).attributeModifiers(LongswordItem.createAttributeModifier(ToolMaterials.WOOD, 5, -2.7f, 2, 1.5f))));
    public static final Item STONE_LONGSWORD = registerItem("stone_longsword", new LongswordItem(ToolMaterials.STONE, new Item.Settings().maxCount(1).attributeModifiers(LongswordItem.createAttributeModifier(ToolMaterials.STONE, 5, -2.7f, 2, 1.5f))));
    public static final Item IRON_LONGSWORD = registerItem("iron_longsword", new LongswordItem(ToolMaterials.IRON, new Item.Settings().maxCount(1).attributeModifiers(LongswordItem.createAttributeModifier(ToolMaterials.IRON, 5, -2.7f, 2, 1.5f))));
    public static final Item GOLDEN_LONGSWORD = registerItem("golden_longsword", new LongswordItem(ToolMaterials.GOLD, new Item.Settings().maxCount(1).attributeModifiers(LongswordItem.createAttributeModifier(ToolMaterials.GOLD, 5, -2.7f, 2, 1.5f))));
    public static final Item DIAMOND_LONGSWORD = registerItem("diamond_longsword", new LongswordItem(ToolMaterials.DIAMOND, new Item.Settings().maxCount(1).attributeModifiers(LongswordItem.createAttributeModifier(ToolMaterials.DIAMOND, 5, -2.7f, 2, 1.5f))));
    public static final Item NETHERITE_LONGSWORD = registerItem("netherite_longsword", new LongswordItem(ToolMaterials.NETHERITE, new Item.Settings().maxCount(1).fireproof().attributeModifiers(LongswordItem.createAttributeModifier(ToolMaterials.NETHERITE, 5, -2.7f, 2, 1.5f))));
    public static final Item IRON_LANCE = registerItem("iron_lance", new LanceItem(ToolMaterials.IRON, new Item.Settings().maxCount(1).attributeModifiers(LanceItem.createAttributeModifier(ToolMaterials.WOOD, 5, -3.2f, 2, 1.5f))));
    public static final Item GOLDEN_LANCE = registerItem("golden_lance", new LanceItem(ToolMaterials.GOLD, new Item.Settings().maxCount(1).attributeModifiers(LanceItem.createAttributeModifier(ToolMaterials.WOOD, 5, -3.2f, 2, 1.5f))));
    public static final Item DIAMOND_LANCE = registerItem("diamond_lance", new LanceItem(ToolMaterials.DIAMOND, new Item.Settings().maxCount(1).attributeModifiers(LanceItem.createAttributeModifier(ToolMaterials.WOOD, 5, -3.2f, 2, 1.5f))));
    public static final Item NETHERITE_LANCE = registerItem("netherite_lance", new LanceItem(ToolMaterials.NETHERITE, new Item.Settings().maxCount(1).attributeModifiers(LanceItem.createAttributeModifier(ToolMaterials.WOOD, 5, -3.2f, 2, 1.5f))));
    public static final Item WOODEN_CLAYMORE = registerItem("wooden_claymore", new ClaymoreItem(ToolMaterials.WOOD, new Item.Settings().maxCount(1).attributeModifiers(ClaymoreItem.createAttributeModifier(ToolMaterials.WOOD, 5, -3.2f, 2, 1.5f))));
    public static final Item STONE_CLAYMORE = registerItem("stone_claymore", new ClaymoreItem(ToolMaterials.STONE, new Item.Settings().maxCount(1).attributeModifiers(ClaymoreItem.createAttributeModifier(ToolMaterials.WOOD, 5, -3.2f, 2, 1.5f))));
    public static final Item IRON_CLAYMORE = registerItem("iron_claymore", new ClaymoreItem(ToolMaterials.IRON, new Item.Settings().maxCount(1).attributeModifiers(ClaymoreItem.createAttributeModifier(ToolMaterials.WOOD, 5, -3.2f, 2, 1.5f))));
    public static final Item GOLDEN_CLAYMORE = registerItem("golden_claymore", new ClaymoreItem(ToolMaterials.GOLD, new Item.Settings().maxCount(1).attributeModifiers(ClaymoreItem.createAttributeModifier(ToolMaterials.WOOD, 5, -3.2f, 2, 1.5f))));
    public static final Item DIAMOND_CLAYMORE = registerItem("diamond_claymore", new ClaymoreItem(ToolMaterials.DIAMOND, new Item.Settings().maxCount(1).attributeModifiers(ClaymoreItem.createAttributeModifier(ToolMaterials.WOOD, 5, -3.2f, 2, 1.5f))));
    public static final Item NETHERITE_CLAYMORE = registerItem("netherite_claymore", new ClaymoreItem(ToolMaterials.NETHERITE, new Item.Settings().maxCount(1).fireproof().attributeModifiers(ClaymoreItem.createAttributeModifier(ToolMaterials.WOOD, 5, -3.2f, 2, 1.5f))));
    public static final Item FRYING_PAN = registerItem("frying_pan", new FryingPanItem(new Item.Settings().maxCount(1).maxDamage(250)));
    public static final Item NETHERITE_HORSE_ARMOR = registerItem("netherite_horse_armor", new AnimalArmorItem(ArmorMaterials.NETHERITE, AnimalArmorItem.Type.EQUESTRIAN, false, new Item.Settings().maxCount(1).fireproof()));
    public static final Item FRIED_EGG = registerItem("fried_egg", new Item(new Item.Settings().maxCount(16).food(ModFoodComponents.FRIED_EGG)));
    public static final Item FROST_ARROW = registerItem("frost_arrow", new FrostArrowItem(new Item.Settings()));
    public static final Item EXPLOSIVE_ARROW = registerItem("explosive_arrow", new ExplosiveArrowItem(new Item.Settings()));
    public static final Item RICOCHET_ARROW = registerItem("ricochet_arrow", new RicochetArrowItem(new Item.Settings()));
    public static final Item WARP_ARROW = registerItem("warp_arrow", new WarpArrowItem(new Item.Settings()));
    public static final Item MAGIC_ARROW = registerItem("magic_arrow", new MagicArrowItem(new Item.Settings()));
    public static final Item ECHO_ARROW = registerItem("echo_arrow", new EchoArrowItem(new Item.Settings()));
    public static final Item GUIDED_ARROW = registerItem("guided_arrow", new GuidedArrowItem(new Item.Settings().maxCount(16)));

    public static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, AFMWMain.id(name), item);
    }
    public static void registerModItems() {
        AFMWMain.LOGGER.info("Registering AFMW Items");
    }
}
