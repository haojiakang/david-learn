package com.david.learn;

import com.weibo.api.motan.transport.async.MotanAsync;

/**
 * Created by jiakang on 2017/8/17.
 */
@MotanAsync
public interface FooService {

    String hello(String name);
}
