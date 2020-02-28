package com.bit.java.util;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

//封装公共工具方法，如加载配置文件，json序列化等


public class CommUtils {
    private static final Gson GSON = new GsonBuilder().create();

    //加载配置文件，fileName为要加载的配置文件名称
    public static Properties  loadProperties(String fileName){
        Properties properties = new Properties();
        InputStream in = CommUtils.class.getClassLoader().
                getResourceAsStream(fileName);
        try {
            properties.load(in);
        } catch (IOException e) {
           return null;
        }
        return properties;
    }
    /*
    * 将任意对象序列化为json字符串
    * */
    public static String objectToJson(Object object){
        return GSON.toJson(object);
    }

    /*
    * 将任意对象反序列化为对象
    * objClass：反序列化的反射对象
    * */
    public static Object jsonToObject(String jsonStr,Class objClass){
        return GSON.fromJson(jsonStr,objClass);
    }
}
