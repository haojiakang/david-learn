package com.david.cisco.PortSystem;

/**
 * Created by haojk on 11/4/16.
 */
public class TrafficXPort {

    private Traffic trafficI;
    private PhysicalPort port;
    private String action;

    public Traffic getTrafficI() {
        return trafficI;
    }

    public void setTrafficI(Traffic trafficI) {
        this.trafficI = trafficI;
    }

    public PhysicalPort getPort() {
        return port;
    }

    public void setPort(PhysicalPort port) {
        this.port = port;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "TrafficXPort{" +
                "trafficI=" + trafficI +
                ", port=" + port +
                ", action='" + action + '\'' +
                '}';
    }
}
