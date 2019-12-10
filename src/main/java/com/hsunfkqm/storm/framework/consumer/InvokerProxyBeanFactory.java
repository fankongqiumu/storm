package com.hsunfkqm.storm.framework.consumer;


import com.hsunfkqm.storm.framework.cluster.ClusterStrategy;
import com.hsunfkqm.storm.framework.cluster.engine.ClusterEngine;
import com.hsunfkqm.storm.framework.internal.StormRequest;
import com.hsunfkqm.storm.framework.internal.StormResponse;
import com.hsunfkqm.storm.framework.internal.ProviderService;
import com.hsunfkqm.storm.framework.register.IRegisterCenter4Invoker;
import com.hsunfkqm.storm.framework.register.RegisterCenter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author hsun
 * @Descrption Netty 消费端bean代理工厂
 * @DATE 19-12-03 下午08:19
 ***/
public class InvokerProxyBeanFactory implements InvocationHandler {

    private ExecutorService fixedThreadPool = null;
    private Class<?> targetInterface;
    //超时时间
    private int consumeTimeout;
    private static int threadWorkerNumber = 10;
    private String clusterStrategy;


    public InvokerProxyBeanFactory(Class<?> targetInterface, int consumeTimeout, String clusterStrategy) {
        this.targetInterface = targetInterface;
        this.consumeTimeout = consumeTimeout;
        this.clusterStrategy = clusterStrategy;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String serviceKey = targetInterface.getName();
        //获取某个接口的服务提供者列表
        IRegisterCenter4Invoker registerCenter4Consumer = RegisterCenter.singleton();
        List<ProviderService> providerServices = registerCenter4Consumer.getServiceMetaDataMap4Consume().get(serviceKey);
        ClusterStrategy clusterStrategyService = ClusterEngine.queryClusterStrategy(clusterStrategy);
        ProviderService providerService = clusterStrategyService.select(providerServices);
        ProviderService newProvider = providerService.copy();
        newProvider.setServiceMethod(method);
        newProvider.setServiceItf(targetInterface);

        final StormRequest request = new StormRequest();
        //设置本次调用的唯一标识
        request.setUniqueKey(UUID.randomUUID().toString() + "-" + Thread.currentThread().getId());
        request.setProviderService(newProvider);
        //设置本次调用的超时时间
        request.setInvokeTimeout(consumeTimeout);
        request.setInvokedMethodName(method.getName());
        request.setArgs(args);

        try {
            //构建用来发起调用的线程池
            if (fixedThreadPool == null) {
                synchronized (InvokerProxyBeanFactory.class) {
                    if (null == fixedThreadPool) {
                        fixedThreadPool = Executors.newFixedThreadPool(threadWorkerNumber);
                    }
                }
            }
            String serverIp = request.getProviderService().getServerIp();
            int serverPort = request.getProviderService().getServerPort();
            InetSocketAddress inetSocketAddress = new InetSocketAddress(serverIp, serverPort);
            Future<StormResponse> responseFuture = fixedThreadPool
                    .submit(InvokerServiceCallable.of(inetSocketAddress, request));
            //获取调用的返回结果
            StormResponse response = responseFuture
                    .get(request.getInvokeTimeout(), TimeUnit.MILLISECONDS);
            if (response != null) {
                return response.getResult();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    public Object getProxy() {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class<?>[]{targetInterface}, this);
    }


    private static volatile InvokerProxyBeanFactory singleton;

    /**
     * @param targetInterface
     * @param consumeTimeout
     * @param clusterStrategy
     * @return
     * @throws Exception
     */
    public static InvokerProxyBeanFactory singleton(Class<?> targetInterface, int consumeTimeout, String clusterStrategy) throws Exception {
        if (null == singleton) {
            synchronized (InvokerProxyBeanFactory.class) {
                if (null == singleton) {
                    singleton = new InvokerProxyBeanFactory(targetInterface, consumeTimeout, clusterStrategy);
                }
            }
        }
        return singleton;
    }


}
