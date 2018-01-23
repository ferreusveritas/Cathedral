package com.ferreusveritas.cathedral;

import com.ferreusveritas.cathedral.features.basalt.Basalt;
import com.ferreusveritas.cathedral.features.dwarven.Dwemer;
import com.ferreusveritas.cathedral.features.extras.Extras;
import com.ferreusveritas.cathedral.features.gargoyle.Gargoyle;
import com.ferreusveritas.cathedral.proxy.CommonProxy;

import net.minecraft.creativetab.CreativeTabs;

/*Bugs:
 * 
 * Basalt slab recipe doesn't include regular basalt 
 * 
 */



import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Cathedral.MODID, version = Cathedral.VERSION, dependencies = "required-after:chisel;required-after:ProjRed|Exploration;after:ThermalFoundation")
public class Cathedral {

	public static final String MODID = "cathedral";
	public static final String VERSION = "1.12.2-1.9.4";

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
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e){
		//Run before anything else. Read your config, create blocks, items, etc, and register them with the GameRegistry.
		Basalt.preInit(this);
		Dwemer.preInit(this);
		Extras.preInit(this);
		Gargoyle.preInit(this);
		proxy.preInit();
	}

	@EventHandler
	public void init(FMLInitializationEvent event){
		//Do your mod setup. Build whatever data structures you care about. Register recipes.
		Basalt.init(this);
		Dwemer.init(this);
		MarbleFixer.init();
		Extras.init(this);
		Gargoyle.init(this);
		proxy.init();

		tabBasalt.setTabIconItemStack(new ItemStack(Basalt.basaltBlock, 1, 4));
		tabCathedral.setTabIconItemStack(new ItemStack(Extras.stoneRailingBlock, 1, 1));
	}

	@EventHandler
	public void PostInit(FMLPostInitializationEvent e){
		//Handle interaction with other mods, complete your setup based on this.
	}

}