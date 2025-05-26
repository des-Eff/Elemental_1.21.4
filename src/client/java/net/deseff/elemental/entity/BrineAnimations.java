package net.deseff.elemental.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.animation.*;
import net.minecraft.client.render.entity.animation.Animation.Builder;
import net.minecraft.client.render.entity.animation.Transformation.Targets;
import net.minecraft.client.render.entity.animation.Transformation.Interpolations;

@Environment(EnvType.CLIENT)
public class BrineAnimations {
    public static final Animation IDLING;
    public static final Animation SWIMMING;

    static {
        IDLING = Builder.create(2.0F).looping()
                .addBoneAnimation("smallRods", new Transformation(Transformation.Targets.ROTATE,
                        new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(2.0F, AnimationHelper.createRotationalVector(0.0F, 1080.0F, 0.0F), Transformation.Interpolations.LINEAR)))
                .addBoneAnimation("headGroup", new Transformation(Targets.TRANSLATE, new Keyframe[]{
                        new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Interpolations.CUBIC),
                        new Keyframe(1.0F, AnimationHelper.createTranslationalVector(0.0F, 1.0F, 0.0F), Interpolations.CUBIC),
                        new Keyframe(2.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Interpolations.CUBIC)}))
                .addBoneAnimation("longRod", new Transformation(Targets.ROTATE, new Keyframe[]{
                        new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
                        new Keyframe(2.0F, AnimationHelper.createRotationalVector(0.0F, -360.0F, 0.0F), Interpolations.LINEAR)}))
                .addBoneAnimation("longRod", new Transformation(Targets.TRANSLATE, new Keyframe[]{
                        new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Interpolations.CUBIC),
                        new Keyframe(1.0F, AnimationHelper.createTranslationalVector(0.0F, 1.0F, 0.0F), Interpolations.CUBIC),
                        new Keyframe(2.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Interpolations.CUBIC)}))
                .build();

        SWIMMING = Builder.create(1.5F).looping()
                .addBoneAnimation("root", new Transformation(Targets.TRANSLATE, new Keyframe[]{
                        new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, -17.0F, 0.0F), Interpolations.LINEAR)}))
                .addBoneAnimation("smallRods", new Transformation(Targets.ROTATE, new Keyframe[]{
                        new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
                        new Keyframe(1.5F, AnimationHelper.createRotationalVector(0.0F, 720.0F, 0.0F), Interpolations.LINEAR)}))
                .addBoneAnimation("longRod", new Transformation(Targets.ROTATE, new Keyframe[]{
                        new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
                        new Keyframe(1.5F, AnimationHelper.createRotationalVector(0.0F, -720.0F, 0.0F), Interpolations.LINEAR)}))
                .addBoneAnimation("rods", new Transformation(Targets.ROTATE, new Keyframe[]{
                        new Keyframe(0.0F, AnimationHelper.createRotationalVector(90.0F, 0.0F, 0.0F), Interpolations.LINEAR)}))
                .build();
    }
}