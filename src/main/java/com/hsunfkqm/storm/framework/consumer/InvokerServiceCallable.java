package com.hsunfkqm.storm.framework.consumer;


import com.hsunfkqm.storm.framework.internal.StormRequest;
import com.hsunfkqm.storm.framework.internal.StormResponse;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * @author hsun
 * @Descrption Netty 请求发起线程
 * @DATE 19-11-25 下午08:51
 ***/
public class InvokerServiceCallable implements Callable<StormResponse> {

    private static final Logger logger = LoggerFactory.getLogger(InvokerServiceCallable.class);

    private Channel channel;
    private InetSocketAddress inetSocketAddress;
    private StormRequest request;

    public static InvokerServiceCallable of(InetSocketAddress inetSocketAddress, StormRequest request) {
        return new InvokerServiceCallable(inetSocketAddress, request);
    }


    public InvokerServiceCallable(InetSocketAddress inetSocketAddress, StormRequest request) {
        this.inetSocketAddress = inetSocketAddress;
        this.request = request;
    }

    @Override
    public StormResponse call() throws Exception {
        //初始化返回结果容器,将本次调用的唯一标识作为Key存入返回结果的Map
        InvokerResponseHolder.initResponseData(request.getUniqueKey());
        //根据本地调用服务提供者地址获取对应的Netty通道channel队列
        ArrayBlockingQueue<Channel> blockingQueue = NettyChannelPoolFactory.channelPoolFactoryInstance().acquire(inetSocketAddress);
        try {
            if (channel == null) {
                //从队列中获取本次调用的Netty通道channel
                channel = blockingQueue.poll(request.getInvokeTimeout(), TimeUnit.MILLISECONDS);
            }

            //若获取的channel通道已经不可用,则重新获取一个
            while (!channel.isOpen() || !channel.isActive() || !channel.isWritable()) {
                logger.warn("----------retry get new Channel------------");
                channel = blockingQueue.poll(request.getInvokeTimeout(), TimeUnit.MILLISECONDS);
                if (channel == null) {
                    //若队列中没有可用的Channel,则重新注册一个Channel
                    channel = NettyChannelPoolFactory.channelPoolFactoryInstance().registerChannel(inetSocketAddress);
                }
            }

            //将本次调用的信息写入Netty通道,发起异步调用
            ChannelFuture channelFuture = channel.writeAndFlush(request);
            channelFuture.syncUninterruptibly();

            //从返回结果容器中获取返回结果,同时设置等待超时时间为invokeTimeout
            long invokeTimeout = request.getInvokeTimeout();
            return InvokerResponseHolder.getValue(request.getUniqueKey(), invokeTimeout);
        } catch (Exception e) {
            logger.error("service invoke error.", e);
        } finally {
            //本次调用完毕后,将Netty的通道channel重新释放到队列中,以便下次调用复用
            NettyChannelPoolFactory.channelPoolFactoryInstance().release(blockingQueue, channel, inetSocketAddress);
        }
        return null;
    }
}
