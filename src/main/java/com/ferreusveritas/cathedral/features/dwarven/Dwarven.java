package com.ferreusveritas.cathedral.features.dwarven;

import com.ferreusveritas.cathedral.CathedralMod;
import com.ferreusveritas.cathedral.ModConstants;
import com.ferreusveritas.cathedral.common.blocks.BlockMultiVariant;
import com.ferreusveritas.cathedral.features.BlockForm;
import com.ferreusveritas.cathedral.features.IFeature;
import com.ferreusveritas.cathedral.features.dwarven.FeatureTypes.EnumCarvedType;
import com.ferreusveritas.cathedral.features.dwarven.FeatureTypes.EnumGlassType;
import com.ferreusveritas.cathedral.features.dwarven.FeatureTypes.EnumLightType;
import com.ferreusveritas.cathedral.proxy.ModelHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.registries.IForgeRegistry;

import static com.ferreusveritas.cathedral.util.InterModCommsUtils.addChiselVariation;

public class Dwarven implements IFeature {
	
	public static final String featureName = "dwemer";
	
	public Block blockCarved, lightNormal, glassNormal, barsNormal, doorNormal, doorTall;
	public Item  itemDoorNormal, itemDoorTall;
	
	public final CreativeTabs tabDwemer;
	
	public Dwarven() {
		tabDwemer = new CreativeTabs("tabDwemer") {
			@Override
			public ItemStack getTabIconItem() {
				return new ItemStack(blockCarved, 1, EnumCarvedType.EMBEDDED.getMetadata());
			}
		};
	}
	
	@Override
	public String getName() {
		return featureName;
	}
	
	@Override
	public void preInit() {}
	
	@Override
	public void createBlocks() {
		
		blockCarved = new BlockMultiVariant<EnumCarvedType>(Material.ROCK, EnumCarvedType.class, featureObjectName(BlockForm.BLOCK, "carved")) {
			@Override
			public void makeVariantProperty() {
				variant = PropertyEnum.<EnumCarvedType>create("variant", EnumCarvedType.class);			
			}
		}.setCreativeTab(tabDwemer)
				.setHardness(CathedralMod.basaltHardness)
				.setResistance(CathedralMod.basaltResistance);
		
		lightNormal = new BlockMultiVariant<EnumLightType>(Material.ROCK, EnumLightType.class, featureObjectName(BlockForm.LIGHT, "normal")) {
			@Override
			public void makeVariantProperty() {
				variant = PropertyEnum.<EnumLightType>create("variant", EnumLightType.class);			
			}
		}.setCreativeTab(tabDwemer)
				.setHardness(CathedralMod.basaltHardness)
				.setResistance(CathedralMod.basaltResistance)
				.setLightLevel(1.0F);
		
		glassNormal = new BlockDwarvenGlass(featureObjectName(BlockForm.GLASS, "normal"));
		
		barsNormal = new BlockDwarvenBars(featureObjectName(BlockForm.BARS, "normal"));
		
		doorNormal = new BlockShortDoor(Material.IRON, featureObjectName(BlockForm.DOOR, "normal"))
				.setCreativeTab(tabDwemer)
				.setHardness(3.5f)
				.setResistance(CathedralMod.basaltResistance);
		
		doorTall = new BlockTallDoor(Material.IRON, featureObjectName(BlockForm.DOOR, "tall"))
				.setCreativeTab(tabDwemer)
				.setHardness(3.5f)
				.setResistance(CathedralMod.basaltResistance);
		
		//Dwemer Catwalk
		//GameRegistry.registerBlock(dwemerCatwalkBlock, "dwemcatwalk");
	}
	
	@Override
	public void createItems() {		
	}
	
	@Override
	public void registerBlocks(IForgeRegistry<Block> registry) {
		registry.registerAll(
				blockCarved,
				lightNormal,
				glassNormal,
				barsNormal,
				doorNormal,
				doorTall
				);
	}
	
	@Override
	public void registerItems(IForgeRegistry<Item> registry) {
		
		registry.register(((BlockMultiVariant<EnumCarvedType>)blockCarved).getItemMultiTexture());
		registry.register(((BlockMultiVariant<EnumLightType>)lightNormal).getItemMultiTexture());
		registry.register(((BlockMultiVariant<EnumGlassType>)glassNormal).getItemMultiTexture());
		
		registry.register(new ItemMultiTexture(barsNormal, barsNormal, new ItemMultiTexture.Mapper() {
			public String apply(ItemStack stack) {
				return BlockDwarvenBars.EnumType.byMetadata(stack.getMetadata()).getUnlocalizedName();
			}
		}).setRegistryName(barsNormal.getRegistryName()));
		
		ItemDoor itemDoor = new ItemDoor(doorNormal);
		ItemTallDoor itemTallDoor = new ItemTallDoor(doorTall);
		
		itemDoor.setUnlocalizedName(doorNormal.getUnlocalizedName()).setRegistryName(doorNormal.getRegistryName()).setCreativeTab(tabDwemer);
		itemTallDoor.setUnlocalizedName(doorTall.getUnlocalizedName()).setRegistryName(doorTall.getRegistryName()).setCreativeTab(tabDwemer);
		
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
		if(OreDictionary.doesOreNameExist("ingotDwemer") && !OreDictionary.getOres("ingotDwemer").isEmpty()){
			metalIngot = "ingotDwemer";//In case a Skyrim mod is out there
		} else
			if(OreDictionary.doesOreNameExist("ingotDawnstone") && !OreDictionary.getOres("ingotDawnstone").isEmpty()) {
				metalIngot = "ingotDawnstone";//Basically a perfect fit (Embers)
			} else
				if(OreDictionary.doesOreNameExist("ingotBrass") && !OreDictionary.getOres("ingotBrass").isEmpty()){
					metalIngot = "ingotBrass";//Brass would also make sense
				} else
					if(OreDictionary.doesOreNameExist("ingotBronze") && !OreDictionary.getOres("ingotBronze").isEmpty()){
						metalIngot = "ingotBronze";//Bronze is close enough and pretty common
					} else {
						metalIngot = "ingotGold";//This sucks but whatever
					}
		
		//Recipe for Dwarven Stone
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
		
		//Dwarven Lights Recipes
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
		
		//Recipe for Dwarven Bars
		registry.register(
				new ShapedOreRecipe(
						null,
						new ItemStack(barsNormal, 16, 0), 
						new Object[]{
								"iii",
								"iii",
								'i', metalIngot
						}
						).setRegistryName(ModConstants.MODID, "dwemer_bars")
				);
		
		//Recipe for Dwarven Tall Door
		GameRegistry.addShapedRecipe(
				new ResourceLocation(ModConstants.MODID, "dwemer_door_tall"),
				null,
				new ItemStack(((BlockTallDoor)doorTall).getDoorItem(), 1), 
				"x",
				"x",
				"x",
				'x', new ItemStack(blockCarved, 1, 8)
				);
		
		//Recipe for Dwarven Short Door
		GameRegistry.addShapedRecipe(
				new ResourceLocation(ModConstants.MODID, "dwemer_door_normal"),
				null,
				new ItemStack(((BlockShortDoor)doorNormal).getDoorItem(), 1), 
				"x",
				"x",
				'x', new ItemStack(blockCarved, 1, 8)
				);
		
		//Recipe for Dwarven Catwalk
		//GameRegistry.addRecipe(new ItemStack(dwemerCatwalkBlock, 3, 0), "XXX", 'X', new ItemStack(dwemerBars, 1, 0));
		
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerModels(ModelRegistryEvent event) {
		
		((BlockMultiVariant<EnumCarvedType>)blockCarved).registerItemModels();
		((BlockMultiVariant<EnumLightType>)lightNormal).registerItemModels();
		((BlockMultiVariant<EnumGlassType>)glassNormal).registerItemModels();
		
		for(BlockDwarvenBars.EnumType type: BlockDwarvenBars.EnumType.values()) {
			ModelHelper.regModel(Item.getItemFromBlock(barsNormal), type.getMetadata(), new ResourceLocation(ModConstants.MODID, barsNormal.getRegistryName().getResourcePath() + "." + type.getUnlocalizedName()));
		}
		
		ModelLoader.setCustomStateMapper(doorNormal, new StateMap.Builder().ignore(BlockDoor.POWERED).build());
		ModelLoader.setCustomStateMapper(doorTall, new StateMap.Builder().ignore(BlockDoor.POWERED).build());
		
		ModelHelper.regModel(((BlockShortDoor)doorNormal).getDoorItem(), 0, doorNormal.getRegistryName());
		ModelHelper.regModel(((BlockTallDoor)doorTall).getDoorItem(), 0, doorTall.getRegistryName());
		
	}
	
	@Override
	public void init() {
		((BlockMultiVariant<EnumCarvedType>)blockCarved).addChiselVariation("dwemer");
		((BlockMultiVariant<EnumLightType>)lightNormal).addChiselVariation("dwemerlight");
		((BlockMultiVariant<EnumGlassType>)glassNormal).addChiselVariation("glass");
		
		//Add chisel variations for Dwemer Bars
		for(BlockDwarvenBars.EnumType type: BlockDwarvenBars.EnumType.values()) {
			addChiselVariation("dwemerbars", barsNormal, type.getMetadata());
		}
	}

	@Override
	public void postInit() {}
}
