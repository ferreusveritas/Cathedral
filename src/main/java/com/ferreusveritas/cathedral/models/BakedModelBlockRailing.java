package com.ferreusveritas.cathedral.models;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import com.ferreusveritas.cathedral.features.cathedral.BlockRailing;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.Properties.PropertyAdapter;

public class BakedModelBlockRailing implements IBakedModel {
	
	IBakedModel bakedPost;
	IBakedModel bakedCap;
	IBakedModel bakedSides[];
	
	TextureAtlasSprite particleSprite;
	
	public BakedModelBlockRailing(IBakedModel bakedPost, IBakedModel bakedCap, IBakedModel[] bakedSides, TextureAtlasSprite particleSprite) {
		this.bakedPost = bakedPost;
		this.bakedCap  = bakedCap;
		this.bakedSides = bakedSides;
		this.particleSprite = particleSprite;
	}
	
	@Override
	public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
		
		List<BakedQuad> quadsList = new LinkedList<BakedQuad>();
		
		if(state instanceof IExtendedBlockState) {
			IExtendedBlockState exState = (IExtendedBlockState) state;
			
			Function<PropertyAdapter<Boolean>, Boolean> getter = p -> Optional.ofNullable(exState.getValue(p)).orElse(false);
			
			if(getter.apply(BlockRailing.POSTCAP)) {
				quadsList.addAll(bakedCap.getQuads(exState, side, rand));
				return quadsList;
			}
			
			if(getter.apply(BlockRailing.POST)) {
				quadsList.addAll(bakedPost.getQuads(exState, side, rand));
			}
			
			boolean dirs[] = new boolean[] {
				getter.apply(BlockRailing.SOUTH),
				getter.apply(BlockRailing.WEST),
				getter.apply(BlockRailing.NORTH),
				getter.apply(BlockRailing.EAST)
			};
			
			for(int i = 0; i < 4; i++) {
				if(dirs[i]) {
					quadsList.addAll(bakedSides[i].getQuads(exState, side, rand));	
				}
			}
			
		}
		
		return quadsList;
	}
	
	@Override
	public boolean isAmbientOcclusion() {
		return true;
	}
	
	@Override
	public boolean isGui3d() {
		return false;
	}
	
	@Override
	public boolean isBuiltInRenderer() {
		return true;
	}
	
	@Override
	public TextureAtlasSprite getParticleTexture() {
		return particleSprite;
	}
	
	@Override
	public ItemOverrideList getOverrides() {
		return null;
	}
	
}
