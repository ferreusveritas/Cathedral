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
		super.init();
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
