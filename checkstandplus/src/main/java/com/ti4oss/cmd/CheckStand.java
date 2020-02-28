package com.ti4oss.cmd;

import com.ti4oss.cmd.command.Commands;
import com.ti4oss.cmd.command.Subject;
import com.ti4oss.cmd.command.impl.AbstractCommand;
import com.ti4oss.entity.Account;

/**
 * Author: Cottonrose
 * Created: 2019/5/9
 */
public class CheckStand extends AbstractCommand {
    
    public static void main(String[] args) {
        Subject subject = new Subject();
        new CheckStand().execute(subject);
    }
    
    @Override
    public void execute(Subject subject) {
        Commands.getHelpCommand().execute(subject);
        while (true) {
            System.out.print(">>");
            String line = scanner.nextLine();
            String commandCode = line.trim().toUpperCase();
            Account account = subject.getAccount();
            if (account == null) {
                Commands.getEntranceCommand(commandCode).execute(subject);
            } else {
                switch (account.getAcc1ountType()) {
                    case ADMIN:
                        Commands.getAdminCommand(commandCode).execute(subject);
                        break;
                    case CUSTOMER:
                        Commands.getCustomerCommand(commandCode).execute(subject);
                        break;
                    default:
                }
            }
        }
    }
    
}
