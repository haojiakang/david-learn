package com.david.test;

import com.david.learn.Base62Util;
import org.junit.Test;

/**
 * Created by jiakang on 2017/7/26.
 */
public class Base62UtilTest {

    @Test
    public void encodeTest() {
//        long number = 4132101617847066L;
//        String encode = Base62Util.encode(number);
//        System.out.println(encode);
        String number = "4132101617847066";
        String s = Base62Util.encodeBase62(number);
        System.out.println(s);
    }
}
