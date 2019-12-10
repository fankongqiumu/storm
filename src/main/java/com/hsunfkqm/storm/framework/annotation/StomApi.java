package com.hsunfkqm.storm.framework.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author hsun
 * @Descrption 针对stormService服务对外提供http调用
 * @DATE 19-11-23 下午7:13
 ***/
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface StomApi {
    String url();
}
