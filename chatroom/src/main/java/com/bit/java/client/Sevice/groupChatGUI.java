package com.bit.java.client.Sevice;

import com.bit.java.util.CommUtils;
import com.bit.java.vo.MessageVO;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Set;

/**
 * @program: chatroom
 * @description:
 * @author: Cottonrose
 * @create: 2019-08-21 15:04
 */
public class groupChatGUI {
    private JPanel groupPanel;
    private JTextArea readFromServer;
    private JTextField sendToServer;
    private JPanel friendsPanel;
    private JFrame frame;

    private String groupName;
    private Set<String> friends ;
    private String myName;
    private ConnectToServer connectToServer;

    public groupChatGUI(String groupName,Set<String> friends,String myName,ConnectToServer connectToServer){
        this.groupName = groupName;
        this.friends = friends;
        this.myName = myName;
        this.connectToServer = connectToServer;
        frame = new JFrame(groupName);
        frame.setContentPane(groupPanel);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setSize(400,400);
        frame.setVisible(true);
        friendsPanel.setLayout(new BoxLayout(friendsPanel,BoxLayout.Y_AXIS));
        Iterator<String> iterator = friends.iterator();
        //加载群中好友列表
        while(iterator.hasNext()){
            String labelName = iterator.next();
            JLabel jLabel = new JLabel(labelName);
            friendsPanel.add(jLabel);
        }
        sendToServer.addKeyListener(new KeyAdapter() { //用于接收键盘事件的抽象适配器类
            @Override
            public void keyPressed(KeyEvent e) {
                StringBuilder sb = new StringBuilder();
                sb.append(sendToServer.getText());
                //捕捉回车按钮
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    String strToServer = sb.toString();
                    //type:"4"
                    //content:myName-msg
                    //to:groupName
                    MessageVO messageVO = new MessageVO();
                    messageVO.setType("4");
                    messageVO.setContent(myName+"-"+strToServer);
                    messageVO.setTo(groupName);
                    try {
                        PrintStream out = new PrintStream(connectToServer.getOut(),
                                true,"UTF-8");
                        out.println(CommUtils.objectToJson(messageVO));
                    } catch (UnsupportedEncodingException e1) {
                        e1.printStackTrace();
                    }

                }
            }
        });
    }
    //从服务器读取信息
    public void ReadFromServer(String msg){
        readFromServer.append(msg+"\n");
    }

    public JFrame getFrame() {
        return frame;
    }
}