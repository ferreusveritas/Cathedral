package com.ferreusveritas.cathedral.features.lectern;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemWrittenBook;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WrittenBookHandler implements IBookReadHandler {
	
	public boolean isReadable(ItemStack itemStack) {
		return itemStack.getItem() instanceof ItemWrittenBook;
	}
	
	@Override
	public void read(EntityPlayer player, ItemStack bookStack, BlockPos lecternPos) {
		if(isReadable(bookStack)) {
			if (bookStack.hasTagCompound()) {
				NBTTagCompound nbttagcompound = bookStack.getTagCompound();
				boolean resolved = nbttagcompound.getBoolean("resolved");
				if(resolved) {
					World world = player.world;
					if(!world.isRemote && player instanceof EntityPlayerMP) {
						PacketOpenBook packet = new PacketOpenBook();
						packet.lecternPos = lecternPos;
						PacketHandler.channel.sendTo(packet, (EntityPlayerMP) player);
					}
				}
			}
		}
	}
	
}
