package com.myself.nettychat.controller;

import com.myself.nettychat.constont.CookieConstant;
import com.myself.nettychat.constont.H5Constant;
import com.myself.nettychat.dataobject.User;
import com.myself.nettychat.form.LoginForm;
import com.myself.nettychat.repository.UserMsgRepository;
import com.myself.nettychat.service.UserService;
import com.myself.nettychat.store.TokenStore;
import com.myself.nettychat.common.utils.CookieUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Controller
@RequestMapping("/admin")
public class NcLoginController extends NcChangeController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserMsgRepository userMsgRepository;



    @GetMapping("/loginsui")
    public ModelAndView loginSui(Map<String,Object> map){
        return new ModelAndView(H5Constant.LOGIN_SUI);
    }

    /**
     * 注册页面
     * @return
     */
    @GetMapping("/regis")
    public ModelAndView register(){
        return new ModelAndView(H5Constant.LOGIN_SUI);
    }


    @PostMapping("/toRegister")
    public ModelAndView toRegister(@Valid LoginForm form, BindingResult bindingResult , HttpServletResponse response,
                                   Map<String, Object> map){
        if (bindingResult.hasErrors()){
            map.put("msg",bindingResult.getFieldError().getDefaultMessage());
            return new ModelAndView(H5Constant.LOGIN_SUI,map);
        }
        List<User> userList = userService.findAll();
        for (User item:userList){
            if (item.getUserName().equals(form.getFUserName())){
                map.put("msg","用户名已存在，请重新填写唯一用户名");
                return new ModelAndView(H5Constant.LOGIN_SUI,map);
            }
        }
        User user = new User();
        BeanUtils.copyProperties(form,user);
        userService.save(user);
        map.put("userName",user.getUserName());
        map.put("passWord",user.getPassWord());
        return new ModelAndView(H5Constant.LOGIN_SUI,map);
    }

    @PostMapping("/toLogin")
    public ModelAndView toLogin(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                @RequestParam(value = "size",defaultValue = "10") Integer size,
                                @Valid LoginForm form, BindingResult bindingResult , HttpServletResponse response,
                                Map<String, Object> map){
        if (bindingResult.hasErrors()){
            map.put("msg",bindingResult.getFieldError().getDefaultMessage());
            return new ModelAndView(H5Constant.LOGIN_SUI,map);
        }
        try {
            User user = userService.findByUserName(form.getFUserName());
            if (user.getPassWord().equals(form.getFPassWord())){
                
                String token = UUID.randomUUID().toString();
                
                TokenStore.add(token,user.getId());
                
                CookieUtil.set(response, CookieConstant.TOKEN,token,CookieConstant.EXPIRE);










                return new ModelAndView(H5Constant.HOME);
            }else{
                map.put("msg","密码错误");
                return new ModelAndView(H5Constant.LOGIN_SUI,map);
            }
        }catch (Exception e){
            map.put("msg","用户不存在");
            return new ModelAndView(H5Constant.LOGIN_SUI,map);
        }
    }

}
