package com.david.learn;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by haojk on 6/8/16.
 */
public class EchoClientHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = Logger.getLogger(EchoClientHandler.class.getName());

    private final ByteBuf firstMessage;

    /**
     * Creates a client-side handler.
     */
    public EchoClientHandler(int firstMessageSize){
        if(firstMessageSize < 0){
            throw new IllegalArgumentException("firstMessageSize: " + firstMessageSize);
        }
        firstMessage = Unpooled.buffer(firstMessageSize);
        for(int i = 0; i < firstMessage.capacity(); i++){
            firstMessage.writeByte((byte) i);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx){
        ctx.writeAndFlush(firstMessage);
        System.out.println("active");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception{
        ctx.write(msg);
        System.out.println("read");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception{
        ctx.flush();
        System.out.println("readok");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        //close the connection when an exception is raised.
        logger.log(Level.WARNING, "Unexpected exception from downstream.", cause);
        ctx.close();
    }

}
