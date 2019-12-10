package com.hsunfkqm.storm.framework.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author hsun
 * @Descrption
 * @DATE 19-11-23 下午07:35
 ***/
public class MainServer {


    public static void main(String[] args) throws Exception {

        //发布服务
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("storm-server.xml");
        System.out.println(" 服务发布完成");
    }
}
