package com.ferreusveritas.stonelore.gargoyle;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class EntityGargoyle extends TileEntity {

	private int direction = 0;
	private int material = 0;

	public int getDirection(){
		return direction;
	}

	public void setDirection(int dir){
		this.direction = dir;
	}

	public int getMaterial(){
		return material;
	}

	public void setMaterial(int material){
		this.material = material;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag){
		super.readFromNBT(tag);

		direction = tag.getInteger("direction");
		material = tag.getInteger("material");
	}

	@Override
	public void writeToNBT(NBTTagCompound tag){
		super.writeToNBT(tag);

		tag.setInteger("direction", direction);
		tag.setInteger("material", material);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet){
		this.readFromNBT(packet.func_148857_g());
	}

	@Override
	public Packet getDescriptionPacket(){
		NBTTagCompound tag = new NBTTagCompound();

		this.writeToNBT(tag);

		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, tag);
	}

}
