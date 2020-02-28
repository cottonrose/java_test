package com.bit.java.client.Sevice;

import com.bit.java.client.dao.AccountDao;
import com.bit.java.client.entity.User;
import com.bit.java.util.CommUtils;
import com.bit.java.vo.MessageVO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import java.util.Set;

/**
 * @program: chatroom
 * @description:
 * @author: Cottonrose
 * @create: 2019-08-17 13:20
 */
public class UserLogin {
    private JPanel UserLogin;
    private JPanel userPanel;
    private JTextField userNameText;
    private JPasswordField passWordText;
    private JButton Login;
    private JButton Register;
    private AccountDao accountDao = new AccountDao();
    private JFrame frame;

    public UserLogin() {
        frame = new JFrame("用户登录");
        frame.setContentPane(UserLogin);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
        //注册按钮
        Register.addActionListener(e -> {
            //弹出注册页面
            new UserReg();
        });
        //登陆按钮
        Login.addActionListener(e -> {
            //校验用户信息
            String userName = userNameText.getText();
            String passWord = String.valueOf(passWordText.getPassword());
            User user = accountDao.userLogin(userName, passWord);
            if (user != null) {
                //成功，加载用户列表
                JOptionPane.showMessageDialog(frame, "登录成功", "提示信息",
                        JOptionPane.INFORMATION_MESSAGE);
                frame.setVisible(false);
                //与服务器建立连接，将当前用户的用户名与密码发送到服务端
                ConnectToServer connectToServer = new ConnectToServer();
                MessageVO msgToServer = new MessageVO();
                msgToServer.setType("1");
                msgToServer.setContent(userName);
                //序列化为json字符串
                String json2Server = CommUtils.objectToJson(msgToServer);
                try {
                    PrintStream out = new PrintStream(connectToServer.getOut(),
                            true, "UTF-8");
                    out.println(json2Server);
                    //读取服务端发回的所有在线用户信息
                    Scanner in = new Scanner(connectToServer.getIn());
                    if (in.hasNextLine()) {
                        String msgFromServerStr = in.nextLine();
                        MessageVO msgFromServer = (MessageVO) CommUtils.jsonToObject(
                                msgFromServerStr, MessageVO.class);
                        Set<String> users = (Set<String>) CommUtils.jsonToObject
                                (msgFromServer.getContent(), Set.class);
                        System.out.println("所有在线用户为" + users);
                        //加载用户列表界面
                        //将当前用户名，所有在线好友，与服务器建立的连接传递到好友列表界面
                        new FriendsList(userName, users, connectToServer);

                    }
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
            } else {
                //失败，停留在当前登陆页面，提示用户信息错误
                JOptionPane.showMessageDialog(frame, "登录失败", "错误信息",
                        JOptionPane.ERROR_MESSAGE);
            }
        });


    }
    public static void main (String[]args){
        UserLogin userLogin = new UserLogin();
    }
}