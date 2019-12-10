package com.hsunfkqm.storm.framework.spring;

import com.hsunfkqm.storm.framework.consumer.InvokerFactoryBean;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.w3c.dom.Element;

/**
 * @author hsun
 * @Descrption 服务引入解析
 * @DATE 19-11-23 下午6:09
 ***/
public class ConsumerFactoryBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

    //logger
    private static final Logger logger = LoggerFactory.getLogger(ConsumerFactoryBeanDefinitionParser.class);

    protected Class getBeanClass(Element element) {
        return InvokerFactoryBean.class;
    }

    protected void doParse(Element element, BeanDefinitionBuilder bean) {

        try {
            String timeOut = element.getAttribute("timeout");
            String targetInterface = element.getAttribute("interface");
            String clusterStrategy = element.getAttribute("clusterStrategy");
            String remoteAppKey = element.getAttribute("remoteAppKey");
            String groupName = element.getAttribute("groupName");

            bean.addPropertyValue("timeout", Integer.parseInt(timeOut));
            bean.addPropertyValue("targetInterface", Class.forName(targetInterface));
            bean.addPropertyValue("remoteAppKey", remoteAppKey);

            if (StringUtils.isNotBlank(clusterStrategy)) {
                bean.addPropertyValue("clusterStrategy", clusterStrategy);
            }
            if (StringUtils.isNotBlank(groupName)) {
                bean.addPropertyValue("groupName", groupName);
            }
        } catch (Exception e) {
            logger.error("ConsumerFactoryBeanDefinitionParser error.", e);
            throw new RuntimeException(e);
        }

    }
}
