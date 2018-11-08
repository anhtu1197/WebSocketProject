package com.myself.nettychat;

import com.myself.nettychat.bootstrap.BaseAuthService;
import org.springframework.stereotype.Service;


@Service
public class DefaultAutoService implements BaseAuthService {

    @Override
    public boolean authorized(String username, String password) {
        return true;
    }
}
