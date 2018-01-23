package com.ferreusveritas.cathedral.proxy;

import com.ferreusveritas.cathedral.features.dwarven.Dwemer;
import com.ferreusveritas.cathedral.renderers.RendererBars;
import com.ferreusveritas.cathedral.renderers.RendererChain;
import com.ferreusveritas.cathedral.renderers.RendererGargoyle;
import com.ferreusveritas.cathedral.renderers.RendererItemDoor;
import com.ferreusveritas.cathedral.renderers.RendererRoofTiles;
import com.ferreusveritas.cathedral.renderers.RendererShortDoor;
import com.ferreusveritas.cathedral.renderers.RendererStoneRailing;
import com.ferreusveritas.cathedral.renderers.RendererTallDoor;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy {

	@Override
	public void preInit() {
	}

	@Override
	public void init() {
		RenderingRegistry.registerBlockHandler(new RendererShortDoor());
		RenderingRegistry.registerBlockHandler(new RendererTallDoor());
		RenderingRegistry.registerBlockHandler(new RendererBars());
		RenderingRegistry.registerBlockHandler(new RendererChain());
		RenderingRegistry.registerBlockHandler(new RendererRoofTiles());
		RenderingRegistry.registerBlockHandler(new RendererStoneRailing());
		RenderingRegistry.registerBlockHandler(new RendererGargoyle());
		
		MinecraftForgeClient.registerItemRenderer(Dwemer.tallDoorItem, new RendererItemDoor(3, 4f/16f, "tall"));
		MinecraftForgeClient.registerItemRenderer(Dwemer.shortDoorItem, new RendererItemDoor(2, 3f/16f, "short"));
	}
	

	@Override
	public EntityPlayer getClientPlayer() {
		return Minecraft.getMinecraft().thePlayer;
	}

	@Override
	public World getClientWorld() {
		return Minecraft.getMinecraft().theWorld;
	}
}
