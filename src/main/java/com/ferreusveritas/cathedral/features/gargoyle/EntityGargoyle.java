package com.ferreusveritas.cathedral.features.gargoyle;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
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

	private void read(NBTTagCompound tag) {
		if(tag.hasKey("species")) {
			direction = tag.getInteger("direction");
			material = tag.getInteger("material");
		}
	}
	
	private void write(NBTTagCompound tag) {
		tag.setInteger("direction", direction);
		tag.setInteger("material", material);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag){
		super.readFromNBT(tag);
		read(tag);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag){
		super.writeToNBT(tag);
		write(tag);
		return tag;
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound syncData = new NBTTagCompound();
		this.write(syncData);
		return new SPacketUpdateTileEntity(this.pos, 1, syncData);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		read(pkt.getNbtCompound());
	}
	
	//Packages up the data on the server to send to the client.  Client handles it with handleUpdateTag() which reads it with readFromNBT()
	public NBTTagCompound getUpdateTag() {
		return this.writeToNBT(new NBTTagCompound());
	}
	
}
