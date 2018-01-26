package com.ferreusveritas.cathedral.features.dwemer;

import com.ferreusveritas.cathedral.Cathedral;
import com.ferreusveritas.cathedral.features.IFeature;
import com.ferreusveritas.cathedral.features.extras.BlockCatwalk;

import net.minecraft.block.Block;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

public class Dwemer implements IFeature {

	public static final String featureName = "dwemer";
	
	public BlockBars dwemerBars;

	public Block dwemerCatwalkBlock;

	public BlockShortDoor shortDoorBlock;
	public ItemShortDoor shortDoorItem;

	public BlockTallDoor	tallDoorBlock;
	public ItemTallDoor tallDoorItem;

	public BlockDwemer dwemerBlock;
	public BlockDwemerLight dwemerLightBlock;
	public BlockGlass dwemerGlassBlock;
	
	@Override
	public String getName() {
		return featureName;
	}

	@Override
	public void preInit() {}

	@Override
	public void createBlocks() {
		dwemerBlock = new BlockDwemer();
		dwemerLightBlock = (BlockDwemerLight) new BlockDwemerLight();

		dwemerGlassBlock = (BlockGlass) new BlockGlass(Material.GLASS, false)
				.setCreativeTab(Cathedral.tabBasalt)
				//.setStepSound(SoundType.GLASS)
				.setHardness(0.3F);

		dwemerCatwalkBlock = new BlockCatwalk(Material.IRON)
				.setCreativeTab(Cathedral.tabBasalt)
				.setHardness(2.5f)
				.setResistance(20F)
				//.setStepSound(SoundType.METAL)
				.setRegistryName("dwemercatwalk");
		
		dwemerBars = new BlockBars();
		
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerBlocks(IForgeRegistry<Block> registry) {
		registry.register(dwemerBars);
	}

	@Override
	public void registerItems(IForgeRegistry<Item> registry) {

		registry.register(new ItemMultiTexture(dwemerBlock, dwemerBlock, new ItemMultiTexture.Mapper() {
            public String apply(ItemStack stack) {
                return BlockDwemer.EnumType.byMetadata(stack.getMetadata()).getUnlocalizedName();
            }
        }).setRegistryName(dwemerBlock.getRegistryName()));

		
		registry.register(new ItemMultiTexture(dwemerBars, dwemerBars, new ItemMultiTexture.Mapper() {
            public String apply(ItemStack stack) {
                return BlockBars.EnumType.byMetadata(stack.getMetadata()).getUnlocalizedName();
            }
        }).setRegistryName(dwemerBars.getRegistryName()));

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
