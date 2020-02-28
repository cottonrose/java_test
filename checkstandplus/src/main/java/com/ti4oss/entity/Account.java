package com.ti4oss.entity;

import com.ti4oss.common.AccountStatus;
import com.ti4oss.common.AccountType;
import lombok.Data;

/**
 * Author: Cottonrose
 * Created: 2019/5/9
 */
@Data
public class Account {
    
    private Integer id;
    private String username;
    private String password;
    private String name;
    private AccountType accountType;
    private AccountStatus accountStatus;

    
}
