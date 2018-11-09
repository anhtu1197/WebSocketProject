package com.myself.nettychat.config;

import com.myself.nettychat.task.MsgAsyncTesk;
import com.myself.nettychat.constont.LikeRedisTemplate;
import com.myself.nettychat.constont.LikeSomeCacheTemplate;
import com.myself.nettychat.common.utils.StringUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;



@Component
@Qualifier("textWebSocketFrameHandler")
@ChannelHandler.Sharable
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<Object>{

    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Autowired
    private LikeRedisTemplate redisTemplate;
    @Autowired
    private LikeSomeCacheTemplate cacheTemplate;
    @Autowired
    private MsgAsyncTesk msgAsyncTesk;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx,
                                Object msg) throws Exception {
        if(msg instanceof TextWebSocketFrame){
            textWebSocketFrame(ctx, (TextWebSocketFrame) msg);
        }else if(msg instanceof WebSocketFrame){ //khi đã có kế nối
            handleWebSocketFrame(ctx, (WebSocketFrame) msg);
        }
    }

    private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
        if(frame instanceof BinaryWebSocketFrame){
            //Trả về client
            BinaryWebSocketFrame imgBack= (BinaryWebSocketFrame) frame.copy();
            for (Channel channel : channels){
                channel.writeAndFlush(imgBack.retain());
            }
            //Lưu tại server
            BinaryWebSocketFrame img= (BinaryWebSocketFrame) frame;
            ByteBuf byteBuf=img.content();
            try {
                FileOutputStream outputStream=new FileOutputStream("D:\\a.jpg");
                byteBuf.readBytes(outputStream,byteBuf.capacity());
                byteBuf.clear();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void textWebSocketFrame(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        Channel incoming = ctx.channel();
        String rName = StringUtil.getName(msg.text());
        String rMsg = StringUtil.getMsg(msg.text());
        if (rMsg.equals("")){
            return;
        }
        //Xử lý login
        if (redisTemplate.check(incoming.id(),rName)){
            //Lưu trữ tạm thời nội dung chat
            cacheTemplate.save(rName,rMsg);
            //Lưu trữ id ngẫu nhiên ứng với username
            redisTemplate.save(incoming.id(),rName);
            //Lưu trữ username với API
            redisTemplate.saveChannel(rName,incoming);
        }else{
            incoming.writeAndFlush(new TextWebSocketFrame("Đã có kết nối thứ 2, hệ thống sẽ tự động ngắt kết nối"));
            channels.remove(ctx.channel());
            ctx.close();
            return;
        }
        for (Channel channel : channels) {
            //Lưu trữ cuộc trò chuyện hiện tại
            if (channel != incoming){
                channel.writeAndFlush(new TextWebSocketFrame(  rMsg + "-" + rName));
            } else {
                channel.writeAndFlush(new TextWebSocketFrame(rMsg + "-" + rName ));
            }
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress());
        channels.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        //Xóa kết nối
        String name = (String) redisTemplate.getName(ctx.channel().id());
        redisTemplate.deleteChannel(name);
        //Xóa kết nối mặc định
        redisTemplate.delete(ctx.channel().id());
        channels.remove(ctx.channel());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //Trạng thái online
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //Drop
        msgAsyncTesk.saveChatMsgTask();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        //Error
        cause.printStackTrace();
        ctx.close();
    }
}
