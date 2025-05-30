package net.midget807.afmweapons.entity.afmw.client;

import net.midget807.afmweapons.entity.afmw.FrostArrowEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.util.Identifier;

public class FrostArrowEntityRenderer extends ProjectileEntityRenderer<FrostArrowEntity> {
    public FrostArrowEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(FrostArrowEntity entity) {
        return new Identifier("afmweapons:textures/entity/projectiles/frost_arrow.png");
    }
}
