package com.lzx.BBChat.ServerService.UserCheckMessageInBoxHandler;

import com.lzx.BBChat.Common.Message.Message;
import com.lzx.BBChat.Common.Message.MessageInBox;
import com.lzx.BBChat.Common.Message.MessageType;
import com.lzx.BBChat.Common.Utils.Utils;
import com.lzx.BBChat.Server.Server;
import com.lzx.BBChat.ServerService.LogPrinter.LogPrinter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

/**
 * 这个类是服务器用于处理客户端请求查看收信箱
 * 需要：
 * server ,oos,ois
 */
public class UserCheckMessageInBoxHandler {
    public static void handlerUserCheckMessageInBox(Server server,String userName ,ObjectOutputStream oos, ObjectInputStream ois ){
        try {
            //返回给客户端，同意请求
            Message message_allow_user_check_message_inbox = new Message("Server", userName, "同意请求", Utils.getCurrentTime(), MessageType.MESSAGE_ALLOW_USER_CHECK_MESSAGE_INBOX_REQUEST);
            //发送消息
            oos.writeObject(message_allow_user_check_message_inbox);

            //向客户端发送 该用户的MessageInBox 并清空
            MessageInBox messageInBox = server.getUserMessageInBox().get(userName);
            oos.writeObject(messageInBox);
            //清空
            server.getUserMessageInBox().put(userName,new MessageInBox());
            LogPrinter.printLog(userName + " 清空了收件箱 ");
            //结束
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
