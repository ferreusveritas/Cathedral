package com.ferreusveritas.cathedral.features.chess;

import com.ferreusveritas.cathedral.features.IFeature;
import com.ferreusveritas.cathedral.models.ModelBlockChess;
import com.ferreusveritas.cathedral.models.ModelUtils;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

public class Chess implements IFeature {
	
	public static final String featureName = "chess";
	
	public Block blockChess;
	
	@Override
	public String getName() {
		return featureName;
	}
	
	@Override
	public void preInit() { }
	
	@Override
	public void createBlocks() {
		blockChess = new BlockChess();
	}
	
	@Override
	public void createItems() { }
	
	@Override
	public void registerBlocks(IForgeRegistry<Block> registry) {
		registry.register(blockChess);
	}
	
	@Override
	public void registerItems(IForgeRegistry<Item> registry) { }
	
	@Override
	public void registerRecipes(IForgeRegistry<IRecipe> registry) { }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerModels(ModelRegistryEvent event) {
		ModelUtils.setupCustomModel(blockChess, "chess", resloc -> new ModelBlockChess(resloc));
	}
	
	@Override
	public void init() { }
	
	@Override
	public void postInit() { }
	
}
