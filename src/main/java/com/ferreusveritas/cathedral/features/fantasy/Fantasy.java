package com.ferreusveritas.cathedral.features.fantasy;

import com.ferreusveritas.cathedral.features.IFeature;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class Fantasy implements IFeature {

	public static final String featureName = "fantasy";
	
	Block fantasy;
	Block fantasy2;
	Block extraFantasy;
	Block extraFantasy2;
	
	@Override
	public String getName() {
		return featureName;
	}

	@Override
	public void preInit() {}

	@Override
	public void createBlocks() {
		
		/*{
		Block chiselFantasy = GameRegistry.findBlock("chisel", "fantasyblock2");
		Block chiselEndstone = GameRegistry.findBlock("chisel", "end_Stone");

		//Create an array of Block Definitions that will be used to make Slabs and Stairs
		baseBlocks.ensureCapacity(5);
		baseBlocks.add(new BaseBlockDef(0, chiselFantasy, 0, "fantasy-brick", "FantasyBrick", 2.0f, 10f));
		baseBlocks.add(new BaseBlockDef(1, chiselFantasy, 3, "fantasy-damaged", "FantasyDamaged", 2.0f, 10f));
		baseBlocks.add(new BaseBlockDef(2, chiselFantasy, 14, "fantasy-disarray", "FantasyDisarray", 2.0f, 10f));
		baseBlocks.add(new BaseBlockDef(3, chiselEndstone, 0, "endstone-brick", "EndstoneBrick", 3.0f, 15f));
		baseBlocks.add(new BaseBlockDef(4, chiselEndstone, 2, "endstone-checkered", "EndstoneCheckered", 3.0f, 15f));
	}*/

		//Explicitly Add Carving Variations for Stairs and Slabs
		/*{
			ICarvingRegistry Carving = CarvingUtils.getChiselRegistry();
			Carving.addVariation("fantasystairs", extraStairs[0], 0, 0);
			Carving.addVariation("fantasystairs", extraStairs[1], 0, 1);
			Carving.addVariation("fantasystairs", extraStairs[2], 0, 2);

			Carving.addVariation("fantasyslab", extraSlabs, 0, 0);
			Carving.addVariation("fantasyslab", extraSlabs, 1, 1);
			Carving.addVariation("fantasyslab", extraSlabs, 2, 2);
		}*/

		
		//Extra blocks that Chisel should have had
		//extraFantasy = (BlockCarvable) new BlockCarvable(Material.ROCK).setCreativeTab(Cathedral.tabCathedral).setHardness(2.0f).setResistance(10.0f);
		//extraFantasy2 = (BlockCarvable) new BlockCarvable(Material.ROCK).setCreativeTab(Cathedral.tabCathedral).setHardness(2.0f).setResistance(10.0f);
		//extraStone = (BlockCarvable) new BlockCarvable(Material.ROCK).setCreativeTab(Cathedral.tabCathedral).setHardness(1.5f).setResistance(10.0f);

		//Add variations for Extra Blocks
		/*extraFantasy.carverHelper.addVariation("tile." + Cathedral.MODID + "_fantasy.fantasypillarlarge.name", 0, "fantasypillarlarge", null, 0, Cathedral.MODID, (ISubmapManager) null, 100);
		extraFantasy.carverHelper.addVariation("tile." + Cathedral.MODID + "_fantasy.fantasyknot.name", 1, "fantasyknot", null, 0, Cathedral.MODID, (ISubmapManager) null, 100);
		extraFantasy.carverHelper.addVariation("tile." + Cathedral.MODID + "_fantasy.fantasyembedded.name", 2, "fantasyembedded", null, 0, Cathedral.MODID, (ISubmapManager) null, 100);
		extraFantasy2.carverHelper.addVariation("tile." + Cathedral.MODID + "_fantasy.fantasy2pillarlarge.name", 0, "fantasy2pillarlarge", null, 0, Cathedral.MODID, (ISubmapManager) null, 100);
		extraFantasy2.carverHelper.addVariation("tile." + Cathedral.MODID + "_fantasy.fantasy2knot.name", 1, "fantasy2knot", null, 0, Cathedral.MODID, (ISubmapManager) null, 100);
		extraFantasy2.carverHelper.addVariation("tile." + Cathedral.MODID + "_fantasy.fantasy2embedded.name", 2, "fantasy2embedded", null, 0, Cathedral.MODID, (ISubmapManager) null, 100);
		*/
	
	}

	@Override
	public void createItems() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerBlocks(IForgeRegistry<Block> registry) {
		
		/*extraFantasy.carverHelper.registerBlock(extraFantasy, "fantasyblock");
		extraFantasy2.carverHelper.registerBlock(extraFantasy2, "fantasyblock2");
		
		extraFantasy.carverHelper.registerVariations("fantasyblock");
		extraFantasy2.carverHelper.registerVariations("fantasy");*/
		
	}

	@Override
	public void registerItems(IForgeRegistry<Item> registry) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerRecipes(IForgeRegistry<IRecipe> registry) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerModels(ModelRegistryEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postInit() {
		// TODO Auto-generated method stub
		
	}
	
}
