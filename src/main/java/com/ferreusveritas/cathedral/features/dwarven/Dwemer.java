package com.ferreusveritas.cathedral.features.dwarven;

import com.ferreusveritas.cathedral.Cathedral;
import com.ferreusveritas.cathedral.blocks.BlockBars;
import com.ferreusveritas.cathedral.blocks.BlockCarvable;
import com.ferreusveritas.cathedral.blocks.BlockCarvableGlass;
import com.ferreusveritas.cathedral.blocks.BlockCatwalk;
import com.ferreusveritas.cathedral.blocks.BlockShortDoor;
import com.ferreusveritas.cathedral.blocks.BlockTallDoor;
import com.ferreusveritas.cathedral.items.ItemShortDoor;
import com.ferreusveritas.cathedral.items.ItemSubBlocks;
import com.ferreusveritas.cathedral.items.ItemTallDoor;
import com.ferreusveritas.stonelore.basalt.Basalt;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import team.chisel.ctmlib.ISubmapManager;

public class Dwemer {

	public static BlockBars	dwemerBars;

	public static Block dwemerCatwalkBlock;

	public static BlockShortDoor shortDoorBlock;
	public static ItemShortDoor shortDoorItem;

	public static BlockTallDoor	tallDoorBlock;
	public static ItemTallDoor tallDoorItem;

	public static BlockCarvable dwemerBlock;
	public static BlockCarvable dwemerLightBlock;
	public static BlockCarvableGlass dwemerGlassBlock;

	public static void preInit(Cathedral basalt){
		dwemerBlock = (BlockCarvable) new BlockCarvable(Material.rock).setCreativeTab(Cathedral.tabBasalt).setHardness(Basalt.basaltHardness).setResistance(Basalt.basaltResistance);
		dwemerLightBlock = (BlockCarvable) new BlockCarvable(Material.rock).setCreativeTab(Cathedral.tabBasalt).setHardness(Basalt.basaltHardness).setResistance(Basalt.basaltResistance).setLightLevel(1.0F);
		dwemerGlassBlock = (BlockCarvableGlass) new BlockCarvableGlass().setCreativeTab(Cathedral.tabBasalt).setHardness(0.3F).setStepSound(Block.soundTypeGlass);			
		dwemerCatwalkBlock = new BlockCatwalk(Material.iron).setCreativeTab(Cathedral.tabBasalt).setHardness(2.5f).setResistance(20F).setStepSound(Block.soundTypeMetal).setBlockName(Cathedral.MODID + "_dwemercatwalk").setBlockTextureName("dwemer-catwalk");
		dwemerBars = new BlockBars();
	}

	public static void init(Cathedral basalt){

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

		BlockCarvable.addBlocks(dwemerNames, dwemerBlock, "dwemer");

		//Dwemer Light Blocks
		String dwemerLightNames[]  = {
				"dwemer-light-path",//0
				"dwemer-light-vent",//1
				"dwemer-light-gas"//2
		};

		BlockCarvable.addBlocks(dwemerLightNames, dwemerLightBlock, "dwemlite");

		//Dwemer Glass Blocks
		dwemerGlassBlock.carverHelper.addVariation("tile.basalt_dwemer.dwemer-glass-fence.name", 0, "dwemer-glass-fence", null, 0, Cathedral.MODID, (ISubmapManager) null, 100);
		dwemerGlassBlock.carverHelper.addVariation("tile.basalt_dwemer.dwemer-glass-ornate.name", 1, "dwemer-glass-ornate", null, 0, Cathedral.MODID, (ISubmapManager) null, 100);
		dwemerGlassBlock.carverHelper.registerAll(dwemerGlassBlock, "glass");

		//Dwemer Bars
		GameRegistry.registerBlock(dwemerBars, ItemSubBlocks.class, "DwemerBars");
		dwemerBars.addVariations();

		//Dwemer Catwalk
		GameRegistry.registerBlock(dwemerCatwalkBlock, "dwemcatwalk");

		//Doors
		shortDoorBlock = (BlockShortDoor) new BlockShortDoor().setBlockName(Cathedral.MODID + "_doorShort").setBlockTextureName(Cathedral.MODID + ":short").setHardness(3.5f).setResistance(Basalt.basaltResistance);
		tallDoorBlock = (BlockTallDoor) new BlockTallDoor().setBlockName(Cathedral.MODID + "_doorTall").setBlockTextureName(Cathedral.MODID + ":tall").setHardness(3.5f).setResistance(Basalt.basaltResistance);

		GameRegistry.registerBlock(shortDoorBlock, "ShortDoor");
		GameRegistry.registerBlock(tallDoorBlock, "TallDoor");

		shortDoorItem = (ItemShortDoor) new ItemShortDoor(Material.iron).setUnlocalizedName(Cathedral.MODID + "_doorShort").setCreativeTab(Cathedral.tabBasalt);
		GameRegistry.registerItem(shortDoorItem, "ShortDoorItem");
		shortDoorBlock.setDroppedItem(shortDoorItem);

		tallDoorItem = (ItemTallDoor) new ItemTallDoor(Material.iron).setUnlocalizedName(Cathedral.MODID + "_doorTall").setCreativeTab(Cathedral.tabBasalt);
		GameRegistry.registerItem(tallDoorItem, "TallDoorItem");
		tallDoorBlock.setDroppedItem(tallDoorItem);

		//Recipes

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

	}
}
