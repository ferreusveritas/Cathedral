package com.ferreusveritas.cathedral.features.dwarven;

import com.ferreusveritas.cathedral.common.blocks.StandardEnum;

import net.minecraft.util.math.MathHelper;

public class FeatureTypes {

	public static enum EnumCarvedType implements StandardEnum {
		
		EMBEDDED,
		PILLAR,
		ALTAR,
		CARVING1,
		CARVING2,
		LAYERED,
		SCALEPILLAR,	
		WORMGEAR,
		RAYS,
		KNOT,
		MASK,
		PANEL,
		VENT;	
		
		public int getMetadata() {
			return ordinal();
		}
		
		@Override
		public String toString() {
			return getName();
		}
		
		public static EnumCarvedType byMetadata(int meta) {
			return values()[MathHelper.clamp(meta, 0, values().length - 1)];
		}
		
	}

	
	public static enum EnumLightType implements StandardEnum {
		
		PATH,
		VENT,
		GAS;
		
		@Override
		public int getMetadata() {
			return ordinal();
		}

		@Override
		public String toString() {
			return getName();
		}

		public static EnumLightType byMetadata(int meta) {
			return values()[MathHelper.clamp(meta, 0, values().length - 1)];
		}

	}
	
	public static enum EnumGlassType implements StandardEnum {
		FENCE,
		ORNATE;

		@Override
		public int getMetadata() {
			return ordinal();
		}

		@Override
		public String toString() {
			return getName();
		}

		public static EnumGlassType byMetadata(int meta) {
			return values()[MathHelper.clamp(meta, 0, values().length - 1)];
		}
		
		
	}
	
}
