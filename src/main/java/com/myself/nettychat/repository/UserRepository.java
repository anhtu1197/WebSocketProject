package com.myself.nettychat.repository;

import com.myself.nettychat.dataobject.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User,Integer> {

    User findByUserName(String userName);

}
