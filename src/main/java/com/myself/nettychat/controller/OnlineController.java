package com.myself.nettychat.controller;

import com.myself.nettychat.common.utils.ResultVOUtil;
import com.myself.nettychat.constont.LikeRedisTemplate;
import com.myself.nettychat.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static com.myself.nettychat.common.utils.ResultVOUtil.success;

@Controller
@RequestMapping("/")
public class OnlineController {
    @Autowired
   private LikeRedisTemplate redisTemplate;
    @RequestMapping
    public String handleRequest (Model model) {
        ResultVo resultVoOnline= ResultVOUtil.success(redisTemplate.getOnline());
        ResultVo resultVoSize = ResultVOUtil.success(redisTemplate.getSize());
        model.addAttribute("onlinePeo", resultVoOnline.getData());
        model.addAttribute("onlineNum", resultVoSize.getData());
        return "my-page";
    }
}
