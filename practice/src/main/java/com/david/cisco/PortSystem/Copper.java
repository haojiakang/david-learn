package com.david.cisco.PortSystem;

/**
 * Created by haojk on 11/4/16.
 */
public class Copper extends PhysicalPort {

    private Device device;

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    @Override
    public String toString() {
        return "Copper{" +
                "id=" + id +
                "device=" + device +
                '}';
    }
}
