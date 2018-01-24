package com.ferreusveritas.cathedral.features.basalt;

import com.ferreusveritas.cathedral.Cathedral;
import com.ferreusveritas.cathedral.blocks.BaseBlockDef;
import com.ferreusveritas.cathedral.blocks.BlockCarvable;
import com.ferreusveritas.cathedral.blocks.BlockCarvableSlab;
import com.ferreusveritas.cathedral.blocks.BlockGenericStairs;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.oredict.OreDictionary;

public class Basalt {

	public static Block basaltBase;//This is assigned from Project Red in PostInit()

	public static BlockCarvable basaltBlock;
	public static BlockCarvable checkeredBlock;

	public static BlockCarvableSlab basaltSlab;
	public static BlockCarvableSlab checkeredSlab;

	public static BlockGenericStairs basaltStairs[] = new BlockGenericStairs[8];

	public static float basaltHardness = 2.5f;
	public static float basaltResistance = 20f;
	public static float marbleHardness = 2.0f;
	public static float marbleResistance = 10f;

	public static void preInit(Cathedral lore){
		
		basaltBase = new Block(Material.ROCK);
		
		basaltBlock = (BlockCarvable) new BlockCarvable(Material.ROCK).setCreativeTab(Cathedral.tabBasalt).setHardness(basaltHardness).setResistance(basaltResistance);
		basaltSlab = (BlockCarvableSlab) new BlockCarvableSlab(basaltBlock).setCreativeTab(Cathedral.tabBasalt).setHardness(basaltHardness).setResistance(basaltResistance);

		checkeredBlock = (BlockCarvable) new BlockCarvable(Material.ROCK).setCreativeTab(Cathedral.tabBasalt).setHardness((basaltHardness + marbleHardness) / 2F).setResistance((basaltResistance + marbleResistance) / 2F);
		checkeredSlab = (BlockCarvableSlab) new BlockCarvableSlab(checkeredBlock).setCreativeTab(Cathedral.tabBasalt).setHardness((basaltHardness + marbleHardness) / 2F).setResistance((basaltResistance + marbleResistance) / 2F);

		/*String basaltNames[] = {
				"basalt-rosette",//0
				"basalt-paver",//1
				"basalt-worn-brick",//2
				"basalt-ornate",//3
				"basalt-poison",//4
				"basalt-sunken-panel",//5
				"basalt-tiles",//6
				"basalt-slabs",//7
				"basalt-vault",//8
				"basalt-smallbricks",//9
				"basalt-smallchaotic",//10
				"basalt-smalltiles",//11
				"basalt-block",//12
				"basalt-sunken",//13
				"basalt-knot"//14
		};*/

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

	}

	public static void init(Cathedral lore){
		//This must be ran in init because project red doesn't register it's stone until after it's init
		/*if(Loader.isModLoaded("ProjRed|Exploration")){
			basaltBase = GameRegistry.findBlock("ProjRed|Exploration", "projectred.exploration.stone");
		} else {
			//basaltBase = GameRegistry.registerBlock(block, "nothing");
		}*/
		
		//Ore Dictionary Registrations
		OreDictionary.registerOre("basalt", new ItemStack(basaltBase, 1, 3));
		OreDictionary.registerOre("basaltBrick", new ItemStack(basaltBase, 1, 4));
		
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
	
	
	public static enum EnumType implements IStringSerializable {
	
		ROSETTE		( 0, "rosette"),
		PAVER		( 1, "paver"),
		WORNBRICK	( 2, "worn-brick"),
		ORNATE		( 3, "ornate"),
		POISON		( 4, "poison"),
		SUNKENPANEL	( 5, "sunken-panel"),
		TILES		( 6, "tiles"),
		SLABS		( 7, "slabs"),
		VAULT		( 8, "vault"),
		SMALLBRICKS	( 9, "smallbricks"),
		SMALLCHAOTI	(10, "smallchaotic"),
		SMALLTILES	(11, "smalltiles"),
		BLOCK		(12, "block"),
		SUNKEN		(13, "sunken"),
		KNOT		(14, "knot"); 
		
		/** Array of the Block's BlockStates */
		private static final Basalt.EnumType[] META_LOOKUP = new Basalt.EnumType[values().length];
		/** The BlockState's metadata. */
		private final int meta;
		/** The EnumType's name. */
		private final String name;
		private final String unlocalizedName;
		
		private EnumType(int index, String name) {
			this.meta = index;
			this.name = name;
			this.unlocalizedName = name;
		}
		
		/** Returns the EnumType's metadata value. */
		public int getMetadata() {
			return this.meta;
		}
		
		@Override
		public String toString() {
			return this.name;
		}
		
		/** Returns an EnumType for the BlockState from a metadata value. */
		public static Basalt.EnumType byMetadata(int meta) {
			if (meta < 0 || meta >= META_LOOKUP.length) {
				meta = 0;
			}
			
			return META_LOOKUP[meta];
		}
		
		@Override
		public String getName() {
			return this.name;
		}
		
		public String getUnlocalizedName() {
			return this.unlocalizedName;
		}
		
		static {
			for (Basalt.EnumType blockbasalt$enumtype : values()) {
				META_LOOKUP[blockbasalt$enumtype.getMetadata()] = blockbasalt$enumtype;
			}
		}

	}
}
