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
						
			Map<EnumFacing, List<BakedQuad> > quadMap = makeDonut(3, isolateCubeSides(mimicModel, mimicState, rand));
			
			if(MinecraftForgeClient.getRenderLayer() == BlockRenderLayer.TRANSLUCENT) {
				quads.addAll(prismModel.getQuads(state, side, rand));
				List<BakedQuad> q = quadMap.get(side);
				if(q != null) {
					quads.addAll(q);
				}
			}
			
		}
		
		return quads;
	}
	
	
	private BakedQuad[] isolateCubeSides(IBakedModel model, IBlockState state, long rand) {

		//Gather all of the quads from the model
		Map<EnumFacing, List<BakedQuad> > quadMap = new HashMap<>(); 
		for(EnumFacing dir : anySides) {
			quadMap.computeIfAbsent(dir, d -> new ArrayList<BakedQuad>()).addAll(model.getQuads(state, dir, rand));
		}
		
		BakedQuad sides[] = new BakedQuad[6];
		
		//Pick the best quad for each cube face
		for(EnumFacing dir : EnumFacing.values()) {
			if(quadMap.get(dir).size() > 0) {//The face completely against the side of the block is the ideal choice
				sides[dir.getIndex()] = quadMap.get(dir).get(0);
			} else {
				List<UnpackedQuad> list = quadMap.get(null).stream().map( q -> new UnpackedQuad(q) ).collect(Collectors.toList());
				list.forEach( q -> q.calcArea() );
				sides[dir.getIndex()] = list.stream().max( (a, b) -> Float.compare(a.area, b.area) ).get().pack();//Find the biggest quad facing in the right direction
			}
		}
		
		for(int i = 0; i < 6; i++) {
			sides[i] = new UnpackedQuad(sides[i]).normalize().pack();
		}
		
		return sides;
	}
	
	private Map<EnumFacing, List<BakedQuad> > makeDonut(int texelRadius, BakedQuad sides[]) {
		
		Map<EnumFacing, List<BakedQuad> > quadMap = new HashMap<>(); 
		
		for(EnumFacing dir : anySides) {
			quadMap.put(dir, new ArrayList<>());
		}
		
		for(EnumFacing dir : EnumFacing.HORIZONTALS) {
			List<BakedQuad> list = quadMap.computeIfAbsent(dir, d -> new ArrayList<BakedQuad>());
			if(sides[dir.getIndex()] != null) {
				list.add(sides[dir.getIndex()]);
			}
		}
		
		float radius = texelRadius / 16f;
		float amount = 0.5f + radius;
		float amount2 = 0.5f - radius;
		
		if(sides[EnumFacing.UP.getIndex()] != null) {
			List<BakedQuad> list = quadMap.computeIfAbsent(EnumFacing.UP, d -> new ArrayList<BakedQuad>());
			UnpackedQuad upqMain = new UnpackedQuad(sides[EnumFacing.UP.getIndex()]);
			UnpackedQuad upq;
			
			float sizeU = upqMain.sprite.getMaxU() - upqMain.sprite.getMinU();
			float sizeV = upqMain.sprite.getMaxV() - upqMain.sprite.getMinV();
			
			upq = new UnpackedQuad(upqMain);
			upq.vertices[0].x += amount;
			upq.vertices[0].u += sizeU * amount; 
			upq.vertices[1].x += amount;
			upq.vertices[1].u += sizeU * amount; 
			
			upq = new UnpackedQuad(upqMain);
			upq.vertices[2].x -= amount;
			upq.vertices[2].u -= sizeU * amount; 
			upq.vertices[3].x -= amount;
			upq.vertices[3].u -= sizeU * amount; 
			list.add(upq.pack());
			
			upq = new UnpackedQuad(upqMain);
			upq.vertices[0].z += amount;
			upq.vertices[0].v += sizeV * amount; 
			upq.vertices[3].z += amount;
			upq.vertices[3].v += sizeV * amount; 
			list.add(upq.pack());

			upq = new UnpackedQuad(upqMain);
			upq.vertices[1].z -= amount;
			upq.vertices[1].v -= sizeV * amount; 
			upq.vertices[2].z -= amount;
			upq.vertices[2].v -= sizeV * amount; 
			list.add(upq.pack());				
		}

		{
			List<BakedQuad> list = quadMap.computeIfAbsent(null, d -> new ArrayList<BakedQuad>());
			
			for(EnumFacing dir: EnumFacing.HORIZONTALS) {
				if(sides[dir.getIndex()] != null) {
					UnpackedQuad q = new UnpackedQuad(sides[dir.getIndex()]);
					EnumFacing CW = dir.rotateY();
					for(int v = 0; v < 4; v++) {
						q.vertices[v].x += dir.getOpposite().getFrontOffsetX() * amount;
						q.vertices[v].y += dir.getOpposite().getFrontOffsetY() * amount;
						q.vertices[v].z += dir.getOpposite().getFrontOffsetZ() * amount;
						q.vertices[v].color = 0xFF999999;
					}
					q.vertices[0].x -= CW.getFrontOffsetX() * amount2;
					q.vertices[0].y -= CW.getFrontOffsetY() * amount2;
					q.vertices[0].z -= CW.getFrontOffsetZ() * amount2;
					q.vertices[0].u += (q.sprite.getMaxU() - q.sprite.getMinU()) * amount2;

					q.vertices[1].x -= CW.getFrontOffsetX() * amount2;
					q.vertices[1].y -= CW.getFrontOffsetY() * amount2;
					q.vertices[1].z -= CW.getFrontOffsetZ() * amount2;
					q.vertices[1].u += (q.sprite.getMaxU() - q.sprite.getMinU()) * amount2;

					q.vertices[2].x += CW.getFrontOffsetX() * amount2;
					q.vertices[2].y += CW.getFrontOffsetY() * amount2;
					q.vertices[2].z += CW.getFrontOffsetZ() * amount2;
					q.vertices[2].u -= (q.sprite.getMaxU() - q.sprite.getMinU()) * amount2;

					q.vertices[3].x += CW.getFrontOffsetX() * amount2;
					q.vertices[3].y += CW.getFrontOffsetY() * amount2;
					q.vertices[3].z += CW.getFrontOffsetZ() * amount2;
					q.vertices[3].u -= (q.sprite.getMaxU() - q.sprite.getMinU()) * amount2;
					
					list.add(q.pack());
				}
			}
		}
		
		if(sides[EnumFacing.DOWN.getIndex()] != null) {
			List<BakedQuad> list = quadMap.computeIfAbsent(EnumFacing.DOWN, d -> new ArrayList<BakedQuad>());
			UnpackedQuad upqMain = new UnpackedQuad(sides[EnumFacing.DOWN.getIndex()]);
			UnpackedQuad upq;
			
			float sizeU = upqMain.sprite.getMaxU() - upqMain.sprite.getMinU();
			float sizeV = upqMain.sprite.getMaxV() - upqMain.sprite.getMinV();
			
			upq = new UnpackedQuad(upqMain);
			upq.vertices[0].x += amount;
			upq.vertices[0].u += sizeU * amount; 
			upq.vertices[1].x += amount;
			upq.vertices[1].u += sizeU * amount; 
			list.add(upq.pack());
			
			upq = new UnpackedQuad(upqMain);
			upq.vertices[2].x -= amount;
			upq.vertices[2].u -= sizeU * amount; 
			upq.vertices[3].x -= amount;
			upq.vertices[3].u -= sizeU * amount; 
			list.add(upq.pack());
			
			
			upq = new UnpackedQuad(upqMain);
			upq.vertices[0].z -= amount;
			upq.vertices[0].v += sizeV * amount; 
			upq.vertices[3].z -= amount;
			upq.vertices[3].v += sizeV * amount; 
			list.add(upq.pack());

			
			upq = new UnpackedQuad(upqMain);
			upq.vertices[1].z += amount;
			upq.vertices[1].v -= sizeV * amount; 
			upq.vertices[2].z += amount;
			upq.vertices[2].v -= sizeV * amount; 
			list.add(upq.pack());
			
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
