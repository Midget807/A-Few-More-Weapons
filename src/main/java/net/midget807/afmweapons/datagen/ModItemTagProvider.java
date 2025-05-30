package net.midget807.afmweapons.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.midget807.afmweapons.AFMWMain;
import net.midget807.afmweapons.item.ModItems;
import net.minecraft.data.server.tag.vanilla.VanillaItemTagProvider;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public static final TagKey<Item> LONGSWORDS = TagKey.of(RegistryKeys.ITEM, AFMWMain.id("longswords"));
    public static final TagKey<Item> HALBERDS = TagKey.of(RegistryKeys.ITEM, AFMWMain.id("halberds"));
    public static final TagKey<Item> LANCES = TagKey.of(RegistryKeys.ITEM, AFMWMain.id("lances"));
    public static final TagKey<Item> TRIPLE_SHOT_PROJECTILES = TagKey.of(RegistryKeys.ITEM, AFMWMain.id("triple_shot_projectiles"));
    public static final TagKey<Item> AFMW_BOW_PROJECTILES = TagKey.of(RegistryKeys.ITEM, AFMWMain.id("bow_projectiles"));
    public static final TagKey<Item> AFMW_ARROWS = TagKey.of(RegistryKeys.ITEM, AFMWMain.id("afmw_arrows"));
    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        this.getOrCreateTagBuilder(LONGSWORDS)
                .add(ModItems.NETHERITE_LONGSWORD)
                .add(ModItems.WOODEN_LONGSWORD)
                .add(ModItems.STONE_LONGSWORD)
                .add(ModItems.IRON_LONGSWORD)
                .add(ModItems.GOLDEN_LONGSWORD)
                .add(ModItems.DIAMOND_LONGSWORD);

        this.getOrCreateTagBuilder(HALBERDS)
                .add(ModItems.NETHERITE_HALBERD)
                .add(ModItems.WOODEN_HALBERD)
                .add(ModItems.STONE_HALBERD)
                .add(ModItems.IRON_HALBERD)
                .add(ModItems.GOLDEN_HALBERD)
                .add(ModItems.DIAMOND_HALBERD);

        this.getOrCreateTagBuilder(LANCES)
                .add(ModItems.NETHERITE_LANCE)
                .add(ModItems.IRON_LANCE)
                .add(ModItems.GOLDEN_LANCE)
                .add(ModItems.DIAMOND_LANCE);

        this.getOrCreateTagBuilder(TRIPLE_SHOT_PROJECTILES)
                .add(Items.ARROW)
                .add(Items.TIPPED_ARROW)
                .add(Items.SPECTRAL_ARROW);

        this.getOrCreateTagBuilder(AFMW_ARROWS)
                .add(ModItems.FROST_ARROW)
                .add(ModItems.EXPLOSIVE_ARROW)
                .add(ModItems.RICOCHET_ARROW)
                .add(ModItems.WARP_ARROW)
                .add(ModItems.MAGIC_ARROW)
                .add(ModItems.ECHO_ARROW);

        this.getOrCreateTagBuilder(AFMW_BOW_PROJECTILES)
                .add(Items.ARROW)
                .add(Items.TIPPED_ARROW)
                .add(Items.SPECTRAL_ARROW)
                .addTag(AFMW_ARROWS);
    }
}
