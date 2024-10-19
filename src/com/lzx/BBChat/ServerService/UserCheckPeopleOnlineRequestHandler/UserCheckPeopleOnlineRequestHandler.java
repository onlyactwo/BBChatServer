package com.lzx.BBChat.ServerService.UserCheckPeopleOnlineRequestHandler;

import com.lzx.BBChat.Common.Message.Message;
import com.lzx.BBChat.Common.Message.MessageType;
import com.lzx.BBChat.Common.Utils.Utils;
import com.lzx.BBChat.Server.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Set;

/**
 * 这个类用于处理客户拉取在线人员情况
 *
 */
public class UserCheckPeopleOnlineRequestHandler {
    public static void handleUserCheckPeopleOnlineRequest(Server server, String userName , ObjectOutputStream oos, ObjectInputStream ois){
        try {
            //返回给客户端，同意请求
            Message message_allow_user_check_message_inbox = new Message("Server", userName, "同意请求", Utils.getCurrentTime(), MessageType.MESSAGE_ALLOW_USER_CHECK_ONLINE_PEOPLE_REQUEST);
            //发送消息
            oos.writeObject(message_allow_user_check_message_inbox);

            //返回在线用户 keySet（）
            HashSet<Object> users = new HashSet<>(server.getUserOnline().keySet());

            //发送消息
            oos.writeObject(users);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
