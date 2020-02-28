package com.bit.java.server;

import com.bit.java.util.CommUtils;
import com.bit.java.vo.MessageVO;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @program: chatroom
 * @description:
 * @author: Cottonrose
 * @create: 2019-08-17 15:51
 */
public class Server {
    private static final String IP;
    private static final int PORT;
    //缓存当前服务器所有在线的客户端信息
    private static Map<String,Socket> clients = new ConcurrentHashMap<>();
    //缓存当前服务器注册的所有群名称以及群好友
    private static Map<String,Set<String>> groups = new ConcurrentHashMap<>();
    static {
        //加载配置文件
        Properties properties = CommUtils.loadProperties("socket.properties");
        IP = properties.getProperty("address");
        PORT = Integer.parseInt(properties.getProperty("port"));
    }

    private static class ExecuteClient implements Runnable{
        private Socket client;
        private Scanner in;
        private PrintStream out;
        public ExecuteClient(Socket client){

            this.client = client;
            try {
                this.in = new Scanner(client.getInputStream());
                this.out = new PrintStream(client.getOutputStream(),true,"UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            while(true) {
                if (in.hasNext()) {
                    String jsonStrFromClient = in.nextLine();
                    MessageVO msgFromClient = (MessageVO) CommUtils.jsonToObject(jsonStrFromClient,
                            MessageVO.class);
                    if (msgFromClient.getType().equals("1")) {
                        //新用户注册到服务端
                        String userName = msgFromClient.getContent();
                        //将当前在线的所有用户名发回客户端
                        MessageVO msgToClient = new MessageVO();
                        msgToClient.setType("1");
                        msgToClient.setContent(CommUtils.objectToJson(clients.keySet()));
                        out.println(CommUtils.objectToJson(msgToClient));
                        //将新上线的用户信息发回给当前已上线的所有用户
                        sendUserLogin("newLogin:"+userName);
                        //将当前新用户注册到服务端缓存
                        clients.put(userName,client);
                        System.out.println(userName+"上线了！");
                        System.out.println("当前聊天室共有"+clients.size()+"人");

                    }else if (msgFromClient.getType().equals("2")){
                        //用户私聊
                        //type:2
                        //Content:myName-msg
                        //to:friendName
                        String friendName = msgFromClient.getTo();
                        Socket clientSocket = clients.get(friendName);
                        try {
                            PrintStream out = new PrintStream(clientSocket.getOutputStream(),
                                    true,"UTF-8");
                            MessageVO msgToClient = new MessageVO();
                            msgToClient.setType("2");
                            msgToClient.setContent(msgFromClient.getContent());
                            System.out.println("收到私聊信息，内容为："+msgFromClient.getContent());
                            out.println(CommUtils.objectToJson(msgToClient));

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else if(msgFromClient.getType().equals("3")){
                        //注册群
                        String groupName = msgFromClient.getContent();
                        //该群的所有群成员
                        Set<String> friends = (Set<String>) CommUtils.jsonToObject(msgFromClient.getTo(),Set.class);
                        groups.put(groupName,friends);
                        System.out.println("有新的群注册成功，群名称为："+groupName+
                                ",一共有"+groups.size()+"个群");
                    }else if (msgFromClient.getType().equals("4")){
                        //群聊信息
                        String groupName = msgFromClient.getTo();
                        Set<String> names = groups.get(groupName);
                        Iterator<String> iterator = names.iterator();
                        while (iterator.hasNext()){
                            String socketName = iterator.next();
                            Socket client = clients.get(socketName);
                            try {
                                PrintStream out = new PrintStream(client.getOutputStream(),
                                        true,"UTF-8");
                                MessageVO messageVO = new MessageVO();
                                messageVO.setType("4");
                                messageVO.setContent(msgFromClient.getContent());
                                //群名-
                                messageVO.setTo(groupName+"-"+CommUtils.objectToJson(names));
                                out.println(CommUtils.objectToJson(messageVO));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
        //向所有在线用户发送新用户上线信息
        private void sendUserLogin(String msg){
            for(Map.Entry<String,Socket> entry : clients.entrySet()){
                Socket socket = entry.getValue();
                try {
                    PrintStream out = new PrintStream(socket.getOutputStream(),
                            true,"UTF-8");
                    out.println(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        ExecutorService executors = Executors.newFixedThreadPool(50);
        for(int i=0;i<50;i++){
            System.out.println("等待客户端连接");
            Socket client = serverSocket.accept();
            System.out.println("有新的连接，端口号为："+client.getPort());
            executors.submit(new ExecuteClient(client));
        }
    }
}