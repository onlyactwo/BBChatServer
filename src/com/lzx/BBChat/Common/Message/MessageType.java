package com.lzx.BBChat.Common.Message;

/**
 * 该类是用于定义Message的类型，以此服务器/客户端做出不同的相应
 * 1.记得要同步到Client端
 */
public class MessageType {


    /* 这是静态方法！
     基本说明：
     这个接口用于定义常量，不同常量用于表示不同的消息类型，表示不同的用途！
     例如，客户端去检测服务器端发回来的消息是什么类型，做出不同的处理
     服务器端去检测客户端发来的消息是什么类型，同样也做出不同的处理
 */
    //登录请求
    public static String MESSAGE_LOGIN_REQUEST = "MESSAGE_LOGIN_REQUEST";

    //注册请求
    public static String MESSAGE_REGISTRATION_REQUEST = "MESSAGE_REGISTRATION_REQUEST";

    //服务器允许登录请求
    public static String MESSAGE_ALLOW_CLIENT_LOGIN_REQUEST = "MESSAGE_ALLOW_CLIENT_LOGIN_REQUEST";

    //服务器允许注册请求
    public static String MESSAGE_ALLOW_CLIENT_REGISTRATION_REQUEST = "MESSAGE_ALLOW_CLIENT_LOGIN_REQUEST";

    //登录成功---- 普通用户
    public static String MESSAGE_LOGIN_SUCCEED_NORMAL_USER = "MESSAGE_LOGIN_SUCCEED_NORMAL_USER";

    //登录成功---- 管理人员
    public static String MESSAGE_LOGIN_SUCCEED_MANAGER = "MESSAGE_LOGIN_SUCCEED_MANAGER";

    //登录失败
    public static String MESSAGE_LOGIN_FAILED = "MESSAGE_LOGIN_FAILED";

    //发送的消息的类型是检测该账号是否存在
    public static String MESSAGE_VERIFY_IS_ACCOUNT_EXISTS = "MESSAGE_VERIFY_IS_ACCOUNT_EXISTS";

    //账号存在
    public static String MESSAGE_ACCOUNT_EXIST = "MESSAGE_ACCOUNT_EXIST";

    //账号不存在
    public static String MESSAGE_ACCOUNT_IS_NOT_EXIST = "MESSAGE_ACCOUNT_IS_NOT_EXIST";

    //账号注册成功
    public static String MESSAGE_ACCOUNT_REGISTRATION_SUCCEED = "SUCCEED";

    //账号注册失败
    public static String MESSAGE_ACCOUNT_REGISTRATION_FAILED = "FAILED";

    //私聊 - 文本文件
    public static String MESSAGE_PRIVATE_CHAT_TXT = "MESSAGE_PRIVATE_CHAT_TXT";

    //私聊请求
    public static String MESSAGE_PRIVATE_CHAT_REQUEST = "MESSAGE_PRIVATE_CHAT_REQUEST";

    //服务器同意私聊请求
    public static String MESSAGE_ALLOW_PRIVATE_CHAT_REQUEST = "MESSAGE_ALLOW_PRIVATE_CHAT_REQUEST";

    //群聊请求
    public static String MESSAGE_GROUP_CHAT_REQUEST = "MESSAGE_GROUP_CHAT_REQUEST";

    //服务器同意群聊请求
    public static String MESSAGE_ALLOW_GROUP_CHAT_REQUEST = "MESSAGE_ALLOW_GROUP_CHAT_REQUEST";

    //群聊 - 文本文件
    public static String MESSAGE_GROUP_CHAT_TXT = "MESSAGE_GROUP_CHAT_TXT";

    //服务器推送消息
    public static String MESSAGE_SERVER_SEND_TXT = "MESSAGE_SERVER_SEND_TXT";

    //客户端请求关闭软件
    public static String MESSAGE_EXIT = "MESSAGE_EXIT";

    //私聊用户不存在
    public static String MESSAGE_PRIVATE_USER_IS_NOT_EXIST = "MESSAGE_PRIVATE_USER_IS_NOT_EXIST";

    //私聊用户在线
    public static String MESSAGE_PRIVATE_USER_ONLINE = "MESSAGE_PRIVATE_USER_ONLINE";

    //私聊用户不在线
    public static String MESSAGE_PRIVATE_USER_OFFLINE = "MESSAGE_PRIVATE_USER_OFFLINE";

    //私聊退出
    public static String MESSAGE_PRIVATE_CHAT_EXIT ="MESSAGE_PRIVATE_CHAT_EXIT";

    //群聊退出
    public static String MESSAGE_GROUP_CHAT_EXIT = "MESSAGE_GROUP_CHAT_EXIT";
}
