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
	private EnumMaterial material;
	
	public ModelBlockPillar(ResourceLocation modelLocation) {
		
		try {
			modelShaft = ModelLoaderRegistry.getModel(new ResourceLocation(modelLocation.getResourceDomain(), "block/cathedral/pillarshaft"));
			modelBase  = ModelLoaderRegistry.getModel(new ResourceLocation(modelLocation.getResourceDomain(), "block/cathedral/pillarbase"));
			modelHead  = ModelLoaderRegistry.getModel(new ResourceLocation(modelLocation.getResourceDomain(), "block/cathedral/pillarhead"));
			modelJoin  = ModelLoaderRegistry.getModel(new ResourceLocation(modelLocation.getResourceDomain(), "block/cathedral/pillarjoin"));
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
	
	private Map<String, ResourceLocation> getTextureMap(EnumMaterial material) {
		
		Map<EnumMaterial, String> base = new HashMap<>();
		
		base.put(EnumMaterial.STONE, "blocks/stone");
		base.put(EnumMaterial.SANDSTONE, "chisel:blocks/sandstoneyellow/tiles-large");
		
		switch(material) {
			case STONE:
				return generic(EnumMaterial.STONE, new ResourceLocation(base.get(material)));
			case SANDSTONE:
				return generic(EnumMaterial.SANDSTONE, new ResourceLocation(base.get(material)));
			case BASALT:
			case DWEMER:
			case ENDSTONE:
			case LIMESTONE:
			case MARBLE:
			case NETHERBRICK:
			case OBSIDIAN:
			case PACKEDICE:
			case QUARTZ:
			case REDSANDSTONE:
			case SNOW:
			default:
				return generic(EnumMaterial.STONE, new ResourceLocation(base.get(EnumMaterial.STONE)));
		}
		
	}
	
	private Map<String, ResourceLocation> getTextureMap() {
		return getTextureMap(material);
	}
	
	@Override
	public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
		
		Map<String, String> stuff = getTextureMap().entrySet().stream().collect(Collectors.toMap( Map.Entry::getKey, e -> e.getValue().toString()));
		
		ImmutableMap<String, String> textures = ImmutableMap.copyOf(stuff);
		
		IBakedModel bakedShaft = modelShaft.retexture(textures).bake(state, format, bakedTextureGetter);
		IBakedModel bakedBase  = modelBase .retexture(textures).bake(state, format, bakedTextureGetter);
		IBakedModel bakedHead  = modelHead .retexture(textures).bake(state, format, bakedTextureGetter);
		
		IBakedModel bakedJoins[] = new IBakedModel[4];
		
		for(EnumFacing dir : EnumFacing.HORIZONTALS) {
			int i = dir.getHorizontalIndex();
			bakedJoins[i] = modelJoin.uvlock(true).retexture(textures).bake(ModelRotation.getModelRotation(0, i * 90), format, bakedTextureGetter);
		}
		
		TextureAtlasSprite particleSprite = bakedTextureGetter.apply(getTextureMap().get("default"));
		
		return new BakedModelBlockPillar(bakedShaft, bakedBase, bakedHead, bakedJoins, particleSprite);
	}
	
}
