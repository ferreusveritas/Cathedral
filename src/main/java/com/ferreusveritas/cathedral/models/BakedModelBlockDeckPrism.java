package com.ferreusveritas.cathedral.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.lwjgl.util.vector.Vector3f;

import com.ferreusveritas.cathedral.common.blocks.MimicProperty;
import com.ferreusveritas.cathedral.common.blocks.MimicProperty.IMimic;
import com.google.common.collect.Maps;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockFaceUV;
import net.minecraft.client.renderer.block.model.BlockPart;
import net.minecraft.client.renderer.block.model.BlockPartFace;
import net.minecraft.client.renderer.block.model.FaceBakery;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.block.model.ModelBlock;
import net.minecraft.client.renderer.block.model.ModelRotation;
import net.minecraft.client.renderer.block.model.SimpleBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.client.renderer.vertex.VertexFormatElement.EnumType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.client.model.Attributes;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BakedModelBlockDeckPrism implements IBakedModel {
	
	protected IBakedModel prismModel;
	
	public BakedModelBlockDeckPrism(IBakedModel prismModel) {
		this.prismModel = prismModel;
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
			
			/*List<BakedQuad> qs = mimicModel.getQuads(mimicState, side, rand);
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
			}*/
			
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
			
			//TODO: Investigate BakedQuadRetextured

			
			List<BakedQuad> mimicQuads = mimicModel.getQuads(mimicState, side, rand);
			quads.addAll(mimicQuads);
			
			//if(MinecraftForgeClient.getRenderLayer() == BlockRenderLayer.CUTOUT) {
				quads.addAll(prismModel.getQuads(state, side, rand));
			//}
			
			//System.out.println(side + " " + quads.size() + " " + MinecraftForgeClient.getRenderLayer());
		}
		
		
		return quads;
	}
	
	
	@SuppressWarnings("unused")
	private IBakedModel bakeDonut(int radius, TextureAtlasSprite sides[]) {
		
		ModelBlock modelBlock = new ModelBlock(null, null, null, false, false, ItemCameraTransforms.DEFAULT, null);
		
		SimpleBakedModel.Builder builder = new SimpleBakedModel.Builder(modelBlock, ItemOverrideList.NONE).setTexture(sides[0]);
		
		for (EnumFacing face: EnumFacing.values()) {
			AxisAlignedBB partBoundary = new AxisAlignedBB(0, 0, 0, 16, 16, 16);
			Vector3f limits[] = AABBLimits(partBoundary);
			
			Map<EnumFacing, BlockPartFace> mapFacesIn = Maps.newEnumMap(EnumFacing.class);
			
			BlockFaceUV uvface = new BlockFaceUV(getUVs(partBoundary, face), getFaceAngle(Axis.Y, face));
			mapFacesIn.put(face, new BlockPartFace(null, -1, null, uvface));
			
			BlockPart part = new BlockPart(limits[0], limits[1], mapFacesIn, null, true);
			builder.addFaceQuad(face, makeBakedQuad(part, part.mapFaces.get(face), sides[face.getIndex()], face, ModelRotation.X0_Y0, false));
		}
		
		return builder.makeBakedModel();
	}
	

	
	public static class UnpackedQuad {
		UnpackedVertex vertices[] = new UnpackedVertex[4];
		TextureAtlasSprite sprite;
		EnumFacing face;
		int tintIndex;
		boolean applyDiffuseLighting = false;

		public static class UnpackedVertex {
			float x, y, z;
			int color;
			float u, v;
			byte nx, ny, nz;
		}
		
		public UnpackedQuad() {
			for(int i = 0; i < 4; i++) {
				vertices[i] = new UnpackedVertex();
			}
		}
		
		public UnpackedQuad(BakedQuad inQuad) {
			this();
			UnpackedQuad outQuad = new UnpackedQuad();
			int[] vertexDataIn = inQuad.getVertexData();
			outQuad.sprite = inQuad.getSprite();
			outQuad.face = inQuad.getFace();
			outQuad.tintIndex = inQuad.getTintIndex();
			
			int vertexNum = 0;
			for(int vertexPos = 0; vertexPos < vertexDataIn.length; vertexPos += inQuad.getFormat().getIntegerSize()) {
				int vfePos = 0;
				UnpackedQuad.UnpackedVertex outVertex = outQuad.vertices[vertexNum];
				for(VertexFormatElement vfe: inQuad.getFormat().getElements()) {
					switch(vfe.getUsage()) {
						case POSITION:
							if(vfe.getType() == EnumType.FLOAT) {
								outVertex.x = Float.intBitsToFloat(vertexDataIn[vertexPos + vfePos + 0]);
								outVertex.y = Float.intBitsToFloat(vertexDataIn[vertexPos + vfePos + 1]);
								outVertex.z = Float.intBitsToFloat(vertexDataIn[vertexPos + vfePos + 2]);
							} else {
								System.err.println("Unhandled " + vfe.getUsage() + " Data Type: " + vfe.getType());
							}
							break;
						case NORMAL:
							if(vfe.getType() == EnumType.BYTE) {
								int normalData = vertexDataIn[vertexPos + vfePos + 0];
								outVertex.nx = (byte) ((normalData >> 24) * 0xFF);
								outVertex.ny = (byte) ((normalData >> 16) * 0xFF);
								outVertex.nz = (byte) ((normalData >> 8) * 0xFF);
							} else {
								System.err.println("Unhandled " + vfe.getUsage() + " Data Type: " + vfe.getType());
							}
							break;
						case COLOR:
							if(vfe.getType() == EnumType.BYTE) {
								outVertex.color = vertexDataIn[vertexPos + vfePos + 0];
							} else {
								System.err.println("Unhandled " + vfe.getUsage() + " Data Type: " + vfe.getType());
							}
							break;
						case UV:
							if(vfe.getType() == EnumType.FLOAT) {
								outVertex.u = Float.intBitsToFloat(vertexDataIn[vertexPos + vfePos + 0]);
								outVertex.v = Float.intBitsToFloat(vertexDataIn[vertexPos + vfePos + 0]);
							} else {
								System.err.println("Unhandled " + vfe.getUsage() + " Data Type: " + vfe.getType());
							}
							break;
						case PADDING://Do nothing
							break;
						case BLEND_WEIGHT:
						case GENERIC:
						case MATRIX:
						default: System.err.println("Unhandled VertexFormat Element Usage: " + vfe.getUsage());
							break;
					}
					
					vfePos += vfe.getSize() / 4;//Size is always in bytes but we are dealing with an array of int32s
				}
			}
		}
		
		public BakedQuad pack() {
			int data[] = new int[7 * 4];
			int n = 0;
			for(UnpackedVertex v : vertices) {
				data[n++] = Float.floatToIntBits(v.x);
				data[n++] = Float.floatToIntBits(v.y);
				data[n++] = Float.floatToIntBits(v.z);
				data[n++] = v.color;
				data[n++] = Float.floatToIntBits(v.u);
				data[n++] = Float.floatToIntBits(v.v);
				data[n++] = 0;//Padding
			}
			
			return new BakedQuad(data, tintIndex, face, sprite, applyDiffuseLighting, Attributes.DEFAULT_BAKED_FORMAT);
		}
		
		public static List<UnpackedQuad> unpackAll(List<BakedQuad> inQuads) {
			return inQuads.stream().map( in -> new UnpackedQuad(in) ).collect(Collectors.toList());
		}
		
		public static List<BakedQuad> packAll(List<UnpackedQuad> inQuads) {
			return inQuads.stream().map( in -> in.pack() ).collect(Collectors.toList());
		}
	}
	
	
	private BakedQuad makeBakedQuad(BlockPart blockPart, BlockPartFace partFace, TextureAtlasSprite atlasSprite, EnumFacing dir, net.minecraftforge.common.model.ITransformation transform, boolean uvlocked) {
		return new FaceBakery().makeBakedQuad(blockPart.positionFrom, blockPart.positionTo, partFace, atlasSprite, dir, transform, blockPart.partRotation, uvlocked, blockPart.shade);
	}
	
	private Vector3f[] AABBLimits(AxisAlignedBB aabb) {
		return new Vector3f[] {
				new Vector3f((float)aabb.minX, (float)aabb.minY, (float)aabb.minZ),
				new Vector3f((float)aabb.maxX, (float)aabb.maxY, (float)aabb.maxZ),
		};
	}
	
	private float[] getUVs(AxisAlignedBB box, EnumFacing face) {
		switch(face) {
			default:
			case DOWN:  return new float[]{ (float) box.minX, 16f - (float) box.minZ, (float) box.maxX, 16f - (float) box.maxZ };
			case UP:    return new float[]{ (float) box.minX, (float) box.minZ, (float) box.maxX, (float) box.maxZ };
			case NORTH: return new float[]{ 16f - (float) box.maxX, (float) box.minY, 16f - (float) box.minX, (float) box.maxY };
			case SOUTH: return new float[]{ (float) box.minX, (float) box.minY, (float) box.maxX, (float) box.maxY };
			case WEST:  return new float[]{ (float) box.minZ, (float) box.minY, (float) box.maxZ, (float) box.maxY };
			case EAST:  return new float[]{ 16f - (float) box.maxZ, (float) box.minY, 16f - (float) box.minZ, (float) box.maxY };
		}
	}
	
	/**
	 * A Hack to determine the UV face angle for a block column on a certain axis
	 * 
	 * @param axis
	 * @param face
	 * @return
	 */
	private int getFaceAngle (Axis axis, EnumFacing face) {
		if(axis == Axis.Y) { //UP / DOWN
			return 0;
		}
		else if(axis == Axis.Z) {//NORTH / SOUTH
			switch(face) {
				case UP: return 0;
				case WEST: return 270;
				case DOWN: return 180;
				default: return 90;
			}
		} else { //EAST/WEST
			return (face == EnumFacing.NORTH) ? 270 : 90;
		}
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
