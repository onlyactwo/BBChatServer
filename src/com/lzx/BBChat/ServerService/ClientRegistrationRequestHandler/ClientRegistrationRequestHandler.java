package com.lzx.BBChat.ServerService.ClientRegistrationRequestHandler;

import com.lzx.BBChat.Common.Message.Message;
import com.lzx.BBChat.Common.Message.MessageType;
import com.lzx.BBChat.Common.User.User;
import com.lzx.BBChat.Common.Utils.Utils;
import com.lzx.BBChat.Server.Server;
import com.lzx.BBChat.ServerService.LogPrinter.LogPrinter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * 这个类是用于处理用户的注册请求
 * 思路：
 * 1.回复同意用户的注册请求
 * 2.接收用户想要注册的User
 * 3.查询该账号是否存在
 *  存在：返回注册失败
 *  不存在：返回注册成功，并添加到用户数据库当中
 *
 *  参数：
 *  server ---- 用于查询数据库
 *  oos、ois ---- 用于和用户通信
 */
public class ClientRegistrationRequestHandler {
    public static void handleClientRegistrationRequest(Server server, Socket clientSocket ,ObjectOutputStream oos , ObjectInputStream ois){

        try {
            //同意客户端的注册请求
            //创建消息,同意客户端的注册请求
            Message message_allow_Client_registration_request = new Message("Server","Client","服务器同意注册请求", Utils.getCurrentTime(), MessageType.MESSAGE_ALLOW_CLIENT_REGISTRATION_REQUEST);
            //发送消息,同意客户端的注册请求
            oos.writeObject(message_allow_Client_registration_request);
            oos.flush();

            //等待客户端发送User对象
            User user = (User) ois.readObject();//接收客户端发来的User对象
            //得到用户名、密码
            String userName = user.getUserName();
            String password = user.getPassword();
            //检查是否是user_exit ---- 检测客户端是否想取消注册请求
            if(userName.equals("\\back")&&password.equals("\\back")){
                //检测到是客户端想要取消注册请求
                //服务器直接返回到等待响应状态
                return;
            }

            //开始验证用户名是否合法
            if(!server.getUserDatabase().containsKey(userName)&&!server.getManagerDatabase().containsKey(userName)){
                //用户名不存在与普通用户数据库和管理人员数据库，可以进行注册
                //添加到用户数据库中
                server.getUserDatabase().put(userName,password);
                //用户注册成功！创建注册成功消息,打印日志
                LogPrinter.printLog(clientSocket.getInetAddress().getHostAddress() + " 注册成功！ 用户名为： " + userName + " 密码为： " + password);
                Message message_registration_succeed = new Message("Server","Client","注册成功",Utils.getCurrentTime(),MessageType.MESSAGE_ACCOUNT_REGISTRATION_SUCCEED);
                //发送注册成功消息
                oos.writeObject(message_registration_succeed);
                oos.flush();
                //返回等待界面
                return;
            }else{
                //用户名存在，注册失败，返回失败Message
                Message message_registration_failed = new Message("Server", "Client", "用户名已存在", Utils.getCurrentTime(), MessageType.MESSAGE_ACCOUNT_REGISTRATION_FAILED);
                //发送消息
                oos.writeObject(message_registration_failed);
                oos.flush();
                //回到等待请求界面
                return;
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

}
