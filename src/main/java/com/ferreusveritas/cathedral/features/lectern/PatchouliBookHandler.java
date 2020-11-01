package com.ferreusveritas.cathedral.features.lectern;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import vazkii.patchouli.common.base.PatchouliSounds;
import vazkii.patchouli.common.book.Book;
import vazkii.patchouli.common.item.ItemModBook;
import vazkii.patchouli.common.network.NetworkHandler;
import vazkii.patchouli.common.network.message.MessageOpenBookGui;

public class PatchouliBookHandler implements IBookReadHandler {
	
	@Override
	public boolean isReadable(ItemStack itemStack) {
		return itemStack.getItem() instanceof ItemModBook;
	}
	
	@Override
	public void read(EntityPlayer player, ItemStack bookStack, BlockPos lecternPos) {
		
		Book book = ItemModBook.getBook(bookStack);
		if(book == null) {
			return;
		}
		
		if(player instanceof EntityPlayerMP) {
			World world = player.world;
			NetworkHandler.INSTANCE.sendTo(new MessageOpenBookGui(book.resourceLoc.toString()), (EntityPlayerMP) player);
			SoundEvent sfx = PatchouliSounds.getSound(book.openSound, PatchouliSounds.book_open); 
			world.playSound(null, player.posX, player.posY, player.posZ, sfx, SoundCategory.PLAYERS, 1F, (float) (0.7 + Math.random() * 0.4));
		}
	}
	
}
