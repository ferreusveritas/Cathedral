package com.ferreusveritas.cathedral.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.client.renderer.vertex.VertexFormatElement.EnumType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.model.Attributes;

/**
 * A no nonsense accessible quad object for quick manipulation
 * 
 * @author ferreusveritas
 */
public class UnpackedQuad {
	public UnpackedVertex vertices[] = new UnpackedVertex[4];
	public TextureAtlasSprite sprite;
	public EnumFacing face;
	public int tintIndex;
	public float area;
	public boolean applyDiffuseLighting = true;
	
	public UnpackedQuad() {
		for(int i = 0; i < 4; i++) {
			vertices[i] = new UnpackedVertex();
		}
	}
	
	public UnpackedQuad(UnpackedQuad o) {
		face = o.face;
		sprite = o.sprite;
		area = o.area;
		tintIndex = o.tintIndex;
		applyDiffuseLighting = o.applyDiffuseLighting;
		for(int i = 0; i < 4; i++) {
			UnpackedVertex vert = vertices[i] = new UnpackedVertex();
			UnpackedVertex oVert = o.vertices[i];
			vert.x = oVert.x;
			vert.y = oVert.y;
			vert.z = oVert.z;
			vert.color = oVert.color;
			vert.u = oVert.u;
			vert.v = oVert.v;
			vert.nx = oVert.nx;
			vert.ny = oVert.ny;
			vert.nz = oVert.nz;
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
			UnpackedVertex outVertex = vertices[vertexNum++];
			//System.out.print(vertexNum + ": ");
			for(VertexFormatElement vfe: inQuad.getFormat().getElements()) {
				//System.out.print(vfe.getUsage() + ":" + vfe.getSize() + " ");
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
			//System.out.println();
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
	
	public float calcArea() {
		float minX = Float.NaN;
		float minY = Float.NaN;
		float minZ = Float.NaN;
		float maxX = Float.NaN;
		float maxY = Float.NaN;
		float maxZ = Float.NaN;
		
		for(UnpackedVertex v : vertices) {
			minX = minX < v.x ? minX : v.x;
			minY = minY < v.y ? minY : v.y;
			minZ = minZ < v.z ? minZ : v.z;
			maxX = maxX > v.x ? maxX : v.x;
			maxY = maxY > v.y ? maxY : v.y;
			maxZ = maxZ > v.z ? maxZ : v.z;
		}

		switch(face.getAxis()) {
			case X: area = (maxY - minY) * (maxZ - minZ);
			case Y: area = (maxX - minX) * (maxZ - minZ);
			case Z: area = (maxX - minX) * (maxY - minY);
			default: area = 0;
		}
		
		return area;
	}
	
	/**
	 * Takes the quad and stretches it out to fill the side of the block and sets the UV coordinates for the
	 * texture to be the full texture for the sprite.
	 * 
	 * @return
	 */
	public UnpackedQuad normalize() {

		{//Snap the corners of the quads texture to the closest corners of the sprite
			float corners[] = new float[] { sprite.getMinU(), sprite.getMinV(), sprite.getMaxU(), sprite.getMaxV() };
			List<UnpackedVertex> verts = Lists.newArrayList(vertices);//Temporary list so we can remove elements as they are organized
			for(int c = 0; c < 4; c++) {//Iterate over each corner of the sprite
				float cU = corners[c & 2];//0 or 2
				float cV = corners[((c & 1) * 2) + 1];//1 or 3
				float minDelta = 999f;
				UnpackedVertex nearest = null;
				for(UnpackedVertex v : verts) {//Find the vertex that has the closest UV coordinates to this corner
					float delU = v.u - cU;
					float delV = v.v - cV;
					float delta = delU * delU + delV * delV;
					if(delta < minDelta) {
						minDelta = delta;
						nearest = v;
					}
				}
				verts.remove(nearest);//Remove this vertex from the temporary list so it's not chosen again by another corner
				nearest.u = cU;
				nearest.v = cV;
			}
		}
		
		{//Snap the corners of quad to the closest corners of the full cube
			
			Axis axis = face.getAxis();
			float limit = MathHelper.clamp(face.getAxisDirection().getOffset(), 0, 1);
			
			for(UnpackedVertex v : vertices) {//Push the quads all the way to the sides of the block
				switch(axis) {
					case X: v.x = limit; break;
					case Y: v.y = limit; break;
					case Z: v.z = limit; break;
				}
			}
			
			List<Vec3d> corners = new ArrayList<>();
			for(int c = 0; c < 8; c++) {//Iterate over each corner of the volume
				double x = axis == Axis.X ? limit : (c >> 2) & 1;
				double y = axis == Axis.Y ? limit : (c >> 1) & 1;
				double z = axis == Axis.Z ? limit : (c >> 0) & 1;
				Vec3d v = new Vec3d(x, y, z);
				if(!corners.contains(v)) {
					corners.add(v);
				}
			}
			
			List<UnpackedVertex> verts = Lists.newArrayList(vertices);//Temporary list so we can remove elements as they are organized
			for(Vec3d corn: corners) {//Iterate over each corner of the volume
				double minDelta = 999f;
				UnpackedVertex nearest = null;
				for(UnpackedVertex v : verts) {//Find the vertex that has the closest UV coordinates to this corner
					Vec3d vert = new Vec3d(v.x, v.y, v.z);
					double delta = corn.squareDistanceTo(vert);
					if(delta < minDelta) {
						minDelta = delta;
						nearest = v;
					}
				}
				verts.remove(nearest);//Remove this vertex from the temporary list so it's not chosen again by another corner
				nearest.x = (float) corn.x;
				nearest.y = (float) corn.y;
				nearest.z = (float) corn.z;
			}
		}
		
		return this;
	}
	
	public UnpackedQuad move(Vec3d offset) {
		for(UnpackedVertex v : vertices) {
			v.x += offset.x;
			v.y += offset.y;
			v.z += offset.z;
		}
		return this;
	}
	
	public UnpackedQuad color(float srcR, float srcG, float srcB, float srcA) {
		for(UnpackedVertex v : vertices) {
			int dstA = (int) (((v.color >> 24) & 0xff) * srcA);
			int dstR = (int) (((v.color >> 16) & 0xff) * srcR);
			int dstG = (int) (((v.color >>  8) & 0xff) * srcG);
			int dstB = (int) (((v.color >>  0) & 0xff) * srcB);
			v.color = dstA << 24 | dstB << 16 | dstG << 8 | dstR;
		}
		return this;
	}
	
	public UnpackedQuad color(float srcR, float srcG, float srcB) {
		return color(srcR, srcG, srcB, 1.0f);
	}

	public UnpackedQuad color(float factor) {
		return color(factor, factor, factor, 1.0f);
	}
	
	public UnpackedQuad color(int color) {
		float srcA = ((color >> 24) & 0xff) / 255f;
		float srcR = ((color >> 16) & 0xff) / 255f;
		float srcG = ((color >>  8) & 0xff) / 255f;
		float srcB = ((color >>  0) & 0xff) / 255f;
		return color(srcR, srcG, srcB, srcA);
	}
	
	/**
	 * This should only be used on a normalized quad
	 * 
	 * @param aabb
	 * @return
	 */
	public UnpackedQuad crop(AxisAlignedBB aabb) {
				
		UnpackedVertex[] v = new UnpackedVertex[5];
		v[4] = new UnpackedVertex();//Dummy vertex
		
		//Sort the vertices depending on the face. This gives us a
		//standard and predictable vertex arrangement.
		int[] sortData = { 0x5401, 0x6732, 0x2046, 0x7513, 0x3102, 0x6457 };
		//				down	up  	north	south	west	east
		//		<<	v#	rxyz	rxyz	rxyz	rxyz	rxyz	rxyz
		//		0	0	0001	0010	0110	0011	0010	0111
		//		4	1	0000	0011	0100	0001	0000	0101
		//		8	2	0100	0111	0000	0101	0001	0100
		//		12	3	0101	0110	0010	0111	0011	0110
		
		for(int i = 0; i < 4; i++) {
			int d = (sortData[face.getIndex()] >> (i * 4)) & 0x7;
			v[i] = getClosestVertex( (d >> 2) & 1, (d >> 1) & 1, (d >> 0) & 1, vertices);	
		}
		
		int[][] mapping = new int[][]{
			{ 0x0144, 0x2344, 0x0123, 0x4444, 0x1244, 0x0344 },//D
			{ 0x0144, 0x2344, 0x4444, 0x0123, 0x0344, 0x1244 },//U
			{ 0x2344, 0x0144, 0x1244, 0x0344, 0x0123, 0x4444 },//N
			{ 0x0144, 0x2344, 0x1244, 0x0344, 0x4444, 0x0123 },//S
			{ 0x0123, 0x4444, 0x1244, 0x0344, 0x0144, 0x2344 },//W
			{ 0x4444, 0x0123, 0x1244, 0x0344, 0x2344, 0x0144 } //E
		};

		int[] map = mapping[face.getIndex()];
		
		v[nyb(map[0],12)].x = v[nyb(map[0],8)].x = v[nyb(map[0],4)].x = v[nyb(map[0],0)].x = (float) aabb.minX;
		v[nyb(map[1],12)].x = v[nyb(map[1],8)].x = v[nyb(map[1],4)].x = v[nyb(map[1],0)].x = (float) aabb.maxX;
		v[nyb(map[2],12)].y = v[nyb(map[2],8)].y = v[nyb(map[2],4)].y = v[nyb(map[2],0)].y = (float) aabb.minY;
		v[nyb(map[3],12)].y = v[nyb(map[3],8)].y = v[nyb(map[3],4)].y = v[nyb(map[3],0)].y = (float) aabb.maxY;
		v[nyb(map[4],12)].z = v[nyb(map[4],8)].z = v[nyb(map[4],4)].z = v[nyb(map[4],0)].z = (float) aabb.minZ;
		v[nyb(map[5],12)].z = v[nyb(map[5],8)].z = v[nyb(map[5],4)].z = v[nyb(map[5],0)].z = (float) aabb.maxZ;
		
		switch(face) {
			case DOWN:// -Y
				v[0].u = v[1].u = sprite.getInterpolatedU(aabb.minX * 16);
				v[2].u = v[3].u = sprite.getInterpolatedU(aabb.maxX * 16);
				v[1].v = v[2].v = sprite.getInterpolatedV((1 - aabb.minZ) * 16);
				v[0].v = v[3].v = sprite.getInterpolatedV((1 - aabb.maxZ) * 16);
				break;
				
			case UP:// +Y
				v[0].u = v[1].u = sprite.getInterpolatedU(aabb.minX * 16);
				v[2].u = v[3].u = sprite.getInterpolatedU(aabb.maxX * 16);
				v[0].v = v[3].v = sprite.getInterpolatedV(aabb.minZ * 16);
				v[1].v = v[2].v = sprite.getInterpolatedV(aabb.maxZ * 16);
				break;
			
			case NORTH:// -Z
				v[2].u = v[3].u = sprite.getInterpolatedU((1 - aabb.minX) * 16);
				v[0].u = v[1].u = sprite.getInterpolatedU((1 - aabb.maxX) * 16);
				v[1].v = v[2].v = sprite.getInterpolatedV((1 - aabb.minY) * 16);
				v[0].v = v[3].v = sprite.getInterpolatedV((1 - aabb.maxY) * 16);
				break;
				
			case SOUTH:// +Z
				v[0].u = v[1].u = sprite.getInterpolatedU(aabb.minX * 16);
				v[2].u = v[3].u = sprite.getInterpolatedU(aabb.maxX * 16);
				v[1].v = v[2].v = sprite.getInterpolatedV((1 - aabb.minY) * 16);
				v[0].v = v[3].v = sprite.getInterpolatedV((1 - aabb.maxY) * 16);
				break;
				
			case WEST:// -X
				v[0].u = v[1].u = sprite.getInterpolatedU(aabb.minZ * 16);
				v[2].u = v[3].u = sprite.getInterpolatedU(aabb.maxZ * 16);
				v[1].v = v[2].v = sprite.getInterpolatedV((1 - aabb.minY) * 16);
				v[0].v = v[3].v = sprite.getInterpolatedV((1 - aabb.maxY) * 16);
				break;
				
			case EAST:// +X
				v[2].u = v[3].u = sprite.getInterpolatedU((1 - aabb.minZ) * 16);
				v[0].u = v[1].u = sprite.getInterpolatedU((1 - aabb.maxZ) * 16);
				v[1].v = v[2].v = sprite.getInterpolatedV((1 - aabb.minY) * 16);
				v[0].v = v[3].v = sprite.getInterpolatedV((1 - aabb.maxY) * 16);
				break;
		}
		
		return this;
	}
	
	private int nyb(int val, int shift) {
		return (val >> shift) & 0x0f;
	}
	
	public UnpackedVertex getClosestVertex(float x, float y, float z, UnpackedVertex[] vertices) {
		float minDelta = 999f;
		UnpackedVertex minVert = null;
		for(UnpackedVertex vert : vertices) {
			float delX = vert.x - x;
			float delY = vert.y - y;
			float delZ = vert.z - z;
			float delta = delX * delX + delY * delY + delZ * delZ;
			if(delta < minDelta) {
				minDelta = delta;
				minVert = vert;
			}
		}
		
		return minVert;
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