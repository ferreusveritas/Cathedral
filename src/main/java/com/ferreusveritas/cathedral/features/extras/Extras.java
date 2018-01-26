package com.ferreusveritas.cathedral.features.extras;

import java.util.ArrayList;

import com.ferreusveritas.cathedral.common.blocks.BaseBlockDef;
import com.ferreusveritas.cathedral.common.blocks.BlockSlabBase;
import com.ferreusveritas.cathedral.common.blocks.BlockGenericStairs;
import com.ferreusveritas.cathedral.features.IFeature;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class Extras implements IFeature {
	
	public static final String featureName = "extras";

	public BlockGenericStairs stairs[] = new BlockGenericStairs[5];
	public BlockSlabBase slabsVarious;
	public Block blockStone;
	
	public ArrayList<BaseBlockDef> baseBlocks = new ArrayList<BaseBlockDef>();
	
	@Override
	public String getName() {
		return featureName;
	}
	
	@Override
	public void preInit() {}
	
	@Override
	public void createBlocks() {
	

	
	//Create and Register Stairs
	/*for(BaseBlockDef baseBlock : baseBlocks){
		extraStairs[baseBlock.select] = (BlockGenericStairs) new BlockGenericStairs(baseBlock).setCreativeTab(Cathedral.tabCathedral);
		GameRegistry.registerBlock(extraStairs[baseBlock.select], baseBlock.blockName + "Stairs");
	}*/

	//Create and Register Slabs
	/*extraSlabs = (BlockGenericSlab) new BlockGenericSlab(baseBlocks).setBlockName(Cathedral.MODID + "_extraslabs");
	GameRegistry.registerBlock(extraSlabs, ItemGenericSlab.class, "ExtraSlabs");*/

	//Explicitly Add Carving Variations for Stairs and Slabs
	/*{
		ICarvingRegistry Carving = CarvingUtils.getChiselRegistry();

		Carving.addVariation("endstonestairs", extraStairs[3], 0, 0);
		Carving.addVariation("endstonestairs", extraStairs[4], 0, 1);

		Carving.addVariation("endstoneslab", extraSlabs, 3, 0);
		Carving.addVariation("endstoneslab", extraSlabs, 4, 1);
	}*/


	/*
	extraStone.carverHelper.addVariation("tile." + Cathedral.MODID + "_stone.stone-panel.name", 0, "stone-panel", null, 0, Cathedral.MODID, (ISubmapManager) null, 100);
	extraStone.carverHelper.addVariation("tile." + Cathedral.MODID + "_stone.stone-knot.name", 1, "stone-knot", null, 0, Cathedral.MODID, (ISubmapManager) null, 100);
	stainedGlass.carverHelper.addVariation("tile." + Cathedral.MODID + "_glass.stained-1.name", 0, "stained-1", null, 0, Cathedral.MODID);
	stainedGlass.carverHelper.addVariation("tile." + Cathedral.MODID + "_glass.stained-2.name", 1, "stained-2", null, 0, Cathedral.MODID);\
	*/

	
	//Register Extra Blocks
	/*
	extraStone.carverHelper.registerBlock(extraStone, "stonebricksmooth");
	stainedGlass.carverHelper.registerBlock(stainedGlass, "stainedglass");
	*/

	//Register Variations
	/*
	extraStone.carverHelper.registerVariations("stonebricksmooth");
	stainedGlass.carverHelper.registerVariations("stainedglass");
	*/
		
	}

	@Override
	public void createItems() {
		
		
				
	}

	@Override
	public void registerBlocks(IForgeRegistry<Block> registry) {

	}
	
	@Override
	public void registerItems(IForgeRegistry<Item> registry) {
	}
	
	@Override
	public void registerRecipes(IForgeRegistry<IRecipe> registry) {
		//Stairs and Slabs
		/*for(BaseBlockDef baseBlock : baseBlocks){
			//Stairs
			GameRegistry.addRecipe(new ItemStack(extraStairs[baseBlock.select], 6, 0), "X  ", "XX ", "XXX", 'X', new ItemStack(baseBlock.block, 1, baseBlock.metaData));
			//Slabs
			GameRegistry.addRecipe(new ItemStack(extraSlabs, 6, baseBlock.select), "XXX", 'X', new ItemStack(baseBlock.block, 1, baseBlock.metaData));
		}*/
	}

	@Override
	public void registerModels(ModelRegistryEvent event) {}
	
	@Override
	public void init() {}
	
	@Override
	public void postInit() {}
	
}
