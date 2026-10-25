package com.synapse.squidmilk.entities;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class SquidBombRenderer extends EntityRenderer<SquidBombEntity> {
    public SquidBombRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public ResourceLocation getTextureLocation(@NotNull SquidBombEntity pEntity) {
        return null;
    }
}
