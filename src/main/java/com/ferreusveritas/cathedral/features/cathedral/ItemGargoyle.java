package com.ferreusveritas.cathedral.features.cathedral;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ItemGargoyle extends ItemBlock {

	public ItemGargoyle(Block block) {
		super(block);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
	//public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hx, float hy, float hz) {	
		BlockGargoyle block = (BlockGargoyle) Block.getBlockFromItem(this);
		ItemStack stack = player.getHeldItem(hand);

		int material = stack.getItemDamage();

		// the position of the block that we are attempting to place at
		BlockPos pos2 = pos.offset(facing); 

		world.setBlockState(pos2, block.getDefaultState());

		EntityGargoyle garg = (EntityGargoyle)world.getTileEntity(pos2);

		int placement = MathHelper.floor((360.0F - player.rotationYaw) * 8.0F / 360.0F + 4.5D) & 7;

		if(facing == EnumFacing.UP){
			placement |= 8;//Floor
		} else if(facing == EnumFacing.DOWN) {
			placement |= 16;//Ceiling
		}

		garg.setDirection(placement);
		garg.setMaterial(material);

		world.setTileEntity(pos2, garg);

		stack.shrink(1);

		return EnumActionResult.SUCCESS;
	}

}
