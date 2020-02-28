package com.ti4oss.cmd.command;

import java.io.Console;
import java.util.Scanner;

/**
 * Author: Cottonrose
 * Created: 2019/5/9
 */
public interface Command {
    
    Scanner scanner = new Scanner(System.in);
    
    Console console = System.console();
    
    void execute(Subject subject);
    
}
