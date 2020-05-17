package com.ferreusveritas.cathedral.features.lectern;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public interface IBookReadHandler {

	public boolean isReadable(ItemStack itemStack);
	public void read(EntityPlayer player, ItemStack bookStack, BlockPos lecternPos);
	
}
