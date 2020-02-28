package com.bit.java.client.Sevice;

import com.bit.java.util.CommUtils;
import com.bit.java.vo.MessageVO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.FeatureDescriptor;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @program: chatroom
 * @description:
 * @author: Cottonrose
 * @create: 2019-08-21 12:43
 */
public class CreateGroupGUI {
    private JPanel createGroupPanel;
    private JPanel friendLabelPanel;
    private JTextField groupNameText;
    private JButton confirmBtn;
    private JFrame frame;
    private String myName;
    private Set<String> friends;
    private ConnectToServer connectToServer;
    private FriendsList friendsList;

    public CreateGroupGUI(String myName, Set<String> friends,
                          ConnectToServer connectToServer, FriendsList friendsList) {
        this.myName = myName;
        this.friends = friends;
        this.connectToServer = connectToServer;
        this.friendsList = friendsList;
        frame = new JFrame("创建群组");
        frame.setContentPane(createGroupPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        //1.将在线好友以checkBox展示到界面
        friendLabelPanel.setLayout(new BoxLayout(friendLabelPanel,BoxLayout.Y_AXIS));//纵向展示
        JCheckBox[] checkBoxes = new JCheckBox[friends.size()];
        Iterator<String> iterator = friends.iterator();
        int i = 0;
        while(iterator.hasNext()){
            String labelName = iterator.next();
            checkBoxes[i] = new JCheckBox(labelName);
            friendLabelPanel.add(checkBoxes[i]);
            i++;
        }
        friendLabelPanel.revalidate();
        //点击提交按钮提交信息到服务端
        confirmBtn.addActionListener(e -> {
            //1.判断哪些好友被选中加入群聊
            Set<String> selectedFriends = new HashSet<>();
            Component[] comps = friendLabelPanel.getComponents();
            for (Component comp:comps){
                JCheckBox checkBox = (JCheckBox)comp;
                if (checkBox.isSelected()){
                    String labelName = checkBox.getText();
                    selectedFriends.add(labelName);
                }
            }
            selectedFriends.add(myName);
            //2.获取群名输入框输入的群名称
            String groupName = groupNameText.getText();
            //3.将群名+选中好友信息发送到服务端
            /*type:3
            * content:groupName
            * to:[user1,user2,user3...]*/
            MessageVO messageVO = new MessageVO();
            messageVO.setType("3");
            messageVO.setContent(groupName);
            messageVO.setTo(CommUtils.objectToJson(selectedFriends));
            try {
                PrintStream out = new PrintStream(connectToServer.getOut(),
                        true,"UTF-8");
                out.println(CommUtils.objectToJson(messageVO));
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }
            //4.将当前创建群的界面隐藏，刷新好友列表界面的群列表
            frame.setVisible(false);
            //addGroupInfo
            //loadGroup
            friendsList.addGroup(groupName,selectedFriends);
            friendsList.loadGroupList();
        });
    }

}