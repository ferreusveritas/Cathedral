package com.ferreusveritas.cathedral.features.cathedral;

import com.ferreusveritas.cathedral.ModConstants;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.registries.IForgeRegistry;

public class CathedralRecipes {

	private Cathedral cathedral;
	
	public CathedralRecipes(Cathedral cathedral) {
		this.cathedral = cathedral;
	}
	
	public void registerRecipes(IForgeRegistry<IRecipe> registry) {
		
		//Stained Glass
		registry.register(
			new ShapedOreRecipe(
				null,
				new ItemStack(cathedral.glassStained, 16, 0),
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

		//Stained Glass Panes
		for(BlockGlassStained.EnumType type : BlockGlassStained.EnumType.values() ) {
			GameRegistry.addShapedRecipe(
				new ResourceLocation(ModConstants.MODID, "pane_stained_" + type.getName()),//Name
				null,//Group
				new ItemStack(cathedral.panesStained, 16, type.getMetadata()),//Output
				"ggg",
				"ggg",
				'g', new ItemStack(cathedral.glassStained, 1, type.getMetadata())
			);
		}
		
		//Stone pillars
		for(EnumMaterial type : EnumMaterial.values() ) {
			GameRegistry.addShapedRecipe(
				new ResourceLocation(ModConstants.MODID, "pillar_" + type.getName()),//Name
				null,//Group
				new ItemStack(cathedral.pillarVarious, 4, type.getMetadata()),//Output
				"s",
				"s",
				"s",
				's', type.getRawMaterialBlock()
			);
		}
		
		//Stone Railings
		for(EnumMaterial material : EnumMaterial.values()) {
			railRecipe(material);
		}
		
		//Chains
		for(BlockChain.EnumType type: BlockChain.EnumType.values()) {
			chainRecipe(type, registry);
		}

		//Deck Prisms
		for(EnumDyeColor color: EnumDyeColor.values()) {
			deckPrismRecipe(color, registry);
		}
		
		//Allow exchange for BRONZE -> DWEMER(or GOLD -> DWEMER) chain in cases where Dwemer and Dawnstone aren't available
		if(!OreDictionary.doesOreNameExist("nuggetDwemer") && !OreDictionary.doesOreNameExist("nuggetDawnstone")){
			GameRegistry.addShapelessRecipe(
				new ResourceLocation(ModConstants.MODID, "chaindwemer"),
				null,// Group
				new ItemStack(cathedral.chainVarious, 1, BlockChain.EnumType.DWEMER.getMetadata()),// Output
				new Ingredient[] { 
					Ingredient.fromStacks(new ItemStack(cathedral.chainVarious, 1, 
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
	
	public void railRecipe(EnumMaterial type) {
		ItemStack input = type.getRawMaterialBlock();
		if(input.getItem() instanceof ItemBlock && ((ItemBlock)input.getItem()).getBlock() != Blocks.AIR) {
			GameRegistry.addShapedRecipe(
					new ResourceLocation(ModConstants.MODID, "railing_" + type.getName()),//Name
					null,//Group
					new ItemStack(cathedral.railingVarious, 8, type.getMetadata()),//Output
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
					new ItemStack(cathedral.gargoyleDemon[type.ordinal()]),//Output
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
					new ItemStack(cathedral.chainVarious, 4, type.getMetadata()),
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
	
	public void deckPrismRecipe(EnumDyeColor color, IForgeRegistry<IRecipe> registry) {
		
        String[] dyes = { "Black", "Red", "Green", "Brown", "Blue", "Purple", "Cyan", "LightGray", "Gray", "Pink", "Lime", "Yellow", "LightBlue", "Magenta", "Orange", "White" };
		
		String oreName = "blockGlass" + dyes[color.getDyeDamage()];
		if(OreDictionary.doesOreNameExist(oreName)){
			registry.register( 
				new ShapedOreRecipe(
					null,
					new ItemStack(cathedral.deckPrism, 4, color.getMetadata()),
					new Object[]{
						"ooo",
						" o ",
						'o', oreName
					}
				).setRegistryName("deckPrism_" + color.getName())
			);
		}
	}
}
