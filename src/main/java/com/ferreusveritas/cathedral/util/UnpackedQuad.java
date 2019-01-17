package com.ferreusveritas.cathedral.util;

import java.util.List;
import java.util.stream.Collectors;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.client.renderer.vertex.VertexFormatElement.EnumType;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.Attributes;

/*
 * A no nonsense accessible quad object for quick manipulation
 */
public class UnpackedQuad {
	public UnpackedVertex vertices[] = new UnpackedVertex[4];
	public TextureAtlasSprite sprite;
	EnumFacing face;
	int tintIndex;
	boolean applyDiffuseLighting = false;

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