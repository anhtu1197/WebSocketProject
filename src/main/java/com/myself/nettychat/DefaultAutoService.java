package com.myself.nettychat;

import com.myself.nettychat.bootstrap.BaseAuthService;
import org.springframework.stereotype.Service;


/**
 * @author  MySelf
 * @create  2018/9/22
 * @desc 默认权限
 **/
@Service
public class DefaultAutoService implements BaseAuthService {

    @Override
    public boolean authorized(String username, String password) {
        return true;
    }
}
