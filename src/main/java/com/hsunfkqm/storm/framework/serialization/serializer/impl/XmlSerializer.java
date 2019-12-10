package com.hsunfkqm.storm.framework.serialization.serializer.impl;


import com.hsunfkqm.storm.framework.serialization.serializer.ISerializer;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * @author hsun
 * @Descrption
 * @DATE 19-11-21 下午6:39
 ***/
public class XmlSerializer implements ISerializer {

    private static final XStream xStream = new XStream(new DomDriver());


    public <T> byte[] serialize(T obj) {
        return xStream.toXML(obj).getBytes();
    }


    public <T> T deserialize(byte[] data, Class<T> clazz) {
        String xml = new String(data);
        return (T) xStream.fromXML(xml);
    }


}
