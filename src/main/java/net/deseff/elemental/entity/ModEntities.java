package net.deseff.elemental.entity;

import net.deseff.elemental.Elemental;
import net.deseff.elemental.registries.entities.BrineEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final RegistryKey<EntityType<?>> BRINE_ENTITY_KEY = RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(Elemental.MOD_ID, "brine"));
    public static final EntityType<BrineEntity> BRINE = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(Elemental.MOD_ID, "brine"),
            EntityType.Builder.create(BrineEntity::new, SpawnGroup.MONSTER)
                    .dimensions(0.9F, 1.75F)
                    .eyeHeight(1.375F)
                    .build(BRINE_ENTITY_KEY)
    );

    public static void registerModEntities() {
        Elemental.LOGGER.info("Registering " + Elemental.MOD_ID + " ModEntities");
    }
}