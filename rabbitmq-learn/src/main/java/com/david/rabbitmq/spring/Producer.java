package com.david.rabbitmq.spring;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.atomic.AtomicInteger;

public class Producer {

    @Autowired
    private AmqpTemplate amqpTemplate;

    private final AtomicInteger counter = new AtomicInteger();

    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("producer.xml");
    }

    @Scheduled(fixedRate = 1000)
    public void execute() {
        System.out.println("execute...");
        amqpTemplate.convertAndSend("hello " + counter.incrementAndGet());
        System.out.println("done");
    }
}