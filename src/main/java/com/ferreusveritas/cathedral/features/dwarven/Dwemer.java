package com.ferreusveritas.cathedral.features.dwarven;

import com.ferreusveritas.cathedral.blocks.BlockBars;
import com.ferreusveritas.cathedral.blocks.BlockCarvable;
import com.ferreusveritas.cathedral.blocks.BlockCarvableGlass;
import com.ferreusveritas.cathedral.blocks.BlockShortDoor;
import com.ferreusveritas.cathedral.blocks.BlockTallDoor;
import com.ferreusveritas.cathedral.features.IFeature;
import com.ferreusveritas.cathedral.items.ItemShortDoor;
import com.ferreusveritas.cathedral.items.ItemTallDoor;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

public class Dwemer implements IFeature {

	public static final String featureName = "dwemer";
	
	public BlockBars	dwemerBars;

	public Block dwemerCatwalkBlock;

	public BlockShortDoor shortDoorBlock;
	public ItemShortDoor shortDoorItem;

	public BlockTallDoor	tallDoorBlock;
	public ItemTallDoor tallDoorItem;

	public BlockCarvable dwemerBlock;
	public BlockCarvable dwemerLightBlock;
	public BlockCarvableGlass dwemerGlassBlock;
	
	public static enum EnumType implements IStringSerializable {
		
		EMBEDDED   	( 0, "embedded"),
		PILLAR     	( 1, "pillar"),
		ALTAR     	( 2, "altar"),
		DECOR      	( 3, "decor"),
		CARVING1   	( 4, "carving-1"),
		CARVING2   	( 5, "carving-2"),
		LAYERED    	( 6, "layered"),
		SCALEPILLAR	( 7, "scale-pillar"),
		WORMGEAR   	( 8, "wormgear"),
		RAYS       	( 9, "rays"),
		KNOT       	(10, "knot"),
		MASK       	(11, "mask"),
		DOORTOP    	(12, "doortop"),
		DOORBOTTOM 	(13, "doorbottom"),
		PANEL      	(14, "panel");
		
		/** Array of the Block's BlockStates */
		private static final Dwemer.EnumType[] META_LOOKUP = new Dwemer.EnumType[values().length];
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
		public static Dwemer.EnumType byMetadata(int meta) {
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
			for (Dwemer.EnumType blockdwemer$enumtype : values()) {
				META_LOOKUP[blockdwemer$enumtype.getMetadata()] = blockdwemer$enumtype;
			}
		}

	}


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
		//dwemerBlock = (BlockCarvable) new BlockCarvable(Material.ROCK).setCreativeTab(Cathedral.tabBasalt).setHardness(Basalt.basaltHardness).setResistance(Basalt.basaltResistance);
		//dwemerLightBlock = (BlockCarvable) new BlockCarvable(Material.ROCK).setCreativeTab(Cathedral.tabBasalt).setHardness(Basalt.basaltHardness).setResistance(Basalt.basaltResistance).setLightLevel(1.0F);
		//dwemerGlassBlock = (BlockCarvableGlass) new BlockCarvableGlass().setCreativeTab(Cathedral.tabBasalt).setHardness(0.3F).setStepSound(SoundType.GLASS);			
		//dwemerCatwalkBlock = new BlockCatwalk(Material.IRON).setCreativeTab(Cathedral.tabBasalt).setHardness(2.5f).setResistance(20F).setStepSound(SoundType.METAL).setBlockName(Cathedral.MODID + "_dwemercatwalk").setBlockTextureName("dwemer-catwalk");
		//dwemerBars = new BlockBars();		
		
		//Dwemer Blocks
		String dwemerNames[]  = {
				"dwemer-embedded",//0
				"dwemer-pillar",//1
				"dwemer-altar",//2
				"dwemer-decor",//3
				"dwemer-carving-1",//4
				"dwemer-carving-2",//5
				"dwemer-layered",//6
				"dwemer-scale-pillar",//7
				"dwemer-wormgear",//8
				"dwemer-rays",//9
				"dwemer-knot",//10
				"dwemer-mask",//11
				"dwemer-doortop",//12
				"dwemer-doorbottom",//13
				"dwemer-panel"//14
		};

		//BlockCarvable.addBlocks(dwemerNames, dwemerBlock, "dwemer");

		//Dwemer Light Blocks
		String dwemerLightNames[]  = {
				"dwemer-light-path",//0
				"dwemer-light-vent",//1
				"dwemer-light-gas"//2
		};

		//BlockCarvable.addBlocks(dwemerLightNames, dwemerLightBlock, "dwemlite");

		//Dwemer Glass Blocks
		//dwemerGlassBlock.carverHelper.addVariation("tile.basalt_dwemer.dwemer-glass-fence.name", 0, "dwemer-glass-fence", null, 0, Cathedral.MODID, (ISubmapManager) null, 100);
		//dwemerGlassBlock.carverHelper.addVariation("tile.basalt_dwemer.dwemer-glass-ornate.name", 1, "dwemer-glass-ornate", null, 0, Cathedral.MODID, (ISubmapManager) null, 100);
		//dwemerGlassBlock.carverHelper.registerAll(dwemerGlassBlock, "glass");

		//Dwemer Bars
		//GameRegistry.registerBlock(dwemerBars, ItemSubBlocks.class, "DwemerBars");
		//dwemerBars.addVariations();

		//Dwemer Catwalk
		//GameRegistry.registerBlock(dwemerCatwalkBlock, "dwemcatwalk");

		//Doors
		//shortDoorBlock = (BlockShortDoor) new BlockShortDoor().setBlockName(Cathedral.MODID + "_doorShort").setBlockTextureName(Cathedral.MODID + ":short").setHardness(3.5f).setResistance(Basalt.basaltResistance);
		//tallDoorBlock = (BlockTallDoor) new BlockTallDoor().setBlockName(Cathedral.MODID + "_doorTall").setBlockTextureName(Cathedral.MODID + ":tall").setHardness(3.5f).setResistance(Basalt.basaltResistance);

		//GameRegistry.registerBlock(shortDoorBlock, "ShortDoor");
		//GameRegistry.registerBlock(tallDoorBlock, "TallDoor");

		//shortDoorItem = (ItemShortDoor) new ItemShortDoor(Material.IRON).setUnlocalizedName(Cathedral.MODID + "_doorShort").setCreativeTab(Cathedral.tabBasalt);
		//GameRegistry.registerItem(shortDoorItem, "ShortDoorItem");
		//shortDoorBlock.setDroppedItem(shortDoorItem);

		//tallDoorItem = (ItemTallDoor) new ItemTallDoor(Material.IRON).setUnlocalizedName(Cathedral.MODID + "_doorTall").setCreativeTab(Cathedral.tabBasalt);
		//GameRegistry.registerItem(tallDoorItem, "TallDoorItem");
		//tallDoorBlock.setDroppedItem(tallDoorItem);

	}

	@Override
	public void createItems() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerBlocks(IForgeRegistry<Block> registry) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerItems(IForgeRegistry<Item> registry) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerRecipes(IForgeRegistry<IRecipe> registry) {
		
		//Attempt to find the best fit for a center metal ingot
		String metalIngot;

		if(OreDictionary.doesOreNameExist("ingotDwemer")){
			metalIngot = "ingotDwemer";//In case a skyrim mod is out there
		} else
			if(OreDictionary.doesOreNameExist("ingotBrass")){
				metalIngot = "ingotBrass";//Brass would also make sense
			} else
				if(OreDictionary.doesOreNameExist("ingotBronze")){
					metalIngot = "ingotBronze";//Bronze is close enough and pretty common
				} else {
					metalIngot = "ingotGold";//This sucks but whatever
				}

		/*
		//Recipe for Dwemer Stone
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(dwemerBlock, 16, 0), true, new Object[]{"bbb", "bnb", "bbb", 'b', "basalt", 'n', metalIngot}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(dwemerBlock, 16, 0), true, new Object[]{"bbb", "bnb", "bbb", 'b', "basaltBrick", 'n', metalIngot}));
		//Recipe for Dwemer Bars
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(dwemerBars, 16, 0), true, new Object[]{"nnn", "nnn", 'n', metalIngot}));

		//Dwemer Lights Recipes
		GameRegistry.addRecipe(new ItemStack(dwemerLightBlock, 2, 0), "XGX", "GLG", "XGX", 'X', new ItemStack(dwemerBlock, 1, 0), 'L', Blocks.glowstone, 'G', new ItemStack(Blocks.stained_glass, 1, 4));

		//Recipe for Dwemer Catwalk
		GameRegistry.addRecipe(new ItemStack(dwemerCatwalkBlock, 3, 0), "XXX", 'X', new ItemStack(dwemerBars, 1, 0));

		//Recipe for Dwemer Doors
		GameRegistry.addRecipe(new ItemStack(tallDoorItem), "X", "X", "X", 'X', new ItemStack(dwemerBlock, 1, 12));
		GameRegistry.addRecipe(new ItemStack(shortDoorItem), "X", "X", 'X', new ItemStack(dwemerBlock, 1, 12));
		*/
		
	}

	@Override
	public void registerModels(ModelRegistryEvent event) {}

	@Override
	public void init() {}

	@Override
	public void postInit() {}
}
