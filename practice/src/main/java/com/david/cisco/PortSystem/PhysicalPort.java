package com.david.cisco.PortSystem;

/**
 * Created by haojk on 11/4/16.
 */
public class PhysicalPort {

    protected String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "PhysicalPort{" +
                "id='" + id + '\'' +
                '}';
    }
}
