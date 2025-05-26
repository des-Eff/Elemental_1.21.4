package net.deseff.elemental.entity;

import net.deseff.elemental.Elemental;
import net.deseff.elemental.registries.entities.BrineEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.state.BreezeEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class BrineRenderer extends MobEntityRenderer<BrineEntity, BrineEntityRenderState, BrineModel>{
    private static final Identifier TEXTURE = Identifier.of(Elemental.MOD_ID, "textures/entity/brine/brine.png");

    public BrineRenderer(EntityRendererFactory.Context context) {
        super(context, new BrineModel(context.getPart(BrineModel.BRINE)), 0.5F);
        // f is the shadow radius
    }

// Might need this render() method, but Blaze doesn't use it so maybe not
    @Override
    public void render(BrineEntityRenderState brineEntityRenderState, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        BrineModel brineModel = this.getModel();
        super.render(brineEntityRenderState, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public Identifier getTexture(BrineEntityRenderState state) { return TEXTURE; }

    @Override
    public BrineEntityRenderState createRenderState() { return new BrineEntityRenderState(); }

    public void updateRenderState(BrineEntity brineEntity, BrineEntityRenderState brineEntityRenderState, float f) {
        super.updateRenderState(brineEntity, brineEntityRenderState, f);
        brineEntityRenderState.idleAnimationState.copyFrom(brineEntity.idleAnimationState);
        brineEntityRenderState.swimAnimationState.copyFrom(brineEntity.swimAnimationState);
    }
}
