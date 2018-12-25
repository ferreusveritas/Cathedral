package com.ferreusveritas.cathedral.models;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import com.ferreusveritas.cathedral.features.cathedral.BlockPillar;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.property.IExtendedBlockState;

public class BakedModelBlockPillar implements IBakedModel {
	
	IBakedModel bakedShaft;
	IBakedModel bakedBase;
	IBakedModel bakedHead;
	IBakedModel bakedJoins[];
	
	TextureAtlasSprite particleSprite;
	
	public BakedModelBlockPillar(IBakedModel bakedShaft, IBakedModel bakedBase, IBakedModel bakedHead, IBakedModel[] bakedJoins, TextureAtlasSprite particleSprite) {
		this.bakedShaft = bakedShaft;
		this.bakedBase  = bakedBase;
		this.bakedHead  = bakedHead;
		this.bakedJoins = bakedJoins;
		this.particleSprite = particleSprite;
	}
	
	@Override
	public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
		
		List<BakedQuad> quadsList = new LinkedList<BakedQuad>();
		
		if(state instanceof IExtendedBlockState) {
			IExtendedBlockState exState = (IExtendedBlockState) state;
			Function<EnumFacing, Boolean> sideGetter = e -> (Boolean)exState.getValue(BlockPillar.CONNECTIONS[e.getIndex()]);
			
			boolean up = sideGetter.apply(EnumFacing.UP);
			boolean down = sideGetter.apply(EnumFacing.DOWN);
			
			quadsList.addAll(bakedShaft.getQuads(exState, side, rand));
			
			if(!up) {
				quadsList.addAll(bakedHead.getQuads(exState, side, rand));
			}
			
			if(!down) {
				quadsList.addAll(bakedBase.getQuads(exState, side, rand));
			}
			
			for(EnumFacing dir: EnumFacing.HORIZONTALS) {
				if(sideGetter.apply(dir)) {
					if(!(side == EnumFacing.UP && !up)) {
						quadsList.addAll(bakedJoins[dir.getHorizontalIndex()].getQuads(exState, side, rand));
					}
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
