package com.david.test.aop;

import java.util.Date;

/**
 * Created by jiakang on 2017/7/7.
 */
public class UserManager {

    private Date dateValue;

    public Date getDateValue() {
        return dateValue;
    }

    public void setDateValue(Date dateValue) {
        this.dateValue = dateValue;
    }

    @Override
    public String toString() {
        return "UserManager{" +
                "dateValue=" + dateValue +
                '}';
    }
}
