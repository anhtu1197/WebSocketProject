package com.myself.nettychat.bootstrap;

import com.myself.nettychat.common.properties.InitNetty;

public interface BootstrapServer {

    void shutdown();

    void setServerBean(InitNetty serverBean);

    void start();

}
