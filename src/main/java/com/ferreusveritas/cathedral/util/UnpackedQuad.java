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
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.model.Attributes;

/*
 * A no nonsense accessible quad object for quick manipulation
 */
public class UnpackedQuad {
	public UnpackedVertex vertices[] = new UnpackedVertex[4];
	public TextureAtlasSprite sprite;
	public EnumFacing face;
	public int tintIndex;
	public float area;
	public boolean applyDiffuseLighting = false;

	public static class UnpackedVertex {
		public float x;
		public float y;
		public float z;
		public int color = 0xFFFFFFFF;
		public float u;
		public float v;
		public byte nx, ny, nz;
	}
	
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