package com.ferreusveritas.cathedral.features.lectern;

import com.ferreusveritas.cathedral.CathedralMod;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;

public class TileEntityLectern extends TileEntity {

	protected ItemStack book = ItemStack.EMPTY;
	private boolean locked = false;
	
	public boolean setBook(ItemStack itemStack) {

		if(isBook(itemStack)) {
			book = itemStack;
			markDirty();
			return true;
		}

		return false;
	}

	public void clearBook() {
		book = ItemStack.EMPTY;
		markDirty();
	}

	public ItemStack getBook() {
		return book;
	}

	public boolean hasBook() {
		return !getBook().isEmpty();
	}

	public void readBook(EntityPlayer player) {
		BookManager.read(player, getBook(), getPos());
	}

	public void ejectBook() {
		if(hasBook() && !isLocked()) {
			ItemStack stack = getBook();
			clearBook();
			spawnAsEntity(world, pos.up(), stack);
		}
	}

	public static boolean isBook(ItemStack stack) {
		return BookManager.isReadable(stack);
	}

	public boolean isLocked() {
		return locked;
	}
	
	protected void setLocked(boolean lock) {
		locked = lock;
		markDirty();
	}
	
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

		if(hand == EnumHand.MAIN_HAND) {
			if(player.isCreative() && player.getHeldItemMainhand().getItem() == Items.IRON_INGOT) {
				toggleLock(player);
			}
			else if(player.isSneaking()) {
				ejectBook();
			}
			else if(hasBook()) {
				readBook(player);
			}
			else {
				addBook(player, player.getHeldItem(hand));
			}

			return true;
		}

		return false;
	}

	public void toggleLock(EntityPlayer player) {
		setLocked(!isLocked());
		if(!player.world.isRemote) {
			player.sendMessage(new TextComponentString(isLocked() ? "locked" : "unlocked"));
		}
	}
	
	public void addBook(EntityPlayer player, ItemStack stack) {
		if(!hasBook() && !isLocked()) {
			ItemStack copyOfStack = stack.copy();
			copyOfStack.setCount(1);
			boolean accepted = setBook(copyOfStack);
			if(accepted) {
				if(!player.isCreative()) {
					stack.shrink(1);
				}
			}
		}
	}

	public static void spawnAsEntity(World worldIn, BlockPos pos, ItemStack stack) {
		Block.spawnAsEntity(worldIn, pos, stack);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		readExtraData(compound);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		return writeExtraData(compound);
	}

	public void readExtraData(NBTTagCompound compound) {

		if(compound.hasKey("Book", NBT.TAG_COMPOUND)) {
			NBTTagCompound bookData = compound.getCompoundTag("Book");
			book = new ItemStack(bookData);
		}

		if(compound.hasKey("locked", NBT.TAG_BYTE)) {
			setLocked(compound.getBoolean("locked"));
		}
		
	}

	public NBTTagCompound writeExtraData(NBTTagCompound compound) {

		if(!getBook().isEmpty()) {
			compound.setTag("Book", getBook().writeToNBT(new NBTTagCompound()));
		}

		compound.setBoolean("locked", isLocked());
		
		return compound;
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(pos, 0, writeToNBT(new NBTTagCompound()));
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		readFromNBT(pkt.getNbtCompound());
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return writeExtraData(super.getUpdateTag());
	}

	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		readExtraData(tag);
		super.handleUpdateTag(tag);
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
		Block oldBlock = oldState.getBlock();
		Block newBlock = newState.getBlock();
		return !(oldBlock == newBlock && newBlock == CathedralMod.lectern.blockLectern); 
	}

}
