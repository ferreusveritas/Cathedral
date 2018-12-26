package com.ferreusveritas.cathedral.models;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.ferreusveritas.cathedral.features.cathedral.EnumMaterial;
import com.google.common.collect.ImmutableMap;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.model.ModelRotation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.IModelState;

public class ModelBlockRailing implements IModel {
	
	private IModel modelPost;
	private IModel modelCap;
	private IModel modelSide;
	private EnumMaterial material;
	
	public ModelBlockRailing(ResourceLocation modelLocation) {
		
		try {
			modelPost = ModelLoaderRegistry.getModel(new ResourceLocation(modelLocation.getResourceDomain(), "block/cathedral/rail_post"));
			modelCap  = ModelLoaderRegistry.getModel(new ResourceLocation(modelLocation.getResourceDomain(), "block/cathedral/rail_cap"));
			modelSide = ModelLoaderRegistry.getModel(new ResourceLocation(modelLocation.getResourceDomain(), "block/cathedral/rail_side"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		if(modelLocation instanceof ModelResourceLocation) {
			ModelResourceLocation mrl = (ModelResourceLocation) modelLocation;
			material = EnumMaterial.byName(mrl.getVariant());
		}
		
	}
	
	@Override
	public Collection<ResourceLocation> getTextures() {
		return getTextureMap().values();
	}
	
	private static Map<String, ResourceLocation> generic(EnumMaterial material, ResourceLocation defaultTexture) {
		Map<String, ResourceLocation> stuff = new HashMap<>();
		stuff.put("default", defaultTexture);
		return stuff;
	}
	
	private Map<String, ResourceLocation> getTextureMap() {
		return generic(material, material.getBevelledTexture());
	}
	
	@Override
	public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
		
		Map<String, String> stuff = getTextureMap().entrySet().stream().collect(Collectors.toMap( Map.Entry::getKey, e -> e.getValue().toString()));
		
		ImmutableMap<String, String> textures = ImmutableMap.copyOf(stuff);
		
		IBakedModel bakedPost = modelPost.retexture(textures).bake(state, format, bakedTextureGetter);
		IBakedModel bakedCap  = modelCap .retexture(textures).bake(state, format, bakedTextureGetter);
		
		IBakedModel bakedSides[] = new IBakedModel[4];
		
		for(EnumFacing dir : EnumFacing.HORIZONTALS) {
			int i = dir.getHorizontalIndex();
			bakedSides[i] = modelSide.uvlock(true).retexture(textures).bake(ModelRotation.getModelRotation(0, i * 90), format, bakedTextureGetter);
		}
		
		TextureAtlasSprite particleSprite = bakedTextureGetter.apply(material.getFlatTexture());
		
		return new BakedModelBlockRailing(bakedPost, bakedCap, bakedSides, particleSprite);
	}
	
}
