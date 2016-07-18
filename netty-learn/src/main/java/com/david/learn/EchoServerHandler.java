package com.david.learn;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by haojk on 6/8/16.
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = Logger.getLogger(EchoServerHandler.class.getName());

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception{
        ctx.write(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception{
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        //Close the connection when an exception is raised.
        logger.log(Level.WARNING, "Unexpected exception from downstream.", cause);
        ctx.close();
    }

}
