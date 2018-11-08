package com.myself.nettychat.controller;

import com.myself.nettychat.constont.CookieConstant;
import com.myself.nettychat.constont.H5Constant;
import com.myself.nettychat.dataobject.User;
import com.myself.nettychat.service.UserService;
import com.myself.nettychat.store.TokenStore;
import com.myself.nettychat.common.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@Controller
@RequestMapping("/su")
public class NcChangeController {

    @Autowired
    private UserService userService;


    @GetMapping("/me")
    public ModelAndView Me(Map<String,Object> map){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie == null){
            map.put("msg","cookie中不存在token");
            return new ModelAndView(H5Constant.LOGIN_SUI,map);
        }
        Integer userId = (Integer) TokenStore.get(cookie.getValue());
        if (userId == null){
            map.put("msg","用户信息不存在");
            return new ModelAndView(H5Constant.LOGIN_SUI,map);
        }
        User user = userService.findOne(userId);
        map.put("userName",user.getUserName());
        return new ModelAndView(H5Constant.ME,map);
    }


    @GetMapping("/find")
    public ModelAndView find(Map<String,Object> map){
        return new ModelAndView(H5Constant.FIND);
    }


    @GetMapping("/chat")
    public ModelAndView chat(Map<String,Object> map){
        return new ModelAndView(H5Constant.CHAT);
    }


    @GetMapping("/home")
    public ModelAndView home(Map<String,Object> map){
        return new ModelAndView(H5Constant.HOME);
    }

}
