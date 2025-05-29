package net.deseff.elemental;

import net.deseff.elemental.entity.BrineEntityModel;
import net.deseff.elemental.entity.BrineEntityRenderer;
import net.deseff.elemental.entity.ModEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class ElementalClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {

		EntityRendererRegistry.register(ModEntities.BRINE, BrineEntityRenderer::new);
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
	}
}