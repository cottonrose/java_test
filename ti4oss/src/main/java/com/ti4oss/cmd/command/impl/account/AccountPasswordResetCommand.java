package com.ti4oss.cmd.command.impl.account;

import com.ti4oss.cmd.command.Subject;
import com.ti4oss.cmd.command.annotation.AdminCommand;
import com.ti4oss.cmd.command.annotation.CommandMeta;
import com.ti4oss.cmd.command.impl.AbstractCommand;

/**
 * Author: Cottonrose
 * Created: 2019/5/17
 */
@CommandMeta(
        name = "CZMM",
        desc = "重置密码",
        group = "帐号信息"
)
@AdminCommand
public class AccountPasswordResetCommand extends AbstractCommand {
    
    @Override
    public void execute(Subject subject) {
        todoPrintln("账号密码重置");
    }
}
