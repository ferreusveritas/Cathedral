package com.ferreusveritas.cathedral.features.cathedral;

import com.ferreusveritas.cathedral.CathedralMod;
import com.ferreusveritas.cathedral.models.BakedModelBlockDeckPrism;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModelBakeEventListener {
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onModelBakeEvent(ModelBakeEvent event) {
		
		Block block = CathedralMod.cathedral.deckPrism;
		IBakedModel model = event.getModelRegistry().getObject(new ModelResourceLocation(block.getRegistryName(), "normal"));
		if (model instanceof IBakedModel) {
			IBakedModel prismModel = (IBakedModel) model;
			BakedModelBlockDeckPrism newPrismModel = new BakedModelBlockDeckPrism(prismModel);
			event.getModelRegistry().putObject(new ModelResourceLocation(block.getRegistryName(), "normal"), newPrismModel);
		}
		
	}
	
}