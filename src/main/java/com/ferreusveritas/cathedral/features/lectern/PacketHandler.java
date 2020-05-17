package com.ferreusveritas.cathedral.features.lectern;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {

	private static int packetId = 0;

	public static SimpleNetworkWrapper channel = null;

	public PacketHandler() {}

	public static int nextID() {
		return packetId++;
	}

	public static void registerMessages(String channelName) {
		channel = NetworkRegistry.INSTANCE.newSimpleChannel(channelName);
		registerMessages();
	}
	
	public static void registerMessages() {
		channel.registerMessage(PacketOpenBook.Handler.class, PacketOpenBook.class, nextID(), Side.CLIENT);
	}

}
