package com.david.test;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by jiakang on 2018/5/7
 *
 * @author jiakang
 */
public class NioTest {

    @Test
    public void test() {
        String inFile = "/Users/jiakang/nio_copy.txt";
        String outFile = "/Users/jiakang/result.txt";

        try (FileInputStream fin = new FileInputStream(inFile);
             FileOutputStream fos = new FileOutputStream(outFile);

             //获取读的通道
             FileChannel fcin = fin.getChannel();
             //获取写的通道
             FileChannel fout = fos.getChannel()
        ) {
            //定义缓冲区，并指定大小
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            while (true) {
                //清空缓冲区
                buffer.clear();
                //从通道读取一个数据到缓冲区
                int r = fcin.read(buffer);
                //判断是否有从通道读到的数据
                if (r == -1) {
                    break;
                }
                //将buffer指针指向头部
                buffer.flip();
                //把缓冲区数据写入通道
                fout.write(buffer);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}