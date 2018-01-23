package com.ferreusveritas.cathedral.blocks;

import com.ferreusveritas.cathedral.Cathedral;

import net.minecraft.block.BlockStairs;

public class BlockGenericStairs extends BlockStairs {

	public BlockGenericStairs(BaseBlockDef blockDef) {
		super(blockDef.block, blockDef.metaData);
		setBlockName(Cathedral.MODID + "_" + blockDef.name + "-stairs");
		setCreativeTab(Cathedral.tabCathedral);
		this.useNeighborBrightness = true;
	}

}
