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

public class ModelBlockPillar implements IModel {
	
	private IModel modelShaft;
	private IModel modelBase;
	private IModel modelHead;
	private IModel modelJoin;
	private IModel modelPane;
	private IModel modelRail;

	private EnumMaterial material;
	
	public ModelBlockPillar(ResourceLocation modelLocation) {
		
		try {
			modelShaft = ModelLoaderRegistry.getModel(new ResourceLocation(modelLocation.getResourceDomain(), "block/cathedral/pillar_shaft"));
			modelBase  = ModelLoaderRegistry.getModel(new ResourceLocation(modelLocation.getResourceDomain(), "block/cathedral/pillar_base"));
			modelHead  = ModelLoaderRegistry.getModel(new ResourceLocation(modelLocation.getResourceDomain(), "block/cathedral/pillar_head"));
			modelJoin  = ModelLoaderRegistry.getModel(new ResourceLocation(modelLocation.getResourceDomain(), "block/cathedral/pillar_join"));
			modelPane  = ModelLoaderRegistry.getModel(new ResourceLocation(modelLocation.getResourceDomain(), "block/cathedral/pillar_pane"));
			modelRail  = ModelLoaderRegistry.getModel(new ResourceLocation(modelLocation.getResourceDomain(), "block/cathedral/pillar_rail"));
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
	
	private Map<String, ResourceLocation> generic(EnumMaterial material, ResourceLocation defaultTexture) {
		String name = material.getName();
		
		String path = "cathedral:blocks/cathedral/";
		String prefix = "pillar_";
		
		Map<String, ResourceLocation> stuff = new HashMap<>();
		
		stuff.put("default",    defaultTexture);
		stuff.put("shaftside",  new ResourceLocation(path + prefix + name + "_shaft_side"));
		stuff.put("headside",   new ResourceLocation(path + prefix + name + "_head_side"));
		stuff.put("headbottom", new ResourceLocation(path + prefix + name + "_head_bottom"));
		stuff.put("basetop",    new ResourceLocation(path + prefix + name + "_base_top"));
		stuff.put("baseside",   new ResourceLocation(path + prefix + name + "_base_side"));
		
		return stuff;
	}
	
	private Map<String, ResourceLocation> getTextureMap() {
		return generic(material, material.getFlatTexture());
	}
	
	@Override
	public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
		
		Map<String, String> stuff = getTextureMap().entrySet().stream().collect(Collectors.toMap( Map.Entry::getKey, e -> e.getValue().toString()));
		
		ImmutableMap<String, String> textures = ImmutableMap.copyOf(stuff);
		
		IBakedModel bakedShaft = modelShaft.retexture(textures).bake(state, format, bakedTextureGetter);
		IBakedModel bakedBase  = modelBase .retexture(textures).bake(state, format, bakedTextureGetter);
		IBakedModel bakedHead  = modelHead .retexture(textures).bake(state, format, bakedTextureGetter);
		
		IBakedModel bakedJoins[] = new IBakedModel[4];
		IBakedModel bakedPanes[] = new IBakedModel[4];
		IBakedModel bakedRails[] = new IBakedModel[4];
		
		for(EnumFacing dir : EnumFacing.HORIZONTALS) {
			int i = dir.getHorizontalIndex();
			bakedJoins[i] = modelJoin.uvlock(true).retexture(textures).bake(ModelRotation.getModelRotation(0, i * 90), format, bakedTextureGetter);
			bakedPanes[i] = modelPane.uvlock(true).retexture(textures).bake(ModelRotation.getModelRotation(0, i * 90), format, bakedTextureGetter);
			bakedRails[i] = modelRail.uvlock(true).retexture(textures).bake(ModelRotation.getModelRotation(0, i * 90), format, bakedTextureGetter);
		}
		
		TextureAtlasSprite particleSprite = bakedTextureGetter.apply(material.getFlatTexture());
		
		return new BakedModelBlockPillar(bakedShaft, bakedBase, bakedHead, bakedJoins, bakedPanes, bakedRails, particleSprite);
	}
	
}
