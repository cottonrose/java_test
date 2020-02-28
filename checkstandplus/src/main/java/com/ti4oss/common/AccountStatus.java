package com.ti4oss.common;

import lombok.Getter;
import lombok.ToString;

/**
 * Author: Cottonrose
 * Created: 2019/5/9
 */
@Getter
@ToString
public enum AccountStatus {
    
    UNLOCK(1, "启用"), LOCK(2, "锁定");
    
    private final int flag;
    private final String desc;
    
    AccountStatus(int flag, String desc) {
        this.flag = flag;
        this.desc = desc;
    }
    
    public static AccountStatus valueOf(int flag) {
        for (AccountStatus accountStatus : values()) {
            if (accountStatus.flag == flag) {
                return accountStatus;
            }
        }
        throw new RuntimeException("AccountStatus flag " + flag + " not found.");
    }
}
