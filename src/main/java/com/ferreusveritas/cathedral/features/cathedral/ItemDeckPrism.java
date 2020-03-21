package com.ferreusveritas.cathedral.features.cathedral;

import com.ferreusveritas.cathedral.features.roofing.BlockShinglesSlab;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemDeckPrism extends ItemBlock {
	
	public ItemDeckPrism(Block block) {
		super(block);
		setHasSubtypes(true);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return super.getUnlocalizedName() + "." + EnumDyeColor.byMetadata(stack.getMetadata());
	}
	
	/**
	 * Called when a Block is right-clicked with this Item
	 */
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		IBlockState existingState = world.getBlockState(pos);
		
		ItemStack itemstack = player.getHeldItem(hand);
		
		if (!itemstack.isEmpty() && canUseBlockForPrism(existingState) && player.canPlayerEdit(pos, facing, itemstack)) {
			int meta = itemstack.getMetadata();
			IBlockState placeState = this.block.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, player, hand);
			EnumDyeColor color = EnumDyeColor.byMetadata(meta);
			
			if (placeBlockAt(itemstack, player, world, pos, facing, color, placeState)) {
				placeState = world.getBlockState(pos);
				SoundType soundtype = placeState.getBlock().getSoundType(placeState, world, pos, player);
				world.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
				itemstack.shrink(1);
			}
			
			return EnumActionResult.SUCCESS;
		}
		else {
			return EnumActionResult.FAIL;
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side, EntityPlayer player, ItemStack stack) {
		return true;
	}
	
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, EnumDyeColor color, IBlockState newState) {
		
		IBlockState targetState = world.getBlockState(pos);
		
		if(world.getTileEntity(pos) == null) {//Ensure there's no tile entity at this location
			if(super.placeBlockAt(stack, player, world, pos, side, 0, 0, 0, newState)) {
				if(newState.getBlock() instanceof BlockDeckPrism) {
					BlockDeckPrism deckPrism = (BlockDeckPrism) newState.getBlock();
					deckPrism.setBaseBlock(world, pos, targetState);
					deckPrism.setPrismColor(world, pos, color);
					return true;
				}
			}
		}
		
		return false;
	}
	
	public boolean canUseBlockForPrism(IBlockState state) {
		
		Block block = state.getBlock();
		
		if(state.isNormalCube() || block instanceof BlockSlab || block instanceof BlockShinglesSlab) {
			
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
