package com.ferreusveritas.cathedral.common.blocks;

import net.minecraft.block.BlockGlass;
import net.minecraft.block.material.Material;

public class BlockGlassBase extends BlockGlass {

	public BlockGlassBase(String name) {
		super(Material.GLASS, false);
		setRegistryName(name);
		setUnlocalizedName(name);
	}
	
}
