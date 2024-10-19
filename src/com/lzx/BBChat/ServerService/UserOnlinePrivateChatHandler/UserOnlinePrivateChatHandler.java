package com.lzx.BBChat.ServerService.UserOnlinePrivateChatHandler;

import com.lzx.BBChat.Common.Message.Message;
import com.lzx.BBChat.Common.Message.MessageInBox;
import com.lzx.BBChat.Common.Message.MessageType;
import com.lzx.BBChat.Common.Utils.Utils;
import com.lzx.BBChat.Server.Server;
import com.lzx.BBChat.ServerService.LogPrinter.LogPrinter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 这个类服务器是用于处理 A用户与在线 B用户之间的私聊请求
 * 前提：
 * 服务器向客户端发送了该用户在线的Message，现在客户端会向服务器不断地发送 Message（服务器端需要去不断地接收该用户发来的Message）
 * 思路：
 * 1.服务器不断接收客户端发来的消息（要检测是正常的消息，还是想要退出私聊）
 * 2.服务器将该消息转发给对应的 B用户的 Socket
 * 3.用户应该不断监听服务器发来的消息！
 * 需要：
 * A、B用户的用户名
 * Server -》得到Online集合-》查找B用户的User-》得到与B用户通信的Socket-》oos
 */
public class UserOnlinePrivateChatHandler {
    public static void handleUserOnlinePrivateChat(Server server, String userName , String targetName, ObjectOutputStream oos ,ObjectInputStream ois){
        try {
            Message message_return_send_message_result = new Message("Server",userName,"发送成功", Utils.getCurrentTime(),MessageType.MESSAGE_PRIVATE_CHAT_SEND_SUCCEED);
            while (true) {
                //不断接收用户发来的Message
                Message message_from_user_to_targetUser = (Message)ois.readObject();
                //判断该语句的内容
                if(message_from_user_to_targetUser.getMesType().equals(MessageType.MESSAGE_PRIVATE_CHAT_TXT)){
                    //是正常的消息
                    //服务器转发给该用户的信箱
                    //在维护的信箱里面查看，是否维护了
                    if(server.getUserMessageInBox().containsKey(targetName)){
                        //服务器维护了该用户的信箱
                        //向该信箱内添加Message
                        MessageInBox targetUserMessageInBox = server.getUserMessageInBox().get(targetName);
                        targetUserMessageInBox.addMessageToBox(message_from_user_to_targetUser);

                        //返回发送的结果 ---- 成功
                        //发送消息
                        oos.writeObject(message_return_send_message_result);
                        oos.flush();
                    }else{
                        //服务器没有维护该用户的信箱
                        //为该用户维护信箱
                        server.getUserMessageInBox().put(targetName,new MessageInBox());
                        //向该信箱发送消息
                        MessageInBox targetUserMessageInBox = server.getUserMessageInBox().get(targetName);
                        targetUserMessageInBox.addMessageToBox(message_from_user_to_targetUser);

                        //返回发送的结果 ---- 成功
                        //发送消息
                        oos.writeObject(message_return_send_message_result);
                        oos.flush();
                    }
                }else if(message_from_user_to_targetUser.getMesType().equals(MessageType.MESSAGE_PRIVATE_CHAT_EXIT)){
                    //用户退出消息
                    //服务器不用再接收 客户端发来的消息了
                    //退出到等待状态
                    break;
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            LogPrinter.printLog(userName + "发送消息失败！");
            throw new RuntimeException(e);
        }

    }
}
