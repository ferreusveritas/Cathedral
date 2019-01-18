package com.ferreusveritas.cathedral.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ferreusveritas.cathedral.common.blocks.MimicProperty;
import com.ferreusveritas.cathedral.common.blocks.MimicProperty.IMimic;
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
	
	public BakedModelBlockDeckPrism(IBakedModel prismModel) {
		this.prismModel = prismModel;
	}
	
	public static final EnumFacing[] anySides = new EnumFacing[] {
		EnumFacing.DOWN, EnumFacing.UP, EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.WEST, EnumFacing.EAST, null
	};
	
	@Override
	public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
		
		List<BakedQuad> quads = new ArrayList<BakedQuad>();
		
		if (state != null && state.getBlock() instanceof IMimic && state instanceof IExtendedBlockState) {
			IExtendedBlockState extendedState = ((IExtendedBlockState) state);
			IBlockState mimicState = extendedState.getValue(MimicProperty.MIMIC);
			IBakedModel mimicModel = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(mimicState);			
			
			if(MinecraftForgeClient.getRenderLayer() == BlockRenderLayer.TRANSLUCENT) {
				quads.addAll(prismModel.getQuads(state, side, rand));
			}
			
			if(MinecraftForgeClient.getRenderLayer() == BlockRenderLayer.SOLID) {				
				Map<EnumFacing, List<BakedQuad> > quadMap = makeDonut(3, isolateCubeSides(mimicModel, mimicState, rand));
				
				List<BakedQuad> q = quadMap.get(side);
				if(q != null) {
					quads.addAll(q);
				}
			}
			
		}
		
		return quads;
	}
	
	
	private UnpackedQuad[] isolateCubeSides(IBakedModel model, IBlockState state, long rand) {

		//Gather all of the quads from the model
		Map<EnumFacing, List<BakedQuad> > quadMap = new HashMap<>(); 
		for(EnumFacing dir : anySides) {
			quadMap.computeIfAbsent(dir, d -> new ArrayList<BakedQuad>()).addAll(model.getQuads(state, dir, rand));
		}
		
		UnpackedQuad sides[] = new UnpackedQuad[6];
		
		//Pick the best quad for each cube face
		for(EnumFacing dir : EnumFacing.values()) {
			if(quadMap.get(dir).size() > 0) {//The face completely against the side of the block is the ideal choice
				sides[dir.getIndex()] = new UnpackedQuad(quadMap.get(dir).get(0)).normalize();
			} else {
				List<UnpackedQuad> list = quadMap.get(null).stream().filter( q -> q.getFace() == dir).map( q -> new UnpackedQuad(q) ).collect(Collectors.toList());
				list.forEach( q -> q.calcArea() );
				sides[dir.getIndex()] = list.stream().max( (a, b) -> Float.compare(a.area, b.area) ).get().normalize();//Find the biggest quad facing in the right direction
			}
		}
		
		return sides;
	}
	
	private Map<EnumFacing, List<BakedQuad> > makeDonut(int texelRadius, UnpackedQuad sides[]) {
		
		Map<EnumFacing, List<BakedQuad> > quadMap = new HashMap<>(); 
		
		for(EnumFacing dir : anySides) {
			quadMap.put(dir, new ArrayList<>());
		}
		
		//The horizontal sides are unchanged and can be added directly
		for(EnumFacing dir : EnumFacing.HORIZONTALS) {
			UnpackedQuad side = sides[dir.getIndex()];
			if(side != null) {
				quadMap.get(dir).add(side.pack());
			}
		}
		
		//Create face with hold in it
		float radius = texelRadius / 16f;
		float min = 0.5f - radius;
		float max = 0.5f + radius;
		
		AxisAlignedBB aabbs[] = { 
			new AxisAlignedBB(0, 0, 0, 1, 1, min),
			new AxisAlignedBB(0, 0, max, 1, 1, 1),
			new AxisAlignedBB(0, 0, min, min, 1, max),
			new AxisAlignedBB(max, 0, min, 1, 1, max)
		};
		
		for(EnumFacing dir : new EnumFacing[] { EnumFacing.DOWN, EnumFacing.UP } ) {
			UnpackedQuad side = sides[dir.getIndex()];
			if(side != null) {
				for(AxisAlignedBB aabb: aabbs) {
					quadMap.get(dir).add(new UnpackedQuad(side).crop(aabb).pack());
				}
			}
		}
		
		//Populate inside of hole
		AxisAlignedBB holeAABB = new AxisAlignedBB(min, 0, min, max, 1, max);
			
		for(EnumFacing dir: EnumFacing.HORIZONTALS) {
			UnpackedQuad side = sides[dir.getIndex()];
			if(side != null) {
				UnpackedQuad upq = new UnpackedQuad(side).crop(holeAABB).move(new Vec3d(dir.getOpposite().getDirectionVec()).scale(radius * 2));
				upq.color(0xFFBBBBBB);//Fake ambient occlusion inside of donut hole
				quadMap.get(null).add(upq.pack());
			}
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
