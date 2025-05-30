package net.midget807.afmweapons.entity.afmw.client;

import net.midget807.afmweapons.entity.afmw.RicochetArrowEntity;
import net.midget807.afmweapons.entity.afmw.WarpArrowEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.util.Identifier;

public class RicochetArrowEntityRenderer extends ProjectileEntityRenderer<RicochetArrowEntity> {
    public RicochetArrowEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(RicochetArrowEntity entity) {
        return new Identifier("afmweapons:textures/entity/projectiles/ricochet_arrow.png");
    }
}
