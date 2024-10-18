package com.lzx.BBChat.Common.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * 这个类是 一个 客户端与服务器端 共有的工具类
 * 1.输入
 */
public class Utils {
    private static final Scanner sc = new Scanner(System.in);
    public static int getInt(){
        while (true) {
            try {
                int res = sc.nextInt();
                return res;
            } catch (Exception e) {
                System.out.println("输入有误，请检查！");
            }
        }
    }

    public static  double getDouble(){
        while(true){
            try {
                double res = sc.nextDouble();
                return res;
            } catch (Exception e) {
                System.out.println("输入有误，请检查！");
            }
        }
    }

    public static String getString(int minLength, int maxLength){
        while (true) {
            try {
                String s = sc.nextLine();
                if(s.length() >= maxLength||s.length()<= minLength){
                    System.out.println("长度不对，请检查！");
                    continue;
                }
                return s;
            } catch (Exception e) {
                System.out.println("输入有误，请检查！");
            }
        }

    }

    public static char getChar(){
        while(true){
            char res;
            String receive = sc.nextLine();
            if(receive.length()!=1){
                System.out.println("长度不对，请检查输入！");
            }else{
                res = receive.charAt(0);
                return res;
            }
        }
    }

    public static String getCurrentTime(){
        //获取当前时间
        Date date = new Date();
        //规范化日期格式
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss E");
        //返回时间
        return simpleDateFormat.format(date);
    }
}
