package com.ferreusveritas.cathedral.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class UnpackedModel {
	
	public IBakedModel bakedModel;
	public Map<EnumFacing, List<UnpackedQuad>> upqMap = new HashMap<>();
	
	public static final EnumFacing[] anySides = new EnumFacing[] {
		EnumFacing.DOWN, EnumFacing.UP, EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.WEST, EnumFacing.EAST, null
	};
	
	private UnpackedModel() {
		for(EnumFacing dir : anySides) {
			upqMap.put(dir, new ArrayList<UnpackedQuad>());
		}
	}
	
	public UnpackedModel(IBakedModel bakedModel, IBlockState state, long rand) {
		this();
		this.bakedModel = bakedModel;
		
		//Gather all of the quads from the model
		for(EnumFacing dir : anySides) {
			upqMap.get(dir).addAll(bakedModel.getQuads(state, dir, rand).stream().map(q -> new UnpackedQuad(q)).collect(Collectors.toList()));
		}
	}
	
	/**
	 * @param quad The {@link UnpackedQuad} to be added
	 * @param side The direction culling list this quad will be added to
	 * @return this object for chaining
	 */
	public UnpackedModel addQuad(UnpackedQuad quad, EnumFacing side) {
		upqMap.get(side).add(quad);
		return this;
	}
	
	public List<UnpackedQuad> getQuads(EnumFacing dir) {
		return upqMap.get(dir);
	}
	
	
	////////////////////////////////////////////////////////////////
	// Model Manipulation                                        //
	////////////////////////////////////////////////////////////////
	
	public AxisAlignedBB getModelAABB() {
		double minX = Double.NaN;
		double minY = Double.NaN;
		double minZ = Double.NaN;
		double maxX = Double.NaN;
		double maxY = Double.NaN;
		double maxZ = Double.NaN;
		for(Entry<EnumFacing, List<UnpackedQuad>> entry : upqMap.entrySet()) {
			for(UnpackedQuad upq : entry.getValue() ) {
				for(UnpackedVertex v : upq.vertices) {
					minX = minX > v.x ? v.x : minX;
					minY = minY > v.y ? v.y : minY;
					minZ = minZ > v.z ? v.z : minZ;
					maxX = maxX < v.x ? v.x : maxX;
					maxY = maxY < v.y ? v.y : maxY;
					maxZ = maxZ < v.z ? v.z : maxZ;
				}
			}
		}
		
		return new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ);
	}
	
	/**
	 * @return A new {@link UnpackedModel} that is a full block with all of the texture of this model
	 */
	public UnpackedModel makeFullBlock() {
		UnpackedModel upm = new UnpackedModel();
		
		//Pick the best quad for each cube face
		for(EnumFacing dir : EnumFacing.values()) {
			if(upqMap.get(dir).size() > 0) {//The face completely against the side of the block is the ideal choice
				upm.addQuad(new UnpackedQuad(upqMap.get(dir).get(0)).normalize(), dir);
			} else {
				List<UnpackedQuad> list = upqMap.get(null).stream().filter( q -> q.face == dir).map( q -> new UnpackedQuad(q) ).collect(Collectors.toList());
				list.forEach( q -> q.calcArea() );
				Optional<UnpackedQuad> oq = list.stream().max( (a, b) -> Float.compare(a.area, b.area) );//Find the biggest quad facing in the right direction
				if(oq.isPresent()) {
					upm.addQuad(oq.get().normalize(), dir);
				}
			}
		}
		
		return upm;
	}
	
	public UnpackedModel makePartialBlock(AxisAlignedBB aabb) {
		return makePartialBlock(aabb, true);
	}
	
	/**
 	* @param aabb The new model's volume
 	* @param clip set true to clip the input AABB to a single block
 	* @return A new {@link UnpackedModel} that is a block with the dimensions defined by aabb with all of the texture of this model
 	*/
	public UnpackedModel makePartialBlock(AxisAlignedBB aabb, boolean clip) {
		
		if(clip) {//Clip the contents to the volume of single block
			aabb = aabb.intersect(new AxisAlignedBB(BlockPos.ORIGIN));
		}
		
		UnpackedModel upm = makeFullBlock();
		
		for(EnumFacing dir : EnumFacing.values()) {
			List<UnpackedQuad> upqList = upm.upqMap.get(dir);
			for(UnpackedQuad upq : upqList) {
				upq.crop(aabb);
			}
		}
		
		if(aabb.minX != 0.0) {
			List<UnpackedQuad> list = upm.upqMap.get(EnumFacing.WEST);
			upm.upqMap.get(null).addAll(list);
			list.clear();
		}
		
		if(aabb.maxX != 1.0) {
			List<UnpackedQuad> list = upm.upqMap.get(EnumFacing.EAST);
			upm.upqMap.get(null).addAll(list);
			list.clear();
		}
		
		if(aabb.minY != 0.0) {
			List<UnpackedQuad> list = upm.upqMap.get(EnumFacing.DOWN);
			upm.upqMap.get(null).addAll(list);
			list.clear();
		}
		
		if(aabb.maxY != 1.0) {
			List<UnpackedQuad> list = upm.upqMap.get(EnumFacing.UP);
			upm.upqMap.get(null).addAll(list);
			list.clear();
		}
		
		if(aabb.minZ != 0.0) {
			List<UnpackedQuad> list = upm.upqMap.get(EnumFacing.NORTH);
			upm.upqMap.get(null).addAll(list);
			list.clear();
		}
		
		if(aabb.maxZ != 1.0) {
			List<UnpackedQuad> list = upm.upqMap.get(EnumFacing.SOUTH);
			upm.upqMap.get(null).addAll(list);
			list.clear();
		}
		
		return upm;
	}
	
	
	////////////////////////////////////////////////////////////////
	// Baked Quad Packing                                         //
	////////////////////////////////////////////////////////////////
	
	public List<BakedQuad> getBakedQuads(EnumFacing side) {
		return upqMap.get(side).stream().map( upq -> upq.pack()).collect(Collectors.toList());
	}
	
	public Map<EnumFacing, List<BakedQuad>> pack() {
		return pack( e -> true );
	}
	
	public Map<EnumFacing, List<BakedQuad>> pack(Predicate<EnumFacing> predicate) {
		Map<EnumFacing, List<BakedQuad>> bqMap = newBakedStorage();
		packInto(predicate, bqMap);
		return bqMap;
	}

	public UnpackedModel packInto(Map<EnumFacing, List<BakedQuad>> bqMap) {
		return packInto( e -> true, bqMap);
	}
	
	public UnpackedModel packInto(Predicate<EnumFacing> predicate, Map<EnumFacing, List<BakedQuad>> bqMap) {
		for(EnumFacing side : anySides) {
			if(predicate.test(side)) {
				bqMap.get(side).addAll(getBakedQuads(side));
			}
		}
		return this;
	}
	
	public static Map<EnumFacing, List<BakedQuad> > newBakedStorage() {
		Map<EnumFacing, List<BakedQuad> > quadMap = new HashMap<>();
		for(EnumFacing dir : UnpackedModel.anySides) {
			quadMap.put(dir, new ArrayList<>());
		}
		return quadMap;
	}
	
}