package net.midget807.afmweapons.entity.afmw.client;

import net.midget807.afmweapons.entity.afmw.MagicArrowEntity;
import net.midget807.afmweapons.entity.afmw.WarpArrowEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.util.Identifier;

public class MagicArrowEntityRenderer extends ProjectileEntityRenderer<MagicArrowEntity> {
    public MagicArrowEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(MagicArrowEntity entity) {
        return new Identifier("afmweapons:textures/entity/projectiles/magic_arrow.png");
    }
}
