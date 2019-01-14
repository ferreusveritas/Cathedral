package com.ferreusveritas.cathedral.proxy;


import com.ferreusveritas.cathedral.features.cathedral.ModelBakeEventListener;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {

	@Override
	public void preInit() {
		registerClientEventHandlers();
	}

	public void registerClientEventHandlers() {
		MinecraftForge.EVENT_BUS.register(new ModelBakeEventListener());
	}
	
	@Override
	public void init() {
		/*
		RenderingRegistry.registerBlockHandler(new RendererShortDoor());
		RenderingRegistry.registerBlockHandler(new RendererTallDoor());
		RenderingRegistry.registerBlockHandler(new RendererBars());
		RenderingRegistry.registerBlockHandler(new RendererChain());
		RenderingRegistry.registerBlockHandler(new RendererRoofTiles());
		RenderingRegistry.registerBlockHandler(new RendererStoneRailing());
		RenderingRegistry.registerBlockHandler(new RendererGargoyle());
		
		MinecraftForgeClient.registerItemRenderer(Dwemer.tallDoorItem, new RendererItemDoor(3, 4f/16f, "tall"));
		MinecraftForgeClient.registerItemRenderer(Dwemer.shortDoorItem, new RendererItemDoor(2, 3f/16f, "short"));
		*/
	}
	
	@Override
	public void registerModels() {}

	@Override
	public EntityPlayer getClientPlayer() {
		return Minecraft.getMinecraft().player;
	}

	@Override
	public World getClientWorld() {
		return Minecraft.getMinecraft().world;
	}
	
}
