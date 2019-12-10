package com.hsunfkqm.storm.framework.serialization.serializer;

/**
 * @author hsun
 * @Descrption
 * @DATE 19-11-27 下午08:32
 ***/
public interface ISerializer {

    /**
     * 序列化
     *
     * @param obj
     * @param <T>
     * @return
     */
     <T> byte[] serialize(T obj);


    /**
     * 反序列化
     *
     * @param data
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T deserialize(byte[] data, Class<T> clazz);
}
