package com.ferreusveritas.stonelore;

/*Bugs:
 * 
 * Basalt slab recipe doesn't include regular basalt 
 * 
 */


import com.ferreusveritas.stonelore.basalt.Basalt;
import com.ferreusveritas.stonelore.dwemer.Dwemer;
import com.ferreusveritas.stonelore.extras.Extras;
import com.ferreusveritas.stonelore.gargoyle.Gargoyle;
import com.ferreusveritas.stonelore.proxy.CommonProxy;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.item.ItemStack;

@Mod(modid = StoneLore.MODID, version = StoneLore.VERSION, dependencies = "required-after:chisel;required-after:ProjRed|Exploration;after:ThermalFoundation")
public class StoneLore {

	public static final String MODID = "basalt";
	public static final String VERSION = "1.7.10-1.9.4";

	@Instance(MODID)
	public static StoneLore instance;

	@SidedProxy(clientSide = "com.ferreusveritas.stonelore.proxy.ClientProxy", serverSide = "com.ferreusveritas.stonelore.proxy.CommonProxy")
	public static CommonProxy proxy;

	public static final BasaltTab tabBasalt = new BasaltTab("tabBasalt");
	public static final StoneLoreTab tabStoneLore = new StoneLoreTab("tabStoneLore");

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
		tabStoneLore.setTabIconItemStack(new ItemStack(Extras.stoneRailingBlock, 1, 1));
	}

	@EventHandler
	public void PostInit(FMLPostInitializationEvent e){
		//Handle interaction with other mods, complete your setup based on this.
	}

}