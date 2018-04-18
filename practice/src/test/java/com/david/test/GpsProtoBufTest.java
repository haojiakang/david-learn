package com.david.test;

import com.david.learn.protobuf.GpsData;
import com.google.protobuf.util.JsonFormat;
import org.junit.Test;

public class GpsProtoBufTest {

    @Test
    public void test() {
        System.out.println("构建一个GPS模型开始。。。");
        GpsData.gps_data.Builder gps_builder = GpsData.gps_data.newBuilder();
        gps_builder.setAltitude(1);
        gps_builder.setDataTime("2018-04-13 16:21:44");
        gps_builder.setGpsStatus(1);
        gps_builder.setLat(39.123);
        gps_builder.setLon(120.112);
        gps_builder.setDirection(30.2F);
        gps_builder.setId(100L);

        GpsData.gps_data gps_data = gps_builder.build();
        System.out.println(gps_data.toString());
        System.out.println("构建GPS模型结束");

        System.out.println("gps byte 开始");
//        for (byte b : gps_data.toByteArray()) {
//            System.out.println(b);
//        }
        System.out.println("\n" + "bytes长度" + gps_data.toByteString().size());
        System.out.println("gps byte 结束");

        System.out.println("使用gps 反序列化生成对象开始");
        GpsData.gps_data gd = null;
        try {
            gd = GpsData.gps_data.parseFrom(gps_data.toByteArray());
            System.out.println(gd.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("使用gps反序列化生成对象结束");

        System.out.println("使用gps转成json对象开始。。。");
        String jsonFromatM = "";
        try {
            jsonFromatM = JsonFormat.printer().print(gd);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(jsonFromatM);
        System.out.println("json数据大小：" + jsonFromatM.getBytes().length);
        System.out.println("使用gps转成json对象结束");
    }
}
