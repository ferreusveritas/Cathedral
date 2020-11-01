package com.ferreusveritas.cathedral.features.roofing;

import com.ferreusveritas.cathedral.ModConstants;
import com.ferreusveritas.cathedral.features.BlockForm;
import com.ferreusveritas.cathedral.features.IFeature;
import com.ferreusveritas.cathedral.proxy.ModelHelper;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
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

	public BlockShinglesStairs roofingShinglesStairsNatural;
	public BlockShinglesStairs roofingShinglesStairsColored[] = new BlockShinglesStairs[EnumDyeColor.values().length];

	public BlockShinglesHorizontal roofingShinglesHorizontalNatural;
	public BlockShinglesHorizontal roofingShinglesHorizontalColored[] = new BlockShinglesHorizontal[EnumDyeColor.values().length];

	public BlockShinglesSlab roofingShinglesSlabNatural;
	public BlockShinglesSlab roofingShinglesSlabColored[] = new BlockShinglesSlab[EnumDyeColor.values().length];

	public Item clayTile;
	public Item firedTile;
	
	public final CreativeTabs tabRoofing;

	public Roofing() {
		tabRoofing = new CreativeTabs("tabRoofing") {
			@Override
			public ItemStack getTabIconItem() {
				return new ItemStack(roofingShinglesStairsNatural, 1);
			}
		};
	}
	
	@Override
	public String getName() {
		return featureName;
	}

	@Override
	public void preInit() {}

	@Override
	public void createBlocks() {
	
		for(EnumDyeColor color: EnumDyeColor.values()) {
			roofingShinglesStairsColored[color.getMetadata()] = (BlockShinglesStairs) new BlockShinglesStairs(color, featureObjectName(BlockForm.SHINGLES, color.getName()));
			roofingShinglesHorizontalColored[color.getMetadata()] = (BlockShinglesHorizontal) new BlockShinglesHorizontal(color, featureObjectName(BlockForm.BLOCK, color.getName()));
			roofingShinglesSlabColored[color.getMetadata()] = (BlockShinglesSlab) new BlockShinglesSlab(color, featureObjectName(BlockForm.SLAB, color.getName()));
		}
		
		roofingShinglesStairsNatural = (BlockShinglesStairs)new BlockShinglesStairs(null, featureObjectName(BlockForm.SHINGLES, "natural"));
		roofingShinglesHorizontalNatural = (BlockShinglesHorizontal)new BlockShinglesHorizontal(null, featureObjectName(BlockForm.BLOCK, "natural"));
		roofingShinglesSlabNatural = (BlockShinglesSlab)new BlockShinglesSlab(null, featureObjectName(BlockForm.SLAB, "natural"));
	}

	@Override
	public void createItems() {
		clayTile = new Item()
				.setRegistryName("claytile")
				.setUnlocalizedName("claytile")
				.setCreativeTab(tabRoofing);
		firedTile = new Item()
				.setRegistryName("firedtile")
				.setUnlocalizedName("firedtile")
				.setCreativeTab(tabRoofing);
	}

	@Override
	public void registerBlocks(IForgeRegistry<Block> registry) {
		registry.registerAll(roofingShinglesStairsColored);
		registry.register(roofingShinglesStairsNatural);
		registry.registerAll(roofingShinglesHorizontalColored);
		registry.register(roofingShinglesHorizontalNatural);
		registry.register(roofingShinglesSlabNatural);
		registry.registerAll(roofingShinglesSlabColored);
	}

	@Override
	public void registerItems(IForgeRegistry<Item> registry) {
		
		registry.registerAll(clayTile, firedTile);
		
		registry.register(new ItemBlock(roofingShinglesStairsNatural).setRegistryName(roofingShinglesStairsNatural.getRegistryName()));
		registry.register(new ItemBlock(roofingShinglesHorizontalNatural).setRegistryName(roofingShinglesHorizontalNatural.getRegistryName()));
		registry.register(new ItemRoofingSlab(roofingShinglesSlabNatural, roofingShinglesSlabNatural, roofingShinglesHorizontalNatural).setRegistryName(roofingShinglesSlabNatural.getRegistryName()));
		
		for(int i = 0; i < EnumDyeColor.values().length; i++) {
			registry.register(new ItemBlock(roofingShinglesStairsColored[i]).setRegistryName(roofingShinglesStairsColored[i].getRegistryName()));
			registry.register(new ItemBlock(roofingShinglesHorizontalColored[i]).setRegistryName(roofingShinglesHorizontalColored[i].getRegistryName()));
			registry.register(new ItemRoofingSlab(roofingShinglesSlabColored[i], roofingShinglesSlabColored[i], roofingShinglesHorizontalColored[i]).setRegistryName(roofingShinglesSlabColored[i].getRegistryName()));
		}
		
	}

	@Override
	public void registerRecipes(IForgeRegistry<IRecipe> registry) {
		
		BlockShinglesHorizontal block = roofingShinglesHorizontalNatural;
		BlockShinglesStairs stairs = roofingShinglesStairsNatural;
		BlockShinglesSlab slab = roofingShinglesSlabNatural;

		
		//Clay Tiles
		GameRegistry.addShapedRecipe(
				new ResourceLocation(ModConstants.MODID, "claytile"),//Name
				null,//Group
				new ItemStack(clayTile, 16, 0),//Output
				" x ",
				"x x",
				'x', Items.CLAY_BALL
				);

		GameRegistry.addSmelting(new ItemStack(clayTile), new ItemStack(firedTile), 0.1f);
		
		GameRegistry.addShapedRecipe(
				new ResourceLocation(ModConstants.MODID, "roof_block_natural"),//Name
				null,//Group
				new ItemStack(block),//Output
				"xx",
				"xx",
				'x', new ItemStack(firedTile)
			);
		
		//Moved to PostInit to avoid problems with Quark's automatic stair recipe feature
		/*GameRegistry.addShapedRecipe(
				new ResourceLocation(ModConstants.MODID, "roof_stairs_natural"),//Name
				null,//Group
				new ItemStack(stairs, 4),//Output
				"x ",
				"xx",
				'x', new ItemStack(block)
			);*/
		
		GameRegistry.addShapedRecipe(
				new ResourceLocation(ModConstants.MODID, "roof_slab_natural"),//Name
				null,//Group
				new ItemStack(slab, 4),//Output
				"xx",
				'x', new ItemStack(block)
			);
			
		GameRegistry.addShapedRecipe(
				new ResourceLocation(ModConstants.MODID, "roof_slab_natural_back"),//Name
				null,//Group
				new ItemStack(block),//Output
				"x",
				"x",
				'x', new ItemStack(slab)
			);
		
		GameRegistry.addShapedRecipe(
				new ResourceLocation(ModConstants.MODID, "roof_stairs_natural_back"),//Name
				null,//Group
				new ItemStack(block, 3),//Output
				"xx",
				"xx",
				'x', new ItemStack(stairs)
			);

		
		OreDictionary.registerOre("clayshingles", new ItemStack(roofingShinglesHorizontalNatural));//Natural Terra Cotta Roofing
		
		String dyes[] = {
				"dyeBlack", "dyeRed", "dyeGreen", "dyeBrown", "dyeBlue", "dyePurple", "dyeCyan", "dyeLightGray", "dyeGray",
				"dyePink", "dyeLime", "dyeYellow", "dyeLightBlue", "dyeMagenta", "dyeOrange", "dyeWhite",
		};
		
		for(EnumDyeColor color: EnumDyeColor.values()) {
			
			String colorName = color.getName();
			
			block = roofingShinglesHorizontalColored[color.getMetadata()];
			stairs = roofingShinglesStairsColored[color.getMetadata()];
			slab = roofingShinglesSlabColored[color.getMetadata()];
			
			OreDictionary.registerOre("clayshingles", new ItemStack(roofingShinglesHorizontalColored[color.getMetadata()]));
			
			registry.register(
				new ShapedOreRecipe(
					null,
					new ItemStack(roofingShinglesHorizontalColored[color.getMetadata()], 8, 0),
					new Object[] {
						"xxx",
						"xcx",
						"xxx",
						'x', "clayshingles",
						'c', dyes[color.getDyeDamage()]
					}
				).setRegistryName("clayshingles_" + colorName)
			);
						
			GameRegistry.addShapedRecipe(
					new ResourceLocation(ModConstants.MODID, "roof_block_" + colorName),//Name
					null,
					new ItemStack(block),//Output
					"xx",
					"xx",
					'x', new ItemStack(firedTile)
				);
			
			//Moved to PostInit to avoid problems with Quark's automatic stair recipe feature
			/*GameRegistry.addShapedRecipe(
					new ResourceLocation(ModConstants.MODID, "roof_stairs_" + colorName),//Name
					null,//Group
					new ItemStack(stairs, 4),//Output
					"x ",
					"xx",
					'x', new ItemStack(block)
				);*/
			
			GameRegistry.addShapedRecipe(
					new ResourceLocation(ModConstants.MODID, "roof_slab_" + colorName),//Name
					null,//Group
					new ItemStack(slab, 4),//Output
					"xx",
					'x', new ItemStack(block)
				);
				
			GameRegistry.addShapedRecipe(
					new ResourceLocation(ModConstants.MODID, "roof_slab_" + colorName + "_back"),//Name
					null,//Group
					new ItemStack(block),//Output
					"x",
					"x",
					'x', new ItemStack(slab)
				);
			
			GameRegistry.addShapedRecipe(
					new ResourceLocation(ModConstants.MODID, "roof_stairs_" + colorName + "_back"),//Name
					null,//Group
					new ItemStack(block, 3),//Output
					"xx",
					"xx",
					'x', new ItemStack(stairs)
				);

		}
		
	}

	@Override
	public void registerModels(ModelRegistryEvent event) {

		ModelHelper.regModel(clayTile);
		ModelHelper.regModel(firedTile);
		
		ModelHelper.regModel(roofingShinglesStairsNatural);
		ModelHelper.regModel(roofingShinglesHorizontalNatural);
		ModelHelper.regModel(roofingShinglesSlabNatural);
		
		for(BlockShinglesStairs shingles: roofingShinglesStairsColored) {
			ModelHelper.regModel(shingles);
		}
		
		for(BlockShinglesHorizontal shingles: roofingShinglesHorizontalColored) {
			ModelHelper.regModel(shingles);
		}
		
		for(BlockShinglesSlab shingles: roofingShinglesSlabColored) {
			ModelHelper.regModel(shingles);
		}
		
	}

	@Override
	public void init() {}

	@Override
	public void postInit() {
		BlockShinglesHorizontal block = roofingShinglesHorizontalNatural;
		BlockShinglesStairs stairs = roofingShinglesStairsNatural;
		
		GameRegistry.addShapedRecipe(
			new ResourceLocation(ModConstants.MODID, "roof_stairs_natural"),//Name
			null,//Group
			new ItemStack(stairs, 4),//Output
			"x ",
			"xx",
			'x', new ItemStack(block)
		);
		
		for(EnumDyeColor color: EnumDyeColor.values()) {
			String colorName = color.getName();
			block = roofingShinglesHorizontalColored[color.getMetadata()];
			stairs = roofingShinglesStairsColored[color.getMetadata()];
			
			GameRegistry.addShapedRecipe(
				new ResourceLocation(ModConstants.MODID, "roof_stairs_" + colorName),//Name
				null,//Group
				new ItemStack(stairs, 4),//Output
				"x ",
				"xx",
				'x', new ItemStack(block)
			);
		}
	}
	
}
