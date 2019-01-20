package com.ferreusveritas.cathedral.features.cathedral;

import com.ferreusveritas.cathedral.common.blocks.MimicProperty.IMimicProvider;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class TileEntityDeckPrism extends TileEntity implements IMimicProvider {
	
	private IBlockState baseBlock;
	private EnumDyeColor glassColor;//Can be null for clear glass
	
	public TileEntityDeckPrism() {
		baseBlock = Blocks.STONE.getDefaultState();
		glassColor = EnumDyeColor.WHITE;
	}
	
	public EnumDyeColor getGlassColor() {
		return glassColor;
	}
	
	public void setGlassColor(EnumDyeColor color) {
		this.glassColor = color;
	}
	
	@Override
	public IBlockState getBaseBlock() {
		return baseBlock;
	}
	
	public void setBaseBlock(IBlockState baseBlock) {
		this.baseBlock = baseBlock;
		markDirty();
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
		setGlassColor(EnumDyeColor.byMetadata(compound.getInteger("color")));
		
		Block block = Block.REGISTRY.getObject(new ResourceLocation(compound.getString("blockname")));
		if(block != Blocks.AIR) {
			setBaseBlock(block.getStateFromMeta(compound.getInteger("blockmeta")));
		}
	}
	
	public NBTTagCompound writeExtraData(NBTTagCompound compound) {
		IBlockState state = getBaseBlock();
		String blockName = Block.REGISTRY.getNameForObject(state.getBlock()).toString();
		int blockMeta = state.getBlock().getMetaFromState(state);
		
		compound.setInteger("color", getGlassColor().getMetadata());
		compound.setString("blockname", blockName);
		compound.setInteger("blockmeta", blockMeta);
		
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