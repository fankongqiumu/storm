package com.hsunfkqm.storm.framework.zookeeper;

import com.hsunfkqm.storm.framework.internal.InvokerService;
import com.hsunfkqm.storm.framework.internal.ProviderService;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

/**
 * @author hsun
 * @Descrption 服务治理
 * @DATE 19-12-04 下午10:06
 ***/
public interface IRegisterCenter4Governance {

    /**
     * 获取服务提供者列表与服务消费者列表
     *
     * @param serviceName
     * @param appKey
     * @return
     */
    Pair<List<ProviderService>, List<InvokerService>> queryProvidersAndInvokers(String serviceName, String appKey);


}
