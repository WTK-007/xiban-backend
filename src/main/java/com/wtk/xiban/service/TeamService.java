package com.wtk.xiban.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wtk.xiban.model.domain.Team;
import com.wtk.xiban.model.domain.User;

/**
* @author admin
* @description 针对表【team(队伍)】的数据库操作Service
* @createDate 2023-11-23 16:41:16
*/
public interface TeamService extends IService<Team> {

    /**
     * 创建队伍
     * @param team
     * @param loginUser
     * @return
     */
    long addTeam(Team team, User loginUser);
}
