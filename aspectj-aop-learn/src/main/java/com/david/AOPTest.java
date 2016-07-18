package com.david;

import com.david.service.Hello;
import com.david.service.World;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by haojk on 6/22/16.
 */
public class AOPTest {

    public static void main(String[] args) {
        ApplicationContext con = new ClassPathXmlApplicationContext("applicationContext.xml");
        Hello hello = (Hello) con.getBean("hello");
        hello.foo();
        hello.addUser("David", "123");
        World world = (World) con.getBean("world");
        world.bar();
    }

}
