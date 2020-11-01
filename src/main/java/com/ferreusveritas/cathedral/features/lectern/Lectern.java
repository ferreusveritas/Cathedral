package com.ferreusveritas.cathedral.features.lectern;

import com.ferreusveritas.cathedral.ModConstants;
import com.ferreusveritas.cathedral.features.IFeature;
import com.ferreusveritas.cathedral.proxy.ModelHelper;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

public class Lectern implements IFeature {
	
	public static final String featureName = "lectern";
	
	public Block blockLectern;
	
	@Override
	public String getName() {
		return featureName;
	}
	
	@Override
	public void preInit() { }
	
	@Override
	public void createBlocks() {
		blockLectern = new BlockLectern("lectern");
	}
	
	@Override
	public void createItems() {
	}
	
	@Override
	public void registerBlocks(IForgeRegistry<Block> registry) {
		registry.register(blockLectern);
	}
	
	@Override
	public void registerItems(IForgeRegistry<Item> registry) {
		registry.register(new ItemBlock(blockLectern).setRegistryName(blockLectern.getRegistryName()));
	}
	
	@Override
	public void registerRecipes(IForgeRegistry<IRecipe> registry) {
		
		//Lectern
		GameRegistry.addShapedRecipe(
				new ResourceLocation(ModConstants.MODID, "lectern"),
				null,//Group
				new ItemStack(blockLectern),
				"sss",
				" b ",
				" s ",
				's', "slabWood",
				'b', new ItemStack(Blocks.BOOKSHELF)
				);
		
	}
	
	@Override
	public void registerModels(ModelRegistryEvent event) {
		ModelHelper.regModel(Item.getItemFromBlock(blockLectern));
	}
	
	@Override
	public void init() { }
	
	@Override
	public void postInit() {
		BookManager.add(new WrittenBookHandler());
		
		if (Loader.isModLoaded("patchouli")) {
			initPatchouliCompat();
		}
	}
	
	@Optional.Method(modid = "patchouli")
	public static void initPatchouliCompat() {
		BookManager.add(new PatchouliBookHandler());
	}
	
}
