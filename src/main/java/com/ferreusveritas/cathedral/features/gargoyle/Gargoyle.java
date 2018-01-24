package com.ferreusveritas.cathedral.features.gargoyle;

public class Gargoyle {

	public static BlockGargoyle gargoyleBlock;
	public static String types[] = {"stone", "sandstone", "netherbrick", "obsidian", "dwemer", "packedice", "endstone", "basalt", "marble", "limestone", "snow"};
	
	public static void preInit(){
		gargoyleBlock = new BlockGargoyle();
		//GameRegistry.registerBlock(gargoyleBlock, ItemGargoyle.class, "gargoyle");
		//TileEntity.addMapping(EntityGargoyle.class, "gargoyle");
		
		//whiteTest = new BlockStone().setBlockTextureName(StoneLore.MODID + ":" + "white").setBlockName(StoneLore.MODID + "_" + "whiteTest").setCreativeTab(StoneLore.tabStoneLore);
		//GameRegistry.registerBlock(whiteTest, "whitetest");
	}

	public static void init(){

		/*Block chiselMarble = GameRegistry.findBlock("chisel", "marble");
		Block chiselLimestone = GameRegistry.findBlock("chisel", "limestone");

		GameRegistry.addRecipe(new ItemStack(gargoyleBlock, 1, 0), "X X", "XXX", " X ", 'X', new ItemStack(Blocks.STONE));
		GameRegistry.addRecipe(new ItemStack(gargoyleBlock, 1, 1), "X X", "XXX", " X ", 'X', new ItemStack(Blocks.SANDSTONE));
		GameRegistry.addRecipe(new ItemStack(gargoyleBlock, 1, 2), "X X", "XXX", " X ", 'X', new ItemStack(Blocks.NETHER_BRICK));
		GameRegistry.addRecipe(new ItemStack(gargoyleBlock, 1, 3), "X X", "XXX", " X ", 'X', new ItemStack(Blocks.OBSIDIAN));
		GameRegistry.addRecipe(new ItemStack(gargoyleBlock, 1, 4), "X X", "XXX", " X ", 'X', new ItemStack(Dwemer.dwemerBlock, 1, 14));
		GameRegistry.addRecipe(new ItemStack(gargoyleBlock, 1, 5), "X X", "XXX", " X ", 'X', new ItemStack(Blocks.PACKED_ICE));
		GameRegistry.addRecipe(new ItemStack(gargoyleBlock, 1, 6), "X X", "XXX", " X ", 'X', new ItemStack(Blocks.END_STONE));
		GameRegistry.addRecipe(new ItemStack(gargoyleBlock, 1, 7), "X X", "XXX", " X ", 'X', new ItemStack(Cathedral.basaltBase, 1, 3));
		GameRegistry.addRecipe(new ItemStack(gargoyleBlock, 1, 8), "X X", "XXX", " X ", 'X', new ItemStack(chiselMarble, 1, 0));
		GameRegistry.addRecipe(new ItemStack(gargoyleBlock, 1, 9), "X X", "XXX", " X ", 'X', new ItemStack(chiselLimestone, 1, 0));
		GameRegistry.addRecipe(new ItemStack(gargoyleBlock, 1, 10), "X X", "XXX", " X ", 'X', new ItemStack(Blocks.SNOW));*/

	}

	public static void postInit(){
	}

}
