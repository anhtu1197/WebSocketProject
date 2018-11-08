package com.myself.nettychat.service;

import com.myself.nettychat.dataobject.User;

import java.util.List;


public interface UserService {

    User findOne(Integer id);

    User save(User user);

    User findByUserName(String userName);

    List<User> findAll();
}
