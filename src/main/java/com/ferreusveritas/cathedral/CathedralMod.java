package com.ferreusveritas.cathedral;

import java.util.ArrayList;
import java.util.Collections;

import com.ferreusveritas.cathedral.features.IFeature;
import com.ferreusveritas.cathedral.features.basalt.Basalt;
import com.ferreusveritas.cathedral.features.basalt.BlockBasalt;
import com.ferreusveritas.cathedral.features.cathedral.Cathedral;
import com.ferreusveritas.cathedral.features.dwemer.BlockDwemer;
import com.ferreusveritas.cathedral.features.dwemer.Dwemer;
import com.ferreusveritas.cathedral.features.extras.Extras;
import com.ferreusveritas.cathedral.features.roofing.Roofing;
import com.ferreusveritas.cathedral.proxy.CommonProxy;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = ModConstants.MODID, version = ModConstants.VERSION, dependencies = "after:chisel;after:thermalfoundation")
public class CathedralMod {

	public static Cathedral cathedral;
	public static Basalt basalt;
	public static Dwemer dwemer;
	public static Extras extras;
	public static Roofing roofing;
	
	public static ArrayList<IFeature> features = new ArrayList();
	
	static {
		cathedral = new Cathedral();
		basalt = new Basalt();
		dwemer = new Dwemer();
		extras = new Extras();
		roofing = new Roofing();
		
		Collections.addAll(features,
			cathedral,
			basalt,
			dwemer,
			extras,
			roofing
		);
		
	}
	
	@Instance(ModConstants.MODID)
	public static CathedralMod instance;
	
	@SidedProxy(clientSide = "com.ferreusveritas.cathedral.proxy.ClientProxy", serverSide = "com.ferreusveritas.cathedral.proxy.CommonProxy")
	public static CommonProxy proxy;
	
	public static final CreativeTabs tabBasalt = new CreativeTabs("tabBasalt") {		
        @SideOnly(Side.CLIENT)
		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(basalt.blockCarved, 1, BlockBasalt.EnumType.POISON.getMetadata());
		}
	};
	
	public static final CreativeTabs tabCathedral = new CreativeTabs("tabCathedral") {
        @SideOnly(Side.CLIENT)
		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(cathedral.railingVarious);
		}
	};
	
	public static final CreativeTabs tabDwemer = new CreativeTabs("tabDwemer") {
		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(dwemer.blockCarved, 1, BlockDwemer.EnumType.EMBEDDED.getMetadata());
		}
	};
	
	public static final CreativeTabs tabRoofing = new CreativeTabs("tabRoofing") {
		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(roofing.roofingShinglesNatural, 1);
		}
	};
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent e){
		features.forEach(f -> f.preInit());
		features.forEach(f -> f.createBlocks());
		features.forEach(f -> f.createItems());
		
		proxy.preInit();
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event){
		features.forEach(f -> f.init());
		
		proxy.init();
	}
	
	@Mod.EventHandler
	public void PostInit(FMLPostInitializationEvent e){
		features.forEach(f -> f.postInit());
	}
	
	@Mod.EventBusSubscriber
	public static class RegistrationHandler {
		
		@SubscribeEvent
		public static void registerBlocks(RegistryEvent.Register<Block> event) {
			features.forEach(f -> f.registerBlocks(event.getRegistry()));
		}
		
		@SubscribeEvent
		public static void registerItems(RegistryEvent.Register<Item> event) {
			features.forEach(f -> f.registerItems(event.getRegistry()));
		}
		
		@SubscribeEvent
		public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
			features.forEach(f -> f.registerRecipes(event.getRegistry()));
		}
		
		@SubscribeEvent
		@SideOnly(Side.CLIENT)
		public static void registerModels(ModelRegistryEvent event) {
			OBJLoader.INSTANCE.addDomain(ModConstants.MODID);//Required for obj models to load
			features.forEach(f -> f.registerModels(event));
		}
		
		@SubscribeEvent
		public static void newRegistry(RegistryEvent.NewRegistry event) {
		}
	}
	
}