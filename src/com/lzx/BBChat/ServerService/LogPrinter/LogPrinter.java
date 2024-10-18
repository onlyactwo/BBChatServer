package com.lzx.BBChat.ServerService.LogPrinter;

import com.lzx.BBChat.Common.Utils.Utils;

/**
 * 这个类用于打印服务器日志
 */
public class LogPrinter {
    public static void printLog(String info){
        System.out.println("[  " + Utils.getCurrentTime()+ "    " + info + "  ]");
    }
}
