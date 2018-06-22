package com.ferreusveritas.cathedral.features.roofing;

import com.ferreusveritas.cathedral.CathedralMod;
import com.ferreusveritas.cathedral.ModConstants;
import com.ferreusveritas.cathedral.features.BlockForm;
import com.ferreusveritas.cathedral.features.IFeature;
import com.ferreusveritas.cathedral.proxy.ModelHelper;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.registries.IForgeRegistry;

public class Roofing implements IFeature {

	public static final String featureName = "roofing";

	public BlockShingles roofingShinglesNatural;
	public BlockShingles roofingShinglesColored[] = new BlockShingles[EnumDyeColor.values().length];
	
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
			roofingShinglesColored[color.getMetadata()] = (BlockShingles) new BlockShingles(color, featureObjectName(BlockForm.SHINGLES, color.getName()));
		}
		
		roofingShinglesNatural = (BlockShingles)new BlockShingles(null, featureObjectName(BlockForm.SHINGLES, "natural"));
	}

	@Override
	public void createItems() {
		clayTile = new Item()
				.setRegistryName("claytile")
				.setUnlocalizedName("claytile")
				.setCreativeTab(CathedralMod.tabRoofing);
		firedTile = new Item()
				.setRegistryName("firedtile")
				.setUnlocalizedName("firedtile")
				.setCreativeTab(CathedralMod.tabRoofing);
	}

	@Override
	public void registerBlocks(IForgeRegistry<Block> registry) {
		registry.registerAll(roofingShinglesColored);
		registry.register(roofingShinglesNatural);
	}

	@Override
	public void registerItems(IForgeRegistry<Item> registry) {

		registry.registerAll(clayTile, firedTile);
		
		registry.register(new ItemBlock(roofingShinglesNatural).setRegistryName(roofingShinglesNatural.getRegistryName()));
		
		for(Block roofTile: roofingShinglesColored) {
			registry.register(new ItemBlock(roofTile).setRegistryName(roofTile.getRegistryName()));
		}
		
	}

	@Override
	public void registerRecipes(IForgeRegistry<IRecipe> registry) {
		
		//Clay Tiles
		GameRegistry.addShapedRecipe(
				new ResourceLocation(ModConstants.MODID, "claytile"),//Name
				null,//Group
				new ItemStack(clayTile),//Output
				" x ",
				"x x",
				'x', Items.CLAY_BALL
				);

		GameRegistry.addSmelting(new ItemStack(clayTile), new ItemStack(firedTile), 0.1f);
		
		GameRegistry.addShapedRecipe(
				new ResourceLocation(ModConstants.MODID, "roofshinglesnatural"),//Name
				null,//Group
				new ItemStack(roofingShinglesNatural),//Output
				"xx",
				"xx",
				'x', new ItemStack(firedTile)
				);
		
		OreDictionary.registerOre("clayshingles", new ItemStack(roofingShinglesNatural));//Natural Terra Cotta Roofing
		
		String dyes[] = {
				"dyeBlack", "dyeRed", "dyeGreen", "dyeBrown", "dyeBlue", "dyePurple", "dyeCyan", "dyeLightGray", "dyeGray",
				"dyePink", "dyeLime", "dyeYellow", "dyeLightBlue", "dyeMagenta", "dyeOrange", "dyeWhite",
		};
		
		for(EnumDyeColor color: EnumDyeColor.values()) {
			OreDictionary.registerOre("clayshingles", new ItemStack(roofingShinglesColored[color.getMetadata()]));
			
			registry.register(
				new ShapedOreRecipe(
					null,
					new ItemStack(roofingShinglesColored[color.getMetadata()], 8, 0),
					new Object[] {
						"xxx",
						"xcx",
						"xxx",
						'x', "clayshingles",
						'c', dyes[color.getDyeDamage()]
					}
				).setRegistryName("clayshingles_" + color.getName())
			);
		}
		
	}

	@Override
	public void registerModels(ModelRegistryEvent event) {

		ModelHelper.regModel(clayTile);
		ModelHelper.regModel(firedTile);
		
		ModelHelper.regModel(roofingShinglesNatural);
		
		for(BlockShingles shingles: roofingShinglesColored) {
			ModelHelper.regModel(shingles);
		}
	}

	@Override
	public void init() {}

	@Override
	public void postInit() {}
	
}
