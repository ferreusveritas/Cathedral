package com.ferreusveritas.cathedral.features.cathedral;

import java.util.List;

import javax.annotation.Nullable;

import com.ferreusveritas.cathedral.CathedralMod;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.common.property.Properties;
import net.minecraftforge.common.property.Properties.PropertyAdapter;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockRailing extends Block {
	
	public static final String name = "railing";
	
	public static final PropertyAdapter<Boolean> POSTCAP = new Properties.PropertyAdapter<Boolean>(PropertyBool.create("postcap"));
	public static final PropertyAdapter<Boolean> POST = new Properties.PropertyAdapter<Boolean>(PropertyBool.create("post"));
	public static final PropertyAdapter<Boolean> NORTH = new Properties.PropertyAdapter<Boolean>(PropertyBool.create(EnumFacing.NORTH.getName()));
	public static final PropertyAdapter<Boolean> SOUTH = new Properties.PropertyAdapter<Boolean>(PropertyBool.create(EnumFacing.SOUTH.getName()));
	public static final PropertyAdapter<Boolean> WEST = new Properties.PropertyAdapter<Boolean>(PropertyBool.create(EnumFacing.WEST.getName()));
	public static final PropertyAdapter<Boolean> EAST = new Properties.PropertyAdapter<Boolean>(PropertyBool.create(EnumFacing.EAST.getName()));
	
	protected static final AxisAlignedBB[] AABB_BY_INDEX = new AxisAlignedBB[] {
			new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 1.0D, 0.75D),
			new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 1.0D, 1.0D),
			new AxisAlignedBB(0.0D, 0.0D, 0.25D, 0.75D, 1.0D, 0.75D),
			new AxisAlignedBB(0.0D, 0.0D, 0.25D, 0.75D, 1.0D, 1.0D),
			new AxisAlignedBB(0.25D, 0.0D, 0.0D, 0.75D, 1.0D, 0.75D),
			new AxisAlignedBB(0.3125D, 0.0D, 0.0D, 0.6875D, 1.0D, 1.0D),
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.75D, 1.0D, 0.75D),
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.75D, 1.0D, 1.0D),
			new AxisAlignedBB(0.25D, 0.0D, 0.25D, 1.0D, 1.0D, 0.75D),
			new AxisAlignedBB(0.25D, 0.0D, 0.25D, 1.0D, 1.0D, 1.0D),
			new AxisAlignedBB(0.0D, 0.0D, 0.3125D, 1.0D, 1.0D, 0.6875D),
			new AxisAlignedBB(0.0D, 0.0D, 0.25D, 1.0D, 1.0D, 1.0D),
			new AxisAlignedBB(0.25D, 0.0D, 0.0D, 1.0D, 1.0D, 0.75D),
			new AxisAlignedBB(0.25D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D),
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.75D),
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D),
			new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 0.5D, 0.75D)
	};
	
	protected static final AxisAlignedBB[] CLIP_AABB_BY_INDEX = new AxisAlignedBB[] {
			AABB_BY_INDEX[0].setMaxY(1.5D),
			AABB_BY_INDEX[1].setMaxY(1.5D),
			AABB_BY_INDEX[2].setMaxY(1.5D),
			AABB_BY_INDEX[3].setMaxY(1.5D),
			AABB_BY_INDEX[4].setMaxY(1.5D),
			AABB_BY_INDEX[5].setMaxY(1.5D),
			AABB_BY_INDEX[6].setMaxY(1.5D),
			AABB_BY_INDEX[7].setMaxY(1.5D),
			AABB_BY_INDEX[8].setMaxY(1.5D),
			AABB_BY_INDEX[9].setMaxY(1.5D),
			AABB_BY_INDEX[10].setMaxY(1.5D),
			AABB_BY_INDEX[11].setMaxY(1.5D),
			AABB_BY_INDEX[12].setMaxY(1.5D),
			AABB_BY_INDEX[13].setMaxY(1.5D),
			AABB_BY_INDEX[14].setMaxY(1.5D),
			AABB_BY_INDEX[15].setMaxY(1.5D),
			AABB_BY_INDEX[16]
	};
	
	public BlockRailing() {
		this(name);
	}
	
	public BlockRailing(String name) {
		super(Material.ROCK);
		setUnlocalizedName(name);
		setRegistryName(name);
		setDefaultState(this.blockState.getBaseState().withProperty(EnumMaterial.VARIANT, EnumMaterial.STONE));
		setCreativeTab(CathedralMod.tabCathedral);
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		IProperty[] listedProperties = { EnumMaterial.VARIANT };
		IUnlistedProperty[] unlistedProperties = new IUnlistedProperty[] { POST, NORTH, SOUTH, WEST, EAST, POSTCAP };
		return new ExtendedBlockState(this, listedProperties, unlistedProperties);
	}
	
	@Override
	public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {

		if (state instanceof IExtendedBlockState) {
			IExtendedBlockState retval = (IExtendedBlockState) state;
			
			retval = retval.withProperty(POST, false)
				.withProperty(NORTH, false)
				.withProperty(EAST, false)
				.withProperty(SOUTH, false)
				.withProperty(WEST, false)
				.withProperty(POSTCAP, false);
			
			boolean n = canRailConnectTo(world, pos, EnumFacing.NORTH);
			boolean e = canRailConnectTo(world, pos, EnumFacing.EAST);
			boolean s = canRailConnectTo(world, pos, EnumFacing.SOUTH);
			boolean w = canRailConnectTo(world, pos, EnumFacing.WEST);
			boolean up = canRailConnectTo(world, pos, EnumFacing.UP);
			boolean centerPole = !((n && s && !e && !w) || (!n && !s && e && w));
			
			boolean railbelow = world.getBlockState(pos.down()).getBlock() instanceof BlockRailing;
			
			if(!w && !e && !s && !n) {//There's no connections horizontally
				if(up) {
					return retval.withProperty(POST, Boolean.valueOf(true));
				}
				if(railbelow) { //If there's a rail below then
					return retval.withProperty(POSTCAP, true);//Make a post cap and that's it
				}
			}
			
			return retval
					.withProperty(POST, centerPole || up)
					.withProperty(NORTH, n)
					.withProperty(EAST, e)
					.withProperty(SOUTH, s)
					.withProperty(WEST, w)
					.withProperty(POSTCAP, false);
			
		}
		
		return state;
	}
	
	private boolean canConnectTo(IBlockAccess world, BlockPos pos, EnumFacing facing) {
		IBlockState iblockstate = world.getBlockState(pos);
		Block block = iblockstate.getBlock();
		BlockFaceShape blockfaceshape = iblockstate.getBlockFaceShape(world, pos, facing);
		boolean flag = blockfaceshape == BlockFaceShape.MIDDLE_POLE_THICK || blockfaceshape == BlockFaceShape.MIDDLE_POLE && block instanceof BlockFenceGate;
		return !isExceptionBlockForAttachWithPiston(block) && blockfaceshape == BlockFaceShape.SOLID || flag;
	}
	
	protected static boolean isExceptionBlockForAttachWithPiston(Block block) {
		return Block.isExceptBlockForAttachWithPiston(block)
				|| block == Blocks.BARRIER
				|| block == Blocks.MELON_BLOCK
				|| block == Blocks.PUMPKIN
				|| block == Blocks.LIT_PUMPKIN;
	}
	
	@Override
	public boolean canBeConnectedTo(IBlockAccess world, BlockPos pos, EnumFacing facing) {
		Block connector = world.getBlockState(pos.offset(facing)).getBlock();
		return connector instanceof BlockWall || connector instanceof BlockFenceGate || connector instanceof BlockRailing;
	}
	
	private boolean canRailConnectTo(IBlockAccess world, BlockPos pos, EnumFacing facing) {
		BlockPos other = pos.offset(facing);
		Block block = world.getBlockState(other).getBlock();
		return block.canBeConnectedTo(world, other, facing.getOpposite()) || canConnectTo(world, other, facing.getOpposite());
	}
	
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing face) {
		return face != EnumFacing.UP && face != EnumFacing.DOWN ? BlockFaceShape.MIDDLE_POLE_THICK : BlockFaceShape.CENTER_BIG;
	}
	
	/** Convert the given metadata into a BlockState for this Block */
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(EnumMaterial.VARIANT, EnumMaterial.byMetadata(meta));
	}
	
	/** Convert the BlockState into the correct metadata value */
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(EnumMaterial.VARIANT).getMetadata();
	}
	
	@Override
	public int damageDropped(IBlockState state) {
		return state.getValue(EnumMaterial.VARIANT).getMetadata();
	}
	
	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		for(EnumMaterial type : EnumMaterial.values()) {
			items.add(new ItemStack(this, 1, type.getMetadata()));
		}
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
		return false;
	}
	
	/** Used to determine ambient occlusion and culling when rebuilding chunks for render */
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState state, IBlockAccess access, BlockPos pos, EnumFacing side) {
		return side == EnumFacing.DOWN ? super.shouldSideBeRendered(state, access, pos, side) : true;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess access, BlockPos pos) {
		return AABB_BY_INDEX[getAABBIndex((IExtendedBlockState) getExtendedState(state, access, pos))];
	}
	
	@Override
	public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
		addCollisionBoxToList(pos, entityBox, collidingBoxes, CLIP_AABB_BY_INDEX[getAABBIndex((IExtendedBlockState) getExtendedState(state, world, pos))]);
	}
	
	@Override
	@Nullable
	public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess access, BlockPos pos) {
		return CLIP_AABB_BY_INDEX[getAABBIndex((IExtendedBlockState) getExtendedState(state, access, pos))];
	}
	
	private static int getAABBIndex(IExtendedBlockState state) {
		int i = 0;
		
		if (state.getValue(POSTCAP).booleanValue()) {
			return 16;
		}
		
		if (state.getValue(NORTH).booleanValue()) {
			i |= 1 << EnumFacing.NORTH.getHorizontalIndex();
		}
		
		if (state.getValue(EAST).booleanValue()) {
			i |= 1 << EnumFacing.EAST.getHorizontalIndex();
		}
		
		if (state.getValue(SOUTH).booleanValue()) {
			i |= 1 << EnumFacing.SOUTH.getHorizontalIndex();
		}
		
		if (state.getValue(WEST).booleanValue()) {
			i |= 1 << EnumFacing.WEST.getHorizontalIndex();
		}
		
		return i;
	}
	
	@Override
	public float getBlockHardness(IBlockState blockState, World world, BlockPos pos) {
		return blockState.getValue(EnumMaterial.VARIANT).getHardness();
	}
	
	@Override
	public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion) {
		return world.getBlockState(pos).getValue(EnumMaterial.VARIANT).getExplosionResistance(exploder);
	}
	
}
