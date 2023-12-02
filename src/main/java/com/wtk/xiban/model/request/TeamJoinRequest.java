package com.wtk.xiban.model.request;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TeamJoinRequest implements Serializable {

    private static final long serialVersionUID = 5891554586738418277L;

    /**
     * id
     */
    private Long teamId;

    /**
     * 密码
     */
    private String password;


}
