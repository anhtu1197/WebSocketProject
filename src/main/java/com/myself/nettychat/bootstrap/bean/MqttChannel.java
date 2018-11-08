package com.myself.nettychat.bootstrap.bean;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import lombok.Builder;
import lombok.Data;

import com.myself.nettychat.common.enums.SubStatus;
import com.myself.nettychat.common.enums.SessionStatus;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


@Builder
@Data
public class MqttChannel {

    private transient  volatile Channel channel;


    private String deviceId;


    private boolean isWill;


    private volatile SubStatus subStatus;


    private Set<String> topic  ;



    private volatile SessionStatus sessionStatus;



    private volatile boolean cleanSession;




    private ConcurrentHashMap<Integer,SendMqttMessage> message ; // messageId - message(qos1)  //


    private Set<Integer>  receive;

    public void  addRecevice(int messageId){
        receive.add(messageId);
    }

    public boolean  checkRecevice(int messageId){
        return  receive.contains(messageId);
    }

    public boolean  removeRecevice(int messageId){
        return receive.remove(messageId);
    }


    public void addSendMqttMessage(int messageId,SendMqttMessage msg){
        message.put(messageId,msg);
    }


    public SendMqttMessage getSendMqttMessage(int messageId){
        return  message.get(messageId);
    }


    public  void removeSendMqttMessage(int messageId){
        message.remove(messageId);
    }


    public boolean isLogin(){
        return Optional.ofNullable(this.channel).map(channel1 -> {
            AttributeKey<Boolean> _login = AttributeKey.valueOf("login");
            return channel1.isActive() && channel1.hasAttr(_login);
        }).orElse(false);
    }


    public void close(){
        Optional.ofNullable(this.channel).ifPresent(channel1 -> channel1.close());
    }

    public  boolean isActive(){
        return  channel!=null&&this.channel.isActive();
    }



    public boolean addTopic(Set<String> topics){
        return topic.addAll(topics);
    }


}
