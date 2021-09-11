package com.ferreusveritas.cathedral.features.cathedral;

import com.ferreusveritas.cathedral.ModConstants;
import com.ferreusveritas.cathedral.features.BlockForm;
import com.ferreusveritas.cathedral.features.IFeature;
import com.ferreusveritas.cathedral.features.IVariantEnumType;
import com.ferreusveritas.cathedral.models.ModelBlockPillar;
import com.ferreusveritas.cathedral.models.ModelBlockRailing;
import com.ferreusveritas.cathedral.models.ModelUtils;
import com.ferreusveritas.cathedral.proxy.ModelHelper;
import com.ferreusveritas.cathedral.util.InterModCommsUtils;
import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.function.BiConsumer;

public class Cathedral implements IFeature {
	
	public static final String featureName = "cathedral";
	
	public Block glassStained, panesStained, railingVarious, chainVarious, catwalkVarious, pillarVarious, deckPrism;
	public final BlockGargoyle gargoyleDemon[] = new BlockGargoyle[EnumMaterial.values().length];
	
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
		pillarVarious	= new BlockPillar(featureObjectName(BlockForm.PILLAR, "various"));
		deckPrism		= new BlockDeckPrism();
		
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
			pillarVarious,
			deckPrism
		);
		
		registry.registerAll(gargoyleDemon);
	}


	
	@Override
	public void registerItems(IForgeRegistry<Item> registry) {
		registerMultiTextureItems(registry, (stack) -> EnumMaterial.byMetadata(stack.getMetadata()).getUnlocalizedName(), railingVarious, pillarVarious);
		registerMultiTextureItems(registry, (stack) -> BlockGlassStained.EnumType.byMetadata(stack.getMetadata()).getUnlocalizedName(), glassStained, panesStained);
		registerMultiTextureItems(registry, (stack) -> BlockChain.EnumType.byMetadata(stack.getMetadata()).getUnlocalizedName(), chainVarious);
		registry.register( new ItemDeckPrism(deckPrism).setRegistryName(deckPrism.getRegistryName()) );
		
		for(BlockGargoyle gargoyleBlock : gargoyleDemon) {
			registry.register(new ItemBlock(gargoyleBlock).setRegistryName(gargoyleBlock.getRegistryName()));
		}
		
	}
	
	public void registerMultiTextureItems(IForgeRegistry<Item> registry, ItemMultiTexture.Mapper mapper, Block ... blocks) {
		Lists.newArrayList(blocks).forEach((block) -> registry.register(new ItemMultiTexture(block, block, mapper).setRegistryName(block.getRegistryName())));
	}
	
	@Override
	public void registerRecipes(IForgeRegistry<IRecipe> registry) {
		new CathedralRecipes(this).registerRecipes(registry);
	}
	
	@Override
	public void init() {
		//Add chisel variations for Stained Glass Blocks
		Lists.newArrayList(BlockGlassStained.EnumType.values())
				.forEach(type ->
						InterModCommsUtils.addChiselVariation("cathedralglass", glassStained, type.getMetadata())
				);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerColorHandlers() {
		Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler( (state, world, pos, tint) -> state.getValue(BlockChain.VARIANT).getColor(), new Block[] {chainVarious});
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler( ( stack,  tint) -> BlockChain.EnumType.byMetadata(stack.getItemDamage()).getColor(), new Item[] {Item.getItemFromBlock(chainVarious)});
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler( ( stack,  tint) -> tint == 0 ? EnumDyeColor.byMetadata(stack.getMetadata()).getColorValue() : 0xFFFFFFFF, new Item[] {Item.getItemFromBlock(deckPrism)});
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
		Lists.newArrayList(EnumDyeColor.values()).forEach(color -> ModelHelper.regModel(Item.getItemFromBlock(deckPrism), color.getDyeDamage()));
		
		for(BlockGargoyle gargoyleBlock : gargoyleDemon) {
			ModelHelper.regModel(gargoyleBlock);
		}
		
		ModelUtils.setupCustomMaterialModel(railingVarious, RAILING, resloc -> new ModelBlockRailing(resloc));
		ModelUtils.setupCustomMaterialModel(pillarVarious, PILLAR, resloc -> new ModelBlockPillar(resloc));
	}

}