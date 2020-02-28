package com.bit.java.client.dao;

import com.bit.java.client.entity.User;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountDaoTest {
    private AccountDao accountDao = new AccountDao();
    @Test
    public void userReg() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("123");
        user.setBrief("帅");
        boolean flag = accountDao.userReg(user);
        Assert.assertTrue(flag);

    }

    @Test
    public void userLogin() {
        String username = "test";
        String password = "123";
        User user = accountDao.userLogin(username,password);
        System.out.println(user);
        Assert.assertNotNull(user);
    }
}