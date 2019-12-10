package com.hsunfkqm.storm.framework.zookeeper;


import com.hsunfkqm.storm.framework.internal.InvokerService;
import com.hsunfkqm.storm.framework.internal.ProviderService;

import java.util.List;
import java.util.Map;

/**
 * @author hsun
 * @Descrption 消费端注册中心接口
 * @DATE 19-11-26 下午10:46
 ***/
public interface IRegisterCenter4Invoker {

    /**
     * 消费端初始化服务提供者信息本地缓存
     *
     * @param remoteAppKey
     * @param groupName
     */
    void initProviderMap(String remoteAppKey, String groupName);


    /**
     * 消费端获取服务提供者信息
     *
     * @return
     */
     Map<String, List<ProviderService>> getServiceMetaDataMap4Consume();


    /**
     * 消费端将消费者信息注册到zk对应的节点下
     *
     * @param invoker
     */
     void registerInvoker(final InvokerService invoker);


}
