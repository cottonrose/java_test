package com.ti4oss.cmd.command.impl;

import com.ti4oss.cmd.command.Command;
import com.ti4oss.service.AccountService;
import com.ti4oss.service.GoodsService;
import com.ti4oss.service.OrderService;

import java.util.Arrays;

/**
 * Author: Cottonrose
 * Created: 2019/5/9
 */
public abstract class AbstractCommand implements Command {
    
    protected AccountService accountService;
    
    protected GoodsService goodsService;
    
    protected OrderService orderService;
    
    public AbstractCommand() {
        this.accountService = new AccountService();
        this.goodsService = new GoodsService();
        this.orderService = new OrderService();
    }
    
    protected final void warningPrintln(String message) {
        System.out.println("【警告】 " + message);
    }
    
    protected final void errorPrintln(String message) {
        System.out.println("【错误】 " + message);
    }
    
    protected final void infoPrintln(String message) {
        System.out.println("【提示】 " + message);
    }
    
    protected final void hitPrintln(String message) {
        System.out.println(">> " + message);
    }
    
    protected final void todoPrintln(String message) {
        System.out.println("【学生扩展】 " + message);
    }
    
    protected final String inputPassword(String prompt) {
        hitPrintln(prompt);
        if (console == null) {
            return scanner.nextLine();
        } else {
            char[] chars = console.readPassword();
            String password = new String(chars);
            Arrays.fill(chars, ' ');
            return password;
        }
    }
}