package com.ferreusveritas.cathedral.features.basalt;

import com.ferreusveritas.cathedral.CathedralMod;
import com.ferreusveritas.cathedral.common.blocks.BaseBlockDef;
import com.ferreusveritas.cathedral.common.blocks.BlockGenericSlab;
import com.ferreusveritas.cathedral.common.blocks.BlockGenericStairs;
import com.ferreusveritas.cathedral.features.IFeature;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class Basalt implements IFeature {

	public static final String featureName = "basalt";
	
	public Block basaltBase;//This is assigned from Project Red in PostInit()

	public Block basaltBlock;
	public Block checkeredBlock;

	public BlockGenericSlab basaltSlab;
	public BlockGenericSlab checkeredSlab;

	public BlockGenericStairs basaltStairs[] = new BlockGenericStairs[8];

	public final float basaltHardness = 2.5f;
	public final float basaltResistance = 20f;
	public final float marbleHardness = 2.0f;
	public final float marbleResistance = 10f;
	
	public static enum EnumType implements IStringSerializable {
	
		ROSETTE		( 0, "rosette"),
		PAVER		( 1, "paver"),
		WORNBRICK	( 2, "wornbrick"),
		ORNATE		( 3, "ornate"),
		POISON		( 4, "poison"),
		SUNKENPANEL	( 5, "sunkenpanel"),
		TILES		( 6, "tiles"),
		SLABS		( 7, "slabs"),
		VAULT		( 8, "vault"),
		SMALLBRICKS	( 9, "smallbricks"),
		SMALLCHAOTI	(10, "smallchaotic"),
		SMALLTILES	(11, "smalltiles"),
		BLOCK		(12, "block"),
		SUNKEN		(13, "sunken"),
		KNOT		(14, "knot"); 
		
		private final int meta;
		private final String name;
		private final String unlocalizedName;
		
		private EnumType(int index, String name) {
			this.meta = index;
			this.name = name;
			this.unlocalizedName = name;
		}
		
		public int getMetadata() {
			return meta;
		}
		
		@Override
		public String toString() {
			return name;
		}
		
		public static Basalt.EnumType byMetadata(int meta) {
			return values()[MathHelper.clamp(meta, 0, values().length - 1)];
		}
		
		@Override
		public String getName() {
			return name;
		}
		
		public String getUnlocalizedName() {
			return unlocalizedName;
		}

	}

	@Override
	public String getName() {
		return featureName;
	}

	@Override
	public void preInit() {
	}

	@Override
	public void createBlocks() {
		
		basaltBase = new Block(Material.ROCK);
		
		basaltBlock = new BlockBasalt();		
		
		basaltSlab = (BlockGenericSlab) new BlockGenericSlab(basaltBlock)
			.setRegistryName(basaltBlock.getRegistryName() + "_slab")
			.setUnlocalizedName(basaltBlock.getRegistryName() + "_slab")
			.setCreativeTab(CathedralMod.tabBasalt)
			.setHardness(basaltHardness)
			.setResistance(basaltResistance);

		checkeredBlock = new Block(Material.ROCK)
			.setRegistryName("checkered")
			.setUnlocalizedName("checkered")
			.setCreativeTab(CathedralMod.tabBasalt)
			.setHardness((basaltHardness + marbleHardness) / 2F)
			.setResistance((basaltResistance + marbleResistance) / 2F);
		
		checkeredSlab = (BlockGenericSlab) new BlockGenericSlab(checkeredBlock)
			.setRegistryName("checkered_slab")
			.setUnlocalizedName("checkered_slab")
			.setCreativeTab(CathedralMod.tabBasalt)
			.setHardness((basaltHardness + marbleHardness) / 2F)
			.setResistance((basaltResistance + marbleResistance) / 2F);

		//BlockCarvable.addBlocks(basaltNames, basaltBlock, "basalt");

		//Basalt Slabs
		/*basaltSlab.carverHelper.addVariation("tile.basalt_basaltslab-paver.name", 2, "basalt-worn-brick", Cathedral.MODID);
		basaltSlab.carverHelper.addVariation("tile.basalt_basaltslab-tiles.name", 6, "basalt-tiles", Cathedral.MODID);
		basaltSlab.carverHelper.addVariation("tile.basalt_basaltslab-slabs.name", 7, "basalt-slabs", Cathedral.MODID);
		basaltSlab.carverHelper.addVariation("tile.basalt_basaltslab-smallbricks.name", 9, "basalt-smallbricks", Cathedral.MODID);
		basaltSlab.carverHelper.addVariation("tile.basalt_basaltslab-smallchaotic.name", 10, "basalt-smallchaotic", Cathedral.MODID);
		basaltSlab.carverHelper.addVariation("tile.basalt_basaltslab-smalltiles.name", 11, "basalt-smalltiles", Cathedral.MODID);
		basaltSlab.carverHelper.registerAll(basaltSlab, "basaltslab", ItemCarvableSlab.class);
		basaltSlab.registerSlabTop();*/
		
		//This must be ran in init because project red doesn't register it's stone until after it's init
		/*if(Loader.isModLoaded("ProjRed|Exploration")){
			basaltBase = GameRegistry.findBlock("ProjRed|Exploration", "projectred.exploration.stone");
		} else {
			//basaltBase = GameRegistry.registerBlock(block, "nothing");
		}*/
		
		//Ore Dictionary Registrations
		//OreDictionary.registerOre("basalt", new ItemStack(basaltBase, 1, 3));
		//OreDictionary.registerOre("basaltBrick", new ItemStack(basaltBase, 1, 4));
		
		BaseBlockDef[] baseBlocks = {
				new BaseBlockDef(0, basaltBase, 3, "basalt", "Basalt", basaltHardness, basaltResistance),
				new BaseBlockDef(1, basaltBase, 4, "basalt-brick", "BasaltBrick", basaltHardness, basaltResistance),
				new BaseBlockDef(2, basaltBlock, 6, "basalt-tiles", "BasaltSlabs", basaltHardness, basaltResistance),
				new BaseBlockDef(3, basaltBlock, 7, "basalt-slabs", "BasaltTiles", basaltHardness, basaltResistance),
				new BaseBlockDef(4, basaltBlock, 9, "basalt-smallbricks", "BasaltSmallBricks", basaltHardness, basaltResistance),
				new BaseBlockDef(5, basaltBlock, 11, "basalt-smalltiles", "BasaltSmallTiles", basaltHardness, basaltResistance)
		};
		
		//Basalt Stairs
		
		//Stairs
		for(int i = 0; i < baseBlocks.length; i++){
			//basaltStairs[baseBlocks[i].select] = (BlockGenericStairs) new BlockGenericStairs(baseBlocks[i]).setCreativeTab(Cathedral.tabBasalt);
			//GameRegistry.registerBlock(basaltStairs[baseBlocks[i].select], baseBlocks[i].blockName + "Stairs");
			//GameRegistry.addRecipe(new ItemStack(basaltStairs[baseBlocks[i].select], 6, 0), "X  ", "XX ", "XXX", 'X', new ItemStack(baseBlocks[i].block, 1, baseBlocks[i].metaData) );
		}
		
		//Explicitly Added Variations
		/*{
			ICarvingRegistry Carving = CarvingUtils.getChiselRegistry();
			Carving.addVariation("basaltblock", basaltBase, 3, -2);
			Carving.addVariation("basaltblock", basaltBase, 4, -1);

			for(int i = 0; i < 6; i++){
				Carving.addVariation("basaltStairs", basaltStairs[i], 0, 1);
			}
		}*/
		

	}

	@Override
	public void createItems() {
		// TODO Auto-generated method stub
	}

	@Override
	public void registerBlocks(IForgeRegistry<Block> registry) {
		//registry.register(basaltBase);
		registry.register(basaltBlock);
		registry.register(basaltSlab);
		
		registry.register(checkeredBlock);
		registry.register(checkeredSlab);
	}

	@Override
	public void registerItems(IForgeRegistry<Item> registry) {
		
		registry.register(new ItemMultiTexture(basaltBlock, basaltBlock, new ItemMultiTexture.Mapper() {
            public String apply(ItemStack stack) {
                return Basalt.EnumType.byMetadata(stack.getMetadata()).getUnlocalizedName();
            }
        }).setRegistryName(basaltBlock.getRegistryName()));
		
	}

	@Override
	public void registerRecipes(IForgeRegistry<IRecipe> registry) {
		
		//int basaltSlabMetas[] = { 2, 6, 7, 9, 10, 11 };
		
		//Basalt Slab Recipes
		/*
		for(int i = 0; i < basaltSlabMetas.length; i++){
			GameRegistry.addRecipe(new ItemStack(basaltSlab, 6, basaltSlabMetas[i]), "XXX", 'X', new ItemStack(basaltBlock, 1, basaltSlabMetas[i]) );
		}*/
		
		//GameRegistry.addRecipe(new ItemStack(basaltSlab, 6, 7), "XXX", 'X', new ItemStack(basaltBlock, 1, 1) );//Basalt Paver
		//GameRegistry.addRecipe(new ItemStack(basaltBlock, 1, 7), "X", "X", 'X', new ItemStack(basaltSlab, 1, 7));
		
		//Checkered Tile Recipes
		//GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(checkeredBlock, 4, 0), true, new Object[]{"mb", "bm", 'b', "basalt", 'm', "marble"}));
		//GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(checkeredBlock, 4, 0), true, new Object[]{"bm", "mb", 'b', "basalt", 'm', "marble"}));
		
		//Thermal Expansion Capabilities
		/*if(Loader.isModLoaded("ThermalExpansion")){//Having thermal expansion adds the ability to create basalt from obsidian dust and stone
			//ThermalExpansion Smelter recipe for Basalt
			ItemStack obsidianDust = new ItemStack(GameRegistry.findItem("ThermalFoundation", "material"), 1, 4);
			
			ThermalExpansionHelper.addSmelterRecipe(2000, new ItemStack(Blocks.stone, 1, 0), obsidianDust, new ItemStack(basaltBase, 1, 3));
			ThermalExpansionHelper.addSmelterRecipe(2000, new ItemStack(Blocks.cobblestone, 1, 0), obsidianDust, new ItemStack(basaltBase, 1, 2));
		} else {
			GameRegistry.addRecipe(new ItemStack(basaltBase, 1, 2), "XXX", "XOX", "XXX", 'X', new ItemStack(Blocks.stone, 1), 'O', new ItemStack(Blocks.obsidian));
			GameRegistry.addRecipe(new ItemStack(basaltBase, 1, 3), "XXX", "XOX", "XXX", 'X', new ItemStack(Blocks.cobblestone, 1), 'O', new ItemStack(Blocks.obsidian));
		}*/		
	}

	@Override
	public void registerModels(ModelRegistryEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init() {}

	@Override
	public void postInit() {}
}
