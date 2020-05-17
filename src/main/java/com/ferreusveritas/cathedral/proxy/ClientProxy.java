package com.ferreusveritas.cathedral.proxy;

import com.ferreusveritas.cathedral.features.cathedral.ModelBakeEventListener;
import com.ferreusveritas.cathedral.features.lectern.TileEntityLectern;
import com.ferreusveritas.cathedral.features.lectern.TileEntityLecternRenderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreenBook;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemWrittenBook;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy {

	@Override
	public void preInit() {
		super.preInit();
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
	public void registerTileEntities() {
		super.registerTileEntities();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLectern.class, new TileEntityLecternRenderer());
	}

	@Override
	public void registerModels() {
		super.registerModels();
	}

	@Override
	public EntityPlayer getClientPlayer() {
		return Minecraft.getMinecraft().player;
	}

	@Override
	public World getClientWorld() {
		return Minecraft.getMinecraft().world;
	}

	public void openBookGui(BlockPos lecternPos) {
		World world = getClientWorld();
		TileEntity tileEntity = world.getTileEntity(lecternPos);

		if(tileEntity instanceof TileEntityLectern) {
			TileEntityLectern lecternEntity = (TileEntityLectern) tileEntity;
			ItemStack bookStack = lecternEntity.getBook();
			if(bookStack.getItem() instanceof ItemWrittenBook) {
				Minecraft.getMinecraft().displayGuiScreen(new GuiScreenBook(getClientPlayer(), bookStack, false));
			}
		}
	}

}
