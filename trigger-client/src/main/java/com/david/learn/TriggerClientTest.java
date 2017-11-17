package com.david.learn;

import cn.sina.api.commons.cache.MemcacheClient;
import cn.sina.api.data.protobuf.FirehoseMessageWrap;
import com.weibo.trigger.common.bean.MessageBatch;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created by jiakang on 2017/9/14.
 */
public class TriggerClientTest {

    private static MemcacheClient client;
    public static final String TOPIC = "other";

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context= new ClassPathXmlApplicationContext("classpath:test-client-status.xml");
        client = (MemcacheClient)context.getBean("commonMessageTriggerClient");

        while(true) {
            try {
                MessageBatch batch = (MessageBatch) client.get(TOPIC);

                if (batch == null) {
                    continue;
                }
//                List<String> messages = batch.getResultJson();
//                for (String message : messages) {
//                    System.out.println(message);
//                }
                List<byte[]> messageBytes = batch.getFirehoseMessageFromTrigger();
                for (byte[] messageByte : messageBytes) {
                    FirehoseMessageWrap.FirehoseMessage firehoseMessage = FirehoseMessageWrap.FirehoseMessage.parseFrom(messageByte);
                    if (firehoseMessage == null) {
                        continue;
                    }
                    System.out.println(firehoseMessage);
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }
}
