package com.lzx.BBChat.ServerService.ServerInit;

import com.lzx.BBChat.Common.Utils.Utils;
import com.lzx.BBChat.ServerService.LogPrinter.LogPrinter;

import java.io.*;
import java.util.HashMap;

/**
 * 这个类是用于对服务器进行初始化
 * 1.读取储存在本地的用户数据,加载到服务器
 * 2.读取储存在本地的管理人员数据，加载到服务器
 * "admin" "123456aa"
 * *         "onlyactwo" "lzx2005!"
 * *         "liu" "ll123"
 */
public class ServerInit {
    //文件路径
   private static final File userDatabaseFile = new File("D:\\myjavacode\\小项目\\BBChat\\BBChatServer\\src\\com\\lzx\\BBChat\\Database\\userDatabase.dat");
   private static final File managerDatabaseFile = new File("D:\\myjavacode\\小项目\\BBChat\\BBChatServer\\src\\com\\lzx\\BBChat\\Database\\managerDatabase.dat");
    private static ObjectInputStream ois ;
    public static HashMap<String,String> ServerInitUserDatabase() {
        try {
            HashMap<String, String> userDatabase;
            //加载userDatabase
            ois= new ObjectInputStream(new FileInputStream(userDatabaseFile));
            userDatabase = (HashMap<String, String>) ois.readObject();
            //打印服务器日志
            LogPrinter.printLog("服务器加载用户数据成功！");

            ois.close();
            return userDatabase;

        } catch (IOException e) {
            //打印服务器日志
            LogPrinter.printLog("服务器建立IO失败，请检查本地数据库路径是否正确！");
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            LogPrinter.printLog("加载本地数据失败，请检查路径！");
            e.printStackTrace();
            return null;
        }

    }

    public static HashMap<String,String> ServerInitManagerDatabase() {
        try {
            HashMap<String,String>managerDatabase;
            ois= new ObjectInputStream(new FileInputStream(managerDatabaseFile));
            managerDatabase =(HashMap<String, String>) ois.readObject();
            LogPrinter.printLog("服务器加载用户数据成功！");
            ois.close();
            return managerDatabase;

        } catch (IOException e) {
            LogPrinter.printLog("服务器建立IO失败，请检查本地数据库路径是否正确！");
            return null;
        } catch (ClassNotFoundException e) {
            LogPrinter.printLog("加载本地数据失败，请检查路径！");
            return null;
        }

    }

}
