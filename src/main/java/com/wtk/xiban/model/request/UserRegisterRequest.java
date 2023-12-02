package com.wtk.xiban.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 *
 * @author admin
 */
@Data
public class UserRegisterRequest implements Serializable {


    private static final long serialVersionUID = -6163327911061641915L;
    private String userAccount;

    private String userPassword;

    private String checkPassword;

    private String planetCode;
}
