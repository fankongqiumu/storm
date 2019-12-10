package com.hsunfkqm.storm.framework.spring;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @author hsun
 * @Descrption storm服务引入自定义标签
 * @DATE  19-12-03 下午8:30
 ***/
public class StormRemoteReferenceNamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        registerBeanDefinitionParser("reference", new ConsumerFactoryBeanDefinitionParser());
    }
}
