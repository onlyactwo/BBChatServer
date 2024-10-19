package com.lzx.BBChat.ServerService.UserOfflinePrivateChatHandler;

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
 * 这个类是用于 服务器处理 用户给离线用户发送消息
 * 如果说用户不在线，那么就要将该用户添加到维护信箱的列表中去！
 */
public class UserOfflinePrivateChatHandler {
    public static void handleUserOfflinePrivateChat(Server server, String userName , String targetName,ObjectOutputStream oos , ObjectInputStream ois){
        try {
           Message message_return_send_message_result;
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
                        message_return_send_message_result = new Message("Server",userName,"发送成功", Utils.getCurrentTime(),MessageType.MESSAGE_PRIVATE_CHAT_SEND_SUCCEED);
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
                        message_return_send_message_result = new Message("Server",userName,"发送成功", Utils.getCurrentTime(),MessageType.MESSAGE_PRIVATE_CHAT_SEND_SUCCEED);
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
