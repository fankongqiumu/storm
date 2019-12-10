package com.hsunfkqm.storm.framework.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author hsun
 * @Descrption storm服务
 * @DATE 19-11-23 下午7:09
 ***/
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface StomService {
    Class interfaceClass();
    long timeout();
}
