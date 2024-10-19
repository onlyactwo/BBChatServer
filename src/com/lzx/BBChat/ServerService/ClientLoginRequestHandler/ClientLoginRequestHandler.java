package com.lzx.BBChat.ServerService.ClientLoginRequestHandler;

import com.lzx.BBChat.Common.Message.Message;
import com.lzx.BBChat.Common.Message.MessageInBox;
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
 * 该类用于处理用户登录的请求
 * 由于要验证 账号密码是否存在，所以该类要持有Server的引用
 * 并且持有与客户端通信的Socket
 * 作为函数参数使用
 */
public class ClientLoginRequestHandler {
    //处理用户登录请求
    public static void handleClientLoginRequest(Server server, Socket clientSocket ,ObjectOutputStream oos,ObjectInputStream ois) {
        try {
            //向客户端发送，允许用户登录
            Message message_allow_login = new Message("Server", "Client", "欢迎登录！", Utils.getCurrentTime(), MessageType.MESSAGE_ALLOW_CLIENT_LOGIN_REQUEST);//定义允许登录的Message
            oos.writeObject(message_allow_login);//发送Message
            oos.flush();//处理缓冲区

            //客户端接收到允许操作以后，向服务器端发送user对象
            User user = (User) ois.readObject();//接收客户端发来的User对象
            //得到用户名、密码
            String userName = user.getUserName();
            String password = user.getPassword();
            //检查是否是user_exit
            if(userName.equals("\\back")&&password.equals("\\back")){
                //检测到是客户端想要取消登录请求
                //服务器直接返回到等待响应状态
                return;
            }

            //开始验证User对象是否合法,并且验证是 普通用户 还是 管理人员
            if (!server.getUserDatabase().containsKey(userName)&&!server.getManagerDatabase().containsKey(userName)) {
                //不含有该账号，所以不合法
                // 定义Message，账号不存在，登录失败
                Message message_login_failed = new Message("Server", "Client", "账号不存在！请检查是否输入有误！", Utils.getCurrentTime(), MessageType.MESSAGE_LOGIN_FAILED);
                //发送Message
                oos.writeObject(message_login_failed);
                oos.flush();//处理缓冲

            } else if(server.getUserDatabase().containsKey(userName)){
                //该账号是普通用户的，验证密码是否正确
                if (!server.getUserDatabase().get(userName).equals(password)) {
                    //密码不正确，不合法
                    //定义Message，密码不正确，登录失败
                    Message message_login_failed = new Message("Server", "Client", "密码不正确！请检查是否输入有误！", Utils.getCurrentTime(), MessageType.MESSAGE_LOGIN_FAILED);
                    //发送Message
                    oos.writeObject(message_login_failed);
                    oos.flush();//处理缓冲
                } else {
                    //密码正确，合法,登录成功！

                    //完善该用户信息 ---- 普通用户 ---- socket ---- oos、ois
                    user.setIdentity(User.IDENTITY_NORMAL);//设置用户信息
                    user.setSocket(clientSocket);//设置用户Socket
                    user.setOos(oos);//向用户通信
                    user.setOis(ois);//接收用户通信

                    //将该用户添加到Online
                    server.getUserOnline().put(user.getUserName(),user);
                    LogPrinter.printLog("检测到普通用户  ： " + user.getUserName() + "登录，已添加到在线列表!");

                    //维护该用户的信箱
                    //把该用户添加到维护信箱的列表中(不存在的话)
                    if(!server.getUserMessageInBox().containsKey(user.getUserName())) {
                        server.getUserMessageInBox().put(user.getUserName(), new MessageInBox());
                    }

                    //定义Message，密码正确，登录成功
                    Message message_login_succeed = new Message("Server", "Client", "登录成功！", Utils.getCurrentTime(), MessageType.MESSAGE_LOGIN_SUCCEED_NORMAL_USER);
                    //发送Message
                    oos.writeObject(message_login_succeed);
                    oos.flush();//处理缓冲


                }
            }else if(server.getManagerDatabase().containsKey(userName)){
                //该账号是管理人员的，验证密码是否正确
                if (!server.getManagerDatabase().get(userName).equals(password)) {
                    //密码不正确，不合法
                    //定义Message，密码不正确，登录失败
                    Message message_login_failed = new Message("Server", "Client", "密码不正确！请检查是否输入有误！", Utils.getCurrentTime(), MessageType.MESSAGE_LOGIN_FAILED);
                    //发送Message
                    oos.writeObject(message_login_failed);
                    oos.flush();//处理缓冲
                } else {
                    //密码正确，合法

                    //完善该用户信息 ---- 管理用户 ---- socket ---- oos、ois
                    user.setIdentity(User.IDENTITY_MANAGER);//设置用户信息
                    user.setSocket(clientSocket);//设置用户Socket
                    user.setOos(oos);//向用户通信
                    user.setOis(ois);//接收用户通信

                    //将该用户添加到Online
                    server.getUserOnline().put(user.getUserName(),user);
                    LogPrinter.printLog("检测到管理人员  ： " + user.getUserName() + "登录，已添加到在线列表!");

                    //维护该用户的信箱
                    //把该用户添加到维护信箱的列表中(不存在的话)
                    if(!server.getUserMessageInBox().containsKey(user.getUserName())) {
                        server.getUserMessageInBox().put(user.getUserName(), new MessageInBox());
                    }

                    //定义Message，密码正确，登录成功
                    Message message_login_succeed = new Message("Server", "Client", "登录成功！", Utils.getCurrentTime(), MessageType.MESSAGE_LOGIN_SUCCEED_MANAGER);
                    //发送Message
                    oos.writeObject(message_login_succeed);
                    oos.flush();//处理缓冲

                }
            }

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
