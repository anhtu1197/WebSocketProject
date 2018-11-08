package com.myself.nettychat.constont;

import com.myself.nettychat.dataobject.UserMsg;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class LikeSomeCacheTemplate {

    private List<UserMsg> SomeCache = new LinkedList<>();

    public void save(Object user,Object msg){
        UserMsg userMsg = new UserMsg();
        userMsg.setName(String.valueOf(user));
        userMsg.setMsg(String.valueOf(msg));
        SomeCache.add(userMsg);
    }

    public List<UserMsg> cloneCacheMap(){
        return SomeCache;
    }

    public void clearCacheMap(){
        SomeCache.clear();
    }
}
