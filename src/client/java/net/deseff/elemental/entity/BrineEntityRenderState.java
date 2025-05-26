package net.deseff.elemental.entity;


import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.AnimationState;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class BrineEntityRenderState extends LivingEntityRenderState {
    private static final Identifier DEFAULT_TEXTURE = Identifier.of("textures/entity/brine/brine.png");
    public boolean insideWaterOrBubbleColumn;
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState swimAnimationState = new AnimationState();
    public Identifier texture = DEFAULT_TEXTURE;
}