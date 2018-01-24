package com.ferreusveritas.cathedral.features.gargoyle;

import com.ferreusveritas.cathedral.features.IFeature;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class Gargoyle implements IFeature {

	public static final String featureName = "gargoyle";
	
	public static BlockGargoyle gargoyleBlock;
	public static String types[] = {"stone", "sandstone", "netherbrick", "obsidian", "dwemer", "packedice", "endstone", "basalt", "marble", "limestone", "snow"};
	
	@Override
	public String getName() {
		return featureName;
	}

	@Override
	public void preInit() {}

	@Override
	public void createBlocks() {
		gargoyleBlock = new BlockGargoyle();
		//GameRegistry.registerBlock(gargoyleBlock, ItemGargoyle.class, "gargoyle");
		//TileEntity.addMapping(EntityGargoyle.class, "gargoyle");
		
		//whiteTest = new BlockStone().setBlockTextureName(StoneLore.MODID + ":" + "white").setBlockName(StoneLore.MODID + "_" + "whiteTest").setCreativeTab(StoneLore.tabStoneLore);
		//GameRegistry.registerBlock(whiteTest, "whitetest");
	}

	@Override
	public void createItems() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerBlocks(IForgeRegistry<Block> registry) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerItems(IForgeRegistry<Item> registry) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerRecipes(IForgeRegistry<IRecipe> registry) {
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

	@Override
	public void registerModels(ModelRegistryEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init() {}

	@Override
	public void postInit() {}

}
