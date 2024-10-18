package com.lzx.BBChat.ServerService.ServerChecker;

import com.lzx.BBChat.Server.Server;
import com.lzx.BBChat.ServerService.LogPrinter.LogPrinter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * 这个类主要是用于 给服务器发送指令，查看服务器的一些资源的线程
 * 要持有构造器的引用
 */
public class CheckServer extends Thread {
    private final Server server;
    private final String getUserDatabase = "\\getUserDatabase";
    private final String getManagerDatabase = "\\getManagerDatabase";
    private final String getUserOnline = "\\getUserOnline";
    private final String getAllOrder = "\\help";
    private HashMap<String, String> order_all = new HashMap<>();//存放所有指令

    public CheckServer(Server server) {
        this.server = server;
        //构造器，将指令添加到集合中去
        order_all.put("获取所有普通用户信息： ", getUserDatabase + "\n");
        order_all.put("获取所有管理人员信息:  ", getManagerDatabase + "\n");
        order_all.put("获取所有在线用户信息： ", getUserOnline + "\n");
        order_all.put("获取所有指令： ", getAllOrder + "\n");
    }

    @Override
    public void run() {

        Scanner sc = new Scanner(System.in);
        String order;
        while (true) {
            //监听服务器指令
            order = sc.nextLine();
            switch (order) {
                case "\\getUserDatabase":
                    //获取所有普通用户信息
                    LogPrinter.printLog(server.getUserDatabase().toString());
                    break;
                case "\\getManagerDatabase":
                    //获取所有管理人员信息
                    LogPrinter.printLog(server.getManagerDatabase().toString());
                    break;
                case "\\getUserOnline":
                    //获取所有在线用户信息
                    LogPrinter.printLog(server.getUserOnline().toString());
                    break;
                case "\\help":
                    //打印所有指令信息
                    LogPrinter.printLog(order_all.toString());
                default:
                    LogPrinter.printLog("指令输入错误！查看所有指令请输入\\help");
                    break;
            }

        }
    }
}
