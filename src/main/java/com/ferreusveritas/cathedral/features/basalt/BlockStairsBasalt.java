package com.ferreusveritas.cathedral.features.basalt;

import com.ferreusveritas.cathedral.CathedralMod;

import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;

public class BlockStairsBasalt extends BlockStairs {

	protected BlockStairsBasalt(String name, IBlockState modelState) {
		super(modelState);
		setRegistryName(name);
		setUnlocalizedName(name);
		setCreativeTab(CathedralMod.tabBasalt);
	}

}
