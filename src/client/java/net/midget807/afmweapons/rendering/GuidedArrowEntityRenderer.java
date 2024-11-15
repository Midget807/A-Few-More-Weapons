package net.midget807.afmweapons.rendering;

import net.midget807.afmweapons.entity.afmw.GuidedArrowEntity;
import net.midget807.afmweapons.entity.afmw.WarpArrowEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.util.Identifier;

public class GuidedArrowEntityRenderer extends ProjectileEntityRenderer<GuidedArrowEntity> {
    public GuidedArrowEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(GuidedArrowEntity entity) {
        return new Identifier("afmweapons:textures/entity/projectiles/guided_arrow.png");
    }
}
