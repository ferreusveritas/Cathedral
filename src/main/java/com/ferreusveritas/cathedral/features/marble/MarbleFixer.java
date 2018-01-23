package com.ferreusveritas.cathedral.features.marble;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class MarbleFixer {

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//There's a lot of annoying discrepancies between project red marble and chisel 2 marble.  This is an attempt to fix that.
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void init(){

		if(Loader.isModLoaded("ProjRed|Exploration")){

			//ProjectRed stone block(Basalt, Marble)
			Block projRedStone = GameRegistry.findBlock("ProjRed|Exploration", "projectred.exploration.stone");
			Block projRedStoneWalls = GameRegistry.findBlock("ProjRed|Exploration", "projectred.exploration.stonewalls");
			Block chiselMarble = GameRegistry.findBlock("chisel", "marble");
			Block chiselMarblePillar = GameRegistry.findBlock("chisel", "marble_pillar");
			Block chiselMarbleStairs = GameRegistry.findBlock("chisel", "marble_stairs.0");
			Block chiselMarbleSlab = GameRegistry.findBlock("chisel", "marble_slab");

			//Fix ProjectRed Marble and Marble Brick
			OreDictionary.registerOre("marble", new ItemStack(projRedStone, 1, 0));
			OreDictionary.registerOre("marble", new ItemStack(chiselMarble, 1, 0));
			OreDictionary.registerOre("marbleBrick", new ItemStack(projRedStone, 1, 1));
			OreDictionary.registerOre("marbleBrick", new ItemStack(chiselMarble, 1, 1));

			//Add Marble Wall OreDict recipes
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(projRedStoneWalls, 6, 0), true, new Object[]{"***", "***", '*', "marble"}));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(projRedStoneWalls, 6, 1), true, new Object[]{"***", "***", '*', "marbleBrick"}));

			//Add Marble Pillar OreDict recipes
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(chiselMarblePillar, 6, 0), true, new Object[]{"**", "**", "**", '*', "marble"}));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(chiselMarblePillar, 6, 0), true, new Object[]{"**", "**", "**", '*', "marbleBrick"}));

			//Add Marble Stair OreDict recipes
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(chiselMarbleStairs, 6, 0), true, new Object[]{"*  ", "** ", "***", '*', "marble"}));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(chiselMarbleStairs, 6, 8), true, new Object[]{"*  ", "** ", "***", '*', "marbleBrick"}));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(chiselMarbleStairs, 6, 0), true, new Object[]{"  *", " **", "***", '*', "marble"}));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(chiselMarbleStairs, 6, 8), true, new Object[]{"  *", " **", "***", '*', "marbleBrick"}));

			//Add Marble Slab OreDict recipes
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(chiselMarbleSlab, 6, 0), true, new Object[]{"***", '*', "marble"}));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(chiselMarbleSlab, 6, 1), true, new Object[]{"***", '*', "marbleBrick"}));

			//Generic Marble Conversion(Converts any marble into Chisel Marble)
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(chiselMarble, 1, 0), true, new Object[]{"*", '*', "marble"}));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(chiselMarble, 1, 1), true, new Object[]{"*", '*', "marbleBrick"}));
		}

	}

}
