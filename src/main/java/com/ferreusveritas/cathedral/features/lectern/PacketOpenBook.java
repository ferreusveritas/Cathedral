package com.ferreusveritas.cathedral.features.lectern;

import com.ferreusveritas.cathedral.CathedralMod;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketOpenBook implements IMessage {

	public BlockPos lecternPos = BlockPos.ORIGIN;
	
	@Override
	public void fromBytes(ByteBuf buf) {
		lecternPos = BlockPos.fromLong(buf.readLong());
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(lecternPos.toLong());
	}

	public static class Handler implements IMessageHandler<PacketOpenBook, IMessage> {
		
        @Override
        public IMessage onMessage(PacketOpenBook message, MessageContext ctx) {
        	try{
        		FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
        	}
        	catch(Exception e) {
        		e.printStackTrace();
        	}
            return null;
        }

        private void handle(PacketOpenBook message, MessageContext ctx) {
        	CathedralMod.proxy.openBookGui(message.lecternPos);
        }
	}
	
}
