package com.ferreusveritas.cathedral.features.extras;

import com.ferreusveritas.cathedral.common.blocks.StandardEnum;

import net.minecraft.util.math.MathHelper;

public class FeatureTypes {

	
	public static enum EnumStoneType implements StandardEnum {
		PAVER,
		KNOT;

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

	
}
