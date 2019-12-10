package com.hsunfkqm.storm.framework.cluster.impl;


import com.google.common.collect.Lists;
import com.hsunfkqm.storm.framework.cluster.ClusterStrategy;
import com.hsunfkqm.storm.framework.internal.ProviderService;
import org.apache.commons.lang3.RandomUtils;

import java.util.List;

/**
 * @author hsun
 * @Descrption 加权随机
 * @DATE 19-12-07 下午10:35
 ***/
public class WeightRandomClusterStrategyImpl implements ClusterStrategy {


    @Override
    public ProviderService select(List<ProviderService> providerServices) {
        //存放加权后的服务提供者列表
        List<ProviderService> providerList = Lists.newArrayList();
        for (ProviderService provider : providerServices) {
            int weight = provider.getWeight();
            for (int i = 0; i < weight; i++) {
                providerList.add(provider.copy());
            }
        }

        int MAX_LEN = providerList.size();
        int index = RandomUtils.nextInt(0, MAX_LEN - 1);
        return providerList.get(index);
    }
}
