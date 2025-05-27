package net.deseff.elemental.item;

import net.deseff.elemental.Elemental;
import net.deseff.elemental.entity.ModEntities;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Function;

//TODO: for all items, add models, textures, translations, and loot tables (if necessary).

public class ModItems {
    public static Item register(String id, Function<Item.Settings, Item> itemFactory, Item.Settings settings) {
        // Create the item key.
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Elemental.MOD_ID, id));

        // Create the item instance.
        Item item = itemFactory.apply(settings.registryKey(itemKey));

        // Register the item.
        Registry.register(Registries.ITEM, itemKey, item);

        return item;

    }
    public static final Item BRINE_ROD = register("brine_rod", Item::new, new Item.Settings());

    public static final Item WATER_CHARGE = register("water_charge", WaterChargeItem::new, new Item.Settings().useCooldown(1.0F));

    public static final Item BRINE_SPAWN_EGG = register("brine_spawn_egg", (settings) -> new SpawnEggItem(ModEntities.BRINE, settings), new Item.Settings());

        public static void initialize() {
            // Get the event for modifying entries in the group.
    		// And register an event handler that adds our item to its group.
    		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS)
    		.register((itemGroup) -> itemGroup.add(ModItems.BRINE_ROD));

            ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS)
                    .register((itemGroup) -> itemGroup.add(ModItems.WATER_CHARGE));

            ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS)
                    .register((itemGroup) -> itemGroup.add(ModItems.BRINE_SPAWN_EGG));
        }
}