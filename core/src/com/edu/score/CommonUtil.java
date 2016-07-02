package com.edu.score;

/**
 * Created by Administrator on 2016/7/1.
 */
public class CommonUtil {

    public static boolean isNullOrEmpty(Object obj){
        String var = String.valueOf(obj).trim();
        if(var.equals("null") || var.isEmpty())
            return true;
        else
            return false;
    }
}
