package com.david.lsp;

import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.util.Random;

/**
 * Created by fsdevops on 8/22/16.
 */
public class ProtoStuffTest {

    /**
     * 产生一个随机字符串
     */
    public static String randomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyz0123456789";
        int strlen = str.length();
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int num = random.nextInt(strlen);
            sb.append(str.charAt(num));
        }
        return sb.toString();
    }

    private static ResourceObj getObj(String name, String path, int contentSize) {
        ResourceObj obj = new ResourceObj(name, path, "");
        obj.setContent(randomString(contentSize));
        return obj;
    }

    private static long speedText(int contentSize, int times) {
        ResourceObj obj = getObj("1b.conf", "/home/fsdevops/conf/1b", contentSize);
        long start = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            byte[] bytes = ProtoStuffUtil.serializer(obj);
            ProtoStuffUtil.deserializer(bytes, ResourceObj.class);
        }
        long end = System.currentTimeMillis();
        return end - start;
    }

    private static long speedTextOrg(int contentSize, int times) throws IOException, ClassNotFoundException {
        ResourceObj obj = getObj("1b.conf", "/home/fsdevops/conf/1b", contentSize);
        long start = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            byte[] bytes = baos.toByteArray();

            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            ois.readObject();
        }
        long end = System.currentTimeMillis();
        return end - start;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        System.out.println(speedTextOrg(9999999, 1));
        System.out.println(speedText(2, 1));
    }

    private static void test() {
        ResourceObj obj = getObj("1b.conf", "/home/fsdevops/conf/1b", 88888);
        byte[] bytes = ProtoStuffUtil.serializer(obj);

        ResourceObj obj2 = ProtoStuffUtil.deserializer(bytes, ResourceObj.class);
        System.out.println(obj2.getName());
        System.out.println(obj2.getPath());
        System.out.println(StringUtils.equals(obj.getContent(), obj2.getContent()));
    }
}
