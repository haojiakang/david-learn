package com.david.logback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by haojk on 5/11/16.
 */
public class LogbackDemo {

    private static Logger logger = LoggerFactory.getLogger(LogbackDemo.class);

    public static void main(String[] args){
        logger.trace("======trace");
        logger.debug("======debug");
        logger.info("======info");
        logger.warn("======warn");
        logger.error("======error");

        String name = "Jackie";
        String message = "Thank you!";
        String[] fruits = {"apple", "banana"};

        //logback提供的可以使用变量的打印方式，结果为Hello, Jackie！
        logger.info("Hello, {}", name);

        //可以有多个参数，结果为Hello, Jackie! Thank you!
        logger.info("Hello, {}, {}!", name, message);

        //可以传入一个数组，结果为Fruit: apple, banana
        logger.info("Fruit: {}, {}", fruits);

    }

}
