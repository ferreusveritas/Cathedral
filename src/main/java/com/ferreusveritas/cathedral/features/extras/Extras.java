package com.ferreusveritas.cathedral.features.extras;

import java.util.ArrayList;

import com.ferreusveritas.cathedral.Cathedral;
import com.ferreusveritas.cathedral.blocks.BaseBlockDef;
import com.ferreusveritas.cathedral.blocks.BlockGenericSlab;
import com.ferreusveritas.cathedral.blocks.BlockGenericStairs;
import com.ferreusveritas.cathedral.features.IFeature;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.registries.IForgeRegistry;

public class Extras implements IFeature {
	
	public static final String featureName = "extras";

	public BlockStainedGlass stainedGlass;
	public BlockStoneRailing stoneRailingBlock;
	public BlockChain chainBlock;
	public BlockGenericStairs extraStairs[] = new BlockGenericStairs[5];
	public BlockGenericSlab extraSlabs;
	public Block extraStone;
	
	
	public ArrayList<BaseBlockDef> baseBlocks = new ArrayList<BaseBlockDef>();
	
	@Override
	public String getName() {
		return featureName;
	}
	
	@Override
	public void preInit() {}
	
	@Override
	public void createBlocks() {
	
	stainedGlass = (BlockStainedGlass) new BlockStainedGlass();
	stoneRailingBlock = new BlockStoneRailing();
	chainBlock = new BlockChain();
	
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
		registry.register(stainedGlass);
		registry.register(stoneRailingBlock);
		registry.register(chainBlock);
		//registry.registerAll(stainedGlass, stoneRailingBlock, chainBlock);
	}
	
	@Override
	public void registerItems(IForgeRegistry<Item> registry) {
		
		registry.register(new ItemMultiTexture(stainedGlass, stainedGlass, new ItemMultiTexture.Mapper() {
            public String apply(ItemStack stack) {
                return BlockStainedGlass.EnumType.byMetadata(stack.getMetadata()).getUnlocalizedName();
            }
        }).setRegistryName(stainedGlass.getRegistryName()));
		
		registry.register(new ItemMultiTexture(stoneRailingBlock, stoneRailingBlock, new ItemMultiTexture.Mapper() {
            public String apply(ItemStack stack) {
                return BlockStoneRailing.EnumType.byMetadata(stack.getMetadata()).getUnlocalizedName();
            }
        }).setRegistryName(stoneRailingBlock.getRegistryName()));

		registry.register(new ItemMultiTexture(chainBlock, chainBlock, new ItemMultiTexture.Mapper() {
            public String apply(ItemStack stack) {
                return BlockChain.EnumType.byMetadata(stack.getMetadata()).getUnlocalizedName();
            }
        }).setRegistryName(chainBlock.getRegistryName()));
		
	}
	
	@Override
	public void registerRecipes(IForgeRegistry<IRecipe> registry) {

		//Stained Glass
		registry.register(
			new ShapedOreRecipe(
				null,
				new ItemStack(stainedGlass, 16, 0),
				new Object[] {
					"cgm",
					"glg",
					"ygp", 
					'l', OreDictionary.doesOreNameExist("ingotLead") ? "ingotLead" : "ingotIron",//Lead is the accurate choice. Iron sucks but whatever, 
					'g', "blockGlass",
					'c', "dyeCyan",
					'm', "dyeMagenta",
					'y', "dyeYellow",
					'p', "dyePink"
				}
			).setRegistryName("stainedglass")
		);

		//Stone Railings
		//TODO

		
		//Chains
		for(BlockChain.EnumType type: BlockChain.EnumType.values()) {
			String nuggetName = "nugget" + type.getName();
			if(OreDictionary.doesOreNameExist(nuggetName)){
				registry.register( 
					new ShapedOreRecipe(
						null,
						new ItemStack(chainBlock, 4, type.getMetadata()),
						new Object[]{
							"o",
							"o",
							"o",
							'o', nuggetName
						}
					)
				);
			}
		}
		
		if(!OreDictionary.doesOreNameExist("nuggetDwemer")){
			GameRegistry.addShapelessRecipe(
				new ResourceLocation(Cathedral.MODID, "chaindwemer"),
				null,// Group
				new ItemStack(chainBlock, 1, BlockChain.EnumType.DWEMER.getMetadata()),// Output
				new Ingredient[]{ 
					Ingredient.fromStacks(new ItemStack(chainBlock, 1, BlockChain.EnumType.BRONZE.getMetadata()))
				}// Input
			);
		}

		
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
