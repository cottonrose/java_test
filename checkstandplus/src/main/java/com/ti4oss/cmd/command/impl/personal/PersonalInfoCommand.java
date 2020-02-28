package com.ti4oss.cmd.command.impl.personal;

import com.ti4oss.cmd.command.Subject;
import com.ti4oss.cmd.command.annotation.AdminCommand;
import com.ti4oss.cmd.command.annotation.CommandMeta;
import com.ti4oss.cmd.command.annotation.CustomerCommand;
import com.ti4oss.cmd.command.impl.AbstractCommand;
import com.ti4oss.entity.Account;

/**
 * Author: Cottonrose
 * Created: 2019/5/9
 */
@CommandMeta(
        name = "GRXX",
        desc = "个人信息",
        group = "我的信息"
)
@CustomerCommand
@AdminCommand
public class PersonalInfoCommand extends AbstractCommand {
    @Override
    public void execute(Subject subject) {
        Account account = subject.getAccount();
        if (account == null) {
            errorPrintln("当前未登录");
        } else {
            System.out.println("******************* 个人信息 ********************");
            System.out.println("姓  名：" + account.getName());
            System.out.println("用户名：" + account.getUsername());
            System.out.println("密  码：" + "******");
            System.out.println("类  型：" + account.getAccountType().getDesc());
            System.out.println("状  态：" + account.getAccountStatus().getDesc());
            System.out.println("************************************************");
        }
    }
}
