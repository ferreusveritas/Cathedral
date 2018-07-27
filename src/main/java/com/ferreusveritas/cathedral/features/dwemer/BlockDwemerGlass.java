package com.ferreusveritas.cathedral.features.dwemer;

import java.util.Random;

import com.ferreusveritas.cathedral.CathedralMod;
import com.ferreusveritas.cathedral.common.blocks.BlockMultiVariant;
import com.ferreusveritas.cathedral.features.dwemer.FeatureTypes.EnumGlassType;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockDwemerGlass extends BlockMultiVariant<EnumGlassType> {

	public BlockDwemerGlass(String name) {
		super(Material.GLASS, EnumGlassType.class, name);
		setHardness(0.3F); 
		setSoundType(SoundType.GLASS);
		setCreativeTab(CathedralMod.tabDwemer);
	}
	
	@Override
	public void makeVariantProperty() {
		variant = PropertyEnum.<EnumGlassType>create("variant", EnumGlassType.class);
	}
	
	@Override
	public int quantityDropped(Random random) {
		return 0;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        IBlockState iblockstate = blockAccess.getBlockState(pos.offset(side));
        Block block = iblockstate.getBlock();
        
        if (blockState != iblockstate) return true;
        if (block == this) return false;
        
        return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
    }
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
    protected boolean canSilkHarvest() {
		return true;
	}

}
