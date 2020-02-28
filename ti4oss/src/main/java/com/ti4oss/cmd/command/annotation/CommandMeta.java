package com.ti4oss.cmd.command.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author: Cottonrose
 * Created: 2019/5/9
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CommandMeta {
    
    /**
     * 命令名称
     *
     * @return
     */
    String[] name();
    
    /**
     * 命令描述
     *
     * @return
     */
    String desc();
    
    /**
     * 命令分组
     *
     * @return
     */
    String group();
}
