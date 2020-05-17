package com.ferreusveritas.cathedral.features.chess;

import com.ferreusveritas.cathedral.ModConstants;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;

public class BlockChess extends Block implements ITileEntityProvider {

	public static final AxisAlignedBB FLAT = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1 / 32.0, 1.0);
	
	public final String name = "chess";
	
	public BlockChess() {
		super(Material.ROCK);
		setHardness(2.5f);
		setResistance(10.0f);
		setRegistryName(new ResourceLocation(ModConstants.MODID, name));
		setUnlocalizedName(name);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityChess();
	}

	public TileEntityChess getChessEntity(World world, BlockPos pos) {
		TileEntity tileEntity = world.getTileEntity(pos);
		if(tileEntity instanceof TileEntityChess) {
			return (TileEntityChess) tileEntity;
		}
		
		return null;
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new ExtendedBlockState(this, new IProperty[0], new IUnlistedProperty[] { ChessPiecesProperty.CHESSPIECES });
	}
	
	@Override
	public IBlockState getExtendedState(IBlockState state, IBlockAccess access, BlockPos pos) {
		return state instanceof IExtendedBlockState ? 
			((IExtendedBlockState)state).withProperty(ChessPiecesProperty.CHESSPIECES, getChessPieceData(access, pos)) : state;
	}
	
	private ChessPieceData getChessPieceData(IBlockAccess access, BlockPos pos) {
		TileEntity tileEntity = access.getTileEntity(pos);
		if(tileEntity instanceof TileEntityChess) {
			TileEntityChess chessEntity = (TileEntityChess) tileEntity;
			return chessEntity.getChessPieceData();
		}
		
		return null;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess access, BlockPos pos) {
		return FLAT;
	}
	
	@Override
	public int getLightOpacity(IBlockState state, IBlockAccess world, BlockPos pos) {
		return 0;
	}
	
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}
		
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
		
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
}
