package net.deseff.elemental.ai;

import com.mojang.serialization.Codec;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Unit;

import java.util.Optional;

import static net.deseff.elemental.Elemental.MOD_ID;

public class ModMemoryModules {
    public static final MemoryModuleType<Unit> BRINE_DASH = register("brine_dash", Unit.CODEC);
    public static final MemoryModuleType<Unit> BRINE_DASH_CHARGING = register("brine_dash_charging", Unit.CODEC);
    public static final MemoryModuleType<Unit> BRINE_DASH_RECOVER = register("brine_dash_recover", Unit.CODEC);
    public static final MemoryModuleType<Unit> BRINE_DASH_COOLDOWN = register("brine_dash_cooldown", Unit.CODEC);

    private static <U> MemoryModuleType<U> register(String id, Codec<U> codec) {
        return Registry.register(Registries.MEMORY_MODULE_TYPE, Identifier.of(id), new MemoryModuleType<>(Optional.of(codec)));
    }

    public static void init() {

    }
}