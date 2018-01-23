package com.ferreusveritas.stonelore.blocks;

import com.ferreusveritas.stonelore.StoneLore;

import net.minecraft.block.BlockStairs;

public class BlockGenericStairs extends BlockStairs {

	public BlockGenericStairs(BaseBlockDef blockDef) {
		super(blockDef.block, blockDef.metaData);
		setBlockName(StoneLore.MODID + "_" + blockDef.name + "-stairs");
		setCreativeTab(StoneLore.tabStoneLore);
		this.useNeighborBrightness = true;
	}

}
