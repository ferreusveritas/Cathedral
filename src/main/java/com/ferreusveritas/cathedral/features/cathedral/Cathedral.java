package com.ferreusveritas.cathedral.features.cathedral;

import com.ferreusveritas.cathedral.CathedralMod;
import com.ferreusveritas.cathedral.ModConstants;
import com.ferreusveritas.cathedral.features.BlockForm;
import com.ferreusveritas.cathedral.features.IFeature;
import com.ferreusveritas.cathedral.proxy.ModelHelper;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.registries.IForgeRegistry;

public class Cathedral implements IFeature {
	
	public static final String featureName = "cathedral";
	
	public Block	glassStained, railingVarious, chainVarious, catwalkVarious;
	public BlockGargoyle gargoyleDemon[] = new BlockGargoyle[EnumMaterial.values().length];
	public static String types[] = {"stone", "sandstone", "netherbrick", "obsidian", "dwemer", "packedice", "endstone", "basalt", "marble", "limestone", "snow"};
	
	@Override
	public String getName() {
		return featureName;
	}
	
	@Override
	public void preInit() { }
	
	@Override
	public void createBlocks() {
		glassStained 	= new BlockGlassStained(featureObjectName(BlockForm.GLASS, "stained"));
		railingVarious 	= new BlockRailing(featureObjectName(BlockForm.RAILING, "various"));
		chainVarious 	= new BlockChain(featureObjectName(BlockForm.CHAIN, "various"));
		catwalkVarious	= new BlockCatwalk(Material.IRON, featureObjectName(BlockForm.CATWALK, "various"))
				.setCreativeTab(CathedralMod.tabCathedral)
				.setHardness(2.5f)
				//.setStepSound(SoundType.METAL)
				.setResistance(20F);

		for(EnumMaterial type: EnumMaterial.values()) {
			gargoyleDemon[type.ordinal()] = new BlockGargoyle(featureObjectName(BlockForm.GARGOYLE, "demon_" + type.getName()));
		}
		
		//GameRegistry.registerBlock(gargoyleBlock, ItemGargoyle.class, "gargoyle");
		//TileEntity.addMapping(EntityGargoyle.class, "gargoyle");
	}

	@Override
	public void createItems() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void registerBlocks(IForgeRegistry<Block> registry) {
		registry.registerAll(
			glassStained,
			railingVarious,
			chainVarious
			//catwalkVarious,
		);
		
		registry.registerAll(gargoyleDemon);
	}

	public void railRecipe(EnumMaterial type) {
		ItemStack input = type.getRawMaterialBlock();
		if(input.getItem() instanceof ItemBlock && ((ItemBlock)input.getItem()).getBlock() != Blocks.AIR) {
			GameRegistry.addShapedRecipe(
					new ResourceLocation(ModConstants.MODID, "railing_" + type.getName()),//Name
					null,//Group
					new ItemStack(railingVarious, 8, type.getMetadata()),//Output
					"xxx",
					"x x",
					'x', input
					);
		}
	}
	
	public void gargoyleDemonRecipe(EnumMaterial type) {
		ItemStack input = type.getRawMaterialBlock();
		if(input.getItem() instanceof ItemBlock && ((ItemBlock)input.getItem()).getBlock() != Blocks.AIR) {
			GameRegistry.addShapedRecipe(
					new ResourceLocation(ModConstants.MODID, "gargoyle_demon_" + type.getName()),//Name
					null,//Group
					new ItemStack(gargoyleDemon[type.ordinal()]),//Output
					" s ",
					"fpf",
					"sss",
					's', input,
					'p', Blocks.LIT_PUMPKIN,
					'f', Items.FEATHER
					);
		}
	}

	public void chainRecipe(BlockChain.EnumType type, IForgeRegistry<IRecipe> registry) {
		String nuggetName = "nugget" + type.getOreName();
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
				).setRegistryName("chain" + type.getName())
			);
		}
	}
	
	@Override
	public void registerItems(IForgeRegistry<Item> registry) {
		
		registry.register(new ItemMultiTexture(glassStained, glassStained, new ItemMultiTexture.Mapper() {
			public String apply(ItemStack stack) {
				return BlockGlassStained.EnumType.byMetadata(stack.getMetadata()).getUnlocalizedName();
			}
		}).setRegistryName(glassStained.getRegistryName()));
		
		registry.register(new ItemMultiTexture(railingVarious, railingVarious, new ItemMultiTexture.Mapper() {
			public String apply(ItemStack stack) {
				return EnumMaterial.byMetadata(stack.getMetadata()).getUnlocalizedName();
			}
		}).setRegistryName(railingVarious.getRegistryName()));
		
		registry.register(new ItemMultiTexture(chainVarious, chainVarious, new ItemMultiTexture.Mapper() {
			public String apply(ItemStack stack) {
				return BlockChain.EnumType.byMetadata(stack.getMetadata()).getUnlocalizedName();
			}
		}).setRegistryName(chainVarious.getRegistryName()));
		
		for(BlockGargoyle gargoyleBlock : gargoyleDemon) {
			registry.register(new ItemBlock(gargoyleBlock).setRegistryName(gargoyleBlock.getRegistryName()));
		}
		
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
		for(EnumMaterial material : EnumMaterial.values()) {
			railRecipe(material);
		}
		
		//Chains
		for(BlockChain.EnumType type: BlockChain.EnumType.values()) {
			chainRecipe(type, registry);
		}
		
		//Allow exchange for BRONZE -> DWEMER(or GOLD -> DWEMER) chain in cases where Dwemer and Dawnstone aren't available
		if(!OreDictionary.doesOreNameExist("nuggetDwemer") && !OreDictionary.doesOreNameExist("nuggetDawnstone")){
			GameRegistry.addShapelessRecipe(
				new ResourceLocation(ModConstants.MODID, "chaindwemer"),
				null,// Group
				new ItemStack(chainVarious, 1, BlockChain.EnumType.DWEMER.getMetadata()),// Output
				new Ingredient[] { 
					Ingredient.fromStacks(new ItemStack(chainVarious, 1, 
						(OreDictionary.doesOreNameExist("nuggetBronze") ? BlockChain.EnumType.BRONZE : BlockChain.EnumType.GOLD).getMetadata()
					))
				}// Input
			);
		}

		//Gargoyles
		for(EnumMaterial material : EnumMaterial.values()) {
			gargoyleDemonRecipe(material);
		}
		
	}
	
	@Override
	public void init() {
		
		//Add chisel variations for Stained Glass Blocks
		for(BlockGlassStained.EnumType type: BlockGlassStained.EnumType.values()) {
			FMLInterModComms.sendMessage("chisel", "variation:add", "cathedralglass" + "|" + glassStained.getRegistryName() + "|" + type.getMetadata());
		}
		
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerColorHandlers() {
		
		Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(new IBlockColor() {
			@Override
			public int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
				return state.getValue(BlockChain.VARIANT).getColor();
			}
		}, new Block[] {chainVarious});
		
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemColor() {
			@Override
			public int colorMultiplier(ItemStack stack, int tintIndex) {
				return BlockChain.EnumType.byMetadata(stack.getItemDamage()).getColor();
			}
		}, new Item[] {Item.getItemFromBlock(chainVarious)});
		
	}
	
	@Override
	public void postInit() { }

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModels(ModelRegistryEvent event) {
		
		for(BlockGlassStained.EnumType type: BlockGlassStained.EnumType.values()) {
			ModelHelper.regModel(Item.getItemFromBlock(glassStained), type.getMetadata(), new ResourceLocation(ModConstants.MODID, glassStained.getRegistryName().getResourcePath() + "." + type.getUnlocalizedName()));
		}
		
		for(EnumMaterial type: EnumMaterial.values()) {
			ModelHelper.regModel(Item.getItemFromBlock(railingVarious), type.getMetadata(), new ResourceLocation(ModConstants.MODID, railingVarious.getRegistryName().getResourcePath() + "." + type.getUnlocalizedName()));
		}
		
		for(BlockChain.EnumType type: BlockChain.EnumType.values()) {
			ModelHelper.regModel(Item.getItemFromBlock(chainVarious), type.getMetadata(), new ResourceLocation(ModConstants.MODID, chainVarious.getRegistryName().getResourcePath() + "." + type.getUnlocalizedName()));
		}

		for(BlockGargoyle gargoyleBlock : gargoyleDemon) {
			ModelHelper.regModel(gargoyleBlock);
		}
		
	}

}
