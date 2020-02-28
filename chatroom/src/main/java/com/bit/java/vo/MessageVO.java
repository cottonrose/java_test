package com.bit.java.vo;

import lombok.Data;

/**
 * @program: chatroom
 * @description:服务器与客户端传递信息载体
 * @author: Cottonrose
 * @create: 2019-08-17 15:46
 */
public class MessageVO {

    // 表示告知服务器要进行的动作 1.表示用户注册 2.表示私聊
    private String type;
    // 发送到服务器的具体内容
    private String content;
    //私聊告知服务器要将信息发给哪个用户
    private String to;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}