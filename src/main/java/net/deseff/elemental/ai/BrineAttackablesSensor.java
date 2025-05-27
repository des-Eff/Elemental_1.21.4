package net.deseff.elemental.ai;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import net.deseff.elemental.registries.entities.BrineEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.NearestLivingEntitiesSensor;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class BrineAttackablesSensor extends NearestLivingEntitiesSensor<BrineEntity> {
    @Override
    public Set<MemoryModuleType<?>> getOutputMemoryModules() {
        return ImmutableSet.copyOf(Iterables.concat(super.getOutputMemoryModules(), List.of(MemoryModuleType.NEAREST_ATTACKABLE)));
    }

    	protected void sense(ServerWorld serverWorld, BrineEntity brineEntity) {
    		super.sense(serverWorld, brineEntity);
    		brineEntity.getBrain()
    			.getOptionalRegisteredMemory(MemoryModuleType.MOBS)
    			.stream()
    			.flatMap(Collection::stream)
    			.filter(EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR)
    			.filter(target -> Sensor.testAttackableTargetPredicate(serverWorld, brineEntity, target))
    			.findFirst()
    			.ifPresentOrElse(
    				target -> brineEntity.getBrain().remember(MemoryModuleType.NEAREST_ATTACKABLE, target),
    				() -> brineEntity.getBrain().forget(MemoryModuleType.NEAREST_ATTACKABLE)
    			);
    	}
}
