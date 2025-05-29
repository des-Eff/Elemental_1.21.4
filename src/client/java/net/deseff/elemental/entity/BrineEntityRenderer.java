package net.deseff.elemental.entity;

import net.deseff.elemental.registries.entities.BrineEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BrineEntityRenderer extends GeoEntityRenderer<BrineEntity> {
    public BrineEntityRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new BrineEntityModel());
    }

//    @Override
//    public void render(BrineEntityRenderState entityRenderState, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
//        super.render(entityRenderState, poseStack, bufferSource, packedLight);
//    }
}