package com.lzx.BBChat.Server;

import com.lzx.BBChat.Common.User.User;
import com.lzx.BBChat.Common.Utils.Utils;
import com.lzx.BBChat.ServerService.ClientHandler.ClientHandler;
import com.lzx.BBChat.ServerService.LogPrinter.LogPrinter;
import com.lzx.BBChat.ServerService.ServerChecker.CheckServer;
import com.lzx.BBChat.ServerService.ServerInit.ServerInit;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/**
 * 这个类是服务器端
 * 1.储存   HashMap<String,String> userDatabase ;
 * HashMap<String,String> managerDatabase ;
 * ---- 储存到本地
 * 初始化三个管理员账号密码
 * "admin" "123456aa"
 * "onlyactwo" "lzx2005!"
 * "liu" "ll123"
 * <p>
 * 初始化一个用户
 * "user1" "user1haha"
 * <p>
 * 服务器启动的时候读取数据，服务器关闭的时候再将database写入到本地
 * <p>
 * 2.管理在线用户 HashMap<username,userSocket>online
 * <p>
 * 3.
 */
public class Server {
    private static HashMap<String, String> userDatabase;
    private static HashMap<String, String> managerDatabase;
    private static HashMap<String, User> userOnline;
    private static final int ServerPort = 9999;

    public void startServer() {
        try {
            //初始化在线列表
            userOnline = new HashMap<>();

            //把用户数据加载到服务器中
            userDatabase = ServerInit.ServerInitUserDatabase();
            managerDatabase = ServerInit.ServerInitManagerDatabase();

            //判断用户数据是否加载成功
            if (userDatabase == null || managerDatabase == null) {
                throw new RuntimeException();
            }

        } catch (Exception e) {
            e.printStackTrace();
            LogPrinter.printLog("数据库导入失败！");
            System.exit(1);
        }


          /*  //测试数据是否导入成功
            System.out.println(userDatabase);
            System.out.println(managerDatabase);*/



        //数据导入成功以后开始运行服务器
        try {
            //绑定端口
            ServerSocket serverSocket = new ServerSocket(ServerPort);
            while (true) {
                //服务器等待连接
                Socket socket = serverSocket.accept();
                //连接成功
                LogPrinter.printLog("用户 " + socket.getInetAddress().getHostAddress() + "连接上了服务器");
                //连接成功，启动客户端线程
                ClientHandler clientHandler = new ClientHandler(this, socket);//创建线程任务
                Thread clientHandlerThread = new Thread(clientHandler);
                clientHandlerThread.start();
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {

        //创建服务器
        Server server = new Server();
        //启动查看服务器信息线程
        CheckServer checkServer = new CheckServer(server);
        checkServer.start();
        //启动服务器
        server.startServer();


    }

    public HashMap<String, String> getUserDatabase() {
        return userDatabase;
    }

    public void setUserDatabase(HashMap<String, String> userDatabase) {
        Server.userDatabase = userDatabase;
    }

    public HashMap<String, String> getManagerDatabase() {
        return managerDatabase;
    }

    public void setManagerDatabase(HashMap<String, String> managerDatabase) {
        Server.managerDatabase = managerDatabase;
    }

    public HashMap<String, User> getUserOnline() {
        return userOnline;
    }

    public void setUserOnline(HashMap<String, User> userOnline) {
        Server.userOnline = userOnline;
    }
}
