package net.deseff.elemental.entity;

import net.deseff.elemental.Elemental;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class BrineModel extends EntityModel<BrineEntityRenderState> {
    public static final EntityModelLayer BRINE = new EntityModelLayer(Identifier.of(Elemental.MOD_ID, "brine"), "main");

    private final ModelPart root;
    private final ModelPart headGroup;
    private final ModelPart rods;
    private final ModelPart longRod;
    private final ModelPart smallRods;

    public BrineModel(ModelPart modelPart) {
        super(modelPart);
        this.root = modelPart.getChild("root");
        this.headGroup = this.root.getChild("headGroup");
        this.rods = this.root.getChild("rods");
        this.longRod = this.rods.getChild("longRod");
        this.smallRods = this.rods.getChild("smallRods");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        // Root folder
        ModelPartData modelPartData2 = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        //"headGroup" folder + cube
        ModelPartData modelPartData3 = modelPartData2.addChild(
                "headGroup",
                ModelPartBuilder.create().uv(0, 16).cuboid(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F), ModelTransform.pivot(0.0F, -21.0F, 0.0F)
        );

        //"rods" folder
        ModelPartData modelPartData4 = modelPartData2.addChild("rods", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -21.0F, 0.0F));

        //"longRod" folder + cube
        ModelPartData modelPartData5 = modelPartData4.addChild(
                "longRod",
                ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -8.0F, -1.0F, 2.0F, 16.0F, 2.0F), ModelTransform.pivot(0.0F, 13.0F, 0.0F)
        );

        //"smallRods" folder
        ModelPartData modelPartData6 = modelPartData4.addChild("smallRods", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 21.0F, 0.0F));
        //... + cubes
        modelPartData6.addChild(
                "rod1",
                ModelPartBuilder.create().uv(8, 0).cuboid(-1.0F, -4.0F, -1.0F, 2.0F, 8.0F, 2.0F), ModelTransform.of(-5.0F, -11.0F, 0.0F, 0.0F, 0.0F, 0.3927F)
        );
        modelPartData6.addChild(
                "rod2",
                ModelPartBuilder.create().uv(8, 0).cuboid(-1.0F, -4.0F, -1.0F, 2.0F, 8.0F, 2.0F), ModelTransform.of(0.0F, -11.0F, -5.0F, -0.3927F, 0.0F, 0.0F)
        );
        modelPartData6.addChild(
                "rod3",
                ModelPartBuilder.create().uv(8, 0).cuboid(-1.0F, -4.0F, -1.0F, 2.0F, 8.0F, 2.0F), ModelTransform.of(4.3054F, -10.9392F, 0.0F, 0.0F, 0.0F, -0.3927F)
        );
        modelPartData6.addChild(
                "rod4",
                ModelPartBuilder.create().uv(8, 0).cuboid(-1.0F, -4.0F, -1.0F, 2.0F, 8.0F, 2.0F), ModelTransform.of(0.0F, -11.0F, 5.0F, 0.3927F, 0.0F, 0.0F)
        );
        return TexturedModelData.of(modelData, 32, 32);
    }
    public void setAngles(BrineEntityRenderState brineEntityRenderState) {
        super.setAngles(brineEntityRenderState);

        this.animate(brineEntityRenderState.idleAnimationState, BrineAnimations.IDLING, brineEntityRenderState.age);
        this.animate(brineEntityRenderState.swimAnimationState, BrineAnimations.SWIMMING, brineEntityRenderState.age);
    }
}