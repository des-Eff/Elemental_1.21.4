package net.deseff.elemental.registries.entities;

import java.util.List;
import java.util.Set;

import com.mojang.datafixers.util.Pair;

import com.google.common.collect.*;

import net.deseff.elemental.ai.BrineDashTask;
import net.deseff.elemental.ai.ModMemoryModules;
import net.deseff.elemental.ai.ModSensorTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.*;
import net.minecraft.entity.ai.brain.*;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;


public class BrineBrain { //TODO: attack behavior

    static final List<SensorType<? extends Sensor<? super BrineEntity>>> SENSORS = ImmutableList.of(
            SensorType.NEAREST_LIVING_ENTITIES, SensorType.HURT_BY, SensorType.NEAREST_PLAYERS, ModSensorTypes.BRINE_ATTACK_ENTITY_SENSOR
    );
    static final List<MemoryModuleType<?>> MEMORY_MODULES = ImmutableList.of(
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleType.VISIBLE_MOBS,
            MemoryModuleType.NEAREST_ATTACKABLE,
            MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
            MemoryModuleType.ATTACK_TARGET,
            MemoryModuleType.WALK_TARGET,
            ModMemoryModules.BRINE_DASH,
            ModMemoryModules.BRINE_DASH_CHARGING,
            ModMemoryModules.BRINE_DASH_RECOVER,
            ModMemoryModules.BRINE_DASH_COOLDOWN,
            MemoryModuleType.HURT_BY,
            MemoryModuleType.HURT_BY_ENTITY,
            MemoryModuleType.PATH
    );
    private static final int TIME_BEFORE_FORGETTING_TARGET = 100;

    protected static Brain<?> create(BrineEntity brine, Brain<BrineEntity> brain) {
        addCoreTasks(brain);
        addIdleTasks(brain);
        addFightTasks(brine, brain);
        addSwimTasks(brain);
        brain.setCoreActivities(Set.of(Activity.CORE));
        brain.setDefaultActivity(Activity.FIGHT);
        brain.resetPossibleActivities();
        return brain;
    }

    private static void addCoreTasks(Brain<BrineEntity> brain) {
        brain.setTaskList(Activity.CORE, 0, ImmutableList.of(
                new UpdateLookControlTask(45,90),
                new MoveToTargetTask()
        ));
    }
    private static void addIdleTasks(Brain<BrineEntity> brain) {
        brain.setTaskList(Activity.IDLE, ImmutableList.of(
                Pair.of(0, UpdateAttackTargetTask.create((world, brine) ->
                        brine.getBrain().getOptionalRegisteredMemory(MemoryModuleType.NEAREST_ATTACKABLE)
                )),
                Pair.of(1, UpdateAttackTargetTask.create((world, brine) -> brine.getHurtBy())),
                Pair.of(2, SeekWaterTask.create(16, 1.0F)),
                Pair.of(3,
                        new RandomTask<>(
                                ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT),
                                ImmutableList.of(
                                        Pair.of(StrollTask.create(1.0F), 2),
                                        Pair.of(new WaitTask(20,100), 1),
                                        Pair.of(TaskTriggerer.predicate(Entity::isOnGround), 2)
                                )
                        )
                )
        ));

    }
    //TODO: make it approach on land but not underwater
    private static void addFightTasks(BrineEntity brine, Brain<BrineEntity> brain) {
        brain.setTaskList(
                Activity.FIGHT,
                ImmutableList.of(
                        Pair.of(0, new BrineDashTask())
                ),
                ImmutableSet.of(
                        Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_PRESENT)
                        //, Pair.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT)
                )
        );
    }

     private static void addSwimTasks(Brain<BrineEntity> brain) {
    		brain.setTaskList(
    			Activity.SWIM,
    			ImmutableList.of(
                        Pair.of(0, UpdateAttackTargetTask.create((world, brine) -> brine.getBrain().getOptionalRegisteredMemory(MemoryModuleType.NEAREST_ATTACKABLE))),
                        Pair.of(1, UpdateAttackTargetTask.create((world, brine) -> brine.getHurtBy())),
                        Pair.of(
    					2,
    					new CompositeTask<>(
    						ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT),
    						ImmutableSet.of(),
    						CompositeTask.Order.ORDERED,
    						CompositeTask.RunMode.TRY_ALL,
    						ImmutableList.of(
    							Pair.of(StrollTask.createDynamicRadius(0.75F), 1),
    							Pair.of(StrollTask.create(0.6F, true), 1),
    							Pair.of(TaskTriggerer.predicate(Entity::isInsideWaterOrBubbleColumn), 5)
    						)
    					)
    				)
    			)
    		);
    	}

    static void updateActivities(BrineEntity brine) {
        LivingEntity attackTarget = brine.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);
        if (attackTarget != null && (attackTarget.isSpectator() || (attackTarget instanceof PlayerEntity player && player.isCreative()))) {
            brine.getBrain().forget(MemoryModuleType.ATTACK_TARGET);
        }

        brine.getBrain().resetPossibleActivities(ImmutableList.of(Activity.FIGHT, Activity.IDLE, Activity.SWIM));
    }


}
