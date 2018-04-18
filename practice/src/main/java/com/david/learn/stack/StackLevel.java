package com.david.learn.stack;

import com.david.common.util.Consoles;

/**
 * Created by jiakang on 2018/4/18
 *
 * @author jiakang
 */
public class StackLevel {

    private int level = 1;

    public void stackLevel() {
        String buf = "asdfadf";
        level ++;
        stackLevel();
    }

    public static void main(String[] args) {
        StackLevel si = new StackLevel();
        try {
            si.stackLevel();
        } catch (StackOverflowError e) {
            Consoles.info(si.level);
        }
    }
}