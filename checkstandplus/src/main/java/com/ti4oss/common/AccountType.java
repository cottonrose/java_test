package com.ti4oss.common;

import lombok.Getter;
import lombok.ToString;

/**
 * Author: Cottonrose
 * Created: 2019/5/9
 */
@Getter
@ToString
public enum AccountType {
    
    ADMIN(1, "管理员"), CUSTOMER(2, "客户");
    
    private final int flag;
    private final String desc;
    
    AccountType(int flag, String desc) {
        this.flag = flag;
        this.desc = desc;
    }
    
    public static AccountType valueOf(int flag) {
        for (AccountType accountType : values()) {
            if (accountType.flag == flag) {
                return accountType;
            }
        }
        throw new RuntimeException("AccountType flag " + flag + " not found.");
    }
}