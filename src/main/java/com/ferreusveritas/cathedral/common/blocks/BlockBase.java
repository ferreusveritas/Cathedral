package com.ferreusveritas.cathedral.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;

public class BlockBase extends Block {

	public static final PropertyInteger META = PropertyInteger.create("meta", 0, 15);
	
	public BlockBase(Material materialIn, String name) {
		super(materialIn);
		setRegistryName(name);
		setUnlocalizedName(name);
	}
	
}
