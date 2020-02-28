package com.ti4oss.cmd.command.impl.account;

import com.ti4oss.cmd.command.Subject;
import com.ti4oss.cmd.command.annotation.AdminCommand;
import com.ti4oss.cmd.command.annotation.CommandMeta;
import com.ti4oss.cmd.command.impl.AbstractCommand;
import com.ti4oss.entity.Account;

import java.util.List;

/**
 * Author: Cottonrose
 * Created: 2019/5/17
 */
@CommandMeta(
        name = "CKZH",
        desc = "查看帐号",
        group = "帐号信息"
)
@AdminCommand
public class AccountBrowseCommand extends AbstractCommand {
    
    @Override
    public void execute(Subject subject) {
        List<Account> accountList = this.accountService.queryAllAccount();
        if (accountList.isEmpty()) {
            warningPrintln("暂无账号");
        } else {
            System.out.println("----------------  帐号列表信息  ----------------------");
            System.out.println("|  编号  |  姓名  |  账号  |  密码  |  类型  |  状态  |");
            for (Account account : accountList) {
                System.out.println(
                        new StringBuilder()
                                .append("|   ").append(account.getId()).append("   ")
                                .append("|   ").append(account.getName()).append("   ")
                                .append("|   ").append("******").append("   ")
                                .append("|   ").append(account.getAccountType().getDesc()).append("   ")
                                .append("|   ").append(account.getAccountStatus().getDesc()).append("   ")
                                .append("|   ")
                                .toString()
                );
            }
            System.out.println("------------------------------------------------------");
        }
    }
}
