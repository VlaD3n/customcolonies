package com.minecolonies.core.client.render.gecko;

import com.minecolonies.api.entity.citizen.AbstractEntityCitizen;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class GeckoCitizenRenderer extends GeoEntityRenderer<AbstractEntityCitizen>
{
    public GeckoCitizenRenderer(final EntityRendererProvider.Context context)
    {
        super(context, new GeckoCitizenModel());
        this.shadowRadius = 0.5F;
    }
}
