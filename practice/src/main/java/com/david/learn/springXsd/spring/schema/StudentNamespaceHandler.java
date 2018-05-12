package com.david.learn.springXsd.spring.schema;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Created by jiakang on 2018/5/7
 *
 * @author jiakang
 */
public class StudentNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser("student", new PeopleBeanDefinitionParser());
    }
}