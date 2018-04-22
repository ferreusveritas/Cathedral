package com.ferreusveritas.cathedral.features.dwemer;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemDoor;

	public class ItemShortDoor extends ItemDoor {

	    public ItemShortDoor(Block block, Material material) {
	    	super(block);
	        this.maxStackSize = 64;
	        this.setCreativeTab(CreativeTabs.REDSTONE);
	    }
	
}
