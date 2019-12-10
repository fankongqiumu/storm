package com.hsunfkqm.storm.framework.register;


import com.hsunfkqm.storm.framework.internal.ProviderService;

import java.util.List;
import java.util.Map;

/**
 * @author hsun
 * @Descrption 服务端注册中心接口定义
 * @DATE 19-11-24 下午11:34
 ***/
public interface IRegisterCenter4Provider {


    /**
     * 服务端将服务提供者信息注册到zk对应的节点下
     *
     * @param serviceMetaData
     */
    void registerProvider(final List<ProviderService> serviceMetaData);


    /**
     * 服务端获取服务提供者信息
     * <p/>
     * 注:返回对象,Key:服务提供者接口  value:服务提供者服务方法列表
     *
     * @return
     */
    Map<String, List<ProviderService>> getProviderServiceMap();
}
