package com.hsunfkqm.storm.framework.test;

/**
 * @author hsun
 * @Descrption
 * @DATE 19-12-10 下午7:29
 ***/
public class UserServiceImpl implements UserService {
    @Override
    public String getNameById(Integer id) {
        return "Test-" + id;
    }
}
