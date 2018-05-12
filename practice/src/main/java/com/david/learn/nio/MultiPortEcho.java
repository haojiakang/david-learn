package com.david.learn.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by jiakang on 2018/5/7
 *
 * @author jiakang
 */
public class MultiPortEcho {

    private int ports[];
    private ByteBuffer echoBuffer = ByteBuffer.allocate(5);

    public MultiPortEcho(int ports[]) {
        this.ports = ports;
    }

    private void go() {
        try {
            Selector selector = Selector.open();

            for (int i = 0; i < ports.length; ++i) {
                ServerSocketChannel ssc = ServerSocketChannel.open();
                ssc.configureBlocking(false);
                ServerSocket ss = ssc.socket();
                InetSocketAddress address = new InetSocketAddress(ports[i]);
                ss.bind(address);
                SelectionKey key = ssc.register(selector, SelectionKey.OP_ACCEPT);
                System.out.println("Going to listen on " + ports[i]);
            }

            while (true) {
                int num = selector.select();
                Set selectedKeys = selector.selectedKeys();
                Iterator it = selectedKeys.iterator();
                while (it.hasNext()) {
                    SelectionKey key = (SelectionKey) it.next();
                    if ((key.readyOps() & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT) {
                        ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                        SocketChannel sc = ssc.accept();
                        sc.configureBlocking(false);
                        SelectionKey newKey = sc.register(selector, SelectionKey.OP_READ);
                        it.remove();
                        System.out.println("Got connection from " + sc);
                    } else if ((key.readyOps() & SelectionKey.OP_READ) == SelectionKey.OP_READ) {
                        SocketChannel sc = (SocketChannel) key.channel();
                        int bytesEchoed = 0;
                        while (true) {
                            echoBuffer.clear();
                            int r = sc.read(echoBuffer);
                            if (r <= 0) {
                                sc.close();
                                break;
                            }
                            echoBuffer.flip();
                            sc.write(echoBuffer);
                            bytesEchoed += r;
                        }
                        System.out.println("Echoed " + bytesEchoed + " from " + sc);
                        it.remove();
                    }
                }
                System.out.println("going to clear");
                selectedKeys.clear();
                System.out.println("cleared");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int[] ports = new int[]{8080};
        for (int i = 0; i < args.length; ++i) {
            ports[i] = Integer.parseInt(args[i]);
        }
        MultiPortEcho multiPortEcho = new MultiPortEcho(ports);
        multiPortEcho.go();
    }
}