package com.ferreusveritas.cathedral.features.cathedral;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemDeckPrism extends ItemBlock {

	public ItemDeckPrism(Block block) {
		super(block);
	}
	
	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState) {
		
		pos = pos.offset(side.getOpposite());
		IBlockState targetState = world.getBlockState(pos);

		if(world.getTileEntity(pos) == null) {
			if(canUseBlockForPrism(targetState)) {
				if(super.placeBlockAt(stack, player, world, pos, side, hitX, hitY, hitZ, newState)) {
					if(newState.getBlock() instanceof BlockDeckPrism) {
						BlockDeckPrism deckPrism = (BlockDeckPrism) newState.getBlock();
						deckPrism.setBaseBlock(world, pos, targetState);
					}
				}
			}
			
		}
		
		return false;
	}
	
	public boolean canUseBlockForPrism(IBlockState state) {
		
		Block block = state.getBlock();
		
		if(state.isNormalCube() || block instanceof BlockSlab) {
			
			return 	block != Blocks.BEDROCK &&
					state.getMaterial() != Material.LEAVES &&
					state.getMaterial() != Material.GLASS &&
					state.getMaterial() != Material.GRASS &&
					state.getMaterial() != Material.GROUND &&
					state.getMaterial() != Material.SAND;
		}
		
		return false;
	}
	
}
