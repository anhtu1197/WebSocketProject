package com.myself.nettychat.repository;

import com.myself.nettychat.dataobject.UserMsg;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMsgRepository extends JpaRepository<UserMsg,Integer> {
}
