package com.david.test.customTag;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Created by jiakang on 2017/4/28.
 */
public class MyNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser("user", new UserBeanDefinitionParser());
    }
}
