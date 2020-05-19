package com.ferreusveritas.cathedral.models;

import java.util.ArrayList;
import java.util.List;
import com.ferreusveritas.cathedral.features.cathedral.BlockPillar;
import com.ferreusveritas.cathedral.features.cathedral.PillarConnectionType;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;

public class BakedModelBlockPillar implements IBakedModel {
	
	IBakedModel bakedShaft;
	IBakedModel bakedBase;
	IBakedModel bakedHead;
	IBakedModel bakedJoins[];
	IBakedModel bakedPanes[];
	IBakedModel bakedRails[];

	
	TextureAtlasSprite particleSprite;
	
	public BakedModelBlockPillar(IBakedModel bakedShaft, IBakedModel bakedBase, IBakedModel bakedHead, IBakedModel[] bakedJoins, IBakedModel[] bakedPanes, IBakedModel[] bakedRails, TextureAtlasSprite particleSprite) {
		this.bakedShaft = bakedShaft;
		this.bakedBase  = bakedBase;
		this.bakedHead  = bakedHead;
		this.bakedJoins = bakedJoins;
		this.bakedPanes = bakedPanes;
		this.bakedRails = bakedRails;
		this.particleSprite = particleSprite;
	}
	
	private PillarConnectionType sideGetter(EnumFacing facing, IExtendedBlockState exState) {
		int index = facing.getIndex();
		IUnlistedProperty property = BlockPillar.CONNECTIONS[index];
		PillarConnectionType val = (PillarConnectionType) exState.getValue(property);
		return val != null ? val : PillarConnectionType.None;
	}
	
	@Override
	public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
		
		List<BakedQuad> quadsList = new ArrayList<>(12);
		
		if(state instanceof IExtendedBlockState) {
			IExtendedBlockState exState = (IExtendedBlockState) state;
			
			PillarConnectionType up = sideGetter(EnumFacing.UP, exState);
			PillarConnectionType down = sideGetter(EnumFacing.DOWN, exState);
			
			quadsList.addAll(bakedShaft.getQuads(exState, side, rand));
			
			if(up != PillarConnectionType.Pillar) {
				quadsList.addAll(bakedHead.getQuads(exState, side, rand));
			}
			
			if(down != PillarConnectionType.Pillar) {
				quadsList.addAll(bakedBase.getQuads(exState, side, rand));
			}
			
			for(EnumFacing dir: EnumFacing.HORIZONTALS) {
				PillarConnectionType type = sideGetter(dir, exState);
				switch(type) {
					case Solid:
					case Pillar:
						quadsList.addAll(bakedJoins[dir.getHorizontalIndex()].getQuads(exState, side, rand));
						break;
					case Pane:
						quadsList.addAll(bakedPanes[dir.getHorizontalIndex()].getQuads(exState, side, rand));
						break;
					case Rail:
						quadsList.addAll(bakedRails[dir.getHorizontalIndex()].getQuads(exState, side, rand));
						break;
					case None:
					default:
						break;
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
