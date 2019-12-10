package com.hsunfkqm.storm.framework.test;

/**
 * @author hsun
 * @Descrption storm服务实现
 * @DATE  19-12-03 下午8:30
 ***/
public class HelloServiceImpl implements HelloService {


    @Override
    public String sayHello(String somebody) {
        return "hello " + somebody + "!";
    }


}
