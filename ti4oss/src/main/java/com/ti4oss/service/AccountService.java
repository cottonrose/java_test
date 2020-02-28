package com.ti4oss.service;

import com.ti4oss.dao.AccountDao;
import com.ti4oss.entity.Account;

import java.util.List;

/**
 * Author: Cottonrose
 * Created: 2019/5/10
 */
public class AccountService {
    
    private AccountDao accountDao;
    
    public AccountService() {
        this.accountDao = new AccountDao();
    }
    
    public boolean checkDuplicateUserName(String username) {
        return this.accountDao.checkUserName(username);
    }
    
    public boolean register(Account account) {
        return this.accountDao.register(account);
    }
    
    public Account login(String username, String password ) {
        return this.accountDao.login(username,password);
    }
    
    public List<Account> queryAllAccount(){
        return this.accountDao.queryAccount();
    }
    
}
