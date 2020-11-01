package com.ferreusveritas.cathedral.features.dwarven;

import java.util.Random;

import com.ferreusveritas.cathedral.CathedralMod;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockShortDoor extends BlockDoor {

	ItemDoor doorItem;

	public BlockShortDoor(Material materialIn, String name) {
		super(materialIn);
		setRegistryName(name);
		setUnlocalizedName(name);
		setCreativeTab(CathedralMod.dwarven.tabDwemer);
	}

	public BlockShortDoor setDoorItem(ItemDoor doorItem) {
		this.doorItem = doorItem;
		return this;
	}

	public ItemDoor getDoorItem() {
		return doorItem;
	}

	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		return new ItemStack(doorItem);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return state.getValue(HALF) == BlockDoor.EnumDoorHalf.UPPER ? Items.AIR : this.getDoorItem();
	}

	public BlockPos getPartnerPos(IBlockState state, IBlockAccess source, BlockPos pos) {

		state = state.getActualState(source, pos);
		EnumFacing enumfacing = state.getValue(BlockDoor.FACING);
		boolean hingeOnRight = state.getValue(BlockDoor.HINGE) == BlockDoor.EnumHingePosition.RIGHT;

		switch (enumfacing) {
			default:
			case EAST:  return hingeOnRight ? pos.north() : pos.south();
			case SOUTH: return hingeOnRight ? pos.east() : pos.west();
			case WEST:  return hingeOnRight ? pos.south() : pos.north();
			case NORTH: return hingeOnRight ? pos.west() : pos.east();
		}
	}
	
	public void setDoorAjar(World worldIn, BlockPos pos, boolean open) {
		IBlockState iblockstate = worldIn.getBlockState(pos);
		
		if (iblockstate.getBlock() == this) {
			BlockPos bottomPos = iblockstate.getValue(HALF) == BlockDoor.EnumDoorHalf.LOWER ? pos : pos.down();
			IBlockState bottomState = pos == bottomPos ? iblockstate : worldIn.getBlockState(bottomPos);
			
			if (bottomState.getBlock() == this && bottomState.getValue(BlockDoor.OPEN).booleanValue() != open) {
				worldIn.setBlockState(bottomPos, bottomState.withProperty(BlockDoor.OPEN, Boolean.valueOf(open)), 10);
				worldIn.markBlockRangeForRenderUpdate(bottomPos, bottomPos.up());
				worldIn.playEvent((EntityPlayer)null, open ? this.getOpenSound() : this.getCloseSound(), pos, 0);
				
				//Check if this door is part of a pair and activate the other door in kind
				BlockPos partnerPos = getPartnerPos(bottomState, worldIn, bottomPos);
				IBlockState partnerState = worldIn.getBlockState(partnerPos);
				if(partnerState.getBlock() == this) {
					BlockShortDoor partnerBlock = (BlockShortDoor) partnerState.getBlock();
					boolean isPartnerOpen = partnerBlock.isDoorAjar(partnerState, worldIn, partnerPos);
					if(isPartnerOpen != open) {
						partnerBlock.setDoorAjar(worldIn, partnerPos, open);
					}
				}
			}
		}
	}
	
	public boolean isDoorAjar(IBlockState state, IBlockAccess access, BlockPos pos) {
		IBlockState actualState = getActualState(state, access, pos);
		return actualState.getBlock() == this ? actualState.getValue(BlockDoor.OPEN).booleanValue() : false;
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		BlockPos blockpos = state.getValue(HALF) == BlockDoor.EnumDoorHalf.LOWER ? pos : pos.down();
		IBlockState iblockstate = pos.equals(blockpos) ? state : worldIn.getBlockState(blockpos);

		if (iblockstate.getBlock() != this) {
			return false;
		}
		else {
			setDoorAjar(worldIn, pos, !isDoorAjar(state, worldIn, pos));
			return true;
		}
	}

	/**
	 * Called when a neighboring block was changed and marks that this state should perform any checks during a neighbor
	 * change. Cases may include when redstone power is updated, cactus blocks popping off due to a neighboring solid
	 * block, etc.
	 */
	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block neighborBlock, BlockPos fromPos) {
		if (state.getValue(HALF) == BlockDoor.EnumDoorHalf.UPPER) {
			BlockPos blockpos = pos.down();
			IBlockState iblockstate = world.getBlockState(blockpos);

			if (iblockstate.getBlock() != this) {
				world.setBlockToAir(pos);
			}
			else if (neighborBlock != this) {
				iblockstate.neighborChanged(world, blockpos, neighborBlock, fromPos);
			}
		}
		else {
			boolean invalid = false;
			BlockPos lowerPos = pos;
			BlockPos upperPos = pos.up();
			BlockPos underPos = pos.down();
			IBlockState lowerState = state;
			IBlockState upperState = world.getBlockState(upperPos);
			IBlockState underState = world.getBlockState(underPos);

			if (upperState.getBlock() != this) {
				world.setBlockToAir(pos);
				invalid = true;
			}

			if (!underState.isSideSolid(world, underPos, EnumFacing.UP) && !(underState.getBlock() instanceof BlockStairs)) {
				world.setBlockToAir(pos);
				invalid = true;
				
				if (upperState.getBlock() == this) {
					world.setBlockToAir(upperPos);
				}
			}

			if (invalid) {
				if (!world.isRemote) {
					this.dropBlockAsItem(world, pos, lowerState, 0);
				}
			} else {
				boolean isPoweredWorld = world.isBlockPowered(pos) || world.isBlockPowered(upperPos);
				boolean isPoweredState = ((Boolean)upperState.getValue(POWERED)).booleanValue();
				
				if (neighborBlock != this && (isPoweredWorld || neighborBlock.getDefaultState().canProvidePower()) && isPoweredWorld != isPoweredState) {
					world.setBlockState(upperPos, upperState.withProperty(POWERED, Boolean.valueOf(isPoweredWorld)), 2);
					
					boolean isOpen = lowerState.getValue(BlockDoor.OPEN).booleanValue();
					
					if (isPoweredWorld != isOpen) {
						setDoorAjar(world, lowerPos, isPoweredWorld);
					}
				}
			}
		}
	}

	

	private int getCloseSound() {
		return 1011;
	}

	private int getOpenSound() {
		return 1005;
	}

}
