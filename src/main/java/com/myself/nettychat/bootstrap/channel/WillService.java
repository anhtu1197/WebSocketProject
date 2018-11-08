package com.myself.nettychat.bootstrap.channel;

import com.myself.nettychat.bootstrap.BaseApi;
import com.myself.nettychat.bootstrap.ChannelService;
import com.myself.nettychat.bootstrap.bean.WillMeaasge;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@Data
@NoArgsConstructor
public class WillService implements BaseApi {

    @Autowired
    ChannelService channelService;

    private static ConcurrentHashMap<String, WillMeaasge> willMeaasges = new ConcurrentHashMap<>(); // deviceid -WillMeaasge



    /**
     *
     */
    public void save(String deviceid, WillMeaasge build) {
        willMeaasges.put(deviceid,build); //
    }


    public void doSend( String deviceId) {  //
        if(StringUtils.isNotBlank(deviceId)&&(willMeaasges.get(deviceId))!=null){
            WillMeaasge willMeaasge = willMeaasges.get(deviceId);
            channelService.sendWillMsg(willMeaasge); //
            if(!willMeaasge.isRetain()){ //
                willMeaasges.remove(deviceId);
                log.info("deviceId will message["+willMeaasge.getWillMessage()+"] is removed");
            }
        }
    }

    /**
     *
     */
    public void del(String deviceid ) {willMeaasges.remove(deviceid);}

}
