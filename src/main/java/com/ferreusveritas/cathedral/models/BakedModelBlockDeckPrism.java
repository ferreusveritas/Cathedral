package com.ferreusveritas.cathedral.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ferreusveritas.cathedral.common.blocks.MimicProperty;
import com.ferreusveritas.cathedral.common.blocks.MimicProperty.IMimic;
import com.ferreusveritas.cathedral.util.UnpackedModel;
import com.ferreusveritas.cathedral.util.UnpackedQuad;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BakedModelBlockDeckPrism implements IBakedModel {
	
	protected IBakedModel prismModel;
	
	enum DonutShape {
		FULL,
		SLAB_TOP,
		SLAB_BOTTOM
	}
	
	public BakedModelBlockDeckPrism(IBakedModel prismModel) {
		this.prismModel = prismModel;
	}
	
	@Override
	public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
		
		List<BakedQuad> quads = new ArrayList<BakedQuad>();
		
		if (state != null && state.getBlock() instanceof IMimic && state instanceof IExtendedBlockState) {
			IExtendedBlockState extendedState = ((IExtendedBlockState) state);
			
			if(MinecraftForgeClient.getRenderLayer() == BlockRenderLayer.TRANSLUCENT) {
				quads.addAll(prismModel.getQuads(state, side, rand));
			}
			
			if(MinecraftForgeClient.getRenderLayer() == BlockRenderLayer.SOLID) {
				IBlockState mimicState = extendedState.getValue(MimicProperty.MIMIC);
				IBakedModel mimicModel = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(mimicState);
				UnpackedModel fullBlockModel = new UnpackedModel(mimicModel, mimicState, rand).makeFullBlock();
				
				Map<EnumFacing, List<BakedQuad> > quadMap = makeDonut(fullBlockModel, 3, DonutShape.FULL);
				
				List<BakedQuad> q = quadMap.get(side);
				if(q != null) {
					quads.addAll(q);
				}
			}
			
		}
		
		return quads;
	}
	
	private Map<EnumFacing, List<BakedQuad> > makeDonut(UnpackedModel fullBlockModel, int texelRadius, DonutShape shape) {
		
		Map<EnumFacing, List<BakedQuad> > quadMap = UnpackedModel.newBakedStorage();
		
		//The horizontal sides
		fullBlockModel.packInto(e -> e.getAxis().isHorizontal(), quadMap);
		
		//Create face with hole in it from 4 quads
		float radius = texelRadius / 16f;
		float min = 0.5f - radius;
		float max = 0.5f + radius;
		
		AxisAlignedBB aabbs[] = { 
			new AxisAlignedBB(0, 0, 0, 1, 1, min),
			new AxisAlignedBB(0, 0, max, 1, 1, 1),
			new AxisAlignedBB(0, 0, min, min, 1, max),
			new AxisAlignedBB(max, 0, min, 1, 1, max)
		};
		
		for(AxisAlignedBB aabb: aabbs) {
			UnpackedModel pbm = fullBlockModel.makePartialBlock(aabb);
			pbm.packInto(e -> e.getAxis().isVertical(), quadMap);
		}
		
		//Populate inside walls of hole
		AxisAlignedBB holeAABB = new AxisAlignedBB(min, 0, min, max, 1, max);
		UnpackedModel pbm = fullBlockModel.makePartialBlock(holeAABB);
		
		for(UnpackedQuad sideQuad : pbm.getQuads(null)) {
			sideQuad.move(new Vec3d(sideQuad.face.getOpposite().getDirectionVec()).scale(radius * 2)).color(0.875f);//Fake ambient occlusion inside of donut hole
			quadMap.get(null).add(sideQuad.pack());//Add to the non-culled list(null) since these quads are interior
		}
		
		return quadMap;
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
