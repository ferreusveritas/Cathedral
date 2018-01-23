package com.ferreusveritas.stonelore.gargoyle;

import com.ferreusveritas.stonelore.StoneLore;
import com.ferreusveritas.stonelore.basalt.Basalt;
import com.ferreusveritas.stonelore.dwemer.Dwemer;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class Gargoyle {

	public static BlockGargoyle gargoyleBlock;
	public static String types[] = {"stone", "sandstone", "netherbrick", "obsidian", "dwemer", "packedice", "endstone", "basalt", "marble", "limestone", "snow"};
	
	//public static Block whiteTest;
	
	public static void preInit(StoneLore lore){
		gargoyleBlock = new BlockGargoyle();
		GameRegistry.registerBlock(gargoyleBlock, ItemGargoyle.class, "gargoyle");
		TileEntity.addMapping(EntityGargoyle.class, "gargoyle");
		
		//whiteTest = new BlockStone().setBlockTextureName(StoneLore.MODID + ":" + "white").setBlockName(StoneLore.MODID + "_" + "whiteTest").setCreativeTab(StoneLore.tabStoneLore);
		//GameRegistry.registerBlock(whiteTest, "whitetest");
	}

	public static void init(StoneLore lore){

		Block chiselMarble = GameRegistry.findBlock("chisel", "marble");
		Block chiselLimestone = GameRegistry.findBlock("chisel", "limestone");

		GameRegistry.addRecipe(new ItemStack(gargoyleBlock, 1, 0), "X X", "XXX", " X ", 'X', new ItemStack(Blocks.stone));
		GameRegistry.addRecipe(new ItemStack(gargoyleBlock, 1, 1), "X X", "XXX", " X ", 'X', new ItemStack(Blocks.sandstone));
		GameRegistry.addRecipe(new ItemStack(gargoyleBlock, 1, 2), "X X", "XXX", " X ", 'X', new ItemStack(Blocks.nether_brick));
		GameRegistry.addRecipe(new ItemStack(gargoyleBlock, 1, 3), "X X", "XXX", " X ", 'X', new ItemStack(Blocks.obsidian));
		GameRegistry.addRecipe(new ItemStack(gargoyleBlock, 1, 4), "X X", "XXX", " X ", 'X', new ItemStack(Dwemer.dwemerBlock, 1, 14));
		GameRegistry.addRecipe(new ItemStack(gargoyleBlock, 1, 5), "X X", "XXX", " X ", 'X', new ItemStack(Blocks.packed_ice));
		GameRegistry.addRecipe(new ItemStack(gargoyleBlock, 1, 6), "X X", "XXX", " X ", 'X', new ItemStack(Blocks.end_stone));
		GameRegistry.addRecipe(new ItemStack(gargoyleBlock, 1, 7), "X X", "XXX", " X ", 'X', new ItemStack(Basalt.basaltBase, 1, 3));
		GameRegistry.addRecipe(new ItemStack(gargoyleBlock, 1, 8), "X X", "XXX", " X ", 'X', new ItemStack(chiselMarble, 1, 0));
		GameRegistry.addRecipe(new ItemStack(gargoyleBlock, 1, 9), "X X", "XXX", " X ", 'X', new ItemStack(chiselLimestone, 1, 0));
		GameRegistry.addRecipe(new ItemStack(gargoyleBlock, 1, 10), "X X", "XXX", " X ", 'X', new ItemStack(Blocks.snow));

	}

	public static void postInit(StoneLore lore){
	}

}
