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
                        msgToClient.setContent(CommUtils.objectToJson(clients.keySet()));//返回clients地图中包含的键的视图
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
                        String friendName = msgFromClient.getTo(); //获取客户机中的to属性，即要私聊的朋友名称
                        Socket clientSocket = clients.get(friendName);//获取friendName键所映射的值并赋给clientSocket
                        try {
                            PrintStream out = new PrintStream(clientSocket.getOutputStream(), //创建一个新的打印流（传的是此套接字,即朋友名称值的输出流）out
                                    true,"UTF-8");
                            //将信息保存在msgToClient中
                            MessageVO msgToClient = new MessageVO();
                            msgToClient.setType("2");
                            msgToClient.setContent(msgFromClient.getContent());
                            System.out.println("收到私聊信息，内容为："+msgFromClient.getContent());
                            //将msgToClient中的信息发回客户端
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
            for(Map.Entry<String,Socket> entry : clients.entrySet()){ //client.entrySet()返回clients地图的集合视图，其元素是Map.Entry<String,Socket>类型的
                Socket socket = entry.getValue(); //返回对应的值
                try {
                    PrintStream out = new PrintStream(socket.getOutputStream(),//返回此套接字的输出流。
                            true,"UTF-8"); //创建一个新的打印流。
                    out.println(msg); //将上线用户通过新的打印流传送给所有在线用户
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);//创建一个服务器套接字并绑定到指定端口
        ExecutorService executors = Executors.newFixedThreadPool(50);
        for(int i=0;i<50;i++){
            System.out.println("等待客户端连接");
            Socket client = serverSocket.accept();//侦听要连接到此套接字并接受它。
            System.out.println("有新的连接，端口号为："+client.getPort());
            executors.submit(new ExecuteClient(client));
        }
    }
}