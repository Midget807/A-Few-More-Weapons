package net.midget807.afmweapons.entity.afmw.client;

import net.midget807.afmweapons.entity.afmw.SeekingArrowEntity;
import net.midget807.afmweapons.entity.afmw.WarpArrowEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.util.Identifier;

public class SeekingArrowEntityRenderer extends ProjectileEntityRenderer<SeekingArrowEntity> {
    public SeekingArrowEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(SeekingArrowEntity entity) {
        return new Identifier("afmweapons:textures/entity/projectiles/warp_arrow.png");//todo change
    }
}
