package com.ferreusveritas.cathedral.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ferreusveritas.cathedral.common.blocks.MimicProperty;
import com.ferreusveritas.cathedral.common.blocks.MimicProperty.IMimic;
import com.ferreusveritas.cathedral.features.cathedral.BlockDeckPrism;
import com.ferreusveritas.cathedral.util.UnpackedModel;
import com.ferreusveritas.cathedral.util.UnpackedQuad;
import com.ferreusveritas.cathedral.util.UnpackedVertex;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockSlab.EnumBlockHalf;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.EnumDyeColor;
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
		FULL(new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0)),
		SLAB_TOP(new AxisAlignedBB(0.0, 0.5, 0.0, 1.0, 1.0, 1.0)),
		SLAB_BOTTOM(new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.5, 1.0));
		
		public final AxisAlignedBB aabb;
		
		DonutShape(AxisAlignedBB aabb) {
			this.aabb = aabb;
		}
	}
	
	public BakedModelBlockDeckPrism(IBakedModel prismModel) {
		this.prismModel = prismModel;
	}
	
	@Override
	public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
		List<BakedQuad> quads = new ArrayList<BakedQuad>();
		
		if (state != null && state.getBlock() instanceof IMimic && state instanceof IExtendedBlockState) {
			IExtendedBlockState extendedState = ((IExtendedBlockState) state);
			IBlockState mimicState = extendedState.getValue(MimicProperty.MIMIC);
			IBakedModel mimicModel = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(mimicState);
			DonutShape donutShape = getDonutShape(mimicState);
			
			if(MinecraftForgeClient.getRenderLayer() == BlockRenderLayer.TRANSLUCENT) {
				
				EnumDyeColor color = extendedState.getValue(BlockDeckPrism.COLOR);
				
				UnpackedModel unpackedModel = new UnpackedModel(prismModel, state, 0).color(color.getColorValue() | 0xFF000000);
				
				if(side == null) {
					for(UnpackedQuad upq : unpackedModel.getQuads(q -> q.face == EnumFacing.UP)) {
						for(UnpackedVertex v : upq.vertices) {
							v.y -= 1.0 - (float) donutShape.aabb.maxY;
						}
					}

					for(UnpackedQuad upq : unpackedModel.getQuads(q -> q.face != EnumFacing.UP)) {
						for(UnpackedVertex v : upq.vertices) {
							v.y += (float) donutShape.aabb.minY;
						}
					}
					
					quads.addAll(unpackedModel.pack().get(null));
				}
			}
			
			if(MinecraftForgeClient.getRenderLayer() == BlockRenderLayer.SOLID) {
				UnpackedModel unpackedModel = new UnpackedModel(mimicModel, mimicState, 0);
				
				Map<EnumFacing, List<BakedQuad> > quadMap = makeDonut(unpackedModel, 3, donutShape);
				
				List<BakedQuad> q = quadMap.get(side);
				if(q != null) {
					quads.addAll(q);
				}
			}
			
		}
		
		return quads;
	}
	
	protected DonutShape getDonutShape(IBlockState state) {
		DonutShape donutShape = DonutShape.FULL;
		
		if(state.getBlock() instanceof BlockSlab) {
			BlockSlab slab = (BlockSlab) state.getBlock();
			if(!slab.isDouble()) {
				if(state.getPropertyKeys().contains(BlockSlab.HALF)) {
					EnumBlockHalf slabHalf = state.getValue(BlockSlab.HALF);
					donutShape = slabHalf == EnumBlockHalf.BOTTOM ? DonutShape.SLAB_BOTTOM : DonutShape.SLAB_TOP;
				}
			}
		}
		
		return donutShape;
	}
	
	private Map<EnumFacing, List<BakedQuad> > makeDonut(UnpackedModel unpackedModel, int texelRadius, DonutShape shape) {
		
		Map<EnumFacing, List<BakedQuad> > quadMap = UnpackedModel.newBakedStorage();
		
		AxisAlignedBB envelope = shape.aabb;
		
		unpackedModel = unpackedModel.makeFullBlock();
		
		//The horizontal sides
		unpackedModel.makePartialBlock(envelope).packInto(q -> q.face.getAxis().isHorizontal(), quadMap);
		
		float radius = texelRadius / 16f;
		float min = 0.5f - radius;
		float max = 0.5f + radius;

		//Create face with hole in it from 4 quads
		AxisAlignedBB aabbs[] = { 
			new AxisAlignedBB(0, 0, 0, 1, 1, min),
			new AxisAlignedBB(0, 0, max, 1, 1, 1),
			new AxisAlignedBB(0, 0, min, min, 1, max),
			new AxisAlignedBB(max, 0, min, 1, 1, max)
		};
		
		for(AxisAlignedBB aabb: aabbs) {
			unpackedModel.makePartialBlock(aabb.intersect(envelope)).packInto(q -> q.face.getAxis().isVertical(), quadMap);
		}
		
		//Populate inside walls of hole
		AxisAlignedBB holeAABB = new AxisAlignedBB(min, 0, min, max, 1, max).intersect(envelope);
		UnpackedModel pbm = unpackedModel.makePartialBlock(holeAABB);
		
		for(UnpackedQuad quad : pbm.getQuads(q -> q.face.getAxis().isHorizontal())) {
			quad.move(new Vec3d(quad.face.getOpposite().getDirectionVec()).scale(radius * 2)).color(0.875f);//Fake ambient occlusion inside of donut hole
			quadMap.get(null).add(quad.pack());//Add to the non-culled list(null) since these quads are interior
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
