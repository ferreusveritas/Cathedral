package com.ferreusveritas.cathedral.common.blocks;

import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;

public class BlockStairsGeneric extends BlockStairs {

	public BlockStairsGeneric(String name, IBlockState modelState) {
		super(modelState);
		setRegistryName(name);
		setUnlocalizedName(name);
		this.useNeighborBrightness = true;
	}

}
