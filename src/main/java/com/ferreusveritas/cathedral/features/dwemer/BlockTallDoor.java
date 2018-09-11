package com.ferreusveritas.cathedral.features.dwemer;

import java.util.Random;

import com.ferreusveritas.cathedral.CathedralMod;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTallDoor extends Block {
	public static final PropertyEnum<EnumDoorThird> THIRD = PropertyEnum.<EnumDoorThird>create("third", EnumDoorThird.class);
	protected static final AxisAlignedBB SOUTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.1875D);
	protected static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.8125D, 1.0D, 1.0D, 1.0D);
	protected static final AxisAlignedBB WEST_AABB = new AxisAlignedBB(0.8125D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
	protected static final AxisAlignedBB EAST_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.1875D, 1.0D, 1.0D);
	
	ItemTallDoor doorItem;
	
	protected BlockTallDoor(Material materialIn, String name) {
		super(materialIn);
		setRegistryName(name);
		setUnlocalizedName(name);
		setCreativeTab(CathedralMod.tabDwemer);
		this.setDefaultState(this.blockState.getBaseState()
				.withProperty(BlockDoor.FACING, EnumFacing.NORTH)
				.withProperty(BlockDoor.OPEN, Boolean.valueOf(false))
				.withProperty(BlockDoor.HINGE, BlockDoor.EnumHingePosition.LEFT)
				.withProperty(BlockDoor.POWERED, Boolean.valueOf(false))
				.withProperty(THIRD, EnumDoorThird.LOWER));
	}
	
	public BlockTallDoor setDoorItem(ItemTallDoor doorItem) {
		this.doorItem = doorItem;
		return this;
	}
	
	public ItemTallDoor getDoorItem() {
		return doorItem;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		state = state.getActualState(source, pos);
		EnumFacing enumfacing = state.getValue(BlockDoor.FACING);
		boolean open = !state.getValue(BlockDoor.OPEN).booleanValue();
		boolean hingeOnRight = state.getValue(BlockDoor.HINGE) == BlockDoor.EnumHingePosition.RIGHT;
		
		switch (enumfacing) {
			default:
			case EAST:  return open ? EAST_AABB  : (hingeOnRight ? NORTH_AABB : SOUTH_AABB);
			case SOUTH: return open ? SOUTH_AABB : (hingeOnRight ? EAST_AABB  : WEST_AABB);
			case WEST:  return open ? WEST_AABB  : (hingeOnRight ? SOUTH_AABB : NORTH_AABB);
			case NORTH: return open ? NORTH_AABB : (hingeOnRight ? WEST_AABB  : EAST_AABB);
		}
	}
	
	// Gets the localized name of this block. Used for the statistics page.
	@Override
	public String getLocalizedName() {
		return I18n.translateToLocal((this.getUnlocalizedName() + ".name").replaceAll("tile", "item"));
	}
	
	// Used to determine ambient occlusion and culling when rebuilding chunks for render
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	// Determines if an entity can path through this block
	@Override
	public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
		return getActualState(worldIn.getBlockState(pos), worldIn, pos).getValue(BlockDoor.OPEN);  
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	private int getCloseSound() {
		return 1011;
	}
	
	private int getOpenSound() {
		return 1005;
	}
	
	// Get the MapColor for this Block and the given BlockState
	@Override
	public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return MapColor.GOLD;
	}
	
	
	public BlockPos getBottomPos(BlockPos pos, IBlockState state) {
		return pos.down(state.getValue(THIRD).ordinal());
	}
	
	// Called when the block is right clicked by a player.
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		BlockPos bottomPos = getBottomPos(pos, state);
		
		IBlockState iblockstate = pos.equals(bottomPos) ? state : worldIn.getBlockState(bottomPos);
		
		if (iblockstate.getBlock() != this) {
			return false;
		}
		else {
			state = iblockstate.cycleProperty(BlockDoor.OPEN);
			worldIn.setBlockState(bottomPos, state, 10);
			worldIn.markBlockRangeForRenderUpdate(bottomPos, pos);
			worldIn.playEvent(playerIn, state.getValue(BlockDoor.OPEN).booleanValue() ? this.getOpenSound() : this.getCloseSound(), pos, 0);
			return true;
		}
	}
	
	public void toggleDoor(World worldIn, BlockPos pos, boolean open) {
		IBlockState iblockstate = worldIn.getBlockState(pos);
		
		if (iblockstate.getBlock() == this) {
			BlockPos bottomPos = getBottomPos(pos, iblockstate);
			IBlockState iblockstate1 = pos == bottomPos ? iblockstate : worldIn.getBlockState(bottomPos);
			
			if (iblockstate1.getBlock() == this && iblockstate1.getValue(BlockDoor.OPEN).booleanValue() != open) {
				worldIn.setBlockState(bottomPos, iblockstate1.withProperty(BlockDoor.OPEN, Boolean.valueOf(open)), 10);
				worldIn.markBlockRangeForRenderUpdate(bottomPos, pos);
				worldIn.playEvent((EntityPlayer)null, open ? this.getOpenSound() : this.getCloseSound(), pos, 0);
			}
		}
	}
	
	/**
	 * Called when a neighboring block was changed and marks that this state should perform any checks during a neighbor
	 * change. Cases may include when redstone power is updated, cactus blocks popping off due to a neighboring solid
	 * block, etc.
	 */
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
				
		if(state.getValue(THIRD) == EnumDoorThird.LOWER) {
			boolean invalid = false;
			BlockPos lowerPos = pos;
			BlockPos middlePos = pos.up();
			BlockPos upperPos = pos.up(2);
			IBlockState lowerState = state;
			IBlockState middleState = worldIn.getBlockState(middlePos);
			IBlockState upperState = worldIn.getBlockState(upperPos);
			
			if (upperState.getBlock() != this ) {
				worldIn.setBlockToAir(middlePos);
				worldIn.setBlockToAir(lowerPos);
				invalid = true;
			}
			
			if (middleState.getBlock() != this) {
				worldIn.setBlockToAir(upperPos);
				worldIn.setBlockToAir(lowerPos);
				invalid = true;
			}
			
			if (!worldIn.getBlockState(lowerPos.down()).isSideSolid(worldIn,  lowerPos.down(), EnumFacing.UP)) {
				worldIn.setBlockToAir(lowerPos);
				invalid = true;
				
				if (middleState.getBlock() == this) {
					worldIn.setBlockToAir(middlePos);
				}
				
				if (upperState.getBlock() == this) {
					worldIn.setBlockToAir(upperPos);
				}
				
			}
			
			if (!invalid) {
				boolean isPowered = worldIn.isBlockPowered(lowerPos) || worldIn.isBlockPowered(middlePos) || worldIn.isBlockPowered(upperPos);
				
				if (blockIn != this && (isPowered || blockIn.getDefaultState().canProvidePower()) && isPowered != upperState.getValue(BlockDoor.POWERED).booleanValue()) {
					worldIn.setBlockState(upperPos, upperState.withProperty(BlockDoor.POWERED, Boolean.valueOf(isPowered)), 2);
					
					if (isPowered != lowerState.getValue(BlockDoor.OPEN).booleanValue()) {
						worldIn.setBlockState(lowerPos, lowerState.withProperty(BlockDoor.OPEN, Boolean.valueOf(isPowered)), 2);
						worldIn.markBlockRangeForRenderUpdate(lowerPos, lowerPos);
						worldIn.playEvent((EntityPlayer)null, isPowered ? this.getOpenSound() : this.getCloseSound(), lowerPos, 0);
					}
				}
			}
		} else { //Pass message down to the proper door block
			BlockPos blockpos = pos.down(state.getValue(THIRD).ordinal());
			IBlockState iblockstate = worldIn.getBlockState(blockpos);
						
			if (iblockstate.getBlock() != this) {
				worldIn.setBlockToAir(pos);
			}
			else {
				iblockstate.neighborChanged(worldIn, blockpos, blockIn, fromPos);
			}
		}
		
	}
	
	// Get the Item that this Block should drop when harvested.
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return this.getDoorItem();
	}
	
	// Checks if this block can be placed exactly at the given position.
	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		if (pos.getY() >= worldIn.getHeight() - 1) {
			return false;
		}
		else {
			IBlockState state = worldIn.getBlockState(pos.down());
			return (state.isTopSolid() || state.getBlockFaceShape(worldIn, pos.down(), EnumFacing.UP) == BlockFaceShape.SOLID)
					&& super.canPlaceBlockAt(worldIn, pos) 
					&& super.canPlaceBlockAt(worldIn, pos.up())
					&& super.canPlaceBlockAt(worldIn, pos.up(2));
			
		}
	}
	
	@Override
	public EnumPushReaction getMobilityFlag(IBlockState state) {
		return EnumPushReaction.DESTROY;
	}
	
	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		return new ItemStack(this.getItem());
	}
	
	private Item getItem() {
		return doorItem;
	}
	
	// Called before the Block is set to air in the world. Called regardless of if the player's tool can actually collect this block
	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
		int third = state.getValue(THIRD).ordinal();
		if(player.capabilities.isCreativeMode) {
			BlockPos.getAllInBox(pos.up(0 - third), pos.up(2 - third)).forEach(p -> worldIn.setBlockToAir(p));
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}
	
	// Get the actual Block state of this Block at the given position. This applies properties not visible in the metadata, such as fence connections.
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		
		IBlockState states[] = new IBlockState[3];
		int third = state.getValue(THIRD).ordinal();
		for(int i = 0; i < 3; i++) {
			states[i] = worldIn.getBlockState(pos.up(i - third));
			if(states[i].getBlock() != this) {
				return state;//Something went wrong
			}
		}
		
		return state
				.withProperty(BlockDoor.HINGE,   states[0].getValue(BlockDoor.HINGE))
				.withProperty(BlockDoor.OPEN,    states[0].getValue(BlockDoor.OPEN))
				.withProperty(BlockDoor.FACING,  states[1].getValue(BlockDoor.FACING))
				.withProperty(BlockDoor.POWERED, states[2].getValue(BlockDoor.POWERED));
	}
	
	// Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed blockstate.
	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.getValue(THIRD) != EnumDoorThird.MIDDLE ? state : state.withProperty(BlockDoor.FACING, rot.rotate(state.getValue(BlockDoor.FACING)));
	}
	
	// Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed blockstate.
	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		return mirrorIn == Mirror.NONE ? state : state.withRotation(mirrorIn.toRotation(state.getValue(BlockDoor.FACING))).cycleProperty(BlockDoor.HINGE);
	}
	
	// Convert the given metadata into a BlockState for this Block
	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumDoorThird third = EnumDoorThird.values()[meta >> 2];
		IBlockState state = this.getDefaultState().withProperty(THIRD, third);
		
		switch(third) {
			case LOWER: return state.withProperty(BlockDoor.HINGE, BlockDoor.EnumHingePosition.values()[meta & 1]).withProperty(BlockDoor.OPEN, (meta & 2) > 0);
			case MIDDLE: return state.withProperty(BlockDoor.FACING, EnumFacing.getHorizontal(meta & 3).rotateYCCW());
			case UPPER: return state.withProperty(BlockDoor.POWERED, (meta & 1) > 0);
			default: return state;
		}
	}
	
	// Convert the BlockState into the correct metadata value
	@Override
	public int getMetaFromState(IBlockState state) {
		switch(state.getValue(THIRD)) {
			case LOWER:  return 0 << 2 | ((state.getValue(BlockDoor.HINGE) == BlockDoor.EnumHingePosition.RIGHT) ? 1 : 0) | (state.getValue(BlockDoor.OPEN) ? 2 : 0);
			case MIDDLE: return 1 << 2 | state.getValue(BlockDoor.FACING).rotateY().getHorizontalIndex();
			case UPPER:  return 2 << 2 | (state.getValue(BlockDoor.POWERED) ? 1 : 0);
			default: return 0;
		}
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {THIRD, BlockDoor.FACING, BlockDoor.OPEN, BlockDoor.HINGE, BlockDoor.POWERED});
	}
	
	/**
	 * Get the geometry of the queried face at the given position and state. This is used to decide whether things like
	 * buttons are allowed to be placed on the face, or how glass panes connect to the face, among other things.
	 * <p>
	 * Common values are {@code SOLID}, which is the default, and {@code UNDEFINED}, which represents something that
	 * does not fit the other descriptions and will generally cause other things not to connect to the face.
	 * 
	 * @return an approximation of the form of the given face
	 */
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return BlockFaceShape.UNDEFINED;
	}
	
	public static enum EnumDoorThird implements IStringSerializable {
		LOWER,
		MIDDLE,
		UPPER;
		
		@Override
		public String toString() {
			return this.getName();
		}
		
		@Override
		public String getName() {
			switch(this) {
				case UPPER: return "upper";
				case MIDDLE: return "middle";
				default:
				case LOWER: return "lower";
			}
		}
	}
	
	public static enum EnumHingePosition implements IStringSerializable {
		LEFT,
		RIGHT;
		
		@Override
		public String toString() {
			return this.getName();
		}
		
		@Override
		public String getName() {
			return this == LEFT ? "left" : "right";
		}
	}
}