package com.ferreusveritas.cathedral.features.cathedral;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import com.ferreusveritas.cathedral.ModConstants;
import com.ferreusveritas.cathedral.features.BlockForm;
import com.ferreusveritas.cathedral.features.IFeature;
import com.ferreusveritas.cathedral.features.IVariantEnumType;
import com.ferreusveritas.cathedral.models.ExtendedModelResourceLocation;
import com.ferreusveritas.cathedral.models.ModelBlockPillar;
import com.ferreusveritas.cathedral.models.ModelBlockRailing;
import com.ferreusveritas.cathedral.models.ModelLoaderKeyed;
import com.ferreusveritas.cathedral.proxy.ModelHelper;
import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.registries.IForgeRegistry;

public class Cathedral implements IFeature {
	
	public static final String featureName = "cathedral";
	
	public Block	glassStained, panesStained, railingVarious, chainVarious, catwalkVarious, pillarVarious;
	public final BlockGargoyle gargoyleDemon[] = new BlockGargoyle[EnumMaterial.values().length];
	public final static String types[] = {"stone", "sandstone", "netherbrick", "obsidian", "dwemer", "packedice", "endstone", "basalt", "marble", "limestone", "snow"};
	
	public final static String PILLAR = "pillar";
	public final static String RAILING = "railing";
	
	@Override
	public String getName() {
		return featureName;
	}
	
	@Override
	public void preInit() { }
	
	@Override
	public void createBlocks() {
		glassStained 	= new BlockGlassStained(featureObjectName(BlockForm.GLASS, "stained"));
		panesStained 	= new BlockPaneStained(featureObjectName(BlockForm.PANE, "stained"));
		railingVarious 	= new BlockRailing(featureObjectName(BlockForm.RAILING, "various"));
		chainVarious 	= new BlockChain(featureObjectName(BlockForm.CHAIN, "various"));
		catwalkVarious	= new BlockCatwalk(Material.IRON, featureObjectName(BlockForm.CATWALK, "various"));
		pillarVarious = new BlockPillar(featureObjectName(BlockForm.PILLAR, "various"));
		
		for(EnumMaterial type: EnumMaterial.values()) {
			gargoyleDemon[type.ordinal()] = new BlockGargoyle(featureObjectName(BlockForm.GARGOYLE, "demon_" + type.getName()), type);
		}
		
	}

	@Override
	public void createItems() {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void registerBlocks(IForgeRegistry<Block> registry) {
		registry.registerAll(
			glassStained,
			panesStained,
			railingVarious,
			chainVarious,
			//catwalkVarious,
			pillarVarious
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
		
		ItemMultiTexture.Mapper mapper = (stack) -> EnumMaterial.byMetadata(stack.getMetadata()).getUnlocalizedName();
		Consumer<Block> itemBlockMaker = (block) -> registry.register(new ItemMultiTexture(block, block, mapper).setRegistryName(block.getRegistryName()));
		Lists.newArrayList(glassStained, panesStained, railingVarious, chainVarious, pillarVarious).forEach(itemBlockMaker);
		
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
		Lists.newArrayList(BlockGlassStained.EnumType.values()).forEach(type -> FMLInterModComms.sendMessage("chisel", "variation:add", "cathedralglass" + "|" + glassStained.getRegistryName() + "|" + type.getMetadata()));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerColorHandlers() {
		Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler( (state, world, pos, tint) -> state.getValue(BlockChain.VARIANT).getColor(), new Block[] {chainVarious});
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler( ( stack,  tint) -> BlockChain.EnumType.byMetadata(stack.getItemDamage()).getColor(), new Item[] {Item.getItemFromBlock(chainVarious)});
	}
	
	@Override
	public void postInit() { }

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModels(ModelRegistryEvent event) {
		BiConsumer<Block, IVariantEnumType> reg = (block, type) -> ModelHelper.regModel(Item.getItemFromBlock(block), type.getMetadata(), new ResourceLocation(ModConstants.MODID, block.getRegistryName().getResourcePath() + "." + type.getUnlocalizedName()));
		Lists.newArrayList(BlockGlassStained.EnumType.values()).forEach(type -> reg.accept(glassStained, type) );
		Lists.newArrayList(BlockGlassStained.EnumType.values()).forEach(type -> reg.accept(panesStained, type) );
		Lists.newArrayList(EnumMaterial.values()).forEach(type -> reg.accept(railingVarious, type) );
		Lists.newArrayList(BlockChain.EnumType.values()).forEach(type -> reg.accept(chainVarious, type) );
		Lists.newArrayList(EnumMaterial.values()).forEach(type -> reg.accept(pillarVarious, type) );
		
		for(BlockGargoyle gargoyleBlock : gargoyleDemon) {
			ModelHelper.regModel(gargoyleBlock);
		}
		
		setupCustomModel(railingVarious, RAILING, resloc -> new ModelBlockRailing(resloc));
		setupCustomModel(pillarVarious, PILLAR, resloc -> new ModelBlockPillar(resloc));
	}
	
	public void setupCustomModel(Block block, String resName, @Nonnull Function<ResourceLocation, IModel> loader) {
		ModelLoaderRegistry.registerLoader(new ModelLoaderKeyed(resName, loader));
		Function<EnumMaterial, ModelResourceLocation> resMaker = mat -> new ExtendedModelResourceLocation(ModConstants.MODID, resName, mat.getName(), resName);
		Map<EnumMaterial, ModelResourceLocation> matModelMap = Lists.newArrayList(EnumMaterial.values()).stream().collect(Collectors.toMap(mat -> mat, resMaker));
		IStateMapper mapper = blk -> blk.getBlockState().getValidStates().stream().collect(Collectors.toMap(b -> b, b -> matModelMap.get( b.getValue(EnumMaterial.VARIANT) )));
		ModelLoader.setCustomStateMapper(block, mapper);
	}
	
	@SideOnly(Side.CLIENT)
	public void setGenericStateMapper(Block block, ModelResourceLocation modelLocation) {
		ModelLoader.setCustomStateMapper(block, state -> {
			return block.getBlockState().getValidStates().stream().collect(Collectors.toMap(b -> b, b -> modelLocation));
		});
	}
	
}
