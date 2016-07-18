package com.david.rabbitmq.spring;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Consumer implements MessageListener {

    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("consumer.xml");
    }

    public void onMessage(Message message) {
        System.out.println(message);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}