package com.myself.nettychat.controller;

import com.myself.nettychat.common.utils.ResultVOUtil;
import com.myself.nettychat.common.utils.SendUtil;
import com.myself.nettychat.constont.LikeRedisTemplate;
import com.myself.nettychat.vo.ResultVo;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/back")
public class NCBackController {

    @Autowired
    private LikeRedisTemplate redisTemplate;


    @GetMapping("/size")
    public ResultVo getSize(){
        return ResultVOUtil.success(redisTemplate.getSize());
    }


    @GetMapping("/online")
    public ResultVo getOnline(){
        return ResultVOUtil.success(redisTemplate.getOnline());
    }


    @PostMapping("/send")
    public ResultVo send(@RequestParam String name,@RequestParam String msg){
        Channel channel = (Channel) redisTemplate.getChannel(name);
        if (channel == null){
            return ResultVOUtil.error(555,"当前用户连接已断开");
        }
        String result = SendUtil.sendTest(msg,channel);
        return ResultVOUtil.success(result);
    }

}
