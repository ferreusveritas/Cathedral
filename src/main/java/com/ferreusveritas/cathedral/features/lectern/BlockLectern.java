package com.ferreusveritas.cathedral.features.lectern;

import com.ferreusveritas.cathedral.CathedralMod;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockLectern extends BlockHorizontal implements ITileEntityProvider {

	public final String name;

	public BlockLectern(String name) {
		super(Material.WOOD);
		this.name = name;
		setRegistryName(name);
		setUnlocalizedName(name);
		setHardness(2.0F);
		setResistance(5.0F);

		setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		setCreativeTab(CathedralMod.tabCathedral);
	}


	////////////////////////////////////////////////////////////////
	// BlockStates
	////////////////////////////////////////////////////////////////

	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {FACING});
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumFacing)state.getValue(FACING)).getHorizontalIndex();
	}

	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}

	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
	}

	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
	}
	

	////////////////////////////////////////////////////////////////
	// Interaction
	////////////////////////////////////////////////////////////////

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		TileEntity tileEntity = world.getTileEntity(pos);
		if(tileEntity instanceof TileEntityLectern) {
			return ((TileEntityLectern) tileEntity).onBlockActivated(world, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
		}
		
		return false;
	}

	
	////////////////////////////////////////////////////////////////
	// Tile Entity
	////////////////////////////////////////////////////////////////

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityLectern();
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		super.breakBlock(world, pos, state);
        world.removeTileEntity(pos);
	}

	
	////////////////////////////////////////////////////////////////
	// Block Shapes
	////////////////////////////////////////////////////////////////

	public boolean isFullCube(IBlockState state) {
		return false;
	}

	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return face == EnumFacing.DOWN ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
	}

}
