package com.ferreusveritas.cathedral.features.cathedral;

import com.ferreusveritas.cathedral.CathedralMod;
import com.ferreusveritas.cathedral.ModConstants;
import com.ferreusveritas.cathedral.features.BlockForm;
import com.ferreusveritas.cathedral.features.IFeature;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.registries.IForgeRegistry;

public class Cathedral implements IFeature {

	public static final String featureName = "cathedral";
	
	public Block	glassStained, railingVarious, chainVarious, catwalkVarious;
	public BlockGargoyle	gargoyleDemon;
	public static String types[] = {"stone", "sandstone", "netherbrick", "obsidian", "dwemer", "packedice", "endstone", "basalt", "marble", "limestone", "snow"};
	
	
	@Override
	public String getName() {
		return featureName;
	}

	@Override
	public void preInit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createBlocks() {
		glassStained 	= new BlockGlassStained(featureObjectName(BlockForm.GLASS, "stained"));
		railingVarious 	= new BlockRailing(featureObjectName(BlockForm.RAILING, "various"));
		chainVarious 	= new BlockChain(featureObjectName(BlockForm.CHAIN, "various"));
		
		catwalkVarious = new BlockCatwalk(Material.IRON, featureObjectName(BlockForm.CATWALK, "various"))
				.setCreativeTab(CathedralMod.tabCathedral)
				.setHardness(2.5f)
				//.setStepSound(SoundType.METAL)
				.setResistance(20F);
		
		gargoyleDemon	= new BlockGargoyle(featureObjectName(BlockForm.GARGOYLE, "demon"));
		
		//GameRegistry.registerBlock(gargoyleBlock, ItemGargoyle.class, "gargoyle");
		//TileEntity.addMapping(EntityGargoyle.class, "gargoyle");
		
		//whiteTest = new BlockStone().setBlockTextureName(StoneLore.MODID + ":" + "white").setBlockName(StoneLore.MODID + "_" + "whiteTest").setCreativeTab(StoneLore.tabStoneLore);
		//GameRegistry.registerBlock(whiteTest, "whitetest");
	}

	@Override
	public void createItems() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerBlocks(IForgeRegistry<Block> registry) {
		registry.registerAll(
			//glassStained,
			//railingVarious,
			//chainVarious,
			//catwalkVarious,
			//gargoyleDemon
		);
	}

	@Override
	public void registerItems(IForgeRegistry<Item> registry) {

		/*
		registry.register(new ItemMultiTexture(glassStained, glassStained, new ItemMultiTexture.Mapper() {
            public String apply(ItemStack stack) {
                return BlockGlassStained.EnumType.byMetadata(stack.getMetadata()).getUnlocalizedName();
            }
        }).setRegistryName(glassStained.getRegistryName()));
        */
		
		/*
		registry.register(new ItemMultiTexture(railingVarious, railingVarious, new ItemMultiTexture.Mapper() {
            public String apply(ItemStack stack) {
                return BlockRailing.EnumType.byMetadata(stack.getMetadata()).getUnlocalizedName();
            }
        }).setRegistryName(railingVarious.getRegistryName()));
        */

		/*
		registry.register(new ItemMultiTexture(chainVarious, chainVarious, new ItemMultiTexture.Mapper() {
            public String apply(ItemStack stack) {
                return BlockChain.EnumType.byMetadata(stack.getMetadata()).getUnlocalizedName();
            }
        }).setRegistryName(chainVarious.getRegistryName()));
        */
		
		/*
		registry.register(new ItemBlock(gargoyleDemon).setRegistryName(gargoyleDemon.getRegistryName()));
		*/

	}

	@Override
	public void registerRecipes(IForgeRegistry<IRecipe> registry) {

		//Stained Glass
		registry.register(
			new ShapedOreRecipe(
				null,
				new ItemStack(glassStained, 16, 0),
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
						new ItemStack(chainVarious, 4, type.getMetadata()),
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
				new ResourceLocation(ModConstants.MODID, "chaindwemer"),
				null,// Group
				new ItemStack(chainVarious, 1, BlockChain.EnumType.DWEMER.getMetadata()),// Output
				new Ingredient[]{ 
					Ingredient.fromStacks(new ItemStack(chainVarious, 1, BlockChain.EnumType.BRONZE.getMetadata()))
				}// Input
			);
		}
		
		//Gargoyles
		/*Block chiselMarble = GameRegistry.findBlock("chisel", "marble");
		Block chiselLimestone = GameRegistry.findBlock("chisel", "limestone");

		GameRegistry.addRecipe(new ItemStack(gargoyleBlock, 1, 0), "X X", "XXX", " X ", 'X', new ItemStack(Blocks.STONE));
		GameRegistry.addRecipe(new ItemStack(gargoyleBlock, 1, 1), "X X", "XXX", " X ", 'X', new ItemStack(Blocks.SANDSTONE));
		GameRegistry.addRecipe(new ItemStack(gargoyleBlock, 1, 2), "X X", "XXX", " X ", 'X', new ItemStack(Blocks.NETHER_BRICK));
		GameRegistry.addRecipe(new ItemStack(gargoyleBlock, 1, 3), "X X", "XXX", " X ", 'X', new ItemStack(Blocks.OBSIDIAN));
		GameRegistry.addRecipe(new ItemStack(gargoyleBlock, 1, 4), "X X", "XXX", " X ", 'X', new ItemStack(Dwemer.dwemerBlock, 1, 14));
		GameRegistry.addRecipe(new ItemStack(gargoyleBlock, 1, 5), "X X", "XXX", " X ", 'X', new ItemStack(Blocks.PACKED_ICE));
		GameRegistry.addRecipe(new ItemStack(gargoyleBlock, 1, 6), "X X", "XXX", " X ", 'X', new ItemStack(Blocks.END_STONE));
		GameRegistry.addRecipe(new ItemStack(gargoyleBlock, 1, 7), "X X", "XXX", " X ", 'X', new ItemStack(Cathedral.basaltBase, 1, 3));
		GameRegistry.addRecipe(new ItemStack(gargoyleBlock, 1, 8), "X X", "XXX", " X ", 'X', new ItemStack(chiselMarble, 1, 0));
		GameRegistry.addRecipe(new ItemStack(gargoyleBlock, 1, 9), "X X", "XXX", " X ", 'X', new ItemStack(chiselLimestone, 1, 0));
		GameRegistry.addRecipe(new ItemStack(gargoyleBlock, 1, 10), "X X", "XXX", " X ", 'X', new ItemStack(Blocks.SNOW));*/
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
