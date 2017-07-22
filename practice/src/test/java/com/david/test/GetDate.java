package com.david.test;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by jiakang on 2017/7/12.
 */
public class GetDate {

    @Test
    public void getDateOfTomorrow() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DATE);
        calendar.set(Calendar.DATE, day + 1);
        Date date = calendar.getTime();
        long time = date.getTime();
        System.out.println(time);
    }

    @Test
    public void getCurrentTimeAdded() {
        long curr = System.currentTimeMillis();
        long added = curr + 10000;
        System.out.println(added);
    }
}
