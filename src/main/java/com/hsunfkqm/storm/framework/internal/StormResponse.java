package com.hsunfkqm.storm.framework.internal;

import java.io.Serializable;

/**
 * @author hsun
 * @Descrption
 * @DATE 19-12-01 下午09:54
 ***/
public class StormResponse implements Serializable {

    private static final long serialVersionUID = 5785265307118147202L;
    //UUID,唯一标识一次返回值
    private String uniqueKey;
    //客户端指定的服务超时时间
    private long invokeTimeout;
    //接口调用返回的结果对象
    private Object result;

    public String getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    public long getInvokeTimeout() {
        return invokeTimeout;
    }

    public void setInvokeTimeout(long invokeTimeout) {
        this.invokeTimeout = invokeTimeout;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
