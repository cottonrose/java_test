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
        name = {"GY", "ABOUT", "A"},
        desc = "关于系统",
        group = "公共命令"
)
@EntranceCommand
@CustomerCommand
@AdminCommand
public class AboutCommand extends AbstractCommand {
    
    @Override
    public void execute(Subject subject) {
        System.out.println("******************** 关于 ***********************");
        System.out.println("        名称：基于字符界面的在线购物系统           ");
        System.out.println("        作者: secondriver                        ");
        System.out.println("        版本: v0.0.1                             ");
        System.out.println("        意见反馈：secondriver@yeah.net            ");
        System.out.println("*************************************************");
    }
}
