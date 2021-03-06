package com.myself.nettychat.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;


@Data
@Entity
@DynamicUpdate
public class UserMsg implements Serializable {

    private static final long serialVersionUID = 4133316147283239759L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String msg;

    private Date createTime;

    private Date updateTime;

}
