package com.david.learn;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.MalformedInputException;

/**
 * Created by haojk on 5/25/16.
 */
public class NIOTest {

    private static final String fullName = "src/main/java/"+NIOTest.class.getName().replace(".", "/")+".java";

    private void bufferTest(){
        //创建Buffer
        CharBuffer buff = CharBuffer.allocate(8);
        System.out.println("capacity: "+buff.capacity());
        System.out.println("limit: " + buff.limit());
        System.out.println("position: " + buff.position());

        //放入元素
        buff.put('a');
        buff.put('b');
        buff.put('c');
        System.out.println("放入三个元素后，position = "+buff.position());

        //调用flip()方法
        buff.flip();
        System.out.println("执行flip方法后，limit = "+buff.limit());
        System.out.println("position = "+buff.position());

        //取出第一个元素
        System.out.println("第一个元素(position=0): "+buff.get());
        System.out.println("第一个元素取出后，position = "+buff.position());

        //调用clear方法
        buff.clear();
        System.out.println("执行clear后，limit = "+buff.limit());
        System.out.println("执行clear后，缓冲区内容并没有被清除。第三个元素为："+buff.get(2));
        System.out.println("执行绝对读取后，position = "+buff.position());
    }

    private void fileChannelTest(){
        File f = new File(fullName);
        try (
                //创建FileInputStream，以该文件输入流创建FileChannel
                FileChannel inChannel = new FileInputStream(f).getChannel();
                //以文件输出流创建FileChannel，用以控制输出
                FileChannel outChannel = new FileOutputStream("a.txt").getChannel()) {
            //将FileChannel里的全部数据映射成ByteBuffer
            MappedByteBuffer buffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, f.length());
            //使用GBK的字符集来创建解码器
            Charset charset = Charset.forName("utf-8");
            //直接将buffer里的数据全部输出
            outChannel.write(buffer);
            //再次调用buffer的clear()方法，复原limit/position的位置
            buffer.clear();
            //创建解码器(CharsetDecoder对象)
            CharsetDecoder decoder = charset.newDecoder();
            //使用解码器ByteBuffer转换成CharBuffer
            CharBuffer charBuffer = decoder.decode(buffer);
            //CharBuffer的toString方法可以获取对应的字符串
            System.out.println(charBuffer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void randomFileChannelTest(){
        File f = new File("a.txt");
        try(
                //创建一个RandomAccessFile对象
                RandomAccessFile raf = new RandomAccessFile(f, "rw");
                //获取RandomAccessFile对应的channel
                FileChannel randomChannel = raf.getChannel()){
            //将Channel中的所有数据映射成ByteBuffer
            ByteBuffer buffer = randomChannel.map(FileChannel.MapMode.READ_ONLY, 0, f.length());
            //把Channel的记录指针移到最后
            randomChannel.position(f.length());
            //将buffer中的所有数据输出
            randomChannel.write(buffer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readFile(){
        try(
                //创建文件输入流
                FileInputStream fis = new FileInputStream(fullName);
                //创建一个FileChannel
                FileChannel fileChannel = fis.getChannel()
        ) {
            //定义一个ByteBuffer对象，用于重复取水
            ByteBuffer byteBuffer = ByteBuffer.allocate(512);

            //将FileChannel中的数据放入ByteBuffer中
            while(fileChannel.read(byteBuffer) != -1){
                //锁定Buffer空白区
                byteBuffer.flip();
                //创建Charset对象
                Charset charset = Charset.forName("utf-8");
                //创建解码器
                CharsetDecoder decoder = charset.newDecoder();
                CharBuffer charBuffer = null;
                try {
                    //将ByteBuffer的内容转码
                    charBuffer = decoder.decode(byteBuffer);
                } catch (MalformedInputException e){
                    String msg = e.getMessage();
                    if(msg.contains("1")){

                    }else if(msg.contains("2")){

                    }
                }
                System.out.println(charBuffer);
                //将Buffer初始化，为下一次读取数据做准备
                byteBuffer.clear();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        NIOTest test = new NIOTest();
//        test.bufferTest();
//        test.fileChannelTest();
//        test.randomFileChannelTest();
        test.readFile();
    }

}
