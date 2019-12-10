package com.hsunfkqm.storm.framework.serialization.serializer.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.hsunfkqm.storm.framework.serialization.common.FDateJsonDeserializer;
import com.hsunfkqm.storm.framework.serialization.common.FDateJsonSerializer;
import com.hsunfkqm.storm.framework.serialization.serializer.Serializer;

import java.util.Date;

/**
 * @author hsun
 * @Descrption
 * @DATE 19-12-01 下午08:32
 ***/
public class JSONSerializer implements Serializer {


    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        SimpleModule module = new SimpleModule("DateTimeModule", Version.unknownVersion());
        module.addSerializer(Date.class, new FDateJsonSerializer());
        module.addDeserializer(Date.class, new FDateJsonDeserializer());

        objectMapper.registerModule(module);

    }

    private static ObjectMapper getObjectMapperInstance() {
        return objectMapper;
    }


    public <T> byte[] serialize(T obj) {
        if (obj == null) {
            return new byte[0];
        }

        try {
            String json = objectMapper.writeValueAsString(obj);
            return json.getBytes();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public <T> T deserialize(byte[] data, Class<T> clazz) {
        String json = new String(data);
        try {
            return (T) objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
