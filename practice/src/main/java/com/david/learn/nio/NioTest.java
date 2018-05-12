package com.david.learn.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by jiakang on 2018/5/7
 *
 * @author jiakang
 */
public class NioTest {

    public static void main(String[] args) {
        ByteBuffer echoBuffer = ByteBuffer.allocate(1024);
        SocketChannel channel = null;
        Selector selector = null;
        try {
            channel = SocketChannel.open();
            channel.configureBlocking(false);
            //请求连接
            channel.connect(new InetSocketAddress("localhost", 8080));
            selector = Selector.open();
            channel.register(selector, SelectionKey.OP_CONNECT);
            int num = selector.select();
            Set selectedKeys = selector.selectedKeys();
            Iterator it = selectedKeys.iterator();
            while (it.hasNext()) {
                SelectionKey key = (SelectionKey) it.next();
                it.remove();
                if (key.isConnectable()) {
                    if (channel.isConnectionPending()) {
                        if (channel.finishConnect()) {
                            //只有当前连接成功后才能注册OP_READ事件
                            key.interestOps(SelectionKey.OP_READ);
                            echoBuffer.put("123456789adsdf@jaisdfjie".getBytes());
                            echoBuffer.flip();
                            System.out.println("##" + new String(echoBuffer.array()));
                            channel.write(echoBuffer);
                            System.out.println("写入完毕");
                        } else {
                            key.cancel();
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}