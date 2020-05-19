package com.ferreusveritas.cathedral.features.extras;

import com.ferreusveritas.cathedral.common.blocks.StandardEnum;

import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class FeatureTypes {
	
	public static enum EnumStoneType implements StandardEnum {
		PAVER,
		KNOT,
		EMBEDDED,
		PILLAR,
		VAULT,
		METALKNOT;
		
		@Override
		public int getMetadata() {
			return ordinal();
		}
		
		@Override
		public String toString() {
			return getName();
		}
		
		public static EnumStoneType byMetadata(int meta) {
			return values()[MathHelper.clamp(meta, 0, values().length - 1)];
		}
	
	}
	
	public static enum EnumEndStoneType implements StandardEnum {
		PAVER,
		CHECKERED;
		
		@Override
		public int getMetadata() {
			return ordinal();
		}
		
		@Override
		public String toString() {
			return getName();
		}
		
		public static EnumEndStoneType byMetadata(int meta) {
			return values()[MathHelper.clamp(meta, 0, values().length - 1)];
		}
	
	}

	public static enum EnumEndStoneSlabType implements IStringSerializable {

		RAW("raw", new ResourceLocation("chisel", "endstone"), 0), // <-- Redo
		SMALLTILES("smalltiles", new ResourceLocation("chisel", "endstone"), 8),
		LAYERS("layers", new ResourceLocation("chisel", "endstone"), 15),
		BRICKS("bricks", new ResourceLocation("chisel", "endstone2"), 0),
		SMALLBRICKS("smallbricks", new ResourceLocation("chisel", "endstone2"), 1),
		TILES("tiles", new ResourceLocation("chisel", "endstone2"), 3),
		SLABS("slabs", new ResourceLocation("cathedral", "extras_block_endstone"), 0),
		CHECKERED("checkered", new ResourceLocation("cathedral", "extras_block_endstone"), 1);
		
		private ResourceLocation baseResourceLocation;
		private String name;
		private int baseMeta;
		
		EnumEndStoneSlabType(String name, ResourceLocation baseResourceLocation, int baseMeta) {
			this.name = name;
			this.baseResourceLocation = baseResourceLocation;
			this.baseMeta = baseMeta;
		}

		public int getMetadata() {
			return ordinal();
		}

		@Override
		public String getName() {
			return name;
		}

		public static EnumEndStoneSlabType byMetadata(int meta) {
			return values()[meta];
		}

		public String getUnlocalizedName() {
			return name;
		}
		
		public ResourceLocation getBaseResourceLocation() {
			return baseResourceLocation;
		}
	
		public int getBaseMeta() {
			return baseMeta;
		}
	}
	
	
	public static enum EnumLimestoneSlabType implements IStringSerializable {

		RAW("raw", new ResourceLocation("chisel", "limestone2"), 7),
		SMALLTILES("smalltiles", new ResourceLocation("chisel", "limestone"), 8),
		LAYERS("layers", new ResourceLocation("chisel", "limestone"), 15),
		BRICKS("bricks", new ResourceLocation("chisel", "limestone2"), 0),
		SMALLBRICKS("smallbricks", new ResourceLocation("chisel", "limestone2"), 1),
		TILES("tiles", new ResourceLocation("chisel", "limestone2"), 3);
		
		private ResourceLocation baseResourceLocation;
		private String name;
		private int baseMeta;
		
		EnumLimestoneSlabType(String name, ResourceLocation baseResourceLocation, int baseMeta) {
			this.name = name;
			this.baseResourceLocation = baseResourceLocation;
			this.baseMeta = baseMeta;
		}

		public int getMetadata() {
			return ordinal();
		}

		@Override
		public String getName() {
			return name;
		}

		public static EnumLimestoneSlabType byMetadata(int meta) {
			return values()[meta];
		}

		public String getUnlocalizedName() {
			return name;
		}
		
		public ResourceLocation getBaseResourceLocation() {
			return baseResourceLocation;
		}
	
		public int getBaseMeta() {
			return baseMeta;
		}
	}
	
	
	public static enum EnumMarbleSlabType implements IStringSerializable {

		RAW("raw", new ResourceLocation("chisel", "marble2"), 7),
		SMALLTILES("smalltiles", new ResourceLocation("chisel", "marble"), 8),
		LAYERS("layers", new ResourceLocation("chisel", "marble"), 15),
		BRICKS("bricks", new ResourceLocation("chisel", "marble2"), 0),
		SMALLBRICKS("smallbricks", new ResourceLocation("chisel", "marble2"), 1),
		TILES("tiles", new ResourceLocation("chisel", "marble2"), 3);
		
		private ResourceLocation baseResourceLocation;
		private String name;
		private int baseMeta;
		
		EnumMarbleSlabType(String name, ResourceLocation baseResourceLocation, int baseMeta) {
			this.name = name;
			this.baseResourceLocation = baseResourceLocation;
			this.baseMeta = baseMeta;
		}

		public int getMetadata() {
			return ordinal();
		}

		@Override
		public String getName() {
			return name;
		}

		public static EnumMarbleSlabType byMetadata(int meta) {
			return values()[meta];
		}

		public String getUnlocalizedName() {
			return name;
		}
		
		public ResourceLocation getBaseResourceLocation() {
			return baseResourceLocation;
		}
	
		public int getBaseMeta() {
			return baseMeta;
		}
	}
	
}
