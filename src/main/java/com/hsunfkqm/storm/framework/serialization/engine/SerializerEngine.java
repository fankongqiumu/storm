package com.hsunfkqm.storm.framework.serialization.engine;



import avro.shaded.com.google.common.collect.Maps;
import com.hsunfkqm.storm.framework.serialization.common.SerializeType;
import com.hsunfkqm.storm.framework.serialization.serializer.Serializer;
import com.hsunfkqm.storm.framework.serialization.serializer.impl.*;

import java.util.Map;

/**
 * @author hsun
 * @Descrption  序列化引擎
 * @DATE 19-12-05 下午9:29
 ***/
public class SerializerEngine {

    public static final Map<SerializeType, Serializer> serializerMap = Maps.newConcurrentMap();

    static {
        serializerMap.put(SerializeType.DefaultJavaSerializer, new DefaultJavaSerializer());
        serializerMap.put(SerializeType.HessianSerializer, new HessianSerializer());
        serializerMap.put(SerializeType.JSONSerializer, new JSONSerializer());
        serializerMap.put(SerializeType.XmlSerializer, new XmlSerializer());
        serializerMap.put(SerializeType.ProtoStuffSerializer, new ProtoStuffSerializer());
        serializerMap.put(SerializeType.MarshallingSerializer, new MarshallingSerializer());

        //以下三类不能使用普通的java bean
        serializerMap.put(SerializeType.AvroSerializer, new AvroSerializer());
        serializerMap.put(SerializeType.ThriftSerializer, new ThriftSerializer());
        serializerMap.put(SerializeType.ProtocolBufferSerializer, new ProtocolBufferSerializer());
    }


    public static <T> byte[] serialize(T obj, String serializeType) {
        SerializeType serialize = SerializeType.queryByType(serializeType);
        if (serialize == null) {
            throw new RuntimeException("serialize is null");
        }

        Serializer serializer = serializerMap.get(serialize);
        if (serializer == null) {
            throw new RuntimeException("serialize error");
        }

        try {
            return serializer.serialize(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static <T> T deserialize(byte[] data, Class<T> clazz, String serializeType) {

        SerializeType serialize = SerializeType.queryByType(serializeType);
        if (serialize == null) {
            throw new RuntimeException("serialize is null");
        }
        Serializer serializer = serializerMap.get(serialize);
        if (serializer == null) {
            throw new RuntimeException("serialize error");
        }

        try {
            return serializer.deserialize(data, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
