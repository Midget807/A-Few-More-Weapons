package net.midget807.afmweapons.rendering;

import net.midget807.afmweapons.entity.afmw.ExplosiveArrowEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.util.Identifier;

public class ExplosiveArrowEntityRenderer extends ProjectileEntityRenderer<ExplosiveArrowEntity> {
    public ExplosiveArrowEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(ExplosiveArrowEntity entity) {
        return new Identifier("textures/entity/projectiles/arrow.png");
    }

}
