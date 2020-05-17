package com.ferreusveritas.cathedral.proxy;

import com.ferreusveritas.cathedral.ModConstants;
import com.ferreusveritas.cathedral.features.cathedral.TileEntityDeckPrism;
import com.ferreusveritas.cathedral.features.chess.TileEntityChess;
import com.ferreusveritas.cathedral.features.lectern.PacketHandler;
import com.ferreusveritas.cathedral.features.lectern.TileEntityLectern;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {

	public void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntityDeckPrism.class, new ResourceLocation(ModConstants.MODID, "deckprism"));
		GameRegistry.registerTileEntity(TileEntityLectern.class, new ResourceLocation(ModConstants.MODID, "lectern"));
		GameRegistry.registerTileEntity(TileEntityChess.class, new ResourceLocation(ModConstants.MODID, "chess"));
	}

	public void preInit() {
		PacketHandler.registerMessages("lectern");
	}

	public void init() {}

	public void registerModels() {}

	public EntityPlayer getClientPlayer() {
		return null;
	}

	public World getClientWorld() {
		return null;
	}
	
	public void openBookGui(BlockPos lecternPos) { }
	
}
