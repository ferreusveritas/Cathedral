package com.ferreusveritas.cathedral.features.extras;

import java.util.ArrayList;

import com.ferreusveritas.cathedral.CathedralMod;
import com.ferreusveritas.cathedral.ModConstants;
import com.ferreusveritas.cathedral.common.blocks.BlockMultiVariant;
import com.ferreusveritas.cathedral.common.blocks.BlockStairsGeneric;
import com.ferreusveritas.cathedral.features.BlockForm;
import com.ferreusveritas.cathedral.features.IFeature;
import com.ferreusveritas.cathedral.features.extras.FeatureTypes.EnumCobblestoneSlabType;
import com.ferreusveritas.cathedral.features.extras.FeatureTypes.EnumEndStoneSlabType;
import com.ferreusveritas.cathedral.features.extras.FeatureTypes.EnumEndStoneType;
import com.ferreusveritas.cathedral.features.extras.FeatureTypes.EnumLimestoneSlabType;
import com.ferreusveritas.cathedral.features.extras.FeatureTypes.EnumMarbleSlabType;
import com.ferreusveritas.cathedral.features.extras.FeatureTypes.EnumStoneType;
import com.ferreusveritas.cathedral.proxy.ModelHelper;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

public class Extras implements IFeature {

	public static final String featureName = "extras";

	public Block blockStone;
	public Block blockEndstone;

	public Block slabEndstone;
	public Block slabEndstoneDouble;

	public Block slabLimestone;
	public Block slabLimestoneDouble;

	public Block slabCobblestone;
	public Block slabCobblestoneDouble;
	
	public Block slabMarble;
	public Block slabMarbleDouble;
	
	public ArrayList<Block> stairsEndstone = new ArrayList<>();
	public ArrayList<Block> stairsLimestone = new ArrayList<>();
	public ArrayList<Block> stairsCobblestone = new ArrayList<>();
	public ArrayList<Block> stairsMarble = new ArrayList<>();

	public Block blockGrassOLantern;
	public IBlockState grassBlockState;

	
	@Override
	public String getName() {
		return featureName;
	}

	@Override
	public void preInit() {
		 grassBlockState = Blocks.GRASS.getDefaultState();
	}

	@Override
	public void createBlocks() {

		//Create carved blocks
		blockStone = new BlockMultiVariant<EnumStoneType>(Material.ROCK, EnumStoneType.class, featureObjectName(BlockForm.BLOCK, "stone")) {
			@Override
			public void makeVariantProperty() {
				variant = PropertyEnum.<EnumStoneType>create("variant", EnumStoneType.class);
			}
		}.setCreativeTab(CathedralMod.tabCathedral)
				.setHardness(1.5f)
				.setResistance(30.0f);

		
		////// Endstone //////
		
		blockEndstone = new BlockMultiVariant<EnumEndStoneType>(Material.ROCK, EnumEndStoneType.class, featureObjectName(BlockForm.BLOCK, "endstone")) {
			@Override
			public void makeVariantProperty() {
				variant = PropertyEnum.<EnumEndStoneType>create("variant", EnumEndStoneType.class);
			}
		}.setCreativeTab(CathedralMod.tabCathedral)
				.setHardness(3.0f)
				.setResistance(15.0f);

		slabEndstone = new BlockSlabEndstone(featureObjectName(BlockForm.SLAB, "endstone"))
				.setCreativeTab(CathedralMod.tabCathedral)
				.setHardness(3.0f)
				.setResistance(15.0f);

		slabEndstoneDouble = new BlockDoubleSlabEndstone(featureObjectName(BlockForm.DOUBLESLAB, "endstone"))
				.setCreativeTab(CathedralMod.tabCathedral)
				.setHardness(3.0f)
				.setResistance(15.0f);

		for(EnumEndStoneSlabType type: EnumEndStoneSlabType.values()) {
			stairsEndstone.add(new BlockStairsGeneric(featureObjectName(BlockForm.STAIRS, "endstone_" + type.getName() ), blockEndstone.getDefaultState()).setCreativeTab(CathedralMod.tabCathedral));
		}

		
		////// Limestone //////
		
		slabLimestone = new BlockSlabLimestone(featureObjectName(BlockForm.SLAB, "limestone"))
				.setCreativeTab(CathedralMod.tabCathedral)
				.setHardness(2.0f)
				.setResistance(10.0f);

		slabLimestoneDouble = new BlockDoubleSlabLimestone(featureObjectName(BlockForm.DOUBLESLAB, "limestone"))
				.setCreativeTab(CathedralMod.tabCathedral)
				.setHardness(2.0f)
				.setResistance(10.0f);
		
		for(EnumLimestoneSlabType type: EnumLimestoneSlabType.values()) {
			stairsLimestone.add(new BlockStairsGeneric(featureObjectName(BlockForm.STAIRS, "limestone_" + type.getName() ), Blocks.COBBLESTONE.getDefaultState()).setCreativeTab(CathedralMod.tabCathedral));
		}
				
		
		////// Cobblestone //////
		
		slabCobblestone = new BlockSlabCobblestone(featureObjectName(BlockForm.SLAB, "cobblestone"))
				.setCreativeTab(CathedralMod.tabCathedral)
				.setHardness(2.0f)
				.setResistance(10.0f);

		slabCobblestoneDouble = new BlockDoubleSlabCobblestone(featureObjectName(BlockForm.DOUBLESLAB, "cobblestone"))
				.setCreativeTab(CathedralMod.tabCathedral)
				.setHardness(2.0f)
				.setResistance(10.0f);
		
		for(EnumCobblestoneSlabType type: EnumCobblestoneSlabType.values()) {
			stairsCobblestone.add(new BlockStairsGeneric(featureObjectName(BlockForm.STAIRS, "cobblestone_" + type.getName() ), Blocks.COBBLESTONE.getDefaultState()).setCreativeTab(CathedralMod.tabCathedral));
		}
		
		////// Marble //////
		
		slabMarble = new BlockSlabMarble(featureObjectName(BlockForm.SLAB, "marble"))
				.setCreativeTab(CathedralMod.tabCathedral)
				.setHardness(1.5f)
				.setResistance(10.0f);

		slabMarbleDouble = new BlockDoubleSlabMarble(featureObjectName(BlockForm.DOUBLESLAB, "marble"))
				.setCreativeTab(CathedralMod.tabCathedral)
				.setHardness(1.5f)
				.setResistance(10.0f);
		
		for(EnumMarbleSlabType type: EnumMarbleSlabType.values()) {
			stairsMarble.add(new BlockStairsGeneric(featureObjectName(BlockForm.STAIRS, "marble_" + type.getName() ), Blocks.COBBLESTONE.getDefaultState()).setCreativeTab(CathedralMod.tabCathedral));
		}

		
		////// Grass o'Lantern //////
		
		blockGrassOLantern = new BlockGrassOLantern();

	}

	@Override
	public void createItems() {
	}

	@Override
	public void registerBlocks(IForgeRegistry<Block> registry) {
		registry.registerAll(
			blockStone,
			blockEndstone,
			slabEndstone,
			slabEndstoneDouble,
			slabLimestone,
			slabLimestoneDouble,
			slabCobblestone,
			slabCobblestoneDouble,
			slabMarble,
			slabMarbleDouble,
			blockGrassOLantern
		);

		registry.registerAll(stairsEndstone.toArray(new Block[0]));
		registry.registerAll(stairsLimestone.toArray(new Block[0]));
		registry.registerAll(stairsCobblestone.toArray(new Block[0]));
		registry.registerAll(stairsMarble.toArray(new Block[0]));
	}

	@Override
	public void registerItems(IForgeRegistry<Item> registry) {
		registry.register(((BlockMultiVariant<EnumStoneType>)blockStone).getItemMultiTexture());
		registry.register(((BlockMultiVariant<EnumEndStoneType>)blockEndstone).getItemMultiTexture());

		//Endstone Slabs
		ItemSlab itemSlabEndstone = new ItemSlab(slabEndstone, (BlockSlab)slabEndstone, (BlockSlab)slabEndstoneDouble);
		itemSlabEndstone.setRegistryName(slabEndstone.getRegistryName());
		registry.register(itemSlabEndstone);

		//Endstone Stairs
		for(EnumEndStoneSlabType type: EnumEndStoneSlabType.values()) {
			registry.register(new ItemBlock(stairsEndstone.get(type.ordinal())).setRegistryName(stairsEndstone.get(type.ordinal()).getRegistryName()));
		}

		//Limestone Slabs
		ItemSlab itemSlabLimestone = new ItemSlab(slabLimestone, (BlockSlab)slabLimestone, (BlockSlab)slabLimestoneDouble);
		itemSlabLimestone.setRegistryName(slabLimestone.getRegistryName());
		registry.register(itemSlabLimestone);
		
		//Limestone Stairs
		for(EnumLimestoneSlabType type: EnumLimestoneSlabType.values()) {
			registry.register(new ItemBlock(stairsLimestone.get(type.ordinal())).setRegistryName(stairsLimestone.get(type.ordinal()).getRegistryName()));
		}

		//Cobblestone Slabs
		ItemSlab itemSlabCobblestone = new ItemSlab(slabCobblestone, (BlockSlab)slabCobblestone, (BlockSlab)slabCobblestoneDouble);
		itemSlabCobblestone.setRegistryName(slabCobblestone.getRegistryName());
		registry.register(itemSlabCobblestone);
		
		//Cobblestone Stairs
		for(EnumCobblestoneSlabType type: EnumCobblestoneSlabType.values()) {
			registry.register(new ItemBlock(stairsCobblestone.get(type.ordinal())).setRegistryName(stairsCobblestone.get(type.ordinal()).getRegistryName()));
		}
		
		//Marble Slabs
		ItemSlab itemSlabMarble = new ItemSlab(slabMarble, (BlockSlab)slabMarble, (BlockSlab)slabMarbleDouble);
		itemSlabMarble.setRegistryName(slabMarble.getRegistryName());
		registry.register(itemSlabMarble);
		
		//Marble Stairs
		for(EnumMarbleSlabType type: EnumMarbleSlabType.values()) {
			registry.register(new ItemBlock(stairsMarble.get(type.ordinal())).setRegistryName(stairsMarble.get(type.ordinal()).getRegistryName()));
		}
		
		registry.register(new ItemBlock(blockGrassOLantern).setRegistryName(blockGrassOLantern.getRegistryName()));
		
	}

	private void tryRegisterBlockOre(String oreName, ItemStack ore) {
		if(!ore.isEmpty()) {
			OreDictionary.registerOre(oreName, ore);
		}
	}

	public static ItemStack getRawEndstone() {
		return new ItemStack(Blocks.END_STONE);
	}

	public static ItemStack getRawLimestone() {
		return new ItemStack(Block.REGISTRY.getObject(new ResourceLocation("chisel", "limestone2")), 1, 7);
	}
	
	@Override
	public void registerRecipes(IForgeRegistry<IRecipe> registry) {

		String endstoneOre = "blockEndstone";

		//Basalt Ore Dictionary Registrations
		tryRegisterBlockOre(endstoneOre, getRawEndstone());

		//Endstone Slab and Stairs Recipes
		for(EnumEndStoneSlabType type: EnumEndStoneSlabType.values()) {
			Block baseBlock = Block.REGISTRY.getObject(type.getBaseResourceLocation());
			if(baseBlock != Blocks.AIR) {
				ItemStack baseItemBlock = new ItemStack(baseBlock, 1, type.getBaseMeta());
				GameRegistry.addShapedRecipe(
						new ResourceLocation(ModConstants.MODID, slabEndstone.getRegistryName().getResourcePath() + "." + type.getUnlocalizedName()),
						null,
						new ItemStack(slabEndstone, 6, type.getMetadata()), //Output
						"xxx",
						'x', baseItemBlock
						);

				GameRegistry.addShapedRecipe(
						new ResourceLocation(ModConstants.MODID, stairsEndstone.get(type.getMetadata()).getRegistryName().getResourcePath()),
						null,
						new ItemStack(stairsEndstone.get(type.getMetadata()), 8), //Output
						"x  ",
						"xx ",
						"xxx",
						'x', baseItemBlock
						);
			}
		}

		//Limestone Slab and Stairs Recipes
		for(EnumLimestoneSlabType type: EnumLimestoneSlabType.values()) {
			Block baseBlock = Block.REGISTRY.getObject(type.getBaseResourceLocation());
			if(baseBlock != Blocks.AIR) {
				ItemStack baseItemBlock = new ItemStack(baseBlock, 1, type.getBaseMeta());
				GameRegistry.addShapedRecipe(
						new ResourceLocation(ModConstants.MODID, slabLimestone.getRegistryName().getResourcePath() + "." + type.getUnlocalizedName()),
						null,
						new ItemStack(slabLimestone, 6, type.getMetadata()), //Output
						"xxx",
						'x', baseItemBlock
						);

				GameRegistry.addShapedRecipe(
						new ResourceLocation(ModConstants.MODID, stairsLimestone.get(type.getMetadata()).getRegistryName().getResourcePath()),
						null,
						new ItemStack(stairsLimestone.get(type.getMetadata()), 8), //Output
						"x  ",
						"xx ",
						"xxx",
						'x', baseItemBlock
						);
			}
		}
		
		//Cobblestone Slab and Stairs Recipes
		for(EnumCobblestoneSlabType type: EnumCobblestoneSlabType.values()) {
			Block baseBlock = Block.REGISTRY.getObject(type.getBaseResourceLocation());
			if(baseBlock != Blocks.AIR) {
				ItemStack baseItemBlock = new ItemStack(baseBlock, 1, type.getBaseMeta());
				GameRegistry.addShapedRecipe(
						new ResourceLocation(ModConstants.MODID, slabCobblestone.getRegistryName().getResourcePath() + "." + type.getUnlocalizedName()),
						null,
						new ItemStack(slabCobblestone, 6, type.getMetadata()), //Output
						"xxx",
						'x', baseItemBlock
						);

				GameRegistry.addShapedRecipe(
						new ResourceLocation(ModConstants.MODID, stairsCobblestone.get(type.getMetadata()).getRegistryName().getResourcePath()),
						null,
						new ItemStack(stairsCobblestone.get(type.getMetadata()), 8), //Output
						"x  ",
						"xx ",
						"xxx",
						'x', baseItemBlock
						);
			}
		}
		
		//Marble Slab and Stairs Recipes
		for(EnumMarbleSlabType type: EnumMarbleSlabType.values()) {
			Block baseBlock = Block.REGISTRY.getObject(type.getBaseResourceLocation());
			if(baseBlock != Blocks.AIR) {
				ItemStack baseItemBlock = new ItemStack(baseBlock, 1, type.getBaseMeta());
				GameRegistry.addShapedRecipe(
						new ResourceLocation(ModConstants.MODID, slabMarble.getRegistryName().getResourcePath() + "." + type.getUnlocalizedName()),
						null,
						new ItemStack(slabMarble, 6, type.getMetadata()), //Output
						"xxx",
						'x', baseItemBlock
						);

				GameRegistry.addShapedRecipe(
						new ResourceLocation(ModConstants.MODID, stairsMarble.get(type.getMetadata()).getRegistryName().getResourcePath()),
						null,
						new ItemStack(stairsMarble.get(type.getMetadata()), 8), //Output
						"x  ",
						"xx ",
						"xxx",
						'x', baseItemBlock
						);
			}
		}
		
		GameRegistry.addShapedRecipe(
				new ResourceLocation(ModConstants.MODID, blockGrassOLantern.getRegistryName().getResourcePath()),
				null,
				new ItemStack(blockGrassOLantern),
				"g",
				"j",
				'g', Blocks.GRASS,
				'j', Blocks.LIT_PUMPKIN
			);
		
	}

	@Override
	public void registerModels(ModelRegistryEvent event) {
		((BlockMultiVariant<EnumStoneType>)blockStone).registerItemModels();
		((BlockMultiVariant<EnumEndStoneType>)blockEndstone).registerItemModels();

		for(EnumEndStoneSlabType type: EnumEndStoneSlabType.values()) {
			ModelHelper.regModel(Item.getItemFromBlock(slabEndstone), type.getMetadata(), new ResourceLocation(ModConstants.MODID, slabEndstone.getRegistryName().getResourcePath() + "." + type.getUnlocalizedName()));
		}

		for(EnumLimestoneSlabType type: EnumLimestoneSlabType.values()) {
			ModelHelper.regModel(Item.getItemFromBlock(slabLimestone), type.getMetadata(), new ResourceLocation(ModConstants.MODID, slabLimestone.getRegistryName().getResourcePath() + "." + type.getUnlocalizedName()));
		}

		for(EnumCobblestoneSlabType type: EnumCobblestoneSlabType.values()) {
			ModelHelper.regModel(Item.getItemFromBlock(slabCobblestone), type.getMetadata(), new ResourceLocation(ModConstants.MODID, slabCobblestone.getRegistryName().getResourcePath() + "." + type.getUnlocalizedName()));
		}
		
		for(EnumMarbleSlabType type: EnumMarbleSlabType.values()) {
			ModelHelper.regModel(Item.getItemFromBlock(slabMarble), type.getMetadata(), new ResourceLocation(ModConstants.MODID, slabMarble.getRegistryName().getResourcePath() + "." + type.getUnlocalizedName()));
		}
		
		ModelHelper.regModel(blockGrassOLantern);
		
		stairsEndstone.forEach(s -> ModelHelper.regModel(s));
		stairsLimestone.forEach(s -> ModelHelper.regModel(s));
		stairsCobblestone.forEach(s -> ModelHelper.regModel(s));
		stairsMarble.forEach(s -> ModelHelper.regModel(s));

	}

	@Override
	public void init() {

		//Add chisel variations for Stone Blocks
		for(EnumStoneType type: EnumStoneType.values()) {
			addChiselVariation("stonebrick", blockStone, type.getMetadata());
		}

		//Add chisel variations for Endstone Blocks
		for(EnumEndStoneType type: EnumEndStoneType.values()) {
			addChiselVariation("endstone", blockEndstone, type.getMetadata());
		}

		for(EnumEndStoneSlabType type: EnumEndStoneSlabType.values()) {
			addChiselVariation("endstoneslab", slabEndstone, type.getMetadata());
		}

		for(EnumLimestoneSlabType type: EnumLimestoneSlabType.values()) {
			addChiselVariation("limestoneslab", slabLimestone, type.getMetadata());
		}
		
		for(EnumMarbleSlabType type: EnumMarbleSlabType.values()) {
			addChiselVariation("marbleslab", slabMarble, type.getMetadata());
		}
		
		stairsEndstone.forEach(s -> addChiselVariation("endstonestairs", s, 0));

		stairsLimestone.forEach(s -> addChiselVariation("limestonestairs", s, 0));
	
		stairsMarble.forEach(s -> addChiselVariation("marblestairs", s, 0));
		
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerColorHandlers() {
		ModelHelper.regColorHandler(blockGrassOLantern, (state, world, pos, tintIndex) -> {
			return tintIndex == 0 ? Minecraft.getMinecraft().getBlockColors().colorMultiplier(grassBlockState, world, pos, tintIndex) : 0xFFFFFFFF;
		});
		
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler( ( stack,  tint) -> tint == 0 ? 0xFF88DD00 : 0xFFFFFFFF, new Item[] {Item.getItemFromBlock(blockGrassOLantern)});
	}
	
	private void addChiselVariation(String group, Block block, int meta) {
		FMLInterModComms.sendMessage("chisel", "variation:add", group + "|" + block.getRegistryName() + "|" + meta);
	}

	@Override
	public void postInit() {}

}
