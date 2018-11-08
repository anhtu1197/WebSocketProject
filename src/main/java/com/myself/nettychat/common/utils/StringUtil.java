package com.myself.nettychat.common.utils;


public class StringUtil {

    public static String getName(String str){
        int nameIndex = str.indexOf("-");
        return str.substring(0,nameIndex);
    }


    public static String getMsg(String str){
        int nameIndex = str.indexOf("-");
        return str.substring(nameIndex + 1,str.length());
    }
}
