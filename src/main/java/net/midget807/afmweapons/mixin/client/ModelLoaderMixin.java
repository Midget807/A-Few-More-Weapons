package net.midget807.afmweapons.mixin.client;

import net.midget807.afmweapons.AFMWMain;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Map;

@Mixin(ModelLoader.class)
public abstract class ModelLoaderMixin {
    @Shadow
    protected abstract void addModel(ModelIdentifier modelId);


    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/model/ModelLoader;addModel(Lnet/minecraft/client/util/ModelIdentifier;)V", ordinal = 3, shift = At.Shift.AFTER))
    public void afmw$addNewModels(BlockColors blockColors, Profiler profiler, Map<Identifier, JsonUnbakedModel> jsonUnbakedModels, Map<Identifier, List<ModelLoader.SourceTrackedData>> blockStates, CallbackInfo ci) {
        //Halberd
        this.addModel(new ModelIdentifier(AFMWMain.MOD_ID, "wooden_halberd_handheld", "inventory"));
        this.addModel(new ModelIdentifier(AFMWMain.MOD_ID, "stone_halberd_handheld", "inventory"));
        this.addModel(new ModelIdentifier(AFMWMain.MOD_ID, "iron_halberd_handheld", "inventory"));
        this.addModel(new ModelIdentifier(AFMWMain.MOD_ID, "golden_halberd_handheld", "inventory"));
        this.addModel(new ModelIdentifier(AFMWMain.MOD_ID, "diamond_halberd_handheld", "inventory"));
        this.addModel(new ModelIdentifier(AFMWMain.MOD_ID, "netherite_halberd_handheld", "inventory"));
        //Longswords
        this.addModel(new ModelIdentifier(AFMWMain.MOD_ID, "wooden_longsword_blocking", "inventory"));
        this.addModel(new ModelIdentifier(AFMWMain.MOD_ID, "wooden_longsword_handheld", "inventory"));
        this.addModel(new ModelIdentifier(AFMWMain.MOD_ID, "stone_longsword_blocking", "inventory"));
        this.addModel(new ModelIdentifier(AFMWMain.MOD_ID, "stone_longsword_handheld", "inventory"));
        this.addModel(new ModelIdentifier(AFMWMain.MOD_ID, "iron_longsword_blocking", "inventory"));
        this.addModel(new ModelIdentifier(AFMWMain.MOD_ID, "iron_longsword_handheld", "inventory"));
        this.addModel(new ModelIdentifier(AFMWMain.MOD_ID, "golden_longsword_blocking", "inventory"));
        this.addModel(new ModelIdentifier(AFMWMain.MOD_ID, "golden_longsword_handheld", "inventory"));
        this.addModel(new ModelIdentifier(AFMWMain.MOD_ID, "diamond_longsword_blocking", "inventory"));
        this.addModel(new ModelIdentifier(AFMWMain.MOD_ID, "diamond_longsword_handheld", "inventory"));
        this.addModel(new ModelIdentifier(AFMWMain.MOD_ID, "netherite_longsword_blocking", "inventory"));
        this.addModel(new ModelIdentifier(AFMWMain.MOD_ID, "netherite_longsword_handheld", "inventory"));

        this.addModel(new ModelIdentifier(AFMWMain.MOD_ID, "frying_pan_handheld", "inventory"));
        this.addModel(new ModelIdentifier(AFMWMain.MOD_ID, "frying_pan_handheld_throwing", "inventory"));

    }
}
