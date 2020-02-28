package com.ti4oss.cmd.command.impl.entrance;

import com.ti4oss.cmd.command.Subject;
import com.ti4oss.cmd.command.annotation.CommandMeta;
import com.ti4oss.cmd.command.annotation.EntranceCommand;
import com.ti4oss.cmd.command.impl.AbstractCommand;
import com.ti4oss.common.AccountStatus;
import com.ti4oss.common.AccountType;
import com.ti4oss.entity.Account;

/**
 * Author: Cottonrose
 * Created: 2019/5/9
 */
@CommandMeta(
        name = {"ZC", "REGISTER"},
        desc = "注册",
        group = "入口命令"
)
@EntranceCommand
public class RegisterCommand extends AbstractCommand {
    
    @Override
    public void execute(Subject subject) {
        hitPrintln("请输入用户名：");
        String username = scanner.nextLine();
        if (this.accountService.checkDuplicateUserName(username)) {
            errorPrintln("用户名已经存在");
            return;
        }
        String password1 = inputPassword("请输入用户密码：");
        String password2 = inputPassword("请再次输入用户密码：");
        if (!password1.equals(password2)) {
            errorPrintln("两次输入的密码不一致");
            return;
        }
        hitPrintln("请输入姓名：");
        String name = scanner.nextLine();
        hitPrintln("请选择用户类型：1 管理员  2 客户");
        int accountTypeFlag = scanner.nextInt();
        AccountType accountType = AccountType.CUSTOMER;
        try {
            accountType = AccountType.valueOf(accountTypeFlag);
        } catch (Exception e) {
            warningPrintln("输出有误，默认选择客户");
        }
        
        final Account account = new Account();
        account.setUsername(username);
        account.setPassword(password1);
        account.setName(name);
        account.setAccountType(accountType);
        account.setAccountStatus(AccountStatus.UNLOCK);
        boolean effect = this.accountService.register(account);
        if (effect) {
            infoPrintln("恭喜，注册成功");
        } else {
            errorPrintln("抱歉，注册失败");
        }
    }
}
