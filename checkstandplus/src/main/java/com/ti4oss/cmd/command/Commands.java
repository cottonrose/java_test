package com.ti4oss.cmd.command;

import com.ti4oss.cmd.command.annotation.AdminCommand;
import com.ti4oss.cmd.command.annotation.CommandMeta;
import com.ti4oss.cmd.command.annotation.CustomerCommand;
import com.ti4oss.cmd.command.annotation.EntranceCommand;
import com.ti4oss.cmd.command.impl.account.AccountBrowseCommand;
import com.ti4oss.cmd.command.impl.account.AccountPasswordResetCommand;
import com.ti4oss.cmd.command.impl.account.AccountStatusSetCommand;
import com.ti4oss.cmd.command.impl.common.AboutCommand;
import com.ti4oss.cmd.command.impl.common.HelpCommand;
import com.ti4oss.cmd.command.impl.common.QuitCommand;
import com.ti4oss.cmd.command.impl.entrance.LoginCommand;
import com.ti4oss.cmd.command.impl.entrance.RegisterCommand;
import com.ti4oss.cmd.command.impl.goods.GoodsBrowseCommand;
import com.ti4oss.cmd.command.impl.goods.GoodsPutAwayCommand;
import com.ti4oss.cmd.command.impl.goods.GoodsSoldOutCommand;
import com.ti4oss.cmd.command.impl.goods.GoodsUpdateCommand;
import com.ti4oss.cmd.command.impl.order.OrderBrowseCommand;
import com.ti4oss.cmd.command.impl.order.OrderCancelCommand;
import com.ti4oss.cmd.command.impl.order.OrderPayCommand;
import com.ti4oss.cmd.command.impl.personal.PasswordModifyCommand;
import com.ti4oss.cmd.command.impl.personal.PersonalInfoCommand;
import com.ti4oss.cmd.command.impl.trolley.TrolleyAddCommand;
import com.ti4oss.cmd.command.impl.trolley.TrolleyBrowseCommand;
import com.ti4oss.cmd.command.impl.trolley.TrolleyOrderCommand;
import com.ti4oss.cmd.command.impl.trolley.TrolleyRemoveCommand;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Author: Cottonrose
 * Created: 2019/5/9
 */
public class Commands {
    
    /**
     * 所有命令集合
     */
    private static final Set<Command> COMMANDS = new HashSet<>();
    
    /**
     * 入口命令
     */
    public static final Map<Set<String>, Command> ENTRANCE_COMMANDS = new HashMap<>();
    
    /**
     * 管理端命令
     */
    public static final Map<Set<String>, Command> ADMIN_COMMANDS = new HashMap<>();
    
    /**
     * 客户端命令
     */
    public static final Map<Set<String>, Command> CUSTOMER_COMMANDS = new HashMap<>();
    
    /**
     * 帮助命令
     */
    private static final Command CACHED_HELP_COMMAND;
    
    static {
        //维护所有的命令对象
        Collections.addAll(COMMANDS,
                
                //入口命令
                new RegisterCommand(),
                new LoginCommand(),
                
                //公共命令
                new AboutCommand(),
                new QuitCommand(),
                CACHED_HELP_COMMAND = new HelpCommand(),
                new PersonalInfoCommand(),
                new PasswordModifyCommand(),
                
                //管理端命令
                //商品相关
                new GoodsPutAwayCommand(),
                new GoodsSoldOutCommand(),
                new GoodsUpdateCommand(),
                new GoodsBrowseCommand(),
                //账号相关
                new AccountBrowseCommand(),
                new AccountPasswordResetCommand(),
                new AccountStatusSetCommand(),
                
                //客户端命令
                //购物车
                new TrolleyAddCommand(),
                new TrolleyRemoveCommand(),
                new TrolleyBrowseCommand(),
                new TrolleyOrderCommand(),
                //订单
                new OrderBrowseCommand(),
                new OrderCancelCommand(),
                new OrderPayCommand()
        
        
        );
        
        for (Command command : COMMANDS) {
            Class<?> cls = command.getClass();
            CommandMeta commandMeta = cls.getDeclaredAnnotation(CommandMeta.class);
            if (commandMeta == null) {
                continue;
            }
            AdminCommand adminCommand = cls.getDeclaredAnnotation(AdminCommand.class);
            CustomerCommand customerCommand = cls.getDeclaredAnnotation(CustomerCommand.class);
            EntranceCommand entranceCommand = cls.getDeclaredAnnotation(EntranceCommand.class);
            String[] commandCodes = commandMeta.name();
            Set<String> commandCodesKey = new HashSet<>(Arrays.asList(commandCodes));
            if (adminCommand != null) {
                ADMIN_COMMANDS.put(commandCodesKey, command);
            }
            if (customerCommand != null) {
                CUSTOMER_COMMANDS.put(commandCodesKey, command);
            }
            if (entranceCommand != null) {
                ENTRANCE_COMMANDS.put(commandCodesKey, command);
            }
            
        }
    }
    
    private Commands() {
    
    }
    
    public static Command getHelpCommand() {
        return CACHED_HELP_COMMAND;
    }
    
    public static Command getAdminCommand(String command) {
        return getCommand(command, ADMIN_COMMANDS);
    }
    
    public static Command getCustomerCommand(String command) {
        return getCommand(command, CUSTOMER_COMMANDS);
    }
    
    public static Command getEntranceCommand(String command) {
        return getCommand(command, ENTRANCE_COMMANDS);
    }
    
    /**
     * 根据命令码获取命令对象
     *
     * @param command
     * @return
     */
    private static Command getCommand(String command, Map<Set<String>, Command> commandMap) {
        String commandCode = command.trim().toUpperCase();
        for (Map.Entry<Set<String>, Command> entry : commandMap.entrySet()) {
            if (entry.getKey().contains(commandCode)) {
                return entry.getValue();
            }
        }
        return CACHED_HELP_COMMAND;
    }
}
