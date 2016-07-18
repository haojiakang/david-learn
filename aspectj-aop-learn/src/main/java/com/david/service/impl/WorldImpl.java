package com.david.service.impl;

import com.david.service.World;
import org.springframework.stereotype.Component;

/**
 * Created by haojk on 6/22/16.
 */
@Component("world")
public class WorldImpl implements World {
    public void bar() {
        System.out.println("执行world组件的bar()方法");
    }
}
