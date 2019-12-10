package com.hsunfkqm.storm.framework.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author hsun
 * @Descrption
 * @DATE 19-11-23 下午09:48
 ***/
public class Client {

    private static final Logger logger = LoggerFactory.getLogger(Client.class);

    public static void main(String[] args) throws Exception {

        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("storm-client.xml");
        final HelloService helloService = (HelloService) context.getBean("helloService");
        String result = helloService.sayHello("World");
        System.out.println(result);
        System.exit(0);
    }
}
