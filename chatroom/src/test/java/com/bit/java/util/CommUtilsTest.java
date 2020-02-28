package com.bit.java.util;

import com.bit.java.client.entity.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.*;

public class CommUtilsTest {

    @Test
    //单元测试
    public void loadProperties() {
        String fileName = "datasource.properties";
        Properties properties = CommUtils.loadProperties(fileName);
        Assert.assertNotNull(properties);
    }

    @Test
    public void objectToJson() {
        User user = new User();
        user.setId(1);
        user.setUsername("test");
        user.setPassword("123");
        user.setBrief("帅");
        String str = CommUtils.objectToJson(user);
        System.out.println(str);
    }

    @Test
    public void jsonToObject() {
        String jsonStr = "{\"id\":1,\"username\":\"test\",\"password\":\"123\",\"brief\":\"帅\"}";
        User user = (User) CommUtils.jsonToObject(jsonStr,User.class);
        System.out.println(user);
    }
}