package com.ferreusveritas.cathedral.features.cathedral;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class TileEntityDeckPrism extends TileEntity {
	
	private IBlockState baseBlock;
	private EnumDyeColor glassColor;//Can be null for clear glass
	
	public TileEntityDeckPrism() {
		baseBlock = Blocks.STONE.getDefaultState();
		glassColor = null;//Clear glass
	}
	
	public EnumDyeColor getGlassColor() {
		return glassColor;
	}
	
	public void setGlassColor(EnumDyeColor color) {
		this.glassColor = color;
	}
	
	public IBlockState getBaseBlock() {
		return baseBlock;
	}
	
	public void setBaseBlock(IBlockState baseBlock) {
		this.baseBlock = baseBlock;;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		if(compound.hasKey("color")) {
			setGlassColor(EnumDyeColor.byMetadata(compound.getInteger("color")));
		}
		
		Block block = Block.REGISTRY.getObject(new ResourceLocation(compound.getString("blockname")));
		if(block != Blocks.AIR) {
			setBaseBlock(block.getStateFromMeta(compound.getInteger("blockmeta")));
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		
		IBlockState state = getBaseBlock();
		String blockName = Block.REGISTRY.getNameForObject(state.getBlock()).toString();
		int blockMeta = state.getBlock().getMetaFromState(state);
		
		if(getGlassColor() != null) {
			compound.setInteger("color", getGlassColor().getMetadata());
		}
		
		compound.setString("blockname", blockName);
		compound.setInteger("blockmeta", blockMeta);
		
		return compound;
	}
	
}