package com.david.cisco.PortSystem;

/**
 * Created by haojk on 11/4/16.
 */
public class Fiber extends PhysicalPort {

    private String wavelength;
    private Device device;

    public String getWavelength() {
        return wavelength;
    }

    public void setWavelength(String wavelength) {
        this.wavelength = wavelength;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    @Override
    public String toString() {
        return "Fiber{" +
                "id=" + id +
                "wavelength='" + wavelength + '\'' +
                ", device=" + device +
                '}';
    }
}
