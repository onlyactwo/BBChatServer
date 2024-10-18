package com.lzx.BBChat.Common.Message;

import java.io.Serializable;

/**
 * 这个类是消息的载体，需要实现串行化
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    //发送者
    String sender;
    //接收者
    String receiver;
    //发送的内容
    String content;
    //发送的时间
    String sendTime;
    //发送的类型 ---- 表示不同消息的类型 ---- 以此做出不同的处理
    String mesType;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getMesType() {
        return mesType;
    }

    public void setMesType(String mesType) {
        this.mesType = mesType;
    }

    public Message(String sender, String receiver, String content, String sendTime, String mesType) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.sendTime = sendTime;
        this.mesType = mesType;
    }
}
