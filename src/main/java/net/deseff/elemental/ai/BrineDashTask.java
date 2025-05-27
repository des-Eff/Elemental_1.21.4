package net.deseff.elemental.ai;

import com.google.common.collect.ImmutableMap;
import net.deseff.elemental.registries.entities.BrineEntity;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Unit;

public class BrineDashTask extends MultiTickTask<BrineEntity> {

    //Variables here
    private static final int MAX_SQUARED_RANGE = 256;
    private static final int DASH_CHARGING_EXPIRY = Math.round(15.0F);
    private static final int RECOVER_EXPIRY = Math.round(4.0F);
    public static final int DASH_COOLDOWN_EXPIRY = Math.round(10.0F);


    public BrineDashTask() {
        super(ImmutableMap.of(
                MemoryModuleType.ATTACK_TARGET,
                MemoryModuleState.VALUE_PRESENT,
                ModMemoryModules.BRINE_DASH_COOLDOWN,
                MemoryModuleState.VALUE_ABSENT,
                ModMemoryModules.BRINE_DASH_CHARGING,
                MemoryModuleState.VALUE_ABSENT,
                ModMemoryModules.BRINE_DASH_RECOVER,
                MemoryModuleState.VALUE_ABSENT,
                ModMemoryModules.BRINE_DASH,
                MemoryModuleState.VALUE_PRESENT,
                MemoryModuleType.WALK_TARGET,
                MemoryModuleState.VALUE_ABSENT
                ),
                DASH_CHARGING_EXPIRY + 1 + RECOVER_EXPIRY);
    }
    protected boolean shouldRun(ServerWorld serverWorld, BrineEntity brineEntity) {
        return brineEntity.getPose() != EntityPose.SWIMMING
                ? false
                : (Boolean) brineEntity.getBrain()
                .getOptionalRegisteredMemory(MemoryModuleType.ATTACK_TARGET)
                .map(target -> isTargetWithinRange(brineEntity, target))
                .map(withinRange -> {
                    if (!withinRange) {
                        brineEntity.getBrain().forget(ModMemoryModules.BRINE_DASH);
                    }
                    return withinRange;
                })
                .orElse(false);
    }

    protected boolean shouldKeepRunning(ServerWorld serverWorld, BrineEntity brineEntity, long l) {
        return brineEntity.getBrain().hasMemoryModule(MemoryModuleType.ATTACK_TARGET) && brineEntity.getBrain().hasMemoryModule(ModMemoryModules.BRINE_DASH);
    }
    protected void run(ServerWorld serverWorld, BrineEntity brineEntity, long l) {
        brineEntity.getBrain().getOptionalRegisteredMemory(MemoryModuleType.ATTACK_TARGET).ifPresent(target -> brineEntity.setPose(EntityPose.SHOOTING));
        brineEntity.getBrain().remember(ModMemoryModules.BRINE_DASH_CHARGING, Unit.INSTANCE, DASH_CHARGING_EXPIRY);
//		brineEntity.playSound(SoundEvents.ENTITY_BRINE_CHARGE, 1.0F, 1.0F);
    }

	protected void finishRunning(ServerWorld serverWorld, BrineEntity brineEntity, long l) {
		if (brineEntity.getPose() == EntityPose.SHOOTING) {
			brineEntity.setPose(EntityPose.STANDING);
		}
		brineEntity.getBrain().remember(ModMemoryModules.BRINE_DASH_COOLDOWN, Unit.INSTANCE, DASH_COOLDOWN_EXPIRY);
		brineEntity.getBrain().forget(ModMemoryModules.BRINE_DASH);
	}

    protected void keepRunning(ServerWorld serverWorld, BrineEntity brineEntity, long l) {
        Brain<BrineEntity> brain = brineEntity.getBrain();
        LivingEntity livingEntity = (LivingEntity)brain.getOptionalRegisteredMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);
        if (livingEntity != null) {
            brineEntity.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, livingEntity.getPos());
            if (!brain.getOptionalRegisteredMemory(ModMemoryModules.BRINE_DASH_CHARGING).isPresent()
                && !brain.getOptionalRegisteredMemory(ModMemoryModules.BRINE_DASH_RECOVER).isPresent()) {
                brain.remember(ModMemoryModules.BRINE_DASH_RECOVER, Unit.INSTANCE, RECOVER_EXPIRY);

//				double d = livingEntity.getX() - breezeEntity.getX();
//				double e = livingEntity.getBodyY(livingEntity.hasVehicle() ? 0.8 : 0.3) - breezeEntity.getChargeY();
//				double f = livingEntity.getZ() - breezeEntity.getZ();
//				ProjectileEntity.spawnWithVelocity(
//					new BreezeWindChargeEntity(breezeEntity, serverWorld), serverWorld, ItemStack.EMPTY, d, e, f, 0.7F, 5 - serverWorld.getDifficulty().getId() * 4
//				);

//				brineEntity.playSound(SoundEvents.ENTITY_BRINE_DASH, 1.5F, 1.0F);

            }
        }
    }

    private static boolean isTargetWithinRange(BrineEntity brine, LivingEntity target) {
        double d = brine.getPos().squaredDistanceTo(target.getPos());
        return d < MAX_SQUARED_RANGE;
    }
}