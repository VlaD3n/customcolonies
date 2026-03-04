package com.minecolonies.core.client.render.gecko;

import com.minecolonies.api.entity.citizen.AbstractEntityCitizen;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class GeckoCitizenModel extends GeoModel<AbstractEntityCitizen>
{
    @Override
    public ResourceLocation getModelResource(final AbstractEntityCitizen animatable)
    {
        ExternalCitizenAssets.reloadIfRequired();
        return ExternalCitizenAssets.getModelLocation();
    }

    @Override
    public ResourceLocation getTextureResource(final AbstractEntityCitizen animatable)
    {
        ExternalCitizenAssets.reloadIfRequired();
        return ExternalCitizenAssets.getTextureLocation(animatable);
    }

    @Override
    public ResourceLocation getAnimationResource(final AbstractEntityCitizen animatable)
    {
        ExternalCitizenAssets.reloadIfRequired();
        return ExternalCitizenAssets.getAnimationLocation();
    }
}
