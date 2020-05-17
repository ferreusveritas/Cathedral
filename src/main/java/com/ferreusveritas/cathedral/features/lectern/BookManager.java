package com.ferreusveritas.cathedral.features.lectern;

import java.util.LinkedList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;


public class BookManager {

	private static LinkedList<IBookReadHandler> entries = new LinkedList<>();
	
	public static void add(IBookReadHandler handler) {
		entries.addFirst(handler);
	}
	
	private static IBookReadHandler findEntry(ItemStack itemStack) {
		for(IBookReadHandler entry : entries) {
			if(entry.isReadable(itemStack)) {
				return entry;
			}
		}
		
		return null;
	}
	
	public static boolean isReadable(ItemStack itemStack) {
		return findEntry(itemStack) != null;
	}
	
	public static void read(EntityPlayer player, ItemStack bookStack, BlockPos lecternPos) {
		IBookReadHandler entry = findEntry(bookStack);
		if(entry != null) {
			entry.read(player, bookStack, lecternPos);
		}
	}
	
}
