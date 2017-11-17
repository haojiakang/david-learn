package com.david.learn;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiakang on 2017/10/31.
 */
public class Publisher {

    public List<Subscriber> subscribers = new ArrayList<>();

    public void send(String msg) {
        for (Subscriber subscriber : subscribers) {
            subscriber.receive(msg);
        }
    }
}
