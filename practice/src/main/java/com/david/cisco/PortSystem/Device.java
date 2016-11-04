package com.david.cisco.PortSystem;

import java.util.List;

/**
 * Created by haojk on 11/4/16.
 */
public class Device {

    private String name;
    private List<PhysicalPort> portList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PhysicalPort> getPortList() {
        return portList;
    }

    public void setPortList(List<PhysicalPort> portList) {
        this.portList = portList;
    }

    @Override
    public String toString() {
        return "Device{" +
                "name='" + name + '\'' +
                ", portList=" + portList +
                '}';
    }
}
