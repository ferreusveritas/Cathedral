package com.ferreusveritas.cathedral.features.dwemer;

import com.ferreusveritas.cathedral.CathedralMod;

import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockShortDoor extends BlockDoor {

	ItemDoor doorItem;
	
	public BlockShortDoor(Material materialIn, String name) {
		super(materialIn);
		setRegistryName(name);
		setUnlocalizedName(name);
		setCreativeTab(CathedralMod.tabDwemer);
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
    
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		BlockPos blockpos = state.getValue(HALF) == BlockDoor.EnumDoorHalf.LOWER ? pos : pos.down();
		IBlockState iblockstate = pos.equals(blockpos) ? state : worldIn.getBlockState(blockpos);

		if (iblockstate.getBlock() != this) {
			return false;
		}
		else {
			state = iblockstate.cycleProperty(OPEN);
			worldIn.setBlockState(blockpos, state, 10);
			worldIn.markBlockRangeForRenderUpdate(blockpos, pos);
			worldIn.playEvent(playerIn, ((Boolean)state.getValue(OPEN)).booleanValue() ? this.getOpenSound() : this.getCloseSound(), pos, 0);
			return true;
		}
	}
	
    private int getCloseSound() {
        return 1011;
    }

    private int getOpenSound() {
        return 1005;
    }

}
