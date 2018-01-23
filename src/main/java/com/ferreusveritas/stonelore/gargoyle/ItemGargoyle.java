package com.ferreusveritas.stonelore.gargoyle;

import com.ferreusveritas.stonelore.items.ItemSubBlocks;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class ItemGargoyle extends ItemSubBlocks {

	public ItemGargoyle(Block block) {
		super(block);
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hx, float hy, float hz) {	
		BlockGargoyle block = (BlockGargoyle) Block.getBlockFromItem(this);

		int material = stack.getItemDamage();

		ForgeDirection dir = ForgeDirection.VALID_DIRECTIONS[side];

		// the position of the block that we are attempting to place at
		int x2 = x + dir.offsetX;
		int y2 = y + dir.offsetY;
		int z2 = z + dir.offsetZ;

		world.setBlock(x2, y2, z2, block);

		EntityGargoyle garg = (EntityGargoyle)world.getTileEntity(x2,  y2,  z2);

		int placement = MathHelper.floor_double((360.0F - player.rotationYaw) * 8.0F / 360.0F + 4.5D) & 7;

		if(side == 1){//UP
			placement |= 8;//Floor
		} else if(side == 0){//DOWN
			placement |= 16;//Ceiling
		}

		garg.setDirection(placement);
		garg.setMaterial(material);

		world.setTileEntity(x2,  y2,  z2, garg);

		stack.stackSize--;

		return true;
	}

}
