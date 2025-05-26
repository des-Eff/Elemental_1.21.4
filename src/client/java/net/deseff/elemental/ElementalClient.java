package net.deseff.elemental;

import net.deseff.elemental.entity.BrineModel;
import net.deseff.elemental.entity.BrineRenderer;
import net.deseff.elemental.entity.ModEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class ElementalClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {

		EntityModelLayerRegistry.registerModelLayer(BrineModel.BRINE, BrineModel::getTexturedModelData);
		EntityRendererRegistry.register(ModEntities.BRINE, BrineRenderer::new);
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
	}
}