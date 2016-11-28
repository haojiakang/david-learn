package com.david.cisco.PortSystem;

import org.junit.Test;

/**
 * Created by haojk on 11/11/16.
 */
public class TestClass {

    @Test
    public void testToString() {
        Traffic traffic = new Traffic();
        traffic.setLayer4Port(4);
        traffic.setName("传输");
        traffic.setType("形式");
        System.out.println(traffic);
    }

}
