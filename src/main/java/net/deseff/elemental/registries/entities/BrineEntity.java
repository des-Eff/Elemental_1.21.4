package net.deseff.elemental.registries.entities;

import com.mojang.serialization.Dynamic;
import net.deseff.elemental.entity.ModEntities;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.control.AquaticMoveControl;
import net.minecraft.entity.ai.pathing.AmphibiousSwimNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.profiler.Profilers;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;


//TODO: get attack behavior
public class BrineEntity extends HostileEntity {
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState swimAnimationState = new AnimationState();
    private static final EntityDimensions SWIMMING_BASE_DIMENSIONS = ModEntities.BRINE.getDimensions().scaled(1.0F,0.5F);
    private boolean wasInWaterLastTick = false;


    public BrineEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
        this.moveControl = new BrineEntity.BrineMoveControl(this);
        this.experiencePoints = 10;
    }

    @Override
    protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
        return BrineBrain.create(this, this.createBrainProfile().deserialize(dynamic));
    }

    @Override
    public Brain<BrineEntity> getBrain() {
        return (Brain<BrineEntity>) super.getBrain();
    }

    @Override
    protected Brain.Profile<BrineEntity> createBrainProfile() {
        return Brain.createProfile(BrineBrain.MEMORY_MODULES, BrineBrain.SENSORS);
    }

    public static DefaultAttributeContainer.Builder createBrineAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.ATTACK_DAMAGE, 6.0F)
                .add(EntityAttributes.MOVEMENT_SPEED, 0.35F)
                .add(EntityAttributes.MAX_HEALTH, 30.0f);
    }


    public void tick() {
        super.tick();

        boolean inWater = this.isInsideWaterOrBubbleColumn();

        if (inWater != wasInWaterLastTick) {
            this.calculateDimensions();
            wasInWaterLastTick = inWater;
            if (inWater) {
                this.swimAnimationState.startIfNotRunning(this.age);
            } else {
                this.swimAnimationState.stop();
                this.idleAnimationState.startIfNotRunning(this.age);
            }
        }
        if (inWater) {
            Vec3d velocity = this.getVelocity();
            double dy = velocity.y;


            this.setPitch((float)(MathHelper.clamp(Math.toDegrees(Math.atan(dy)), -90.0, 90.0)));

        }
    }



    public Optional<LivingEntity> getHurtBy() {
        return this.getBrain()
                .getOptionalRegisteredMemory(MemoryModuleType.HURT_BY)
                .map(DamageSource::getAttacker)
                .filter(attacker -> attacker instanceof LivingEntity)
                .map(livingAttacker -> (LivingEntity)livingAttacker);
    }

    @Override
    public boolean canTarget(EntityType<?> type) {
        return type == EntityType.PLAYER || type == EntityType.IRON_GOLEM || type == EntityType.AXOLOTL;
    }

    @Nullable
    @Override
    public LivingEntity getTarget() {
        return this.getTargetInBrain();
    }

    @Override
    protected void mobTick(ServerWorld world) {
        Profiler profiler = Profilers.get();
        profiler.push("brineBrain");
        this.getBrain().tick(world, this);
        profiler.swap("brineActivityUpdate");
        BrineBrain.updateActivities(this);
        profiler.pop();
        super.mobTick(world);
    }

//    @Override
//    protected void sendAiDebugData() {
//        super.sendAiDebugData();
//        DebugInfoSender.sendBrainDebugData(this);
//        DebugInfoSender.sendBrineDebugData(this);
//    }



    public void tickMovement() {
        if (this.isAlive()) {
            this.setAir(300);
        }

        if (this.getWorld().isClient && this.isInsideWaterOrBubbleColumn()) {

                this.getWorld()
                        .addParticle(
                                ParticleTypes.BUBBLE,
                                this.getParticleX(0.5),
                                this.getRandomBodyY(),
                                this.getParticleZ(0.5),
                                0.0,
                                0.0,
                                0.0
                        );
        }

        super.tickMovement();
    }

    static class BrineMoveControl extends AquaticMoveControl {
        private final BrineEntity brine;

        public BrineMoveControl(BrineEntity brine) {
            super(brine, 85, 10, 1.0F, 0.75F, false);
            this.brine = brine;
        }

        @Override
        public void tick() {
            super.tick();
        }
    }

    @Override
    public boolean isPushedByFluids() {
        return false;
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        return new AmphibiousSwimNavigation(this, world);
    }


    public void travel(Vec3d movementInput) {
        if (this.isLogicalSideForUpdatingMovement() && this.isTouchingWater()) {
            this.updateVelocity(this.getMovementSpeed(), movementInput);
            this.move(MovementType.SELF, this.getVelocity());
            this.setVelocity(this.getVelocity().multiply(0.9));
        } else {
            super.travel(movementInput);
        }
    }

    @Override
    public EntityDimensions getBaseDimensions(EntityPose pose) {
        return this.isInsideWaterOrBubbleColumn() ? SWIMMING_BASE_DIMENSIONS : super.getBaseDimensions(pose);
    }


}