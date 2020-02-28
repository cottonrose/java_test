package com.bit.java.client.Sevice;

import com.bit.java.util.CommUtils;
import com.bit.java.vo.MessageVO;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

/**
 * @program: chatroom
 * @description:
 * @author: Cottonrose
 * @create: 2019-08-20 20:44
 */
public class PrivateChatGUI {
    private JTextArea readFromServer;
    private JTextField sendToServer;
    private JPanel privateChatPanel;
    private String friendName;
    private String myName;
    private ConnectToServer connectToServer;
    private JFrame frame;
    private PrintStream out;

    public PrivateChatGUI(String friendName,String myName,ConnectToServer connectToServer){
        this.friendName = friendName;
        this.myName = myName;
        this.connectToServer = connectToServer;
        try {
            this.out = new PrintStream(connectToServer.getOut(),true,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        frame = new JFrame("与"+friendName+"私聊中");
        frame.setContentPane(privateChatPanel);
        //设置窗口关闭操作，将其设置为隐藏
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setSize(400,400);
        frame.setVisible(true);

        //捕捉输入框的键盘输入
        sendToServer.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
               StringBuilder sb = new StringBuilder();
               sb.append(sendToServer.getText());
               //1.当捕捉到按下Enter键
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    //将当前信息发送到服务端
                    String msg = sb.toString();
                    MessageVO messageVO = new MessageVO();
                    messageVO.setType("2");
                    messageVO.setContent(myName+"-"+msg);
                    messageVO.setTo(friendName);
                    PrivateChatGUI.this.out.println(CommUtils.objectToJson(messageVO));
                    //将自己发送的信息展示到当前私聊界面
                    readFromServer(myName+"说："+msg);
                    sendToServer.setText("");
                }

            }
        });
    }
    public void readFromServer(String msg){
        readFromServer.append(msg+"\n");
    }

    JFrame getFrame(){
        return frame;
    }
}