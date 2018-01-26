package com.ferreusveritas.cathedral.features.roofing;

import com.ferreusveritas.cathedral.CathedralMod;
import com.ferreusveritas.cathedral.features.BlockForm;
import com.ferreusveritas.cathedral.features.IFeature;
import com.ferreusveritas.cathedral.features.extras.BlockRoofTiles;

import net.minecraft.block.Block;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

public class Roofing implements IFeature {

	public static final String featureName = "roofing";
	
	public BlockRoofTiles roofingShinglesColored[] = new BlockRoofTiles[EnumDyeColor.values().length];
	public BlockRoofTiles roofingShinglesNatural;
	
	public Item clayTile;
	public Item firedTile;
	
	@Override
	public String getName() {
		return featureName;
	}

	@Override
	public void preInit() {}

	@Override
	public void createBlocks() {
	
		for(EnumDyeColor color: EnumDyeColor.values()) {
			roofingShinglesColored[color.getMetadata()] = (BlockRoofTiles) new BlockRoofTiles(color, featureObjectName(BlockForm.SHINGLES, color.getName()));
		}
		
		roofingShinglesNatural = (BlockRoofTiles)new BlockRoofTiles(null, featureObjectName(BlockForm.SHINGLES, "natural"));
	}

	@Override
	public void createItems() {
		clayTile = new Item()
				.setRegistryName("claytile")
				.setUnlocalizedName("claytile")
				.setCreativeTab(CathedralMod.tabCathedral);
		firedTile = new Item()
				.setRegistryName("firedtile")
				.setUnlocalizedName("firedtile")
				.setCreativeTab(CathedralMod.tabCathedral);
	}

	@Override
	public void registerBlocks(IForgeRegistry<Block> registry) {
		registry.registerAll(roofingShinglesColored);
		registry.register(roofingShinglesNatural);
	}

	@Override
	public void registerItems(IForgeRegistry<Item> registry) {
		
		for(Block roofTile: roofingShinglesColored) {
			registry.register(new ItemBlock(roofTile).setRegistryName(roofTile.getRegistryName()));
		}
		
		registry.register(new ItemBlock(roofingShinglesNatural).setRegistryName(roofingShinglesNatural.getRegistryName()));
		
		registry.registerAll(clayTile, firedTile);
	}

	@Override
	public void registerRecipes(IForgeRegistry<IRecipe> registry) {
		
		//Clay Tiles
		//GameRegistry.addRecipe(new ItemStack(clayTile, 16), " X ", "X X", 'X', Items.clay_ball);
		//GameRegistry.addSmelting(new ItemStack(clayTile), new ItemStack(firedTile), 0.1f);
		//GameRegistry.addRecipe(new ItemStack(roofTiles[16]), "XX", "XX", 'X', firedTile);

		//Coloring the clay tiles
		/*String dyes[] = {
				"dyeBlack", "dyeRed", "dyeGreen", "dyeBrown", "dyeBlue", "dyePurple", "dyeCyan", "dyeLightGray", "dyeGray",
				"dyePink", "dyeLime", "dyeYellow", "dyeLightBlue", "dyeMagenta", "dyeOrange", "dyeWhite",
		};*/
		
		OreDictionary.registerOre("blockClayTile", new ItemStack(roofingShinglesNatural));//Natural Terra Cotta Roofing
		
		for(int color = 0; color < 16; color++){
			OreDictionary.registerOre("blockClayTile", new ItemStack(roofingShinglesColored[color]));
			//GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(roofTiles[color], 8), true, new Object[]{"ttt", "tdt", "ttt", 't', "blockClayTile", 'd', dyes[color]}));
		}
		
	}

	@Override
	public void registerModels(ModelRegistryEvent event) {}

	@Override
	public void init() {}

	@Override
	public void postInit() {}
	
}
