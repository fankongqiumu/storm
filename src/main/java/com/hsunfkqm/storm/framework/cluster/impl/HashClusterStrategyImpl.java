package com.hsunfkqm.storm.framework.cluster.impl;



import com.hsunfkqm.storm.framework.cluster.ClusterStrategy;
import com.hsunfkqm.storm.framework.helper.IPHelper;
import com.hsunfkqm.storm.framework.internal.ProviderService;

import java.util.List;

/**
 * @author hsun
 * @Descrption 哈希
 * @DATE 19-12-07 下午10:34
 ***/
public class HashClusterStrategyImpl implements ClusterStrategy {

    @Override
    public ProviderService select(List<ProviderService> providerServices) {
        //获取调用方ip
        String localIP = IPHelper.localIp();
        //获取源地址对应的hashcode
        int hashCode = localIP.hashCode();
        //获取服务列表大小
        int size = providerServices.size();

        return providerServices.get(hashCode % size);
    }
}
