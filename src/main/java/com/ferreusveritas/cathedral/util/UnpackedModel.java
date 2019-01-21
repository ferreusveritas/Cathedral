package com.ferreusveritas.cathedral.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class UnpackedModel {
	
	public static final Predicate TRUE = x -> true;
	
	public final Map<EnumFacing, List<UnpackedQuad>> upqMap;
	
	public static final List<EnumFacing> anySides = Lists.newArrayList(new EnumFacing[] { EnumFacing.DOWN, EnumFacing.UP, EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.WEST, EnumFacing.EAST, null });
	
	private UnpackedModel() {
		upqMap = anySides.stream().collect(Collectors.toMap(d -> d, d -> new ArrayList<>()));
	}
	
	public UnpackedModel(IBakedModel bakedModel, IBlockState state, long rand) {
		this();
		
		//Gather all of the quads from the model
		for(EnumFacing dir : anySides) {
			upqMap.get(dir).addAll(bakedModel.getQuads(state, dir, rand).stream().map(UnpackedQuad::new).collect(Collectors.toList()));
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
	
	public UnpackedModel addQuads(List<UnpackedQuad> quads, EnumFacing side) {
		upqMap.get(side).addAll(quads);
		return this;
	}
	
	public List<UnpackedQuad> getQuads(EnumFacing dir) {
		return upqMap.get(dir);
	}
	
	public List<UnpackedQuad> getQuads() {
		return getQuads(TRUE);
	}
	
	public List<UnpackedQuad> getQuads(@Nonnull Predicate<UnpackedQuad> filter) {
		return upqMap.values().stream().flatMap(List::stream).filter(filter).collect(Collectors.toList());
	}
	
	public UnpackedModel apply(Predicate<UnpackedQuad> filter, Consumer<UnpackedQuad> consumer) {
		getQuads(filter).forEach(consumer);
		return this;
	}
	
	public UnpackedModel apply(Consumer<UnpackedQuad> consumer) {
		getQuads().forEach(consumer);
		return this;
	}
	
	////////////////////////////////////////////////////////////////
	// Model Manipulation                                         //
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
					minX = minX < v.x ? minX : v.x;
					minY = minY < v.y ? minY : v.y;
					minZ = minZ < v.z ? minZ : v.z;
					maxX = maxX > v.x ? maxX : v.x;
					maxY = maxY > v.y ? maxY : v.y;
					maxZ = maxZ > v.z ? maxZ : v.z;
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
				getQuads(q -> q.face == dir).stream()
					.map(UnpackedQuad::new)
					.peek(UnpackedQuad::calcArea)
					.max( (a, b) -> Float.compare(a.area, b.area) )//Find the biggest quad facing in the right direction
					.ifPresent(q -> upm.addQuad(q.normalize(), dir));
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
	public UnpackedModel makePartialBlock(AxisAlignedBB aabb_in, boolean clip) {
		
		AxisAlignedBB aabb = clip ? aabb_in.intersect(new AxisAlignedBB(BlockPos.ORIGIN)) : aabb_in;
		UnpackedModel upm = makeFullBlock().apply(q -> q.crop(aabb));
		
		//Since these quads may no longer be touching adjacent blocks they need to be moved to the null side list.
		
		for(EnumFacing dir: EnumFacing.values()) {
			double val = (dir.getAxisDirection().getOffset() + 1) >> 1;
			if(getAABBValue(aabb, dir) != val) {
				List<UnpackedQuad> list = upm.getQuads(dir);
				upm.addQuads(list, null);
				list.clear();
			}
		}
		
		return upm;
	}
	
	public static double getAABBValue(AxisAlignedBB aabb, EnumFacing dir) {
		return new double[] { aabb.minY, aabb.maxY, aabb.minZ, aabb.maxZ, aabb.minX, aabb.maxX }[dir.getIndex()];
	}
	
	public UnpackedModel color(int color) {
		apply(q -> q.color(color));
		return this;
	}
	
	public UnpackedModel move(Vec3d offset) {
		apply(q -> q.move(offset));
		return this;
	}
	
	////////////////////////////////////////////////////////////////
	// Baked Quad Packing                                         //
	////////////////////////////////////////////////////////////////
	
	public Map<EnumFacing, List<BakedQuad>> pack() {
		return pack(TRUE);
	}
	
	public Map<EnumFacing, List<BakedQuad>> pack(Predicate<UnpackedQuad> filter) {
		Map<EnumFacing, List<BakedQuad>> bqMap = newBakedStorage();
		packInto(filter, bqMap);
		return bqMap;
	}
	
	public UnpackedModel packInto(Map<EnumFacing, List<BakedQuad>> bqMap) {
		return packInto(TRUE, bqMap);
	}
	
	public UnpackedModel packInto(Predicate<UnpackedQuad> filter, Map<EnumFacing, List<BakedQuad>> bqMap) {
		for(EnumFacing side : anySides) {
			bqMap.get(side).addAll(upqMap.get(side).stream().filter(filter).map(q -> q.pack()).collect(Collectors.toList()));
		}
		return this;
	}
	
	public static Map<EnumFacing, List<BakedQuad> > newBakedStorage() {
		return anySides.stream().collect(Collectors.toMap(d -> d, d -> new ArrayList<>()));
	}
	
}
