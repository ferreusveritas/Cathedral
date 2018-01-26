package com.ferreusveritas.cathedral.features.cathedral;

import com.ferreusveritas.cathedral.ModConstants;
import com.ferreusveritas.cathedral.features.IFeature;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
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

public class Cathedral implements IFeature {

	public static final String featureName = "cathedral";
	
	public BlockStainedGlass stainedGlass;
	public BlockStoneRailing stoneRailingBlock;
	public BlockChain chainBlock;
	
	public static BlockGargoyle gargoyleBlock;
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
		stainedGlass = (BlockStainedGlass) new BlockStainedGlass();
		stoneRailingBlock = new BlockStoneRailing();
		chainBlock = new BlockChain();
		
		gargoyleBlock = new BlockGargoyle();
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
		registry.registerAll(stainedGlass, stoneRailingBlock, chainBlock);
		
		registry.register(gargoyleBlock);
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
		
		registry.register(new ItemBlock(gargoyleBlock).setRegistryName(gargoyleBlock.getRegistryName()));

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
				new ResourceLocation(ModConstants.MODID, "chaindwemer"),
				null,// Group
				new ItemStack(chainBlock, 1, BlockChain.EnumType.DWEMER.getMetadata()),// Output
				new Ingredient[]{ 
					Ingredient.fromStacks(new ItemStack(chainBlock, 1, BlockChain.EnumType.BRONZE.getMetadata()))
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
