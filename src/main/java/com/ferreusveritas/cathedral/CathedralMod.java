package com.ferreusveritas.cathedral;

import java.util.ArrayList;

import com.ferreusveritas.cathedral.features.IFeature;
import com.ferreusveritas.cathedral.features.basalt.Basalt;
import com.ferreusveritas.cathedral.features.cathedral.Cathedral;
import com.ferreusveritas.cathedral.features.chess.Chess;
import com.ferreusveritas.cathedral.features.dwarven.Dwarven;
import com.ferreusveritas.cathedral.features.extras.Extras;
import com.ferreusveritas.cathedral.features.lectern.Lectern;
import com.ferreusveritas.cathedral.features.roofing.Roofing;
import com.ferreusveritas.cathedral.proxy.CommonProxy;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.config.Config;
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

@Mod(modid = ModConstants.MODID, version = ModConstants.VERSION, dependencies = "required-after:chisel;after:thermalfoundation;after:quark;after:patchouli")
public class CathedralMod {
	
	public static Cathedral cathedral;
	public static Basalt basalt;
	public static Dwarven dwarven;
	public static Extras extras;
	public static Roofing roofing;
	public static Lectern lectern;
	public static Chess chess;
	
	public static ArrayList<IFeature> features = new ArrayList();
	
	public final static float basaltHardness = 2.5f;
	public final static float basaltResistance = 20f;
	public final static float marbleHardness = 2.0f;
	public final static float marbleResistance = 10f;
	
	static {
		cathedral = CathedralConfig.cathedralFeature ? new Cathedral() : null;
		basalt = CathedralConfig.cathedralFeature ? new Basalt() : null;
		dwarven = CathedralConfig.cathedralFeature ? new Dwarven() : null;
		extras = CathedralConfig.extrasFeature ? new Extras() : null;
		roofing = CathedralConfig.roofingFeature ? new Roofing() : null;
		lectern = CathedralConfig.lecternFeature ? new Lectern() : null;
		chess = CathedralConfig.chessFeature ? new Chess() : null;
		
		addFeatures(
				cathedral,
				basalt,
				dwarven,
				extras,
				roofing,
				lectern,
				chess
			);
		
	}
	
	private static void addFeatures(IFeature ... featuresToAdd) {
		for(IFeature feature : featuresToAdd) {
			if(feature != null) {
				features.add(feature);
			}
		}
	}
	
	@Instance(ModConstants.MODID)
	public static CathedralMod instance;
	
	@SidedProxy(clientSide = "com.ferreusveritas.cathedral.proxy.ClientProxy", serverSide = "com.ferreusveritas.cathedral.proxy.CommonProxy")
	public static CommonProxy proxy;
	
	public static boolean usesCathedralItems() {
		return 
				CathedralConfig.cathedralFeature ||
				CathedralConfig.basaltFeature ||
				CathedralConfig.extrasFeature ||
				CathedralConfig.lecternFeature ||
				CathedralConfig.chessFeature;
	}
	
	public static final CreativeTabs tabCathedral = usesCathedralItems() ? new CreativeTabs("tabCathedral") {
		@SideOnly(Side.CLIENT)
		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(cathedral.railingVarious);
		}
	} : null;
	
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
		features.forEach(f -> f.registerColorHandlers());
		
		proxy.init();
		proxy.registerTileEntities();
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
	
	@Config(modid=ModConstants.MODID)
	public static class CathedralConfig{
		
		@Config.Name("Cathedral Blocks")
		@Config.Comment("Railing, Gargoyles, Chains, Stained Glass, Deck Prisms, Pillars")
		public static boolean cathedralFeature = true;
		
		@Config.Name("Basalt Blocks")
		@Config.Comment("Basalt blocks, Checkered Marble and Basalt blocks")
		public static boolean basaltFeature = true;
		
		@Config.Name("Dwarven Blocks")
		@Config.Comment("Dwarven Style Blocks, Dwarven Doors, Dwarven Bars")
		public static boolean dwarvenFeature = true;
		
		@Config.Name("Extra Blocks")
		@Config.Comment("Extra Stone Blocks, Grass O' Lanterns, Various Slabs")
		public static boolean extrasFeature = true;
		
		@Config.Name("Roofing Blocks")
		@Config.Comment("Teracotta tile roofing blocks")
		public static boolean roofingFeature = true;
		
		@Config.Name("Lectern")
		@Config.Comment("A lectern that provides a lockable book stand that can be read in place")
		public static boolean lecternFeature = true;
		
		@Config.Name("Chess")
		@Config.Comment("Chess piece blocks(available with setblock command only)")
		public static boolean chessFeature = true;
		
	}
	
}