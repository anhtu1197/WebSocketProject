package com.myself.nettychat.config;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;


@Data
@Component
public class TCPServer {

    @Autowired
    @Qualifier("serverBootstrap")
    private ServerBootstrap serverBootstrap;

    @Autowired
    @Qualifier("tcpServerBootstrap")
    private ServerBootstrap tcpServerBootstrap;

    @Autowired
    @Qualifier("webSocketAddress")
    private InetSocketAddress webPort;

    @Autowired
    @Qualifier("tcpSocketAddress")
    private InetSocketAddress tcpTcpPort;

    private Channel serverChannel;

    private Channel tcpServerChannel;

    public void startWeb() throws Exception {
        serverChannel =  serverBootstrap.bind(webPort).sync().channel().closeFuture().sync().channel();
    }

    public void startTcp() throws Exception {
        tcpServerChannel = tcpServerBootstrap.bind(tcpTcpPort).sync().channel().closeFuture().sync().channel();
    }

    @PreDestroy
    public void stop() throws Exception {
        serverChannel.close();
        serverChannel.parent().close();
        tcpServerChannel.close();
        tcpServerChannel.parent().close();
    }
}
