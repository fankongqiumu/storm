package com.hsunfkqm.storm.framework.cluster.impl;

import com.hsunfkqm.storm.framework.cluster.ClusterStrategy;
import com.hsunfkqm.storm.framework.internal.ProviderService;
import org.apache.commons.lang3.RandomUtils;

import java.util.List;

/**
 * @author hsun
 * @Descrption 随机
 * @DATE 19-12-07 下午10:32
 ***/
public class RandomClusterStrategyImpl implements ClusterStrategy {
    @Override
    public ProviderService select(List<ProviderService> providerServices) {
        int MAX_LEN = providerServices.size();
        int index = RandomUtils.nextInt(0, MAX_LEN - 1);
        return providerServices.get(index);
    }

}
