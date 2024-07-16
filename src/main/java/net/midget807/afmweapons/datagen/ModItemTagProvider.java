package net.midget807.afmweapons.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.midget807.afmweapons.AFMWMain;
import net.midget807.afmweapons.item.ModItems;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public static final TagKey<Item> LONGSWORDS = TagKey.of(RegistryKeys.ITEM, AFMWMain.id("longswords"));
    public static final TagKey<Item> HALBERDS = TagKey.of(RegistryKeys.ITEM, AFMWMain.id("halberds"));
    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        this.getOrCreateTagBuilder(LONGSWORDS)
                .add(ModItems.WOODEN_LONGSWORD)
                .add(ModItems.STONE_LONGSWORD)
                .add(ModItems.IRON_LONGSWORD)
                .add(ModItems.GOLDEN_LONGSWORD)
                .add(ModItems.DIAMOND_LONGSWORD)
                .add(ModItems.NETHERITE_LONGSWORD);
        this.getOrCreateTagBuilder(HALBERDS)
                .add(ModItems.WOODEN_HALBERD)
                .add(ModItems.STONE_HALBERD)
                .add(ModItems.IRON_HALBERD)
                .add(ModItems.GOLD_HALBERD)
                .add(ModItems.DIAMOND_HALBERD)
                .add(ModItems.NETHERITE_HALBERD);
    }
}
