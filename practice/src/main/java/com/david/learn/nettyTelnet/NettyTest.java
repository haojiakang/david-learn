package com.david.learn.nettyTelnet;

/**
 * Created by jiakang on 2018/5/9
 *
 * @author jiakang
 */
public class NettyTest {

    public static void main(String[] args) {
        NettyTelnetServer server = new NettyTelnetServer();
        try {
            server.open();
        } catch (InterruptedException e) {
            server.close();
        }
    }
}