package net.midget807.afmweapons.rendering;

import net.midget807.afmweapons.entity.afmw.EchoArrowEntity;
import net.midget807.afmweapons.entity.afmw.MagicArrowEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.util.Identifier;

public class EchoArrowEntityRenderer extends ProjectileEntityRenderer<EchoArrowEntity> {
    private static final Identifier DEFAULT_TEXTURE = new Identifier("textures/entity/projectiles/arrow.png");
    private static final Identifier PULSING_TEXTURE = new Identifier("textures/entity/projectiles/spectral_arrow.png");
    public EchoArrowEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(EchoArrowEntity entity) {
        return entity.isPulsing ? PULSING_TEXTURE : DEFAULT_TEXTURE;
    }
}
