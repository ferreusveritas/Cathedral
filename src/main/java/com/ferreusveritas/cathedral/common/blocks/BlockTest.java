package com.ferreusveritas.cathedral.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockTest {
	
	Block block = new BlockMultiVariant<EnumType>(Material.ROCK, EnumType.class);
	
	public static enum EnumType implements StandardEnum {
		
		PAVER,
		POISON,
		SUNKENPANEL,
		VAULT,
		SUNKEN,
		KNOT;
		
		@Override
		public String toString() {
			return getName();
		}

		@Override
		public String getBlockName() {
			return "test";
		}
	}
	
}
