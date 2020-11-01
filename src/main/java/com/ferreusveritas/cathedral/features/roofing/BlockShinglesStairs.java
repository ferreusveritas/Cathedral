package com.ferreusveritas.cathedral.features.roofing;

import com.ferreusveritas.cathedral.CathedralMod;

import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockShinglesStairs extends BlockStairs {

	EnumDyeColor color;
	
	public BlockShinglesStairs(EnumDyeColor color, String name) {
		super(Blocks.STONE_STAIRS.getDefaultState());
		setRegistryName(name);
		setUnlocalizedName(name);
		this.useNeighborBrightness = true;
		//setDefaultState(getDefaultState().withProperty(FORM, EnumForm.STAIRS));
		this.color = color;
		setCreativeTab(CathedralMod.roofing.tabRoofing);
	}

	@Override
	protected BlockStateContainer createBlockState() {
        //return new BlockStateContainer(this, new IProperty[] {FORM, FACING, HALF, SHAPE});
        return new BlockStateContainer(this, new IProperty[] {FACING, HALF, SHAPE});
	}
	
	/** Convert the given metadata into a BlockState for this Block */
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return super.getStateFromMeta(meta);
	}
	
	/** Convert the BlockState into the correct metadata value */
	@Override
	public int getMetaFromState(IBlockState state) {
		return super.getMetaFromState(state);
	}

	@Override
	public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, EntityLiving.SpawnPlacementType type) {
		return false;
	}

	@Override
	public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		
		if(color != null) {
			return MapColor.BLOCK_COLORS[color.getMetadata()];
		}
		
		return MapColor.RED_STAINED_HARDENED_CLAY;
	}
	
}
