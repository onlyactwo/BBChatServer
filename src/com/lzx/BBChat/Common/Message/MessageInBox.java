package com.lzx.BBChat.Common.Message;

import java.io.Serializable;
import java.util.Vector;

/**
 * 这个是用户的收件箱，服务器转发的私聊消息、服务器推送的公告都可以传到收件箱中
 * 服务器维护一个HashMap<userName,MessageInbox>userMessageInBox,用户的信箱
 * 如果服务器关闭，也不需要保存数据
 * 添加用户的情况：
 * 1.
 * 当A用户给B用户发送私聊消息的时候
 * 就去检查 服务器是否维护了 B用户的信箱，如果没有维护，那么就把B用户添加进去
 *
 * 2.当用户登录的时候，也要去检查自己是否在这个里面，如果不在，也要把自己添加进去
 * 当登录的时候，就开启一个线程，不断地去查看是否有收件箱内是否有消息
 */
public class MessageInBox implements Serializable {
    //这个Vector用于装服务器发来的/转发的 消息
    public  Vector<Message> messageInBox = new Vector<>();

    //向信箱中添加消息
    public  void addMessageToBox(Message message){
        messageInBox.add(message);
    }

    //查看信箱是否为空
    public  boolean isMessageBoxEmpty(){
        return messageInBox.isEmpty();
    }

    //查看信箱的内容
    public  Vector<Message> readMessageBox(){
        return messageInBox;
    }
    //清空信箱的内容
    public void clearMessageInBox(){
        messageInBox.clear();
    }
}
