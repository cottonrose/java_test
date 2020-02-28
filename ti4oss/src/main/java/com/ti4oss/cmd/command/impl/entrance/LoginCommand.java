package com.ti4oss.cmd.command.impl.entrance;

import com.ti4oss.cmd.command.Command;
import com.ti4oss.cmd.command.Subject;
import com.ti4oss.cmd.command.annotation.CommandMeta;
import com.ti4oss.cmd.command.annotation.EntranceCommand;
import com.ti4oss.cmd.command.impl.AbstractCommand;
import com.ti4oss.common.AccountStatus;
import com.ti4oss.entity.Account;

/**
 * Author: Cottonrose
 * Created: 2019/5/9
 */
@CommandMeta(
        name = {"DL", "LOGIN"},
        desc = "登录",
        group = "入口命令"
)
@EntranceCommand
public class LoginCommand extends AbstractCommand {
    
    @Override
    public void execute(Subject subject) {
        Account account = subject.getAccount();
        if (account != null) {
            warningPrintln("已经登录过");
            return;
        }
        hitPrintln("请输入用户名：");
        String username = Command.scanner.nextLine();
        String password = inputPassword("请输入密码：");
        account = this.accountService.login(username, password);
        if (account != null && account.getAccountStatus() == AccountStatus.UNLOCK) {
            infoPrintln(account.getAccountType().getDesc() + "登录成功");
            subject.setAccount(account);
        } else {
            errorPrintln("登录失败，用户名或密码错误");
        }
    }
}