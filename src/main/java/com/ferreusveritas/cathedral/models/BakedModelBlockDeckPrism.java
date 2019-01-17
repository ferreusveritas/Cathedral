package com.ferreusveritas.cathedral.models;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
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
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.client.MinecraftForgeClient;
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
		
		//System.out.println();
		//System.out.print("################## getQuads ##################\n");
		
		List<BakedQuad> quads = new ArrayList<BakedQuad>();
		
		if (state != null && state.getBlock() instanceof IMimic && state instanceof IExtendedBlockState) {
			IExtendedBlockState extendedState = ((IExtendedBlockState) state);
			IBlockState mimicState = extendedState.getValue(MimicProperty.MIMIC);
			
			Minecraft mc = Minecraft.getMinecraft();
			BlockRendererDispatcher blockRendererDispatcher = mc.getBlockRendererDispatcher();
			BlockModelShapes blockModelShapes = blockRendererDispatcher.getBlockModelShapes();
			IBakedModel mimicModel = blockModelShapes.getModelForState(mimicState);
			
			Map<EnumFacing, List<BakedQuad> > quadMap = new HashMap<>(); 
			
			for(EnumFacing dir : EnumFacing.values()) {
				quadMap.computeIfAbsent(dir, d -> new ArrayList<BakedQuad>()).addAll(mimicModel.getQuads(mimicState, dir, rand));
			}
			quadMap.computeIfAbsent(null, d -> new ArrayList<BakedQuad>()).addAll(mimicModel.getQuads(mimicState, null, rand));
			
			BakedQuad sides[] = new BakedQuad[6];
			
			for(EnumFacing dir : EnumFacing.values()) {
				if(quadMap.get(dir).size() > 0) {
					sides[dir.getIndex()] = quadMap.get(dir).get(0);
				} else {
					for(BakedQuad q : quadMap.get(null)) {
						if(q.getFace() == dir) {
							sides[dir.getIndex()] = q;
						}
					}
				}
			}
			
			quadMap.clear();
			
			for(EnumFacing dir : EnumFacing.HORIZONTALS) {
				List<BakedQuad> list = quadMap.computeIfAbsent(dir, d -> new ArrayList<BakedQuad>());
				if(sides[dir.getIndex()] != null) {
					list.add(sides[dir.getIndex()]);
				}
			}
			
			if(sides[EnumFacing.UP.getIndex()] != null) {
				List<BakedQuad> list = quadMap.computeIfAbsent(EnumFacing.UP, d -> new ArrayList<BakedQuad>());
				UnpackedQuad upq;
				float amount;
				
				upq = new UnpackedQuad(sides[EnumFacing.UP.getIndex()]);
				amount = 0.5f + 3/16f;
				upq.vertices[0].x += amount;
				upq.vertices[0].u += (upq.sprite.getMaxU() - upq.sprite.getMinU()) * amount; 
				upq.vertices[1].x += amount;
				upq.vertices[1].u += (upq.sprite.getMaxU() - upq.sprite.getMinU()) * amount; 
				list.add(upq.pack());
				
				upq = new UnpackedQuad(sides[EnumFacing.UP.getIndex()]);
				amount = 0.5f + 3/16f;
				upq.vertices[2].x -= amount;
				upq.vertices[2].u -= (upq.sprite.getMaxU() - upq.sprite.getMinU()) * amount; 
				upq.vertices[3].x -= amount;
				upq.vertices[3].u -= (upq.sprite.getMaxU() - upq.sprite.getMinU()) * amount; 
				list.add(upq.pack());
				
				upq = new UnpackedQuad(sides[EnumFacing.UP.getIndex()]);
				amount = 0.5f + 3/16f;
				upq.vertices[0].z += amount;
				upq.vertices[0].v += (upq.sprite.getMaxU() - upq.sprite.getMinU()) * amount; 
				upq.vertices[3].z += amount;
				upq.vertices[3].v += (upq.sprite.getMaxU() - upq.sprite.getMinU()) * amount; 
				list.add(upq.pack());

				upq = new UnpackedQuad(sides[EnumFacing.UP.getIndex()]);
				amount = 0.5f + 3/16f;
				upq.vertices[1].z -= amount;
				upq.vertices[1].v -= (upq.sprite.getMaxU() - upq.sprite.getMinU()) * amount; 
				upq.vertices[2].z -= amount;
				upq.vertices[2].v -= (upq.sprite.getMaxU() - upq.sprite.getMinU()) * amount; 
				list.add(upq.pack());				
			}

			{
				List<BakedQuad> list = quadMap.computeIfAbsent(null, d -> new ArrayList<BakedQuad>());
				
				for(EnumFacing dir: EnumFacing.HORIZONTALS) {
					float amount = 0.5f + 3/16f;
					if(sides[dir.getIndex()] != null) {
						UnpackedQuad q = new UnpackedQuad(sides[dir.getIndex()]);
						EnumFacing CW = dir.rotateY();
						for(int v = 0; v < 4; v++) {
							q.vertices[v].x += dir.getOpposite().getFrontOffsetX() * amount;
							q.vertices[v].y += dir.getOpposite().getFrontOffsetY() * amount;
							q.vertices[v].z += dir.getOpposite().getFrontOffsetZ() * amount;
							q.vertices[v].color = 0xFF999999;
						}
						q.vertices[0].x -= CW.getFrontOffsetX() * 5/16f;
						q.vertices[0].y -= CW.getFrontOffsetY() * 5/16f;
						q.vertices[0].z -= CW.getFrontOffsetZ() * 5/16f;
						q.vertices[0].u += (q.sprite.getMaxU() - q.sprite.getMinU()) * 5/16f;

						q.vertices[1].x -= CW.getFrontOffsetX() * 5/16f;
						q.vertices[1].y -= CW.getFrontOffsetY() * 5/16f;
						q.vertices[1].z -= CW.getFrontOffsetZ() * 5/16f;
						q.vertices[1].u += (q.sprite.getMaxU() - q.sprite.getMinU()) * 5/16f;

						q.vertices[2].x += CW.getFrontOffsetX() * 5/16f;
						q.vertices[2].y += CW.getFrontOffsetY() * 5/16f;
						q.vertices[2].z += CW.getFrontOffsetZ() * 5/16f;
						q.vertices[2].u -= (q.sprite.getMaxU() - q.sprite.getMinU()) * 5/16f;

						q.vertices[3].x += CW.getFrontOffsetX() * 5/16f;
						q.vertices[3].y += CW.getFrontOffsetY() * 5/16f;
						q.vertices[3].z += CW.getFrontOffsetZ() * 5/16f;
						q.vertices[3].u -= (q.sprite.getMaxU() - q.sprite.getMinU()) * 5/16f;
						
						list.add(q.pack());
					}
				}
			}
			
			if(sides[EnumFacing.DOWN.getIndex()] != null) {
				List<BakedQuad> list = quadMap.computeIfAbsent(EnumFacing.DOWN, d -> new ArrayList<BakedQuad>());
				UnpackedQuad upq;
				float amount;
				
				upq = new UnpackedQuad(sides[EnumFacing.DOWN.getIndex()]);
				amount = 0.5f + 3/16f;
				upq.vertices[0].x += amount;
				upq.vertices[0].u += (upq.sprite.getMaxU() - upq.sprite.getMinU()) * amount; 
				upq.vertices[1].x += amount;
				upq.vertices[1].u += (upq.sprite.getMaxU() - upq.sprite.getMinU()) * amount; 
				list.add(upq.pack());
				
				upq = new UnpackedQuad(sides[EnumFacing.DOWN.getIndex()]);
				amount = 0.5f + 3/16f;
				upq.vertices[2].x -= amount;
				upq.vertices[2].u -= (upq.sprite.getMaxU() - upq.sprite.getMinU()) * amount; 
				upq.vertices[3].x -= amount;
				upq.vertices[3].u -= (upq.sprite.getMaxU() - upq.sprite.getMinU()) * amount; 
				list.add(upq.pack());
				
				
				upq = new UnpackedQuad(sides[EnumFacing.DOWN.getIndex()]);
				amount = 0.5f + 3/16f;
				upq.vertices[0].z -= amount;
				upq.vertices[0].v += (upq.sprite.getMaxU() - upq.sprite.getMinU()) * amount; 
				upq.vertices[3].z -= amount;
				upq.vertices[3].v += (upq.sprite.getMaxU() - upq.sprite.getMinU()) * amount; 
				list.add(upq.pack());

				
				upq = new UnpackedQuad(sides[EnumFacing.DOWN.getIndex()]);
				amount = 0.5f + 3/16f;
				upq.vertices[1].z += amount;
				upq.vertices[1].v -= (upq.sprite.getMaxU() - upq.sprite.getMinU()) * amount; 
				upq.vertices[2].z += amount;
				upq.vertices[2].v -= (upq.sprite.getMaxU() - upq.sprite.getMinU()) * amount; 
				list.add(upq.pack());
				
			}
			
			//System.out.print("State: " + mimicState + "\n");
			
			/*
			//XXX: Ignore this
			//holdQuad.getVertexData();
			
			if(holdQuad != null && holdQuad.getFace() == EnumFacing.UP) {
				holdQuad = new BakedQuad( holdQuad.getVertexData(), -1, EnumFacing.UP, holdSprite, false, holdQuad.getFormat());
				quads.add(holdQuad);
			}
			*/
			
			if(MinecraftForgeClient.getRenderLayer() == BlockRenderLayer.TRANSLUCENT) {
				
				List<BakedQuad> q = quadMap.get(side);
				if(q != null) {
					quads.addAll(q);
				}
				
				quads.addAll(prismModel.getQuads(state, side, rand));
			}
			

		
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
			int color = 0xFFFFFFFF;
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
			int[] vertexDataIn = inQuad.getVertexData();
			sprite = inQuad.getSprite();
			face = inQuad.getFace();
			tintIndex = inQuad.getTintIndex();
			
			int vertexNum = 0;
			for(int vertexPos = 0; vertexPos < vertexDataIn.length; vertexPos += inQuad.getFormat().getIntegerSize()) {
				int vfePos = 0;
				UnpackedQuad.UnpackedVertex outVertex = vertices[vertexNum++];
				System.out.print(vertexNum + ": ");
				for(VertexFormatElement vfe: inQuad.getFormat().getElements()) {
					System.out.print(vfe.getUsage() + ":" + vfe.getSize() + " ");
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
								outVertex.nx = (byte) ((normalData >> 0) & 0xFF);
								outVertex.ny = (byte) ((normalData >> 8) & 0xFF);
								outVertex.nz = (byte) ((normalData >> 16) & 0xFF);
							} else {
								System.err.println("Unhandled " + vfe.getUsage() + " Data Type: " + vfe.getType());
							}
							break;
						case COLOR:
							if(vfe.getType() == EnumType.UBYTE) {
								outVertex.color = vertexDataIn[vertexPos + vfePos + 0];
							} else {
								System.err.println("Unhandled " + vfe.getUsage() + " Data Type: " + vfe.getType());
							}
							break;
						case UV:
							if(vfe.getType() == EnumType.FLOAT) {
								outVertex.u = Float.intBitsToFloat(vertexDataIn[vertexPos + vfePos + 0]);
								outVertex.v = Float.intBitsToFloat(vertexDataIn[vertexPos + vfePos + 1]);
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
				System.out.println();
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
				data[n++] = v.nx | v.ny << 8 | v.nz << 16;//Normal + Padding
			}
			
			return new BakedQuad(data, tintIndex, face, sprite, applyDiffuseLighting, Attributes.DEFAULT_BAKED_FORMAT);
		}
		
		public static List<UnpackedQuad> unpackAll(List<BakedQuad> inQuads) {
			return inQuads.stream().map( in -> new UnpackedQuad(in) ).collect(Collectors.toList());
		}
		
		public static List<BakedQuad> packAll(List<UnpackedQuad> inQuads) {
			return inQuads.stream().map( in -> in.pack() ).collect(Collectors.toList());
		}
		
		public void print() {
			String iconName = sprite.getIconName();
			System.out.print("Icon: " + iconName + "\n");
			System.out.print("Face: " + face + "\n");
			
			for(int i = 0; i < 4; i++) {
				System.out.print("Pos:[" + vertices[i].x + ", " + vertices[i].y + ", " + vertices[i].z + "] ");
				System.out.print(String.format("Color:#%08X ", vertices[i].color) + " UV:[" + vertices[i].u + ", " + vertices[i].v + "] ");
				System.out.print("Normal:[" + vertices[i].nx + ", " + vertices[i].ny + ", " + vertices[i].nz + "] ");
				System.out.println();
			}
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
