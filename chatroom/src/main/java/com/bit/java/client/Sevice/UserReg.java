package com.bit.java.client.Sevice;

import com.bit.java.client.dao.AccountDao;
import com.bit.java.client.entity.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @program: chatroom
 * @description:
 * @author: Cottonrose
 * @create: 2019-08-17 13:15
 */
public class UserReg {
    private JPanel UserReg;
    private JTextField usernameText;
    private JPasswordField passwordText;
    private JTextField briefText;
    private JButton regBtn;
    private AccountDao accountDao = new AccountDao();

    public UserReg(){
        JFrame frame = new JFrame("用户注册");
        frame.setContentPane(UserReg);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);//设置相对位置
        frame.pack();
        frame.setVisible(true);
        //点击submit按钮，将信息持久化到数据库中，成功弹出提示框
        regBtn.addActionListener(e -> {
            //1.获取用户输入的注册信息
            String userName = usernameText.getText();
            String passWord = String.valueOf(passwordText.getPassword());
            String brief = briefText.getText();
            //2.将信息包装为User类，保存到数据库中
            User user = new User();
            user.setUsername(userName);
            user.setPassword(passWord);
            user.setBrief(brief);
            //调用Dao对象
            if (accountDao.userReg(user)){
                //弹出提示框--成功
                JOptionPane.showMessageDialog(frame,"注册成功",
                        "提示",JOptionPane.INFORMATION_MESSAGE);
                frame.setVisible(false);//注册成功，返回登陆界面——注册界面设为不可见
            }else {
                //弹出提示框--失败
                JOptionPane.showMessageDialog(frame,"注册失败","错误",JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}