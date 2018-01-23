package com.ferreusveritas.stonelore.items;

import com.ferreusveritas.stonelore.blocks.BlockRoofTiles;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class ItemBlockRoofTiles extends ItemBlock {

	public ItemBlockRoofTiles(Block block) {
		super(block);
	}

	@Override
	public boolean func_150936_a(World world, int x, int y, int z, int side, EntityPlayer player, ItemStack stack) {
		return true;
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hx, float hy, float hz) {

		ForgeDirection dir = ForgeDirection.VALID_DIRECTIONS[side];

		// the position of the block that we are attempting to place at
		int x2 = x + dir.offsetX;
		int y2 = y + dir.offsetY;
		int z2 = z + dir.offsetZ;
		
        int facing = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		BlockRoofTiles block = (BlockRoofTiles) Block.getBlockFromItem(this);

		Block atBlock = world.getBlock(x2, y2, z2);
		int atMeta = world.getBlockMetadata(x2, y2, z2);
		boolean atIsBottom = atMeta == 13;

		Block clickedBlock = world.getBlock(x, y, z);
		int clickedMeta = world.getBlockMetadata(x, y, z);
		boolean clickedIsBottom = clickedMeta == 13;
		boolean clickedIsTop = clickedMeta == 14;

		if(!player.isSneaking()){
			
	        int invert = side != 0 && (side == 1 || hy <= 0.5D) ? 0 : 4;
	        
	        //convert 0,1,2,3 to 2,1,3,0 respectively
	        facing++;//increment by one to rotate by 90 degrees(to align 0 and 3)
	        facing = (facing << 1) & 2 | (facing >> 1) & 1;//swap 1's and 2's bits

			//just try to place a normal stair block
			if (atBlock.isReplaceable(world, x, y, z)) {
				// if we can replace the clicked block do so
				if (clickedBlock.isReplaceable(world, x, y, z)) {
					place(stack, world, x, y, z, block, facing | invert );
				} else {
					place(stack, world, x2, y2, z2, block, facing | invert );
				}
				return true;
			}
			
			return false;
		}

		// if the block at the place target matches, and the block there matches either the top or bottom slab, try to fill in the rest of the block	
		if (clickedBlock == block && ((clickedIsBottom && dir == ForgeDirection.UP) || (clickedIsTop && dir == ForgeDirection.DOWN))) {
			place(stack, world, x, y, z, block, 15);
			return true;
		}
		
		if (atBlock == block && ((!atIsBottom && (hy <= 0.5D || hy == 1.0D)) || (atIsBottom && (hy > 0.5D || hy == 0)))) {
			place(stack, world, x2, y2, z2, block, 15);
			return true;
		}

		// finally just try to place a normal slab
		if (atBlock.isReplaceable(world, x, y, z)) {
			boolean top = hy > 0.5D && dir != ForgeDirection.UP || dir == ForgeDirection.DOWN;
			// if we can replace the clicked block do so
			if (clickedBlock.isReplaceable(world, x, y, z)) {
				place(stack, world, x, y, z, block, top ? 14 : 13 );
			} else {
				place(stack, world, x2, y2, z2, block, top ? 14 : 13);
			}
			return true;
		}
		return false;
	}

	private void place(ItemStack stack, World world, int x, int y, int z, Block toPlace, int metadata) {
		world.setBlock(x, y, z, toPlace, metadata, 2);
		if(metadata != 15){//Not creating a solid block
			stack.stackSize -= 1;
		}
		world.playSoundEffect(x + 0.5f, y + 0.5f, z + 0.5f, this.field_150939_a.stepSound.func_150496_b(), (this.field_150939_a.stepSound.getVolume() + 1.0F) / 2.0F,
				this.field_150939_a.stepSound.getPitch() * 0.8F);
	}
	
}
