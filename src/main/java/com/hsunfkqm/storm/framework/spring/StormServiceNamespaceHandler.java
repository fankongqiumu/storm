package com.hsunfkqm.storm.framework.spring;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @author hsun
 * @Descrption 服务发布自定义标签
 * @DATE 19-12-06 下午7:42
 ***/
public class StormServiceNamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        registerBeanDefinitionParser("service", new ProviderFactoryBeanDefinitionParser());
    }
}
