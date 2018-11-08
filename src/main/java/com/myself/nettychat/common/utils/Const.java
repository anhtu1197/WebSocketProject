package com.myself.nettychat.common.utils;

import io.netty.channel.Channel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


public class Const {

    public static final String HEAD = "gz";
    public static final String TAIL = "xr";
    public static final String CONTROL_TYPE = "s";
    public static final String LOCKS = "nnnnnnnnnnnnnnnnnnnnnnnn";
    public static final char OPEN = 'y';
    public static final String RESULT_TYPE = "j";
    public static final String RESULT_TEXT = "t";
    public static final String SUCCESS = "yyyyyyyyyyyyyyyyyyyyyyyy";
    public static final String ERROR = "nnnnnnnnnnnnnnnnnnnnnnnn";

    private static Map<String, Channel> map = new ConcurrentHashMap<String, Channel>();

    public static void add(String clientId,Channel channel){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("add ChannelId: " + clientId + " " + formatter.format(new Date()));
        map.put(clientId,channel);
    }

    public static boolean hasChannelID(String channelID){
        boolean state = map.containsKey(channelID);
        return state;
    }


    public static Channel get(String clientId){
        return map.get(clientId);
    }


    public static void remove(Channel channel){
        for (Map.Entry entry:map.entrySet()){
            if (entry.getValue()==channel){
                map.remove(entry.getKey());
            }
        }
    }

    public static int getSzie(){
        return map.size();
    }


    public static Set<String> getIdList(){
        return map.keySet();
    }


    public static void mapInfo(){
        System.out.println("channel size : " + map.size());
        for (Map.Entry entry:map.entrySet()){
            System.out.println("clientId: " + entry.getKey());
        }
    }


    public static String isChannel(Channel channel){
        String clientId = null;
        for (Map.Entry entry:map.entrySet()){
            if (entry.getValue() == channel){
                clientId = (String) entry.getKey();
            }
        }
        return clientId;
    }


    public static void ChangeClientId(String clientId,String newID){
        for (Map.Entry entry:map.entrySet()){
            if (entry.getKey()==clientId){
                Channel channel = (Channel) entry.getValue();
                map.remove(entry.getKey());
                map.put(newID,channel);
            }
        }
    }

    public static void changeChannel(String channelID, Channel channel) {
        map.remove(channelID);
        map.put(channelID,channel);
    }

    public enum StateEnum{
        SUCCESS(1,"ok"),
        FALID(2,"no");
        private int state;

        private String stateInfo;

        StateEnum(int state, String stateInfo) {
            this.state = state;
            this.stateInfo = stateInfo;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getStateInfo() {
            return stateInfo;
        }

        public void setStateInfo(String stateInfo) {
            this.stateInfo = stateInfo;
        }

        public static StateEnum stateOf(int index){
            for (StateEnum item:values()){
                if (item.getState() == index){
                    return item;
                }
            }
            return null;
        }
    }


}
