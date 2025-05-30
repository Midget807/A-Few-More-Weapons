package net.midget807.afmweapons.mixin.client;

import net.midget807.afmweapons.AFMWMain;
import net.midget807.afmweapons.cca.afmw.LongswordComponent;
import net.midget807.afmweapons.datagen.ModItemTagProvider;
import net.midget807.afmweapons.item.ModItems;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {

    // === Credit: From Amarite Mod ===

    @Inject(method = "renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/world/World;III)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/ItemRenderer;getModel(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;I)Lnet/minecraft/client/render/model/BakedModel;"))
    private void amarite$storeEntity(LivingEntity entity, ItemStack item, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, World world, int light, int overlay, int seed, CallbackInfo ci) {
        if (item.isIn(ModItemTagProvider.LONGSWORDS) && entity instanceof PlayerEntity player) {
            ItemRendererMixin.entity = player;
        } else if (item.isOf(ModItems.FRYING_PAN) && entity instanceof PlayerEntity player) {
            ItemRendererMixin.entity = player;
        }
    }

    // ================================
    @Unique
    private static PlayerEntity entity;
    @ModifyVariable(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V", at = @At("HEAD"), argsOnly = true)
    public BakedModel useHalberdModels(BakedModel value, ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumer, int light, int overlay, BakedModel model) {

        //Halberd
        if (stack.isOf(ModItems.WOODEN_HALBERD) && renderMode != ModelTransformationMode.GUI && renderMode != ModelTransformationMode.GROUND) {
            return ((ItemRendererAccessor) this).afmw$getModels().getModelManager().getModel(new ModelIdentifier(AFMWMain.MOD_ID, "wooden_halberd_handheld", "inventory"));
        }
        if (stack.isOf(ModItems.STONE_HALBERD) && renderMode != ModelTransformationMode.GUI && renderMode != ModelTransformationMode.GROUND) {
            return ((ItemRendererAccessor) this).afmw$getModels().getModelManager().getModel(new ModelIdentifier(AFMWMain.MOD_ID, "stone_halberd_handheld", "inventory"));
        }
        if (stack.isOf(ModItems.IRON_HALBERD) && renderMode != ModelTransformationMode.GUI && renderMode != ModelTransformationMode.GROUND) {
            return ((ItemRendererAccessor) this).afmw$getModels().getModelManager().getModel(new ModelIdentifier(AFMWMain.MOD_ID, "iron_halberd_handheld", "inventory"));
        }
        if (stack.isOf(ModItems.GOLDEN_HALBERD) && renderMode != ModelTransformationMode.GUI && renderMode != ModelTransformationMode.GROUND) {
            return ((ItemRendererAccessor) this).afmw$getModels().getModelManager().getModel(new ModelIdentifier(AFMWMain.MOD_ID, "golden_halberd_handheld", "inventory"));
        }
        if (stack.isOf(ModItems.DIAMOND_HALBERD) && renderMode != ModelTransformationMode.GUI && renderMode != ModelTransformationMode.GROUND) {
            return ((ItemRendererAccessor) this).afmw$getModels().getModelManager().getModel(new ModelIdentifier(AFMWMain.MOD_ID, "diamond_halberd_handheld", "inventory"));
        }
        if (stack.isOf(ModItems.NETHERITE_HALBERD) && renderMode != ModelTransformationMode.GUI && renderMode != ModelTransformationMode.GROUND) {
            return ((ItemRendererAccessor) this).afmw$getModels().getModelManager().getModel(new ModelIdentifier(AFMWMain.MOD_ID, "netherite_halberd_handheld", "inventory"));
        }

        //Longswords
        if (stack.isOf(ModItems.WOODEN_LONGSWORD) && renderMode != ModelTransformationMode.GUI && renderMode != ModelTransformationMode.GROUND) {
            if (entity != null) {
                LongswordComponent longswordComponent = LongswordComponent.get(ItemRendererMixin.entity);
                if (longswordComponent.isBlocking()) {
                    return ((ItemRendererAccessor) this).afmw$getModels().getModelManager().getModel(new ModelIdentifier(AFMWMain.MOD_ID, "wooden_longsword_blocking", "inventory"));
                } else {
                    return ((ItemRendererAccessor) this).afmw$getModels().getModelManager().getModel(new ModelIdentifier(AFMWMain.MOD_ID, "wooden_longsword_handheld", "inventory"));
                }
            } else {
                return ((ItemRendererAccessor) this).afmw$getModels().getModelManager().getModel(new ModelIdentifier(AFMWMain.MOD_ID, "wooden_longsword_handheld", "inventory"));
            }
        }
        if (stack.isOf(ModItems.STONE_LONGSWORD) && renderMode != ModelTransformationMode.GUI && renderMode != ModelTransformationMode.GROUND) {
            if (entity != null) {
                LongswordComponent longswordComponent = LongswordComponent.get(ItemRendererMixin.entity);
                if (longswordComponent.isBlocking()) {
                    return ((ItemRendererAccessor) this).afmw$getModels().getModelManager().getModel(new ModelIdentifier(AFMWMain.MOD_ID, "stone_longsword_blocking", "inventory"));
                } else {
                    return ((ItemRendererAccessor) this).afmw$getModels().getModelManager().getModel(new ModelIdentifier(AFMWMain.MOD_ID, "stone_longsword_handheld", "inventory"));
                }
            } else {
                return ((ItemRendererAccessor) this).afmw$getModels().getModelManager().getModel(new ModelIdentifier(AFMWMain.MOD_ID, "stone_longsword_handheld", "inventory"));
            }
        }
        if (stack.isOf(ModItems.IRON_LONGSWORD) && renderMode != ModelTransformationMode.GUI && renderMode != ModelTransformationMode.GROUND) {
            if (entity != null) {
                LongswordComponent longswordComponent = LongswordComponent.get(ItemRendererMixin.entity);
                if (longswordComponent.isBlocking()) {
                    return ((ItemRendererAccessor) this).afmw$getModels().getModelManager().getModel(new ModelIdentifier(AFMWMain.MOD_ID, "iron_longsword_blocking", "inventory"));
                } else {
                    return ((ItemRendererAccessor) this).afmw$getModels().getModelManager().getModel(new ModelIdentifier(AFMWMain.MOD_ID, "iron_longsword_handheld", "inventory"));
                }
            } else {
                return ((ItemRendererAccessor) this).afmw$getModels().getModelManager().getModel(new ModelIdentifier(AFMWMain.MOD_ID, "iron_longsword_handheld", "inventory"));
            }
        }
        if (stack.isOf(ModItems.GOLDEN_LONGSWORD) && renderMode != ModelTransformationMode.GUI && renderMode != ModelTransformationMode.GROUND) {
            if (entity != null) {
                LongswordComponent longswordComponent = LongswordComponent.get(ItemRendererMixin.entity);
                if (longswordComponent.isBlocking()) {
                    return ((ItemRendererAccessor) this).afmw$getModels().getModelManager().getModel(new ModelIdentifier(AFMWMain.MOD_ID, "golden_longsword_blocking", "inventory"));
                } else {
                    return ((ItemRendererAccessor) this).afmw$getModels().getModelManager().getModel(new ModelIdentifier(AFMWMain.MOD_ID, "golden_longsword_handheld", "inventory"));
                }
            } else {
                return ((ItemRendererAccessor) this).afmw$getModels().getModelManager().getModel(new ModelIdentifier(AFMWMain.MOD_ID, "golden_longsword_handheld", "inventory"));
            }
        }
        if (stack.isOf(ModItems.DIAMOND_LONGSWORD) && renderMode != ModelTransformationMode.GUI && renderMode != ModelTransformationMode.GROUND) {
            if (entity != null) {
                LongswordComponent longswordComponent = LongswordComponent.get(ItemRendererMixin.entity);
                if (longswordComponent.isBlocking()) {
                    return ((ItemRendererAccessor) this).afmw$getModels().getModelManager().getModel(new ModelIdentifier(AFMWMain.MOD_ID, "diamond_longsword_blocking", "inventory"));
                } else {
                    return ((ItemRendererAccessor) this).afmw$getModels().getModelManager().getModel(new ModelIdentifier(AFMWMain.MOD_ID, "diamond_longsword_handheld", "inventory"));
                }
            } else {
                return ((ItemRendererAccessor) this).afmw$getModels().getModelManager().getModel(new ModelIdentifier(AFMWMain.MOD_ID, "diamond_longsword_handheld", "inventory"));
            }
        }
        if (stack.isOf(ModItems.NETHERITE_LONGSWORD) && renderMode != ModelTransformationMode.GUI && renderMode != ModelTransformationMode.GROUND) {

            if (entity != null) {
                LongswordComponent longswordComponent = LongswordComponent.get(ItemRendererMixin.entity);
                if (longswordComponent.isBlocking()) {
                    return ((ItemRendererAccessor) this).afmw$getModels().getModelManager().getModel(new ModelIdentifier(AFMWMain.MOD_ID, "netherite_longsword_blocking", "inventory"));
                } else {
                    return ((ItemRendererAccessor) this).afmw$getModels().getModelManager().getModel(new ModelIdentifier(AFMWMain.MOD_ID, "netherite_longsword_handheld", "inventory"));
                }
            } else {
                return ((ItemRendererAccessor) this).afmw$getModels().getModelManager().getModel(new ModelIdentifier(AFMWMain.MOD_ID, "netherite_longsword_handheld", "inventory"));
            }

        }
        if (stack.isOf(ModItems.FRYING_PAN) && renderMode != ModelTransformationMode.GUI && renderMode != ModelTransformationMode.GROUND) {
            if (entity != null) {
                if (entity.isUsingItem() && entity.getStackInHand(Hand.OFF_HAND).isOf(Items.EGG)) {
                    return ((ItemRendererAccessor) this).afmw$getModels().getModelManager().getModel(new ModelIdentifier(AFMWMain.MOD_ID, "frying_pan_handheld_throwing", "inventory"));
                } else {
                    return ((ItemRendererAccessor) this).afmw$getModels().getModelManager().getModel(new ModelIdentifier(AFMWMain.MOD_ID, "frying_pan_handheld", "inventory"));
                }
            } else {
                return ((ItemRendererAccessor) this).afmw$getModels().getModelManager().getModel(new ModelIdentifier(AFMWMain.MOD_ID, "frying_pan_handheld", "inventory"));
            }
        }
        return value;
    }
}
