package com.myself.nettychat.common.utils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;


public class CallBackMessage {

    public static final ByteBuf SUCCESS = Unpooled.unreleasableBuffer(
            Unpooled.copiedBuffer("success", Charset.forName("UTF-8")));

    public static final ByteBuf ERROR = Unpooled.unreleasableBuffer(
            Unpooled.copiedBuffer("error", Charset.forName("UTF-8")));

    public static final ByteBuf Check1_test = Unpooled.unreleasableBuffer(
            Unpooled.copiedBuffer("Check1\r", Charset.forName("UTF-8")));

    public static final ByteBuf Check2 = Unpooled.unreleasableBuffer(
            Unpooled.copiedBuffer("Check2", Charset.forName("UTF-8")));


    public static ByteBuf sendString(String send){
        return Unpooled.unreleasableBuffer(
                Unpooled.copiedBuffer(send, Charset.forName("UTF-8")));
    }


}
