package com.ferreusveritas.cathedral.features.extras;

import com.ferreusveritas.cathedral.CathedralMod;
import com.ferreusveritas.cathedral.ModConstants;
import com.ferreusveritas.cathedral.common.blocks.BlockMultiVariant;
import com.ferreusveritas.cathedral.common.blocks.BlockStairsGeneric;
import com.ferreusveritas.cathedral.features.BlockForm;
import com.ferreusveritas.cathedral.features.IFeature;
import com.ferreusveritas.cathedral.features.extras.FeatureTypes.EnumEndStoneSlabType;
import com.ferreusveritas.cathedral.features.extras.FeatureTypes.EnumEndStoneType;
import com.ferreusveritas.cathedral.features.extras.FeatureTypes.EnumStoneType;
import com.ferreusveritas.cathedral.proxy.ModelHelper;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class Extras implements IFeature {
	
	public static final String featureName = "extras";

	public Block blockStone;
	public Block blockEndstone;
	
	public Block slabEndstone;
	public Block slabEndstoneDouble;
	
	public BlockStairsGeneric stairs[] = new BlockStairsGeneric[5];
	
	@Override
	public String getName() {
		return featureName;
	}
	
	@Override
	public void preInit() {}
	
	@Override
	public void createBlocks() {
	
	//Create carved blocks
	blockStone = new BlockMultiVariant<EnumStoneType>(Material.ROCK, EnumStoneType.class, featureObjectName(BlockForm.BLOCK, "stone")) {
		@Override
		public void makeVariantProperty() {
			variant = PropertyEnum.<EnumStoneType>create("variant", EnumStoneType.class);
		}
	}.setCreativeTab(CathedralMod.tabBasalt);
	
	blockEndstone = new BlockMultiVariant<EnumEndStoneType>(Material.ROCK, EnumEndStoneType.class, featureObjectName(BlockForm.BLOCK, "endstone")) {
		@Override
		public void makeVariantProperty() {
			variant = PropertyEnum.<EnumEndStoneType>create("variant", EnumEndStoneType.class);
		}
	}.setCreativeTab(CathedralMod.tabBasalt)
	.setHardness(3)
	.setResistance(45);
	
	slabEndstone = new BlockSlabEndstone(featureObjectName(BlockForm.SLAB, "endstone"))
			.setCreativeTab(CathedralMod.tabBasalt)
			.setHardness(3)
			.setResistance(45);
	
	slabEndstoneDouble = new BlockDoubleSlabEndstone(featureObjectName(BlockForm.DOUBLESLAB, "endstone"))
			.setCreativeTab(CathedralMod.tabBasalt)
			.setHardness(3)
			.setResistance(45);
		
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
				slabEndstoneDouble
			);
	}
	
	@Override
	public void registerItems(IForgeRegistry<Item> registry) {
		registry.register(((BlockMultiVariant<EnumStoneType>)blockStone).getItemMultiTexture());
		registry.register(((BlockMultiVariant<EnumEndStoneType>)blockEndstone).getItemMultiTexture());
		
		//Basalt Slabs
		ItemSlab itemSlabEndstone = new ItemSlab(slabEndstone, (BlockSlab)slabEndstone, (BlockSlab)slabEndstoneDouble);
		itemSlabEndstone.setRegistryName(slabEndstone.getRegistryName());
		registry.register(itemSlabEndstone);
	}
	
	@Override
	public void registerRecipes(IForgeRegistry<IRecipe> registry) {
	}

	@Override
	public void registerModels(ModelRegistryEvent event) {
		((BlockMultiVariant<EnumStoneType>)blockStone).registerItemModels();
		((BlockMultiVariant<EnumEndStoneType>)blockEndstone).registerItemModels();
		
		for(EnumEndStoneSlabType type: EnumEndStoneSlabType.values()) {
			ModelHelper.regModel(Item.getItemFromBlock(slabEndstone), type.getMetadata(), new ResourceLocation(ModConstants.MODID, slabEndstone.getRegistryName().getResourcePath() + "." + type.getUnlocalizedName()));
		}
	}
	
	@Override
	public void init() {}
	
	@Override
	public void postInit() {}
	
}
