package com.david.cisco.PortSystem;

/**
 * Created by haojk on 11/4/16.
 */
public class Traffic {

    private String name;
    private String type;
    private int layer4Port;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLayer4Port() {
        return layer4Port;
    }

    public void setLayer4Port(int layer4Port) {
        this.layer4Port = layer4Port;
    }

    /*@Override
    public String toString() {
        return "Traffic{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", layer4Port=" + layer4Port +
                '}';
    }*/
}
