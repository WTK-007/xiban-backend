package com.wtk.xiban.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wtk.xiban.model.domain.Team;
import com.wtk.xiban.model.domain.User;
import com.wtk.xiban.model.dto.TeamQuery;
import com.wtk.xiban.model.request.TeamDeleteRequest;
import com.wtk.xiban.model.request.TeamJoinRequest;
import com.wtk.xiban.model.request.TeamQuitRequest;
import com.wtk.xiban.model.request.TeamUpdateRequest;
import com.wtk.xiban.model.vo.TeamUserVO;

import java.util.List;

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

    /**
     * 查询队伍用户信息
     * @param teamQuery
     * @param isAdmin
     * @return
     */
    List<TeamUserVO> listTeams(TeamQuery teamQuery, boolean isAdmin);

    /**
     * 更新队伍信息
     * @param teamUpdateRequest
     * @param loginUser
     * @return
     */
    boolean updateTeam(TeamUpdateRequest teamUpdateRequest, User loginUser);

    /**
     * 加入队伍
     * @param teamJoinRequest
     * @param loginUser
     * @return
     */
    boolean joinTeam(TeamJoinRequest teamJoinRequest, User loginUser);

    /**
     * 退出队伍
     * @param teamQuitRequest
     * @param loginUser
     * @return
     */
    boolean quitTeam(TeamQuitRequest teamQuitRequest, User loginUser);

    /**
     * 删除/解散队伍
     * @param teamDeleteRequest
     * @param loginUser
     * @return
     */
    boolean removeTeam(TeamDeleteRequest teamDeleteRequest, User loginUser);
}
