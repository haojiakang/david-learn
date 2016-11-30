package com.david.learn;


import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.common.RemotingHelper;

/**
 * Created by haojk on 11/28/16.
 *
 */
public class SimpleQuickStart {

    public static void main(String[] args) throws MQClientException, InterruptedException {

        //设置NameSev地址
        System.setProperty("NAMESRV_ADDR", "localhost:9876");

        DefaultMQProducer producer = new DefaultMQProducer("YOUR_PRODUCER_GROUP"); //(1)
        producer.start(); //(2)

        for (int i = 0; i < 1000; i++) {
            try {
                Message message = new Message("TopicTest", // topic (3)
                        "TagA", // tag (4)
                        ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET) // body (5)
                );
                SendResult sendResult = producer.send(message); //(6)
                System.out.println(sendResult);
            } catch (Exception e) {
                e.printStackTrace();
                Thread.sleep(1000L);
            }
        }
        producer.shutdown();
    }
}
