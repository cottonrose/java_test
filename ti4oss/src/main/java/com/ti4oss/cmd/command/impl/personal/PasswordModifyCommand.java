package com.ti4oss.cmd.command.impl.personal;

import com.ti4oss.cmd.command.Subject;
import com.ti4oss.cmd.command.annotation.AdminCommand;
import com.ti4oss.cmd.command.annotation.CommandMeta;
import com.ti4oss.cmd.command.annotation.CustomerCommand;
import com.ti4oss.cmd.command.impl.AbstractCommand;

/**
 * Author: Cottonrose
 * Created: 2019/5/9
 */
@CommandMeta(
        name = "XGMM",
        desc = "修改密码",
        group = "我的信息"
)
@AdminCommand
@CustomerCommand
public class PasswordModifyCommand extends AbstractCommand {
    
    @Override
    public void execute(Subject subject) {
        todoPrintln("修改密码功能");
    }
}
