package com.hsunfkqm.storm.framework.cluster;



import com.hsunfkqm.storm.framework.internal.ProviderService;

import java.util.List;

/**
 * @author hsun
 * @Descrption 负载均衡策略
 * @DATE 19-12-07 下午10:30
 ***/
public interface ClusterStrategy {

    /**
     * 负载策略算法
     *
     * @param providerServices
     * @return
     */
     ProviderService select(List<ProviderService> providerServices);
}
