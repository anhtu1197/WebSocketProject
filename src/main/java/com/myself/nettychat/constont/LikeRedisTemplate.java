package com.myself.nettychat.constont;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class LikeRedisTemplate {

    private Map<Object,Object> RedisMap = new ConcurrentHashMap<>();

    private Map<Object,Object> SecondRedisMap = new ConcurrentHashMap<>();

    private Map<Object,Object> ChannelRedisMap = new ConcurrentHashMap<>();

    public void save(Object id,Object name){
        RedisMap.put(id,name);
        SecondRedisMap.put(name,id);
    }


    public void saveChannel(Object name,Object channel){
        ChannelRedisMap.put(name,channel);
    }

    public void deleteChannel(Object name){
        ChannelRedisMap.remove(name);
    }


    public Object getChannel(Object name){
        return ChannelRedisMap.get(name);
    }


    public Integer getSize(){
        return ChannelRedisMap.size();
    }


    public Object getName(Object id){
        return RedisMap.get(id);
    }


    public boolean check(Object id,Object name){
        if (SecondRedisMap.get(name) == null){
            return true;
        }
        if (id.equals(SecondRedisMap.get(name))){
            return true;
        }else{
            return false;
        }
    }

    public void delete(Object id){
        try {
            SecondRedisMap.remove(RedisMap.get(id));
            RedisMap.remove(id);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }


    public Object getOnline() {
        List<Object> result = new ArrayList<>();
        for (Object key:ChannelRedisMap.keySet()){
            result.add(key);
        }
        return result;
    }
}
