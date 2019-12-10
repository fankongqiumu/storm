package com.hsunfkqm.storm.framework.cluster.impl;


import com.hsunfkqm.storm.framework.cluster.ClusterStrategy;
import com.hsunfkqm.storm.framework.internal.ProviderService;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author hsun
 * @Descrption 轮询
 * @DATE 19-12-07 下午10:32
 ***/
public class PollingClusterStrategyImpl implements ClusterStrategy {

    //计数器
    private int index = 0;
    private Lock lock = new ReentrantLock();

    @Override
    public ProviderService select(List<ProviderService> providerServices) {

        ProviderService service = null;
        try {
            lock.tryLock(10, TimeUnit.MILLISECONDS);
            //若计数大于服务提供者个数,将计数器归0
            if (index >= providerServices.size()) {
                index = 0;
            }
            service = providerServices.get(index);
            index++;

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        //兜底,保证程序健壮性,若未取到服务,则直接取第一个
        if (service == null) {
            service = providerServices.get(0);
        }
        return service;
    }
}
