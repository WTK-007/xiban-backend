package com.wtk.xiban.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户退出队伍请求
 */
@Data
public class TeamQuitRequest implements Serializable {

    private static final long serialVersionUID = 5891554586738418277L;

    /**
     * id
     */
    private Long teamId;



}
