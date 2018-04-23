package com.ferreusveritas.cathedral.features.dwemer;

import com.ferreusveritas.cathedral.CathedralMod;
import com.ferreusveritas.cathedral.ModConstants;
import com.ferreusveritas.cathedral.common.blocks.BlockGlassBase;
import com.ferreusveritas.cathedral.features.BlockForm;
import com.ferreusveritas.cathedral.features.IFeature;
import com.ferreusveritas.cathedral.proxy.ModelHelper;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.registries.IForgeRegistry;

public class Dwemer implements IFeature {

	public static final String featureName = "dwemer";

	public Block blockCarved, lightNormal, glassNormal, barsNormal, doorNormal, doorTall;
	public Item  itemDoorNormal, itemDoorTall;
	
	@Override
	public String getName() {
		return featureName;
	}

	@Override
	public void preInit() {}

	@Override
	public void createBlocks() {
		blockCarved = new BlockDwemer(featureObjectName(BlockForm.BLOCK, "carved"));
		lightNormal = (BlockDwemerLight) new BlockDwemerLight(featureObjectName(BlockForm.LIGHT, "normal"));

		glassNormal = (BlockGlass) new BlockGlassBase(Material.GLASS, false, featureObjectName(BlockForm.GLASS, "normal"))
				.setCreativeTab(CathedralMod.tabDwemer)
				//.setStepSound(SoundType.GLASS)
				.setHardness(0.3F);

		barsNormal = new BlockBars(featureObjectName(BlockForm.BARS, "normal"));
		
		doorNormal = new BlockShortDoor(Material.IRON, featureObjectName(BlockForm.DOOR, "normal")).setCreativeTab(CathedralMod.tabDwemer);

		doorTall = new BlockTallDoor(Material.IRON, featureObjectName(BlockForm.DOOR, "tall")).setCreativeTab(CathedralMod.tabDwemer);
		
		//BlockCarvable.addBlocks(dwemerNames, dwemerBlock, "dwemer");
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
	}

	@Override
	public void registerBlocks(IForgeRegistry<Block> registry) {
		registry.registerAll(
			blockCarved,
			lightNormal,
			//glassNormal,
			barsNormal,
			doorNormal,
			doorTall
		);
	}

	@Override
	public void registerItems(IForgeRegistry<Item> registry) {

		registry.register(new ItemMultiTexture(blockCarved, blockCarved, new ItemMultiTexture.Mapper() {
            public String apply(ItemStack stack) {
                return BlockDwemer.EnumType.byMetadata(stack.getMetadata()).getUnlocalizedName();
            }
        }).setRegistryName(blockCarved.getRegistryName()));

		registry.register(new ItemMultiTexture(lightNormal, lightNormal, new ItemMultiTexture.Mapper() {
            public String apply(ItemStack stack) {
                return BlockDwemerLight.EnumType.byMetadata(stack.getMetadata()).getUnlocalizedName();
            }
        }).setRegistryName(lightNormal.getRegistryName()));
		
		registry.register(new ItemMultiTexture(barsNormal, barsNormal, new ItemMultiTexture.Mapper() {
            public String apply(ItemStack stack) {
                return BlockBars.EnumType.byMetadata(stack.getMetadata()).getUnlocalizedName();
            }
        }).setRegistryName(barsNormal.getRegistryName()));
		
		ItemDoor itemDoor = new ItemDoor(doorNormal);
		ItemTallDoor itemTallDoor = new ItemTallDoor(doorTall);
		
		itemDoor.setUnlocalizedName(doorNormal.getUnlocalizedName()).setRegistryName(doorNormal.getRegistryName()).setCreativeTab(CathedralMod.tabDwemer);
		itemTallDoor.setUnlocalizedName(doorTall.getUnlocalizedName()).setRegistryName(doorTall.getRegistryName()).setCreativeTab(CathedralMod.tabDwemer);
		
		((BlockShortDoor)doorNormal).setDoorItem(itemDoor);
		((BlockTallDoor)doorTall).setDoorItem(itemTallDoor);
		
		registry.register(itemDoor);
		registry.register(itemTallDoor);
	}

	@Override
	public void registerRecipes(IForgeRegistry<IRecipe> registry) {
		
		String basaltOre = "blockBasalt";
		String metalIngot;

		//Attempt to find the best fit for a center metal ingot
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
		registry.register(new ShapedOreRecipe(
				null,
				new ItemStack(blockCarved, 16, 0),
				new Object[]{
						"bbb",
						"bib",
						"bbb",
						'b', basaltOre,
						'i', metalIngot
					}
				).setRegistryName(ModConstants.MODID, "dwemer_stone")
			);
		
		//Dwemer Lights Recipes
		GameRegistry.addShapedRecipe(
				new ResourceLocation(ModConstants.MODID, "dwemer_light"),
				null,
				new ItemStack(lightNormal, 2, 0), 
				"xgx",
				"glg",
				"xgx",
				'x', new ItemStack(blockCarved, 1, 0),
				'l', Blocks.GLOWSTONE,
				'g', new ItemStack(Blocks.STAINED_GLASS, 1, 4)
			);

		
		//Recipe for Dwemer Bars
		//GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(dwemerBars, 16, 0), true, new Object[]{"nnn", "nnn", 'n', metalIngot}));

		//Recipe for Dwemer Catwalk
		//GameRegistry.addRecipe(new ItemStack(dwemerCatwalkBlock, 3, 0), "XXX", 'X', new ItemStack(dwemerBars, 1, 0));

		//Recipe for Dwemer Doors
		//GameRegistry.addRecipe(new ItemStack(tallDoorItem), "X", "X", "X", 'X', new ItemStack(dwemerBlock, 1, 12));
		//GameRegistry.addRecipe(new ItemStack(shortDoorItem), "X", "X", 'X', new ItemStack(dwemerBlock, 1, 12));
	}

	@Override
	public void registerModels(ModelRegistryEvent event) {
		
		for(BlockDwemer.EnumType type: BlockDwemer.EnumType.values()) {
			ModelHelper.regModel(Item.getItemFromBlock(blockCarved), type.getMetadata(), new ResourceLocation(ModConstants.MODID, blockCarved.getRegistryName().getResourcePath() + "." + type.getUnlocalizedName()));
		}
		
		for(BlockDwemerLight.EnumType type: BlockDwemerLight.EnumType.values()) {
			ModelHelper.regModel(Item.getItemFromBlock(lightNormal), type.getMetadata(), new ResourceLocation(ModConstants.MODID, lightNormal.getRegistryName().getResourcePath() + "." + type.getUnlocalizedName()));
		}
		
		for(BlockBars.EnumType type: BlockBars.EnumType.values()) {
			ModelHelper.regModel(Item.getItemFromBlock(barsNormal), type.getMetadata(), new ResourceLocation(ModConstants.MODID, barsNormal.getRegistryName().getResourcePath() + "." + type.getUnlocalizedName()));
		}
		
		ModelLoader.setCustomStateMapper(doorNormal, new StateMap.Builder().ignore(BlockDoor.POWERED).build());
		ModelLoader.setCustomStateMapper(doorTall, new StateMap.Builder().ignore(BlockDoor.POWERED).build());
		
		ModelHelper.regModel(((BlockShortDoor)doorNormal).getDoorItem(), 0, doorNormal.getRegistryName());
		ModelHelper.regModel(((BlockTallDoor)doorTall).getDoorItem(), 0, doorTall.getRegistryName());

	}

	@Override
	public void init() {
		//Add chisel variations for Dwemer Blocks
		for(BlockDwemer.EnumType type: BlockDwemer.EnumType.values()) {
			addChiselVariation("dwemer", blockCarved, type.getMetadata());
		}
		
		//Add chisel variations for Dwemer Light Blocks
		for(BlockDwemerLight.EnumType type: BlockDwemerLight.EnumType.values()) {
			addChiselVariation("dwemerlight", lightNormal, type.getMetadata());
		}
	}

	private void addChiselVariation(String group, Block block, int meta) {
		FMLInterModComms.sendMessage("chisel", "variation:add", group + "|" + block.getRegistryName() + "|" + meta);
	}
	
	@Override
	public void postInit() {}
}
