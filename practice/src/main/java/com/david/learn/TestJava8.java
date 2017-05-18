package com.david.learn;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by jiakang on 2017/4/27.
 */
public class TestJava8 {

//    作者：铲草除根
//    链接：https://www.zhihu.com/question/21563900/answer/151439929
//    来源：知乎
//    著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

    @Test
    public void test3() {
        List<String> names = getList();
        long start = System.currentTimeMillis();
        List<String> toUpperCase = names.stream().
                map(String::toUpperCase).collect(Collectors.toList());
        calculateTime("lambda and stream", start);
    }

    private List<String> getList() {
        List<String> names = new ArrayList<>();
        for (int i = 0; i < 500000; i++) {
            names.add("xiaoming" + i);
            names.add("laowang" + i);
        }
        return names;
    }

    private void calculateTime(String method, long start) {

        long end = System.currentTimeMillis();
        System.out.println("开始时间：" + start);
        System.out.println("结束时间：" + end);
        System.out.println("耗时：" + (end - start));
        System.out.println("方法：" + method);
    }

    @Test
    public void test4() {
        List<String> names = getList();
        long start = System.currentTimeMillis();
        List<String> toUpperCase = new ArrayList<>();
        for (String val : names) {
            toUpperCase.add(val.toUpperCase());
        }
        calculateTime("for loop", start);
    }

    @Test
    public void test5() {
        test3();
        test4();
    }

}
