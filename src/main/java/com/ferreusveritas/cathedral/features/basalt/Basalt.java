package com.ferreusveritas.cathedral.features.basalt;

import com.ferreusveritas.cathedral.CathedralMod;
import com.ferreusveritas.cathedral.ModConstants;
import com.ferreusveritas.cathedral.features.BlockForm;
import com.ferreusveritas.cathedral.features.IFeature;
import com.ferreusveritas.cathedral.features.roofing.BlockShingles;
import com.ferreusveritas.cathedral.proxy.ModelHelper;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

public class Basalt implements IFeature {
	
	public static final String featureName = "basalt";
	
	public Block blockCarved;
	public Block blockCheckered;
	
	public Block slabCarved;
	public Block slabCarvedDouble;
	public Block slabCheckered;
	public Block slabCheckeredDouble;
	
	public Block stairsCarved[] = new BlockStairsBasalt[BlockSlabBasalt.EnumType.values().length];
	
	public final float basaltHardness = 2.5f;
	public final float basaltResistance = 20f;
	public final float marbleHardness = 2.0f;
	public final float marbleResistance = 10f;
	
	@Override
	public String getName() {
		return featureName;
	}
	
	@Override
	public void preInit() {

	}

	@Override
	public void createBlocks() {
		
		blockCarved = new BlockBasalt(featureObjectName(BlockForm.BLOCK, "carved"));		
		
		slabCarved = new BlockSlabBasalt(featureObjectName(BlockForm.SLAB, "carved"))
			.setCreativeTab(CathedralMod.tabBasalt)
			.setHardness(basaltHardness)
			.setResistance(basaltResistance);

		slabCarvedDouble = new BlockDoubleSlabBasalt(featureObjectName(BlockForm.DOUBLESLAB, "carved"))
				.setCreativeTab(CathedralMod.tabBasalt)
				.setHardness(basaltHardness)
				.setResistance(basaltResistance);
		
		blockCheckered = new BlockCheckered(featureObjectName(BlockForm.BLOCK, "checkered"));
		
		slabCheckered = new BlockSlabCheckered(featureObjectName(BlockForm.SLAB, "checkered"))
			.setCreativeTab(CathedralMod.tabBasalt)
			.setHardness((basaltHardness + marbleHardness) / 2F)
			.setResistance((basaltResistance + marbleResistance) / 2F);

		slabCheckeredDouble = new BlockDoubleSlabCheckered(featureObjectName(BlockForm.DOUBLESLAB, "checkered"))
				.setCreativeTab(CathedralMod.tabBasalt)
				.setHardness(basaltHardness)
				.setResistance(basaltResistance);
		
		for(BlockSlabBasalt.EnumType type: BlockSlabBasalt.EnumType.values()) {
			stairsCarved[type.ordinal()] = new BlockStairsBasalt(featureObjectName(BlockForm.STAIRS, "carved_" + type.getName() ), blockCarved.getDefaultState());
		}
		
		//Basalt Slabs
		/*basaltSlab.carverHelper.addVariation("tile.basalt_basaltslab-paver.name", 2, "basalt-worn-brick", Cathedral.MODID);
		basaltSlab.carverHelper.addVariation("tile.basalt_basaltslab-tiles.name", 6, "basalt-tiles", Cathedral.MODID);
		basaltSlab.carverHelper.addVariation("tile.basalt_basaltslab-slabs.name", 7, "basalt-slabs", Cathedral.MODID);
		basaltSlab.carverHelper.addVariation("tile.basalt_basaltslab-smallbricks.name", 9, "basalt-smallbricks", Cathedral.MODID);
		basaltSlab.carverHelper.addVariation("tile.basalt_basaltslab-smallchaotic.name", 10, "basalt-smallchaotic", Cathedral.MODID);
		basaltSlab.carverHelper.addVariation("tile.basalt_basaltslab-smalltiles.name", 11, "basalt-smalltiles", Cathedral.MODID);
		basaltSlab.carverHelper.registerAll(basaltSlab, "basaltslab", ItemCarvableSlab.class);
		basaltSlab.registerSlabTop();*/
		
		//This must be ran in init because project red doesn't register it's stone until after it's init
		/*if(Loader.isModLoaded("ProjRed|Exploration")){
			basaltBase = GameRegistry.findBlock("ProjRed|Exploration", "projectred.exploration.stone");
		} else {
			//basaltBase = GameRegistry.registerBlock(block, "nothing");
		}*/
		
		//OreDictionary.registerOre("basalt", new ItemStack(basaltBase, 1, 3));
		//OreDictionary.registerOre("basaltBrick", new ItemStack(basaltBase, 1, 4));

		//Stairs
		//Block basaltBase = Block.REGISTRY.getObject(new ResourceLocation("chisel", "basalt2"));
				
		/*for(int i = 0; i < baseBlocks.length; i++){
			//basaltStairs[baseBlocks[i].select] = (BlockGenericStairs) new BlockGenericStairs(baseBlocks[i]).setCreativeTab(Cathedral.tabBasalt);
			//GameRegistry.registerBlock(basaltStairs[baseBlocks[i].select], baseBlocks[i].blockName + "Stairs");
			//GameRegistry.addRecipe(new ItemStack(basaltStairs[baseBlocks[i].select], 6, 0), "X  ", "XX ", "XXX", 'X', new ItemStack(baseBlocks[i].block, 1, baseBlocks[i].metaData) );
		}*/
		
		//Explicitly Added Variations
		/*{
			ICarvingRegistry Carving = CarvingUtils.getChiselRegistry();
			Carving.addVariation("basaltblock", basaltBase, 3, -2);
			Carving.addVariation("basaltblock", basaltBase, 4, -1);

			for(int i = 0; i < 6; i++){
				Carving.addVariation("basaltStairs", basaltStairs[i], 0, 1);
			}
		}*/
		

	}

	@Override
	public void createItems() {
		// TODO Auto-generated method stub
	}

	@Override
	public void registerBlocks(IForgeRegistry<Block> registry) {
		//registry.register(basaltBase);
		registry.registerAll(
				blockCarved,
				slabCarved,
				slabCarvedDouble,
				blockCheckered,
				slabCheckered,
				slabCheckeredDouble
			);
		
		registry.registerAll(stairsCarved);
	}

	@Override
	public void registerItems(IForgeRegistry<Item> registry) {
		
		//Basalt Blocks
		registry.register(new ItemMultiTexture(blockCarved, blockCarved, new ItemMultiTexture.Mapper() {
            public String apply(ItemStack stack) {
                return BlockBasalt.EnumType.byMetadata(stack.getMetadata()).getUnlocalizedName();
            }
        }).setRegistryName(blockCarved.getRegistryName()));

		//Basalt Slabs
		ItemSlab itemSlabBasalt = new ItemSlab(slabCarved, (BlockSlab)slabCarved, (BlockSlab)slabCarvedDouble);
		itemSlabBasalt.setRegistryName(slabCarved.getRegistryName());
		registry.register(itemSlabBasalt);

		//Checkered Blocks
		registry.register(new ItemMultiTexture(blockCheckered, blockCheckered, new ItemMultiTexture.Mapper() {
            public String apply(ItemStack stack) {
                return BlockCheckered.EnumType.byMetadata(stack.getMetadata()).getUnlocalizedName();
            }
        }).setRegistryName(blockCheckered.getRegistryName()));

		//Checkered Slabs
		ItemSlab itemSlabCheckered = new ItemSlab(slabCheckered, (BlockSlab)slabCheckered, (BlockSlab)slabCheckeredDouble);
		itemSlabCheckered.setRegistryName(slabCheckered.getRegistryName());
		registry.register(itemSlabCheckered);
		
		//Basalt Stairs
		for(BlockSlabBasalt.EnumType type: BlockSlabBasalt.EnumType.values()) {
			registry.register(new ItemBlock(stairsCarved[type.ordinal()]).setRegistryName(stairsCarved[type.ordinal()].getRegistryName()));
		}

	}

	@Override
	public void registerRecipes(IForgeRegistry<IRecipe> registry) {
		
		//Ore Dictionary Registrations
		for(String name : new String[] {"basalt", "basalt2"}) {
			Block basaltBase = Block.REGISTRY.getObject(new ResourceLocation("chisel", name));
			if(basaltBase != Blocks.AIR) {
				OreDictionary.registerOre("basalt", basaltBase);
			}
		}		
		
		//int basaltSlabMetas[] = { 2, 6, 7, 9, 10, 11 };
		
		//Basalt Slab Recipes
		/*
		for(int i = 0; i < basaltSlabMetas.length; i++){
			GameRegistry.addRecipe(new ItemStack(basaltSlab, 6, basaltSlabMetas[i]), "XXX", 'X', new ItemStack(basaltBlock, 1, basaltSlabMetas[i]) );
		}*/
		
		//GameRegistry.addRecipe(new ItemStack(basaltSlab, 6, 7), "XXX", 'X', new ItemStack(basaltBlock, 1, 1) );//Basalt Paver
		//GameRegistry.addRecipe(new ItemStack(basaltBlock, 1, 7), "X", "X", 'X', new ItemStack(basaltSlab, 1, 7));
		
		//Checkered Tile Recipes
		//GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(checkeredBlock, 4, 0), true, new Object[]{"mb", "bm", 'b', "basalt", 'm', "marble"}));
		//GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(checkeredBlock, 4, 0), true, new Object[]{"bm", "mb", 'b', "basalt", 'm', "marble"}));
		
		//Thermal Expansion Capabilities
		/*if(Loader.isModLoaded("ThermalExpansion")){//Having thermal expansion adds the ability to create basalt from obsidian dust and stone
			//ThermalExpansion Smelter recipe for Basalt
			ItemStack obsidianDust = new ItemStack(GameRegistry.findItem("ThermalFoundation", "material"), 1, 4);
			
			ThermalExpansionHelper.addSmelterRecipe(2000, new ItemStack(Blocks.stone, 1, 0), obsidianDust, new ItemStack(basaltBase, 1, 3));
			ThermalExpansionHelper.addSmelterRecipe(2000, new ItemStack(Blocks.cobblestone, 1, 0), obsidianDust, new ItemStack(basaltBase, 1, 2));
		} else {
			GameRegistry.addRecipe(new ItemStack(basaltBase, 1, 2), "XXX", "XOX", "XXX", 'X', new ItemStack(Blocks.stone, 1), 'O', new ItemStack(Blocks.obsidian));
			GameRegistry.addRecipe(new ItemStack(basaltBase, 1, 3), "XXX", "XOX", "XXX", 'X', new ItemStack(Blocks.cobblestone, 1), 'O', new ItemStack(Blocks.obsidian));
		}*/		
	}

	@Override
	public void registerModels(ModelRegistryEvent event) {

		for(BlockBasalt.EnumType type: BlockBasalt.EnumType.values()) {
			ModelHelper.regModel(Item.getItemFromBlock(blockCarved), type.getMetadata(), new ResourceLocation(ModConstants.MODID, blockCarved.getRegistryName().getResourcePath() + "." + type.getUnlocalizedName()));
		}
		
		for(BlockSlabBasalt.EnumType type: BlockSlabBasalt.EnumType.values()) {
			ModelHelper.regModel(Item.getItemFromBlock(slabCarved), type.getMetadata(), new ResourceLocation(ModConstants.MODID, slabCarved.getRegistryName().getResourcePath() + "." + type.getUnlocalizedName()));
		}
		
		for(BlockCheckered.EnumType type: BlockCheckered.EnumType.values()) {
			ModelHelper.regModel(Item.getItemFromBlock(blockCheckered), type.getMetadata(), new ResourceLocation(ModConstants.MODID, blockCheckered.getRegistryName().getResourcePath() + "." + type.getUnlocalizedName()));
		}
		
		for(BlockSlabCheckered.EnumType type: BlockSlabCheckered.EnumType.values()) {
			ModelHelper.regModel(Item.getItemFromBlock(slabCheckered), type.getMetadata(), new ResourceLocation(ModConstants.MODID, slabCheckered.getRegistryName().getResourcePath() + "." + type.getUnlocalizedName()));
		}
		
		for(Block stairs: stairsCarved) {
			ModelHelper.regModel(stairs);
		}
	}

	@Override
	public void init() {
		//Add chisel variations for Basalt
		for(BlockBasalt.EnumType type: BlockBasalt.EnumType.values()) {
			FMLInterModComms.sendMessage("chisel", "variation:add", "basalt|cathedral:basalt_block_carved|" + type.getMetadata());
		}
		
		for(BlockCheckered.EnumType type: BlockCheckered.EnumType.values()) {
			FMLInterModComms.sendMessage("chisel", "variation:add", "basaltcheckered|cathedral:basalt_block_checkered|" + type.getMetadata());
		}
		
	}

	@Override
	public void postInit() {}
}
