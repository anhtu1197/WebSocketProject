package com.myself.nettychat.common.utils;


public class DataResction {


    public static String ResctionHeadAndFeet(String data){
        return data.substring(2,data.length()-2);
    }


    public static String ResctionHeadAndFeet_test(String data){
        return data.substring(2,data.length()-4);
    }


    public static String ResctionCRCCode(String data){
        return data.substring(39,data.length());
    }


    public static String ResctionData(String data){
        return data.substring(0,39);
    }

    public static String ResctionDataNoID(String data){
        return data.substring(14);
    }


    public static String ResctionID(String data){
        return data.substring(0,14);
    }


    public static String ResctionType(String data){
        return data.substring(0,1);
    }


    public static String ResctionRealData(String data){
        return data.substring(1);
    }


    public static String ResctionPower(String data){
        return data.substring(0,4);
    }

    public static String ResctionLatitude(String data){
        return data.substring(3,11);
    }


    public static String ResctionLongitude(String data){
        return data.substring(14,data.length());
    }


}
