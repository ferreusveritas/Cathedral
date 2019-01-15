package com.ferreusveritas.cathedral.models;

import java.util.ArrayList;
import java.util.List;

import com.ferreusveritas.cathedral.common.blocks.MimicProperty;
import com.ferreusveritas.cathedral.common.blocks.MimicProperty.IMimic;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BakedModelBlockDeckPrism implements IBakedModel {
	
	protected IBakedModel prismModel;
	
	public BakedModelBlockDeckPrism(IBakedModel rootsModel) {
		this.prismModel = rootsModel;
	}
	
	@Override
	public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
		List<BakedQuad> quads = new ArrayList<>(16);
		
		if (state != null && state.getBlock() instanceof IMimic && state instanceof IExtendedBlockState) {
			IExtendedBlockState extendedState = ((IExtendedBlockState) state);
			IBlockState mimicState = extendedState.getValue(MimicProperty.MIMIC);
			
			Minecraft mc = Minecraft.getMinecraft();
			BlockRendererDispatcher blockRendererDispatcher = mc.getBlockRendererDispatcher();
			BlockModelShapes blockModelShapes = blockRendererDispatcher.getBlockModelShapes();
			IBakedModel mimicModel = blockModelShapes.getModelForState(mimicState);
			
			//TextureAtlasSprite holdSprite = null;
			//BakedQuad holdQuad = null;
			
			List<BakedQuad> qs = mimicModel.getQuads(mimicState, side, rand);
			for(BakedQuad q : qs) {
				TextureAtlasSprite sprite = q.getSprite();
				//holdSprite = sprite;
				//holdQuad = q;
				String iconName = sprite.getIconName();
				System.out.println(iconName);
				
				//System.out.println(holdQuad);
				int c = 0;
				for(int i : q.getVertexData()) {
					System.out.print(String.format("%08x ", i));
					if(++c % 7 == 0) {
						System.out.println();
					}
				}
			}
			
			/*
			//holdQuad.getVertexData();
			
			if(holdQuad != null && holdQuad.getFace() == EnumFacing.UP) {
				holdQuad = new BakedQuad(
					holdQuad.getVertexData(),
					-1,
					EnumFacing.UP,
					holdSprite,
					false,
					holdQuad.getFormat());
				
				quads.add(holdQuad);
			}
			*/
			
			List<BakedQuad> x = mimicModel.getQuads(mimicState, side, rand);
			//System.out.println(x.size());
			quads.addAll(x);
			
			//if(MinecraftForgeClient.getRenderLayer() == BlockRenderLayer.CUTOUT) {
				quads.addAll(prismModel.getQuads(state, side, rand));
			//}
			
			//System.out.println(side + " " + quads.size() + " " + MinecraftForgeClient.getRenderLayer());
		}
		
		
		return quads;
	}
	
	@Override
	public boolean isAmbientOcclusion() {
		return true;
	}
	
	@Override
	public boolean isGui3d() {
		return true;
	}
	
	@Override
	public boolean isBuiltInRenderer() {
		return true;
	}
	
	@Override
	public TextureAtlasSprite getParticleTexture() {
		return prismModel.getParticleTexture();
	}
	
	@Override
	public ItemOverrideList getOverrides() {
		return null;
	}
	
}
