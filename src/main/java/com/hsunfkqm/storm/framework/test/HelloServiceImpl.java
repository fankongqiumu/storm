package com.hsunfkqm.storm.framework.test;

import com.hsunfkqm.storm.framework.annotation.StomApi;

/**
 * @author hsun
 * @Descrption storm服务实现
 * @DATE  19-12-03 下午8:30
 ***/
public class HelloServiceImpl implements HelloService {


    @Override
    @StomApi(url = "storm.hello.sayHello")
    public String sayHello(String somebody) {
        return "hello " + somebody + "!";
    }


}
