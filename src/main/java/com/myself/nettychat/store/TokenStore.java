package com.myself.nettychat.store;

import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Data
public class TokenStore {

    private static Map<String,Object> TokenStoreMap = new ConcurrentHashMap<String,Object>();


    public static void add(String token,Object userId){
        TokenStoreMap.put(token,userId);
    }

    public static void remove(String token){
        TokenStoreMap.remove(token);
    }

    /
    public static Object get(String token){
        return TokenStoreMap.get(token);
    }
}
