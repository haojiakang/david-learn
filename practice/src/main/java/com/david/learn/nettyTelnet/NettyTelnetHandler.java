package com.david.learn.nettyTelnet;

import cn.hutool.core.util.StrUtil;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.net.InetAddress;
import java.util.Date;

/**
 * Created by jiakang on 2018/5/9
 *
 * @author jiakang
 */
public class NettyTelnetHandler extends SimpleChannelInboundHandler<String>{

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // send greeting for a new connection
        ctx.write(StrUtil.format("Welcome to {}!\r\n", InetAddress.getLocalHost().getHostName()));
        ctx.write(StrUtil.format("It is {} now.\r\n", new Date()));
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String request) throws Exception {
        String response;
        boolean close = false;
        if (request.isEmpty()) {
            response = "Please type something.\r\n";
        } else if ("bye".equals(request.toLowerCase())) {
            response = "Have a good day!\r\n";
            close = true;
        } else {
            response = StrUtil.format("Did you say '{}'?\r\n", request);
        }

        ChannelFuture future = ctx.write(response);
        ctx.flush();
        if (close) {
            future.addListener(ChannelFutureListener.CLOSE);
        }
    }
}