package com.ferreusveritas.stonelore.items;

import com.ferreusveritas.stonelore.blocks.BlockGenericSlab;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class ItemGenericSlab extends ItemBlock {

	public ItemGenericSlab(Block block) {
		super(block);
	}

	@Override
	public boolean func_150936_a(World world, int x, int y, int z, int side, EntityPlayer player, ItemStack stack) {
		return true;
	}

	@Override
	public int getMetadata(int par1){
		return par1;
	}
	
	@Override 
	public String getUnlocalizedName(ItemStack itemStack){
		String name = "";
		
		BlockGenericSlab bgs = (BlockGenericSlab)field_150939_a;
		
		int select = itemStack.getItemDamage() & 7;
		
		name =  select <= bgs.count ? bgs.baseBlocks.get(select).name : "unknown";

		return getUnlocalizedName() + "." + name;
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hx, float hy, float hz) {
		BlockGenericSlab block = (BlockGenericSlab) Block.getBlockFromItem(this);
		int meta = stack.getItemDamage() & 7;
		
		ForgeDirection dir = ForgeDirection.VALID_DIRECTIONS[side];
		Block clickedBlock = world.getBlock(x, y, z);
		int clickedMeta = world.getBlockMetadata(x, y, z);
		int clickedSelect = clickedMeta & 7;
		boolean clickedIsBottom = (clickedMeta & 8) == 0;
		
		// the position of the block that we are attempting to place at
		int x2 = x + dir.offsetX;
		int y2 = y + dir.offsetY;
		int z2 = z + dir.offsetZ;

		Block atBlock = world.getBlock(x2, y2, z2);
		int atMeta = world.getBlockMetadata(x2, y2, z2);
		int atSelect = atMeta & 7;
		boolean atIsBottom = (atMeta & 8) == 0;		

		//boolean selectEquals = atSelect  == clickedSelect;

		// if the metadata at the place target matches, and the block there matches either the top or bottom slab, try to fill in the rest of the block
		if (atBlock == block && atSelect == meta && ((!atIsBottom && (hy <= 0.5D || hy == 1.0D)) || (atIsBottom && (hy > 0.5D || hy == 0)))) {
			place(stack, world, x2, y2, z2, block.baseBlocks.get(meta).block, block.baseBlocks.get(meta).metaData);
			return true;
		}

		if (clickedBlock == block && clickedSelect == meta && ((clickedIsBottom && dir == ForgeDirection.UP) || (!clickedIsBottom && dir == ForgeDirection.DOWN))) {
			place(stack, world, x, y, z, block.baseBlocks.get(meta).block, block.baseBlocks.get(meta).metaData);
			return true;
		}

		// finally just try to place a normal slab
		if (atBlock.isReplaceable(world, x, y, z)) {
			boolean top = hy > 0.5D && dir != ForgeDirection.UP || dir == ForgeDirection.DOWN;
			// if we can replace the clicked block do so
			if (clickedBlock.isReplaceable(world, x, y, z)) {
				place(stack, world, x, y, z, block, meta | (top ? 8 : 0) );
			} else {
				place(stack, world, x2, y2, z2, block, meta | (top ? 8 : 0));
			}
			return true;
		}
		return false;
	}

	private void place(ItemStack stack, World world, int x, int y, int z, Block toPlace, int metadata) {
		world.setBlock(x, y, z, toPlace, metadata, 2);
		stack.stackSize -= 1;
		world.playSoundEffect(x + 0.5f, y + 0.5f, z + 0.5f, this.field_150939_a.stepSound.func_150496_b(), (this.field_150939_a.stepSound.getVolume() + 1.0F) / 2.0F,
				this.field_150939_a.stepSound.getPitch() * 0.8F);
	}
}
