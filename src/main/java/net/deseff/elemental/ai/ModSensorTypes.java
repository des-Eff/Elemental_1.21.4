package net.deseff.elemental.ai;

import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

public class ModSensorTypes {
    public static final SensorType<BrineAttackablesSensor> BRINE_ATTACK_ENTITY_SENSOR = register("brine_attack_entity_sensor", BrineAttackablesSensor::new);

    private static <U extends Sensor<?>> SensorType<U> register(String id, Supplier<U> factory) {
        return Registry.register(Registries.SENSOR_TYPE, Identifier.of(id), new SensorType<>(factory));
    }

    public static void init() {
    }

}