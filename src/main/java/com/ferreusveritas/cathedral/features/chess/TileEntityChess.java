package com.ferreusveritas.cathedral.features.chess;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants.NBT;

public class TileEntityChess extends TileEntity {
	
	public final String PIECES = "pieces";
	
	private ChessPieceData pieces;
	
	public TileEntityChess() {
		pieces = new ChessPieceData();
	}
	
	public void setPiece(int square, ChessPiece piece) {
		if(pieces.setPiece(square, piece)) {
			markDirty();
		}
	}
	
	public ChessPiece getPiece(int square) {
		return pieces.getPiece(square);
	}
	
	public ChessPieceData getChessPieceData() {
		return pieces;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		readExtraData(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		return writeExtraData(compound);
	}
	
	public void readExtraData(NBTTagCompound compound) {
		
		pieces.setEmpty();
		
		if(compound.hasKey(PIECES, NBT.TAG_INT)) {
			int pieceData = compound.getInteger(PIECES);
			pieces.setFromRaw(pieceData);
		}
	}
	
	public NBTTagCompound writeExtraData(NBTTagCompound compound) {
		compound.setInteger(PIECES, pieces.toRaw());
		return compound;
	}
	
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(pos, 0, writeToNBT(new NBTTagCompound()));
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        this.readFromNBT(pkt.getNbtCompound());
	}
	
	@Override
	public NBTTagCompound getUpdateTag() {
		return writeExtraData(super.getUpdateTag());
	}
	
	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		readExtraData(tag);
		super.handleUpdateTag(tag);
	}
	
}
