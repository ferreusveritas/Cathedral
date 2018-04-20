package com.ferreusveritas.cathedral.features.basalt;

import java.util.ArrayList;

import com.ferreusveritas.cathedral.CathedralMod;
import com.ferreusveritas.cathedral.ModConstants;
import com.ferreusveritas.cathedral.common.blocks.BlockStairsGeneric;
import com.ferreusveritas.cathedral.compat.CompatThermalExpansion;
import com.ferreusveritas.cathedral.features.BlockForm;
import com.ferreusveritas.cathedral.features.IFeature;
import com.ferreusveritas.cathedral.proxy.ModelHelper;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.registries.IForgeRegistry;

public class Basalt implements IFeature {
	
	public static final String featureName = "basalt";
	
	public Block blockCarved;
	public Block blockCheckered;
	
	public Block slabCarved;
	public Block slabCarvedDouble;
	public Block slabCheckered;
	public Block slabCheckeredDouble;
	
	public ArrayList<Block> stairsCarved = new ArrayList<>();
	public ArrayList<Block> stairsCheckered = new ArrayList<>();
	
	public final float basaltHardness = 2.5f;
	public final float basaltResistance = 20f;
	public final float marbleHardness = 2.0f;
	public final float marbleResistance = 10f;
	
	@Override
	public String getName() {
		return featureName;
	}
	
	@Override
	public void preInit() {

	}

	@Override
	public void createBlocks() {
		
		blockCarved = new BlockBasalt(featureObjectName(BlockForm.BLOCK, "carved"));		
		
		slabCarved = new BlockSlabBasalt(featureObjectName(BlockForm.SLAB, "carved"))
			.setCreativeTab(CathedralMod.tabBasalt)
			.setHardness(basaltHardness)
			.setResistance(basaltResistance);

		slabCarvedDouble = new BlockDoubleSlabBasalt(featureObjectName(BlockForm.DOUBLESLAB, "carved"))
				.setCreativeTab(CathedralMod.tabBasalt)
				.setHardness(basaltHardness)
				.setResistance(basaltResistance);
		
		blockCheckered = new BlockCheckered(featureObjectName(BlockForm.BLOCK, "checkered"));
		
		slabCheckered = new BlockSlabCheckered(featureObjectName(BlockForm.SLAB, "checkered"))
			.setCreativeTab(CathedralMod.tabBasalt)
			.setHardness((basaltHardness + marbleHardness) / 2F)
			.setResistance((basaltResistance + marbleResistance) / 2F);

		slabCheckeredDouble = new BlockDoubleSlabCheckered(featureObjectName(BlockForm.DOUBLESLAB, "checkered"))
				.setCreativeTab(CathedralMod.tabBasalt)
				.setHardness(basaltHardness)
				.setResistance(basaltResistance);
		
		for(BlockSlabBasalt.EnumType type: BlockSlabBasalt.EnumType.values()) {
			stairsCarved.add(new BlockStairsGeneric(featureObjectName(BlockForm.STAIRS, "carved_" + type.getName() ), blockCarved.getDefaultState()).setCreativeTab(CathedralMod.tabBasalt));
		}
		
		for(BlockSlabCheckered.EnumType type: BlockSlabCheckered.EnumType.values()) {
			stairsCheckered.add(new BlockStairsGeneric(featureObjectName(BlockForm.STAIRS, "checkered_" + type.getName() ), blockCheckered.getDefaultState()).setCreativeTab(CathedralMod.tabBasalt));
		}
		
	}

	@Override
	public void createItems() {
		// TODO Auto-generated method stub
	}

	@Override
	public void registerBlocks(IForgeRegistry<Block> registry) {
		//registry.register(basaltBase);
		registry.registerAll(
				blockCarved,
				slabCarved,
				slabCarvedDouble,
				blockCheckered,
				slabCheckered,
				slabCheckeredDouble
			);
		
		registry.registerAll(stairsCarved.toArray(new Block[0]));
		registry.registerAll(stairsCheckered.toArray(new Block[0]));
	}

	@Override
	public void registerItems(IForgeRegistry<Item> registry) {
		
		//Basalt Blocks
		registry.register(new ItemMultiTexture(blockCarved, blockCarved, new ItemMultiTexture.Mapper() {
            public String apply(ItemStack stack) {
                return BlockBasalt.EnumType.byMetadata(stack.getMetadata()).getUnlocalizedName();
            }
        }).setRegistryName(blockCarved.getRegistryName()));

		//Basalt Slabs
		ItemSlab itemSlabBasalt = new ItemSlab(slabCarved, (BlockSlab)slabCarved, (BlockSlab)slabCarvedDouble);
		itemSlabBasalt.setRegistryName(slabCarved.getRegistryName());
		registry.register(itemSlabBasalt);

		//Checkered Blocks
		registry.register(new ItemMultiTexture(blockCheckered, blockCheckered, new ItemMultiTexture.Mapper() {
            public String apply(ItemStack stack) {
                return BlockCheckered.EnumType.byMetadata(stack.getMetadata()).getUnlocalizedName();
            }
        }).setRegistryName(blockCheckered.getRegistryName()));

		//Checkered Slabs
		ItemSlab itemSlabCheckered = new ItemSlab(slabCheckered, (BlockSlab)slabCheckered, (BlockSlab)slabCheckeredDouble);
		itemSlabCheckered.setRegistryName(slabCheckered.getRegistryName());
		registry.register(itemSlabCheckered);
		
		//Basalt Stairs
		for(BlockSlabBasalt.EnumType type: BlockSlabBasalt.EnumType.values()) {
			registry.register(new ItemBlock(stairsCarved.get(type.ordinal())).setRegistryName(stairsCarved.get(type.ordinal()).getRegistryName()));
		}

		//Checkered Stairs
		for(BlockSlabCheckered.EnumType type: BlockSlabCheckered.EnumType.values()) {
			registry.register(new ItemBlock(stairsCheckered.get(type.ordinal())).setRegistryName(stairsCheckered.get(type.ordinal()).getRegistryName()));
		}
		
	}
	
	private void tryRegisterBlockOre(String oreName, ItemStack ore) {
		if(!ore.isEmpty()) {
			OreDictionary.registerOre(oreName, ore);
		}
	}
	
	public static ItemStack getItemBlockStack(String domain, String name, int metadata) {
		Block block = Block.REGISTRY.getObject(new ResourceLocation(domain, name));
		if(block != Blocks.AIR) {
			return new ItemStack(block, 1, metadata);
		}
		
		return ItemStack.EMPTY;
	}
	
	public static ItemStack getRawBasalt() {
		return getItemBlockStack("chisel", "basalt2", 7);
	}
	
	public static ItemStack getRawMarble() {
		return getItemBlockStack("chisel", "marble2", 7);
	}
	
	@Override
	public void registerRecipes(IForgeRegistry<IRecipe> registry) {

		String basaltOre = "blockBasalt";
		String marbleOre = "blockMarble";
		
		//Basalt Ore Dictionary Registrations
		tryRegisterBlockOre(basaltOre, getRawBasalt());

		//Marble Ore Dictionary Registrations
		tryRegisterBlockOre(marbleOre, getRawMarble());
		
		//Checkered Tile Recipes
		for( boolean flip: new boolean[] { false, true } ) {
			registry.register(
					new ShapedOreRecipe( null, new ItemStack(blockCheckered, 4, 0),
							flip ? "mb" : "bm",
							flip ? "bm" : "mb",
							'b', basaltOre,
							'm', marbleOre
							).setRegistryName(blockCheckered.getRegistryName().getResourcePath() + (flip ? "A" : "B"))
					);
		}
		
		//Basalt Slab and Stairs Recipes
		for(BlockSlabBasalt.EnumType type: BlockSlabBasalt.EnumType.values()) {
			Block baseBlock = Block.REGISTRY.getObject(type.getBaseResourceLocation());
			if(baseBlock != Blocks.AIR) {
				ItemStack baseItemBlock = new ItemStack(baseBlock, 1, type.getBaseMeta());
				GameRegistry.addShapedRecipe(
						new ResourceLocation(ModConstants.MODID, slabCarved.getRegistryName().getResourcePath() + "." + type.getUnlocalizedName()),
						null,
						new ItemStack(slabCarved, 6, type.getMetadata()), //Output
						"xxx",
						'x', baseItemBlock
					);

				GameRegistry.addShapedRecipe(
						new ResourceLocation(ModConstants.MODID, stairsCarved.get(type.getMetadata()).getRegistryName().getResourcePath()),
						null,
						new ItemStack(stairsCarved.get(type.getMetadata()), 8), //Output
						"x  ",
						"xx ",
						"xxx",
						'x', baseItemBlock
					);
			}
		}

		//Checkered Slab and Stairs Recipes
		for(BlockSlabCheckered.EnumType type: BlockSlabCheckered.EnumType.values()) {
			ItemStack baseItemBlock = new ItemStack(blockCheckered, 1, type.getBaseMeta());
				GameRegistry.addShapedRecipe(
						new ResourceLocation(ModConstants.MODID, slabCheckered.getRegistryName().getResourcePath() + "." + type.getUnlocalizedName()),
						null,
						new ItemStack(slabCheckered, 6, type.getMetadata()), //Output
						"xxx",
						'x', baseItemBlock
					);

				GameRegistry.addShapedRecipe(
						new ResourceLocation(ModConstants.MODID, stairsCheckered.get(type.getMetadata()).getRegistryName().getResourcePath()),
						null,
						new ItemStack(stairsCheckered.get(type.getMetadata()), 8), //Output
						"x  ",
						"xx ",
						"xxx",
						'x', baseItemBlock
					);
		}
		
		ItemStack obsidianDust = new ItemStack(Item.REGISTRY.getObject(new ResourceLocation("thermalfoundation", "material")), 1, 770);
		
		//Having Thermal Expansion adds the ability to create basalt from obsidian dust and stone
		CompatThermalExpansion.addSmelterRecipe(2000, new ItemStack(Blocks.STONE, 1, 0), obsidianDust, getRawBasalt(), ItemStack.EMPTY, 0);
	}
	
	@Override
	public void registerModels(ModelRegistryEvent event) {

		for(BlockBasalt.EnumType type: BlockBasalt.EnumType.values()) {
			ModelHelper.regModel(Item.getItemFromBlock(blockCarved), type.getMetadata(), new ResourceLocation(ModConstants.MODID, blockCarved.getRegistryName().getResourcePath() + "." + type.getUnlocalizedName()));
		}
		
		for(BlockSlabBasalt.EnumType type: BlockSlabBasalt.EnumType.values()) {
			ModelHelper.regModel(Item.getItemFromBlock(slabCarved), type.getMetadata(), new ResourceLocation(ModConstants.MODID, slabCarved.getRegistryName().getResourcePath() + "." + type.getUnlocalizedName()));
		}
		
		for(BlockCheckered.EnumType type: BlockCheckered.EnumType.values()) {
			ModelHelper.regModel(Item.getItemFromBlock(blockCheckered), type.getMetadata(), new ResourceLocation(ModConstants.MODID, blockCheckered.getRegistryName().getResourcePath() + "." + type.getUnlocalizedName()));
		}
		
		for(BlockSlabCheckered.EnumType type: BlockSlabCheckered.EnumType.values()) {
			ModelHelper.regModel(Item.getItemFromBlock(slabCheckered), type.getMetadata(), new ResourceLocation(ModConstants.MODID, slabCheckered.getRegistryName().getResourcePath() + "." + type.getUnlocalizedName()));
		}

		stairsCarved.forEach(s -> ModelHelper.regModel(s));
		stairsCheckered.forEach(s -> ModelHelper.regModel(s));
	}

	@Override
	public void init() {
		
		//Add chisel variations for Basalt Blocks
		for(BlockBasalt.EnumType type: BlockBasalt.EnumType.values()) {
			addChiselVariation("basalt", blockCarved, type.getMetadata());
		}
		
		//Add chisel variations for Checkered Blocks
		for(BlockCheckered.EnumType type: BlockCheckered.EnumType.values()) {
			addChiselVariation("basaltcheckered", blockCheckered, type.getMetadata());
		}
		
		//Add chisel variations for Basalt Blocks
		for(BlockSlabBasalt.EnumType type: BlockSlabBasalt.EnumType.values()) {
			addChiselVariation("basaltslab", slabCarved, type.getMetadata());
		}
		
		//Add chisel variations for Checkered Blocks
		for(BlockSlabCheckered.EnumType type: BlockSlabCheckered.EnumType.values()) {
			addChiselVariation("basaltcheckeredslab", slabCheckered, type.getMetadata());
		}
		
		//Add chisel variations for Basalt Blocks
		stairsCarved.forEach(s -> addChiselVariation("basaltstairs", s, 0));
		stairsCheckered.forEach(s -> addChiselVariation("basaltcheckeredstairs", s, 0));
		
		for(String name : new String[] { "basalt", "basalt2" }) {
			Block basalt = Block.REGISTRY.getObject(new ResourceLocation("chisel", name));
			if(basalt != Blocks.AIR) {
				basalt.setCreativeTab(CathedralMod.tabBasalt);
			}
		}
	}

	private void addChiselVariation(String group, Block block, int meta) {
		FMLInterModComms.sendMessage("chisel", "variation:add", group + "|" + block.getRegistryName() + "|" + meta);
	}
	
	@Override
	public void postInit() {}
}
