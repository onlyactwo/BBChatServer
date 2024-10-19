package com.lzx.BBChat.ServerService.UserPrivateChatRequestHandler;

import com.lzx.BBChat.Common.Message.Message;
import com.lzx.BBChat.Common.Message.MessageType;
import com.lzx.BBChat.Common.User.User;
import com.lzx.BBChat.Common.Utils.Utils;
import com.lzx.BBChat.Server.Server;
import com.lzx.BBChat.ServerService.UserOnlinePrivateChatHandler.UserOnlinePrivateChatHandler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/***
 * 该类是用于处理 用户发来的私聊请求
 * 1.向用户发送消息：同意用户私聊请求
 * 2.接收用户发来的想要私聊的用户的用户名
 * 3.验证该用户是否存在
 *      存在：进行 4
 *      不存在：返回私聊用户不存在！
 * 4.验证该用户是否在线：
 *      在线：
 *      返回：私聊用户在线
 *      不在线：
 *      返回：私聊用户不在线
 *
 *      需要：
 *      1.A用户的用户名
 *      2.服务器对他的 oos、ois
 *      3.服务器
 */
public class UserPrivateChatRequestHandler {
    public static void handleUserPrivateChatRequest(Server server, String userName,ObjectOutputStream oos, ObjectInputStream ois){

        try {
            //同意用户私聊的请求
            //创建同意用户私聊请求Message
            Message message_allow_private_chat_request = new Message("Server", userName, "同意私聊请求", Utils.getCurrentTime(), MessageType.MESSAGE_ALLOW_PRIVATE_CHAT_REQUEST);
            //发送给用户
            oos.writeObject(message_allow_private_chat_request);
            oos.flush();

            //接收用户发来的targetUser，对该目标用户进行验证
            //该用户对象不包含Socket、identity、oos、ois等信息
            //只有Server.Online里面才有以上信息
            User targetUser = (User) ois.readObject();

            if(!server.getUserDatabase().containsKey(targetUser.getUserName())){
                //该用户不存在,返回服务器验证结果
                //创建 该用户不存在结果 Message
                Message message_private_user_is_not_exist = new Message("Server", userName, "目标用户不存在", Utils.getCurrentTime(), MessageType.MESSAGE_PRIVATE_USER_IS_NOT_EXIST);
                //发送该Message
                oos.writeObject(message_private_user_is_not_exist);
                oos.flush();
                //服务器返回等待响应状态
                return;
            }else if(server.getUserOnline().containsKey(targetUser.getUserName())){
                //用户在线
                //创建 该用户在线结果 Message
                Message message_private_user_online = new Message("Server", userName, "目标用户在线", Utils.getCurrentTime(), MessageType.MESSAGE_PRIVATE_USER_ONLINE);
                //发送该Message
                oos.writeObject(message_private_user_online);
                oos.flush();

                //开启在线聊天功能
                //传递参数，包括server（用于得到Online），userName(发送者),targetName（接收者，用这个名字在Online中去得到User）
                UserOnlinePrivateChatHandler.handleUserOnlinePrivateChat(server,userName,targetUser.getUserName(),ois);
                //调用结束聊天功能以后，服务器返回等待响应状态,客户端返回到选择功能界面
                return;
            }else {
                //用户离线
                //创建 该用户离线结果 Message
                Message message_private_user_offline = new Message("Server", userName, "目标用户离线", Utils.getCurrentTime(), MessageType.MESSAGE_PRIVATE_USER_OFFLINE);
                //发送该Message
                oos.writeObject(message_private_user_offline);
                oos.flush();

                //开启离线聊天功能
                //代写。。。。。

            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
