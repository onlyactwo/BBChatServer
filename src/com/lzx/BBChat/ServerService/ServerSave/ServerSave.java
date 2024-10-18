package com.lzx.BBChat.ServerService.ServerSave;

import java.io.*;
import java.util.HashMap;

/**
 * 这个类主要是用于当服务器退出时，保存用户数据到本地，只保存用户数据，一般管理员数据不做修改
 */

@SuppressWarnings({"all"})
public class ServerSave {
    public static void ServerSave(HashMap<String,String>userDatabase){
        try {
            //文件路径
            File userDatabaseFile = new File("D:\\myjavacode\\小项目\\BBChat\\BBChatServer\\src\\com\\lzx\\BBChat\\database\\userDatabase.dat");
            //建立O流，分别加载本地数据
            ObjectOutputStream ois ;
            //加载userDatabase
            ois= new ObjectOutputStream(new FileOutputStream(userDatabaseFile));
            ois.writeObject(userDatabaseFile);

            System.out.println("服务器保存用户数据成功！");
            ois.close();

        } catch (IOException e) {
            System.out.println("服务器建立IO失败，请检查本地数据库路径是否正确！");
        }
    }
}
