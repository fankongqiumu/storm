package com.hsunfkqm.storm.framework.internal;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author hsun
 * @Descrption Netty异步调用返回结果包装类
 * @DATE 19-12-01 下午09:44
 ***/
public class StormResponseWrapper {

    //存储返回结果的阻塞队列
    private BlockingQueue<StormResponse> responseQueue = new ArrayBlockingQueue<StormResponse>(1);
    //结果返回时间
    private long responseTime;

    /**
     * 计算该返回结果是否已经过期
     *
     * @return
     */
    public boolean isExpire() {
        StormResponse response = responseQueue.peek();
        if (response == null) {
            return false;
        }

        long timeout = response.getInvokeTimeout();
        if ((System.currentTimeMillis() - responseTime) > timeout) {
            return true;
        }
        return false;
    }

    public static StormResponseWrapper of() {
        return new StormResponseWrapper();
    }

    public BlockingQueue<StormResponse> getResponseQueue() {
        return responseQueue;
    }

    public long getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(long responseTime) {
        this.responseTime = responseTime;
    }
}
