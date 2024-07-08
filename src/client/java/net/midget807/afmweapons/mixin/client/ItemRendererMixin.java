package net.midget807.afmweapons.mixin.client;

import net.midget807.afmweapons.AFMWMain;
import net.midget807.afmweapons.item.ModItems;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
    @ModifyVariable(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V", at = @At("HEAD"), argsOnly = true)
    public BakedModel useHalberdModels(BakedModel value, ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumer, int light, int overlay, BakedModel model) {
        if (stack.isOf(ModItems.NETHERITE_HALBERD) && renderMode != ModelTransformationMode.GUI && renderMode != ModelTransformationMode.GROUND) {
            return ((ItemRendererAccessor) this).afmw$getModels().getModelManager().getModel(new ModelIdentifier(AFMWMain.Mod_ID, "netherite_halberd_handheld", "inventory"));
        }
        if (stack.isOf(ModItems.WOODEN_LONGSWORD) && renderMode != ModelTransformationMode.GUI && renderMode != ModelTransformationMode.GROUND) {
            return ((ItemRendererAccessor) this).afmw$getModels().getModelManager().getModel(new ModelIdentifier(AFMWMain.Mod_ID, "wooden_longsword_handheld", "inventory"));
        }
        if (stack.isOf(ModItems.STONE_LONGSWORD) && renderMode != ModelTransformationMode.GUI && renderMode != ModelTransformationMode.GROUND) {
            return ((ItemRendererAccessor) this).afmw$getModels().getModelManager().getModel(new ModelIdentifier(AFMWMain.Mod_ID, "stone_longsword_handheld", "inventory"));
        }
        if (stack.isOf(ModItems.IRON_LONGSWORD) && renderMode != ModelTransformationMode.GUI && renderMode != ModelTransformationMode.GROUND) {
            return ((ItemRendererAccessor) this).afmw$getModels().getModelManager().getModel(new ModelIdentifier(AFMWMain.Mod_ID, "iron_longsword_handheld", "inventory"));
        }
        if (stack.isOf(ModItems.GOLDEN_LONGSWORD) && renderMode != ModelTransformationMode.GUI && renderMode != ModelTransformationMode.GROUND) {
            return ((ItemRendererAccessor) this).afmw$getModels().getModelManager().getModel(new ModelIdentifier(AFMWMain.Mod_ID, "golden_longsword_handheld", "inventory"));
        }
        if (stack.isOf(ModItems.DIAMOND_LONGSWORD) && renderMode != ModelTransformationMode.GUI && renderMode != ModelTransformationMode.GROUND) {
            return ((ItemRendererAccessor) this).afmw$getModels().getModelManager().getModel(new ModelIdentifier(AFMWMain.Mod_ID, "diamond_longsword_handheld", "inventory"));
        }
        if (stack.isOf(ModItems.NETHERITE_LONGSWORD) && renderMode != ModelTransformationMode.GUI && renderMode != ModelTransformationMode.GROUND) {
            return ((ItemRendererAccessor) this).afmw$getModels().getModelManager().getModel(new ModelIdentifier(AFMWMain.Mod_ID, "netherite_longsword_handheld", "inventory"));
        }
        return value;
    }
}
