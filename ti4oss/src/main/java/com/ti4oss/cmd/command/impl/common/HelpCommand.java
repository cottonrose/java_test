package com.ti4oss.cmd.command.impl.common;

import com.ti4oss.cmd.command.Command;
import com.ti4oss.cmd.command.Commands;
import com.ti4oss.cmd.command.Subject;
import com.ti4oss.cmd.command.annotation.AdminCommand;
import com.ti4oss.cmd.command.annotation.CommandMeta;
import com.ti4oss.cmd.command.annotation.CustomerCommand;
import com.ti4oss.cmd.command.annotation.EntranceCommand;
import com.ti4oss.cmd.command.impl.AbstractCommand;
import com.ti4oss.entity.Account;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: Cottonrose
 * Created: 2019/5/17
 */
@CommandMeta(
        name = {"BZ", "HELP", "H"},
        desc = "帮助信息",
        group = "公共命令"
)
@EntranceCommand
@AdminCommand
@CustomerCommand
public class HelpCommand extends AbstractCommand {
    
    @Override
    public void execute(Subject subject) {
        Account account = subject.getAccount();
        if (account == null) {
            entranceHelp();
        } else {
            switch (account.getAccountType()) {
                case ADMIN:
                    adminHelp();
                    break;
                case CUSTOMER:
                    customerHelp();
                    break;
                default:
            }
        }
    }
    
    private void entranceHelp() {
        printHelp("欢迎", Commands.ENTRANCE_COMMANDS.values());
    }
    
    private void adminHelp() {
        printHelp("管理端", Commands.ADMIN_COMMANDS.values());
    }
    
    private void customerHelp() {
        printHelp("客户端", Commands.CUSTOMER_COMMANDS.values());
    }
    
    private void printHelp(String title, Collection<Command> collection) {
        System.out.println("******************** " + title + " ***********************");
        Map<String, List<String>> helpInfo = new HashMap<>();
        for (Command command : collection) {
            CommandMeta commandMeta = command.getClass().getDeclaredAnnotation(CommandMeta.class);
            String group = commandMeta.group();
            List<String> funcs = helpInfo.computeIfAbsent(group, s -> new ArrayList<>());
            funcs.add(commandMeta.desc() + "(" + join(commandMeta.name()) + ")");
        }
        int i = 0;
        for (Map.Entry<String, List<String>> entry : helpInfo.entrySet()) {
            i++;
            System.out.println(i + ". " + entry.getKey());
            int j = 0;
            for (String item : entry.getValue()) {
                j++;
                System.out.println("\t" + (i) + "." + (j) + " " + item);
            }
        }
        System.out.println("输入菜单括号后面的编号（忽略大小写），进行下一步操作");
        System.out.println("*************************************************");
    }
    
    
    private String join(String[] array) {
        StringBuilder sb = new StringBuilder();
        for (String item : array) {
            sb.append(item).append(", ");
        }
        sb.setLength(sb.length() - 2);
        return sb.toString();
    }
}
