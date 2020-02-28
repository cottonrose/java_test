package com.ti4oss.cmd.command.impl.common;

import com.ti4oss.cmd.command.Subject;
import com.ti4oss.cmd.command.annotation.AdminCommand;
import com.ti4oss.cmd.command.annotation.CommandMeta;
import com.ti4oss.cmd.command.annotation.CustomerCommand;
import com.ti4oss.cmd.command.annotation.EntranceCommand;
import com.ti4oss.cmd.command.impl.AbstractCommand;

/**
 * Author: Cottonrose
 * Created: 2019/5/9
 */
@CommandMeta(
        name = {"TC", "QUIT", "Q"},
        desc = "退出系统",
        group = "公共命令"
)
@EntranceCommand
@AdminCommand
@CustomerCommand
public class QuitCommand extends AbstractCommand {
    
    @Override
    public void execute(Subject subject) {
        System.out.println("*************************************************");
        System.out.println("               欢迎使用，下次再见                 ");
        System.out.println("*************************************************");
        this.closeScanner();
        System.exit(0);
    }
    
    private void closeScanner() {
        this.scanner.close();
    }
}