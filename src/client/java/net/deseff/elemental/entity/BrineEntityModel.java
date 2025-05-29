package net.deseff.elemental.entity;

import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib.animatable.GeoAnimatable;

import net.deseff.elemental.Elemental;
import net.deseff.elemental.registries.entities.BrineEntity;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class BrineEntityModel extends DefaultedEntityGeoModel<BrineEntity> {
    public BrineEntityModel() {
        super(Identifier.of(Elemental.MOD_ID, "brine"));
    }

    @Override
    //TODO: make it so the brine turns to its targets the whole time, rather than just during the dash
    public void setCustomAnimations(BrineEntity animatable, long instanceID, AnimationState<BrineEntity> animationState) {
        super.setCustomAnimations(animatable, instanceID, animationState);

        GeoBone body = getAnimationProcessor().getBone("body");

        if (body != null && animatable.isInsideWaterOrBubbleColumn()) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

            body.setRotX(entityData.headPitch() * -MathHelper.RADIANS_PER_DEGREE);
        }
    }
}