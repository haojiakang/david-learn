package com.david.test.hutool;

import cn.hutool.log.StaticLog;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * Created by jiakang on 2018/5/3
 *
 * @author jiakang
 */
@Slf4j
public class HutoolTest {

    @Test
    public void test() {
        StaticLog.info("haha, this is {}", "a dog");
        log.info("haha, this is {}", "a dog");
    }

    @Test
    public void test1() {
        Inner inner = new Inner();
        inner.clone();
    }

    private class Inner {
        @Override
        protected Object clone() {
            try {
                return super.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}