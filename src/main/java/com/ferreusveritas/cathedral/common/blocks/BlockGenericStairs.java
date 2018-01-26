package com.ferreusveritas.cathedral.common.blocks;

import com.ferreusveritas.cathedral.CathedralMod;

import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;

public class BlockGenericStairs extends BlockStairs {

	public BlockGenericStairs(String name, IBlockState blockDef) {
		super(blockDef);
		name += "-stairs";
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(CathedralMod.tabCathedral);
		this.useNeighborBrightness = true;
	}

}
