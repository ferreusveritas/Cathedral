package com.ferreusveritas.cathedral.models;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.ferreusveritas.cathedral.features.cathedral.EnumMaterial;
import com.ferreusveritas.cathedral.features.chess.ChessMan;
import com.google.common.collect.ImmutableMap;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.IModelState;

public class ModelBlockChess implements IModel {

	private IModel modelPieces[];
	
	private EnumMaterial materialBlack = EnumMaterial.BASALT;
	private EnumMaterial materialWhite = EnumMaterial.MARBLE;
	
	public ModelBlockChess(ResourceLocation resloc) {
		
		modelPieces = new IModel[6];
		
		try {
			for(ChessMan man : ChessMan.values()) {
				if(man != ChessMan.None) {
					modelPieces[man.ordinal() - 1] = ModelLoaderRegistry.getModel(new ResourceLocation(resloc.getResourceDomain(), "block/chess/" + man.name().toLowerCase()));
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	private static Map<String, String> generic(ResourceLocation defaultTexture) {
		Map<String, String> stuff = new HashMap<>();
		stuff.put("default", defaultTexture.toString());
		return stuff;
	}
	
	private Map<String, String> getTextureMap(EnumMaterial material) {
		return generic(material.getFlatTexture());
	}
	
	@Override
	public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
		
		ImmutableMap<String, String> blackTextures = ImmutableMap.copyOf(getTextureMap(materialBlack));
		ImmutableMap<String, String> whiteTextures = ImmutableMap.copyOf(getTextureMap(materialWhite));
		
		IBakedModel[] bakedPieces = new IBakedModel[12];
		
		for(int i = 0; i < 6; i++) {
			bakedPieces[i] = modelPieces[i].retexture(whiteTextures).bake(state, format, bakedTextureGetter);
			bakedPieces[i + 6] = modelPieces[i].retexture(blackTextures).bake(state, format, bakedTextureGetter);
		}
		
		TextureAtlasSprite particleSprite = bakedTextureGetter.apply(materialBlack.getFlatTexture());
		
		return new BakedModelBlockChess(bakedPieces, particleSprite);
	}

}
