package com.myself.nettychat.vo;

import lombok.Data;

import java.io.Serializable;


@Data
public class ResultVo<T> implements Serializable {

    private static final long serialVersionUID = -1020280450330091843L;

    private Integer code;

    private String msg;

    private T data;

}
