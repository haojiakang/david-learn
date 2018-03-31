package com.david.test.bean;

import org.springframework.beans.factory.annotation.Value;

public class TestAnnotationBean {
    private long timeout;
    private String name;

    public long getTimeout() {
        return timeout;
    }

    @Value("${timeout:0}")
    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public String getName() {
        return name;
    }

    @Value("${name:Jane}")
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TestAnnotationBean{" +
                "timeout=" + timeout +
                ", name='" + name + '\'' +
                '}';
    }
}
