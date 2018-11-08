package com.myself.nettychat.task;

import com.myself.nettychat.constont.LikeSomeCacheTemplate;
import com.myself.nettychat.dataobject.User;
import com.myself.nettychat.dataobject.UserMsg;
import com.myself.nettychat.repository.UserMsgRepository;
import com.myself.nettychat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Future;


@Component
public class MsgAsyncTesk {

    @Autowired
    private LikeSomeCacheTemplate cacheTemplate;

    @Autowired
    private UserMsgRepository userMsgRepository;

    @Autowired
    private UserRepository userRepository;

    @Async
    public Future<Boolean> saveChatMsgTask() throws Exception{

        List<UserMsg> userMsgList = cacheTemplate.cloneCacheMap();
        for (UserMsg item:userMsgList){
            
            User user = userRepository.findByUserName(item.getName());
            if (user != null){
                userMsgRepository.save(item);
            }
        }
        
        cacheTemplate.clearCacheMap();
        return new AsyncResult<>(true);
    }

}
