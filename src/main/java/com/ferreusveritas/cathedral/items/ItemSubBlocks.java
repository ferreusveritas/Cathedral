package com.ferreusveritas.cathedral.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlockWithMetadata;
import net.minecraft.item.ItemStack;

public class ItemSubBlocks extends ItemBlockWithMetadata {
	
	public ItemSubBlocks(Block block) {
		super(block, block);
	}
	
	@Override 
	public String getUnlocalizedName(ItemStack stack){
		return getUnlocalizedName() + "_" +  Integer.toHexString(stack.getItemDamage());
	}
	
}
