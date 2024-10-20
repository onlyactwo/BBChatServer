package com.lzx.BBChat.ServerService.ClientHandler;

import com.lzx.BBChat.Common.Message.Message;
import com.lzx.BBChat.Common.Message.MessageType;
import com.lzx.BBChat.Common.Utils.Utils;
import com.lzx.BBChat.Server.Server;
import com.lzx.BBChat.ServerService.ClientLoginRequestHandler.ClientLoginRequestHandler;
import com.lzx.BBChat.ServerService.ClientRegistrationRequestHandler.ClientRegistrationRequestHandler;
import com.lzx.BBChat.ServerService.LogPrinter.LogPrinter;
import com.lzx.BBChat.ServerService.UserCheckMessageInBoxHandler.UserCheckMessageInBoxHandler;
import com.lzx.BBChat.ServerService.UserCheckPeopleOnlineRequestHandler.UserCheckPeopleOnlineRequestHandler;
import com.lzx.BBChat.ServerService.UserPrivateChatRequestHandler.UserPrivateChatRequestHandler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * 等待响应状态
 * <p>
 * 这个类用于开启客户端线程，包括：
 * 1.处理用户登录请求
 * 2.处理用户注册请求
 * 3.处理用户私聊请求
 * 4.处理用户群聊请求
 * 5.处理用户拉取在线用户请求
 * <p>
 * 基本处理逻辑是 客服端向服务器端发送一次请求Message，服务器端就相应一次，调用对应的方法去处理
 * 一直循环
 */
public class ClientHandler implements Runnable {
    private final Server server;//该线程必须持有服务器的引用，才能访问到服务器的资源
    private final Socket clientSocket;//这个socket是用于和该客户端进行通信用的
    //获取与客户端的IO流 ---- 只在构造器中创造一次，避免重复创建
    ObjectOutputStream oos;
    ObjectInputStream ois;

    @Override
    public void run() {
        try {
            while (true) {
                //读一个客户端发送的Message消息
                Message message = (Message) ois.readObject();

                //判断message.mesType 的消息类型
                if (message.getMesType().equals(MessageType.MESSAGE_LOGIN_REQUEST)) {
                    //该消息是用户请求登录
                    LogPrinter.printLog(clientSocket.getInetAddress().getHostAddress() + " 正在请求登录 ");
                    ClientLoginRequestHandler.handleClientLoginRequest(server, clientSocket, oos, ois);
                    //处理完用户的登录请求 -》继续响应其他请求-》等待客户端发送Message

                } else if (message.getMesType().equals(MessageType.MESSAGE_REGISTRATION_REQUEST)) {
                    //该消息是用户注册请求
                    LogPrinter.printLog(clientSocket.getInetAddress().getHostAddress() + " 正在请求注册 ");
                    ClientRegistrationRequestHandler.handleClientRegistrationRequest(server, clientSocket, oos, ois);
                    //处理完用户的注册请求 -》继续响应其他请求-》等待客户端发送Message

                } else if (message.getMesType().equals(MessageType.MESSAGE_PRIVATE_CHAT_REQUEST)) {
                    //该消息是用户私聊请求
                    UserPrivateChatRequestHandler.handleUserPrivateChatRequest(server, message.getSender(), oos, ois);
                    //处理完用户的私聊请求 -》继续响应其他请求-》等待客户端发送Message

                } else if (message.getMesType().equals(MessageType.MESSAGE_GROUP_CHAT_REQUEST)) {
                    //该消息是群聊请求


                } else if(message.getMesType().equals(MessageType.MESSAGE_USER_CHECK_MESSAGE_INBOX_REQUEST)){
                    //用户请求查看信箱
                    UserCheckMessageInBoxHandler.handlerUserCheckMessageInBox(server, message.getSender(),oos,ois);
                    //服务器处理完查看信箱请求 -》继续响应其他请求-》等待客户端发送Message
                }else if (message.getMesType().equals(MessageType.MESSAGE_EXIT)) {
                    //该消息是用户关闭软件请求
                    //关闭流
                    oos.close();
                    ois.close();
                    //结束线程
                    LogPrinter.printLog(" 用户 " + clientSocket.getInetAddress().getHostAddress() + " 断开了与服务器的连接");
                    return;
                }else if(message.getMesType().equals(MessageType.MESSAGE_USER_CHECK_ONLINE_PEOPLE_REQUEST)){
                    //该消息是用户拉取在线人员情况请求
                    UserCheckPeopleOnlineRequestHandler.handleUserCheckPeopleOnlineRequest(server, message.getSender(), oos,ois);
                    //服务器处理完查看在线用户请求 -》继续响应其他请求-》等待客户端发送Message

                }else if (message.getMesType().equals(MessageType.MESSAGE_USER_OFFLINE_REQUEST)) {
                    //用户下线通知
                    //踢出online列表
                    server.getUserOnline().remove(message.getSender());
                    LogPrinter.printLog(message.getSender() + "离线了.。。。。");
                }
            }

        } catch (IOException e) {
            LogPrinter.printLog(clientSocket.getInetAddress().getHostAddress() + " 断开了连接 ");
        } catch (ClassNotFoundException e) {
            LogPrinter.printLog("发送的数据不是Message！");
            throw new RuntimeException(e);
        }

    }

    public ClientHandler(Server server, Socket clientSocket) {
        this.server = server;
        this.clientSocket = clientSocket;
        try {
            this.oos = new ObjectOutputStream(clientSocket.getOutputStream());
            this.ois = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
