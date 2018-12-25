package com.ferreusveritas.cathedral.models;

import java.util.function.Function;

import javax.annotation.Nonnull;

import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;

public class ModelLoaderKeyed implements ICustomModelLoader {
	
	private final String key;
	private final Function<ResourceLocation, IModel> loader;
	
	public ModelLoaderKeyed(@Nonnull String key, @Nonnull Function<ResourceLocation, IModel> loader ) {
		this.key = key;
		this.loader = loader;
	}
	
	@Override
	public void onResourceManagerReload(IResourceManager resourceManager) {	}

	@Override
	public boolean accepts(ResourceLocation modelLocation) {
		return modelLocation instanceof ExtendedModelResourceLocation && ((ExtendedModelResourceLocation) modelLocation).getKey().equals(key);
	}
	
	@Override
	public IModel loadModel(ResourceLocation modelLocation) throws Exception {
		return loader.apply(modelLocation);
	}
	
}
