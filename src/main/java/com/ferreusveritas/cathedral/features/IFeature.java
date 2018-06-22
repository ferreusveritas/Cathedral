package com.ferreusveritas.cathedral.features;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;

public interface IFeature {
	
	public String getName();
	
	public void preInit();
	
	public void createBlocks();
	
	public void createItems();
	
	public void registerBlocks(IForgeRegistry<Block> registry);
	
	public void registerItems(IForgeRegistry<Item> registry);
	
	public void registerRecipes(IForgeRegistry<IRecipe> registry);
	
	public void registerModels(ModelRegistryEvent event);
	
	public void init();
	
	public void postInit();
	
	default String featureObjectName(BlockForm form, String style) {
		return getName() + "_" + form + "_" + style;
	}
	
	default void registerColorHandlers() { }
	
}
