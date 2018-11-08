package com.myself.nettychat.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;


@Data
public class LoginForm {
    @NotEmpty(message = "test")
    private String fUserName;
    @NotEmpty(message = "test")
    private String fPassWord;

}
