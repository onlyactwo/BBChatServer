package com.lzx.BBChat.Common.User;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

/**
 * 这个是用户的基本信息，可以用于登录验证（登录成功后对需要补充的信息进行补充），注册验证（不需要补充信息）
 * 1.账号
 * 2.密码
 * 3.身份（在登录成功以后再进行赋值）
 * 4.Socket（在登录成功以后再进行赋值）
 * 5.oos，ois（在登录成功以后再进行赋值）
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private String userName;//用户名
    private String password;//密码
    private String identity;//身份（普通用户/管理员）
    private Socket socket;//该用户的Socket
    private ObjectOutputStream oos;//用于与该用户通信
    private ObjectInputStream ois;//用于与该用户通信

    //两种身份
    public static final String IDENTITY_NORMAL = "IDENTITY_NORMAL";
    public static final String IDENTITY_MANAGER = "IDENTITY_MANAGER";

    public User() {}

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

   /* @Override*/
   /* public String toString() {
        if (identity.equals("IDENTITY_NORMAL")) {
            //普通用户
            return "普通用户" + userName;
        } else if (identity.equals("IDENTITY_MANAGER")) {
            //管理人员
            return "管理人员" + userName;
        }
        return "用户未知";
    }*/

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public ObjectOutputStream getOos() {
        return oos;
    }

    public void setOos(ObjectOutputStream oos) {
        this.oos = oos;
    }

    public ObjectInputStream getOis() {
        return ois;
    }

    public void setOis(ObjectInputStream ois) {
        this.ois = ois;
    }

}
