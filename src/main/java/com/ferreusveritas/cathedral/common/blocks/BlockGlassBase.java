package com.ferreusveritas.cathedral.common.blocks;

import net.minecraft.block.BlockGlass;
import net.minecraft.block.material.Material;

public class BlockGlassBase extends BlockGlass {

	public BlockGlassBase(Material materialIn, boolean ignoreSimilarity, String name) {
		super(materialIn, ignoreSimilarity);
		setRegistryName(name);
		setUnlocalizedName(name);
	}
	
}
