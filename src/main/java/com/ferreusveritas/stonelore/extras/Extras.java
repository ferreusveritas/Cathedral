package com.ferreusveritas.stonelore.extras;

import java.util.ArrayList;

import com.cricketcraft.chisel.api.carving.CarvingUtils;
import com.cricketcraft.chisel.api.carving.ICarvingRegistry;
import com.ferreusveritas.stonelore.StoneLore;
import com.ferreusveritas.stonelore.blocks.BaseBlockDef;
import com.ferreusveritas.stonelore.blocks.BlockCarvable;
import com.ferreusveritas.stonelore.blocks.BlockCarvableGlass;
import com.ferreusveritas.stonelore.blocks.BlockChain;
import com.ferreusveritas.stonelore.blocks.BlockGenericSlab;
import com.ferreusveritas.stonelore.blocks.BlockGenericStairs;
import com.ferreusveritas.stonelore.blocks.BlockMagic;
import com.ferreusveritas.stonelore.blocks.BlockRoofTiles;
import com.ferreusveritas.stonelore.blocks.BlockStoneRailing;
import com.ferreusveritas.stonelore.items.ItemBlockRoofTiles;
import com.ferreusveritas.stonelore.items.ItemGenericSlab;
import com.ferreusveritas.stonelore.items.ItemSubBlocks;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import team.chisel.ctmlib.ISubmapManager;

public class Extras {

	public static BlockStoneRailing stoneRailingBlock;
	public static BlockGenericStairs extraStairs[] = new BlockGenericStairs[5];
	public static BlockGenericSlab extraSlabs;
	public static BlockCarvable extraFantasy;
	public static BlockCarvable extraFantasy2;
	public static BlockCarvable extraStone;
	public static BlockRoofTiles roofTiles[] = new BlockRoofTiles[17];
	public static BlockChain chainBlock;
	
	public static BlockCarvableGlass stainedGlass;
	
	public static BlockMagic magicBlock;

	public static ArrayList<BaseBlockDef> baseBlocks = new ArrayList<BaseBlockDef>();

	public static Item clayTile;
	public static Item firedTile;

	public static void preInit(StoneLore lore){

		{
			Block chiselFantasy = GameRegistry.findBlock("chisel", "fantasyblock2");
			Block chiselEndstone = GameRegistry.findBlock("chisel", "end_Stone");

			//Create an array of Block Definitions that will be used to make Slabs and Stairs
			baseBlocks.ensureCapacity(5);
			baseBlocks.add(new BaseBlockDef(0, chiselFantasy, 0, "fantasy-brick", "FantasyBrick", 2.0f, 10f));
			baseBlocks.add(new BaseBlockDef(1, chiselFantasy, 3, "fantasy-damaged", "FantasyDamaged", 2.0f, 10f));
			baseBlocks.add(new BaseBlockDef(2, chiselFantasy, 14, "fantasy-disarray", "FantasyDisarray", 2.0f, 10f));
			baseBlocks.add(new BaseBlockDef(3, chiselEndstone, 0, "endstone-brick", "EndstoneBrick", 3.0f, 15f));
			baseBlocks.add(new BaseBlockDef(4, chiselEndstone, 2, "endstone-checkered", "EndstoneCheckered", 3.0f, 15f));
		}

		//Extra blocks that Chisel should have had
		extraFantasy = (BlockCarvable) new BlockCarvable(Material.rock).setCreativeTab(StoneLore.tabStoneLore).setHardness(2.0f).setResistance(10.0f);
		extraFantasy2 = (BlockCarvable) new BlockCarvable(Material.rock).setCreativeTab(StoneLore.tabStoneLore).setHardness(2.0f).setResistance(10.0f);
		extraStone = (BlockCarvable) new BlockCarvable(Material.rock).setCreativeTab(StoneLore.tabStoneLore).setHardness(1.5f).setResistance(10.0f);

		stainedGlass = (BlockCarvableGlass) new BlockCarvableGlass().setCreativeTab(StoneLore.tabStoneLore).setHardness(0.3f).setStepSound(Block.soundTypeGlass);
		stainedGlass.setStained(true);
		
		clayTile = new Item().setUnlocalizedName("clayTile").setCreativeTab(StoneLore.tabStoneLore).setTextureName(StoneLore.MODID + ":clayTile");
		firedTile = new Item().setUnlocalizedName("firedTile").setCreativeTab(StoneLore.tabStoneLore).setTextureName(StoneLore.MODID + ":firedTile");

		//Create and Resister Stairs
		for(BaseBlockDef baseBlock : baseBlocks){
			extraStairs[baseBlock.select] = (BlockGenericStairs) new BlockGenericStairs(baseBlock).setCreativeTab(StoneLore.tabStoneLore);
			GameRegistry.registerBlock(extraStairs[baseBlock.select], baseBlock.blockName + "Stairs");
		}

		for(int color = 0; color < 17; color++){
			roofTiles[color] = (BlockRoofTiles) new BlockRoofTiles(color).setBlockName(StoneLore.MODID + "_rooftiles_" + Integer.toHexString(color)).setCreativeTab(StoneLore.tabStoneLore);
			GameRegistry.registerBlock(roofTiles[color], ItemBlockRoofTiles.class, "rooftiles" + Integer.toHexString(color));
		}

		//Create and Resister Slabs
		extraSlabs = (BlockGenericSlab) new BlockGenericSlab(baseBlocks).setBlockName(StoneLore.MODID + "_extraslabs");
		GameRegistry.registerBlock(extraSlabs, ItemGenericSlab.class, "ExtraSlabs");

		//Testing block.. Do not release!
		/*magicBlock = new BlockMagic();
		magicBlock.setBlockName(StoneLore.MODID + "_magicblock");
		magicBlock.setCreativeTab(StoneLore.tabStoneLore);
		GameRegistry.registerBlock(magicBlock, "MagicBlock");*/

		//Explicitly Add Carving Variations for Stairs and Slabs
		{
			ICarvingRegistry Carving = CarvingUtils.getChiselRegistry();
			Carving.addVariation("fantasystairs", extraStairs[0], 0, 0);
			Carving.addVariation("fantasystairs", extraStairs[1], 0, 1);
			Carving.addVariation("fantasystairs", extraStairs[2], 0, 2);

			Carving.addVariation("endstonestairs", extraStairs[3], 0, 0);
			Carving.addVariation("endstonestairs", extraStairs[4], 0, 1);

			Carving.addVariation("fantasyslab", extraSlabs, 0, 0);
			Carving.addVariation("fantasyslab", extraSlabs, 1, 1);
			Carving.addVariation("fantasyslab", extraSlabs, 2, 2);

			Carving.addVariation("endstoneslab", extraSlabs, 3, 0);
			Carving.addVariation("endstoneslab", extraSlabs, 4, 1);
		}

		//Add variations for Extra Blocks
		extraFantasy.carverHelper.addVariation("tile." + StoneLore.MODID + "_fantasy.fantasypillarlarge.name", 0, "fantasypillarlarge", null, 0, StoneLore.MODID, (ISubmapManager) null, 100);
		extraFantasy.carverHelper.addVariation("tile." + StoneLore.MODID + "_fantasy.fantasyknot.name", 1, "fantasyknot", null, 0, StoneLore.MODID, (ISubmapManager) null, 100);
		extraFantasy.carverHelper.addVariation("tile." + StoneLore.MODID + "_fantasy.fantasyembedded.name", 2, "fantasyembedded", null, 0, StoneLore.MODID, (ISubmapManager) null, 100);
		extraFantasy2.carverHelper.addVariation("tile." + StoneLore.MODID + "_fantasy.fantasy2pillarlarge.name", 0, "fantasy2pillarlarge", null, 0, StoneLore.MODID, (ISubmapManager) null, 100);
		extraFantasy2.carverHelper.addVariation("tile." + StoneLore.MODID + "_fantasy.fantasy2knot.name", 1, "fantasy2knot", null, 0, StoneLore.MODID, (ISubmapManager) null, 100);
		extraFantasy2.carverHelper.addVariation("tile." + StoneLore.MODID + "_fantasy.fantasy2embedded.name", 2, "fantasy2embedded", null, 0, StoneLore.MODID, (ISubmapManager) null, 100);
		extraStone.carverHelper.addVariation("tile." + StoneLore.MODID + "_stone.stone-panel.name", 0, "stone-panel", null, 0, StoneLore.MODID, (ISubmapManager) null, 100);
		extraStone.carverHelper.addVariation("tile." + StoneLore.MODID + "_stone.stone-knot.name", 1, "stone-knot", null, 0, StoneLore.MODID, (ISubmapManager) null, 100);
		stainedGlass.carverHelper.addVariation("tile." + StoneLore.MODID + "_glass.stained-1.name", 0, "stained-1", null, 0, StoneLore.MODID);
		stainedGlass.carverHelper.addVariation("tile." + StoneLore.MODID + "_glass.stained-2.name", 1, "stained-2", null, 0, StoneLore.MODID);

		
		//Register Extra Blocks
		extraFantasy.carverHelper.registerBlock(extraFantasy, "fantasyblock");
		extraFantasy2.carverHelper.registerBlock(extraFantasy2, "fantasyblock2");
		extraStone.carverHelper.registerBlock(extraStone, "stonebricksmooth");
		stainedGlass.carverHelper.registerBlock(stainedGlass, "stainedglass");

		//Register Variations
		extraFantasy.carverHelper.registerVariations("fantasyblock");
		extraFantasy2.carverHelper.registerVariations("fantasy");
		extraStone.carverHelper.registerVariations("stonebricksmooth");
		stainedGlass.carverHelper.registerVariations("stainedglass");

		stoneRailingBlock = new BlockStoneRailing();
		GameRegistry.registerBlock(stoneRailingBlock, ItemSubBlocks.class, "stonerailing");

		chainBlock = new BlockChain();
		chainBlock.setBlockName(StoneLore.MODID + "_chain").setCreativeTab(StoneLore.tabStoneLore);
		GameRegistry.registerBlock(chainBlock, ItemSubBlocks.class, "chainblock");
		
		//Register Items
		GameRegistry.registerItem(clayTile, "clayTile");
		GameRegistry.registerItem(firedTile, "firedTile");
	}

	public static void init(StoneLore lore){

		//Recipes
		for(BaseBlockDef baseBlock : baseBlocks){
			//Stairs
			GameRegistry.addRecipe(new ItemStack(extraStairs[baseBlock.select], 6, 0), "X  ", "XX ", "XXX", 'X', new ItemStack(baseBlock.block, 1, baseBlock.metaData));
			//Slabs
			GameRegistry.addRecipe(new ItemStack(extraSlabs, 6, baseBlock.select), "XXX", 'X', new ItemStack(baseBlock.block, 1, baseBlock.metaData));
		}

		//Clay Tiles
		GameRegistry.addRecipe(new ItemStack(clayTile, 16), " X ", "X X", 'X', Items.clay_ball);
		GameRegistry.addSmelting(new ItemStack(clayTile), new ItemStack(firedTile), 0.1f);
		GameRegistry.addRecipe(new ItemStack(roofTiles[16]), "XX", "XX", 'X', firedTile);

		//Coloring the clay tiles
		String dyes[] = {
				"dyeBlack", "dyeRed", "dyeGreen", "dyeBrown", "dyeBlue", "dyePurple", "dyeCyan", "dyeLightGray", "dyeGray",
				"dyePink", "dyeLime", "dyeYellow", "dyeLightBlue", "dyeMagenta", "dyeOrange", "dyeWhite",
		};

		OreDictionary.registerOre("blockClayTile", new ItemStack(roofTiles[16]));//Natural Terra Cotta Roofing

		for(int color = 0; color < 16; color++){
			OreDictionary.registerOre("blockClayTile", new ItemStack(roofTiles[color]));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(roofTiles[color], 8), true, new Object[]{"ttt", "tdt", "ttt", 't', "blockClayTile", 'd', dyes[color]}));
		}

		stoneRailingBlock.addRecipes();
		
		String metalIngot;
		
		if(OreDictionary.doesOreNameExist("ingotLead")){
			metalIngot = "ingotLead";//Lead is the accurate choice
		} else {
			metalIngot = "ingotIron";//This sucks but whatever
		}
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(stainedGlass, 16, 0), true, new Object[]{"cgm", "glg", "ygp", 
				'l', metalIngot, 
				'g', "blockGlass",
				'c', "dyeCyan",
				'm', "dyeMagenta",
				'y', "dyeYellow",
				'p', "dyePink"
				}));

		//Metal Chains
		String metals[] = {"Iron", "Gold", "Dwemer", "Copper", "Bronze", "Silver", "Enderium"};
		for(int i = 0; i < 7; i++){
			makeMetalChainRecipe(chainBlock, metals[i], i);
		}
		if(!OreDictionary.doesOreNameExist("nuggetDwemer")){
			GameRegistry.addShapelessRecipe(new ItemStack(chainBlock, 1, 2), new ItemStack(chainBlock, 1, 4));
		}
		
	}

	public static void makeMetalChainRecipe(BlockChain chainBlock, String metalName, int subBlockNum){
		if(OreDictionary.doesOreNameExist("nugget" + metalName)){
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(chainBlock, 4, subBlockNum), true, new Object[]{" o ", " o ", " o ", 'o', "nugget" + metalName}));
		}
	}
	
}
