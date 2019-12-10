package com.hsunfkqm.storm.framework.internal;

/**
 * @author hsun
 * @Descrption
 * @DATE 19-11-24 下午5:32
 ***/
public class StormApplicationContext {
    private String appName;
    private String registryAddress;
    private String protocolName;
    private String protocolPort;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getRegistryAddress() {
        return registryAddress;
    }

    public void setRegistryAddress(String registryAddress) {
        this.registryAddress = registryAddress;
    }

    public String getProtocolName() {
        return protocolName;
    }

    public void setProtocolName(String protocolName) {
        this.protocolName = protocolName;
    }

    public String getProtocolPort() {
        return protocolPort;
    }

    public void setProtocolPort(String protocolPort) {
        this.protocolPort = protocolPort;
    }
}
