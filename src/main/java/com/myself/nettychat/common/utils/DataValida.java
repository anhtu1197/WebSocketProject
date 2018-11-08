package com.myself.nettychat.common.utils;

public class DataValida {


    public static boolean ValidateHeadAndFeet(String data){
        boolean state = false;
        if (Const.HEAD.equals(data.substring(0,2)) && Const.TAIL.equals(data.substring(data.length()-2,data.length()))){
            return true;
        }
        return state;
    }

    public static boolean ValidateHeadAndFeet_test(String data){
        boolean state = false;
        if (Const.HEAD.equals(data.substring(0,2)) && Const.TAIL.equals(data.substring(data.length()-4,data.length()-2))){
            return true;
        }
        return state;
    }


    public static boolean ValidateCRCCode(String data,String crcCode){
        boolean state = false;
        if (crcCode.equals(CRC16MySelf.getCRC(data.getBytes()))){
            return true;
        }
        return state;
    }

}
