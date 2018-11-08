package com.myself.nettychat.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import com.myself.nettychat.common.enums.ProtocolEnum;
import com.myself.nettychat.common.mqtts.MqttHander;


@Data
@ConfigurationProperties(prefix = "netty")
public class InitNetty {

    private ProtocolEnum protocol;

    private int webport;

    private int tcpport;

    private int mqttport;

    private int bossThread;

    private int workerThread;

    private boolean keepalive;

    private int backlog;

    private boolean nodelay;

    private boolean reuseaddr;

    private String serverName ;

    private  int  sndbuf ;

    private int revbuf ;

    private int heart ;

    private boolean ssl ;

    private String jksFile;

    private String jksStorePassword;

    private String jksCertificatePassword;

    private Class<MqttHander> mqttHander ;

    private int  initalDelay ;

    private  int period ;

}
