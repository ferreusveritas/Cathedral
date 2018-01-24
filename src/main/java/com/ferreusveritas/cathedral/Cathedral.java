package com.ferreusveritas.cathedral;

import java.util.ArrayList;
import java.util.Collections;

import com.ferreusveritas.cathedral.features.IFeature;
import com.ferreusveritas.cathedral.features.basalt.Basalt;
import com.ferreusveritas.cathedral.features.dwarven.Dwemer;
import com.ferreusveritas.cathedral.features.extras.Extras;
import com.ferreusveritas.cathedral.features.gargoyle.Gargoyle;
import com.ferreusveritas.cathedral.features.marble.MarbleFixer;
import com.ferreusveritas.cathedral.proxy.CommonProxy;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.client.event.ModelRegistryEvent;
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

@Mod(modid = Cathedral.MODID, version = Cathedral.VERSION, dependencies = "required-after:chisel;required-after:ProjRed|Exploration;after:ThermalFoundation")
public class Cathedral {

	public static final String MODID = "cathedral";
	public static final String VERSION = "1.12.2-1.9.4";

	public static Basalt basalt;
	public static Dwemer dwemer;
	public static Gargoyle gargoyle;
	public static Extras extras;
	public static MarbleFixer marblefixer;
	
	public static ArrayList<IFeature> features = new ArrayList();
	
	static {
		basalt = new Basalt();
		dwemer = new Dwemer();
		gargoyle = new Gargoyle();
		extras = new Extras();
		marblefixer = new MarbleFixer();
		
		Collections.addAll(features, basalt, dwemer, gargoyle, extras, marblefixer);
	}
	
	@Instance(MODID)
	public static Cathedral instance;

	@SidedProxy(clientSide = "com.ferreusveritas.cathedral.proxy.ClientProxy", serverSide = "com.ferreusveritas.cathedral.proxy.CommonProxy")
	public static CommonProxy proxy;

	public static final CreativeTabs tabBasalt = new CreativeTabs("tabBasalt") {
		@Override
		public ItemStack getTabIconItem() {
			return null;
		}
	};

	public static final CreativeTabs tabCathedral = new CreativeTabs("tabCathedral") {
		@Override
		public ItemStack getTabIconItem() {
			return null;
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
		features.forEach(f -> f.init());
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
			features.forEach(f -> f.registerModels(event));
		}

		@SubscribeEvent
		public static void newRegistry(RegistryEvent.NewRegistry event) {
		}
	}
	
}