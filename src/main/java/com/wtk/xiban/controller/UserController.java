package com.wtk.xiban.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wtk.xiban.common.BaseResponse;
import com.wtk.xiban.common.ErrorCode;
import com.wtk.xiban.common.ResultUtils;
import com.wtk.xiban.exception.BusinessException;
import com.wtk.xiban.model.domain.User;
import com.wtk.xiban.model.request.UserLoginRequest;
import com.wtk.xiban.model.request.UserRegisterRequest;
import com.wtk.xiban.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.wtk.xiban.contant.UserContant.USER_LOGIN_STATE;

/**
 * 用户接口
 */
@RestController
@RequestMapping("/user")
@CrossOrigin(origins = {"http://127.0.0.1:5173","http://localhost:8000"}, allowCredentials = "true")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private RedisTemplate redisTemplate;

    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest){
        if (userRegisterRequest == null){
//            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "注册参数错误");
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String planetCode = userRegisterRequest.getPlanetCode();
        if (StringUtils.isAnyBlank(userAccount,userPassword,checkPassword,planetCode)){
            throw new BusinessException(ErrorCode.NULL_ERROR,"注册参数有空值");
        }
        long result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        return ResultUtils.success(result,"用户注册成功，返回用户编号");
    }

    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request){
        if (userLoginRequest == null){
            throw new BusinessException(ErrorCode.NULL_ERROR,"登录对象为空");
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount,userPassword)){
            throw new BusinessException(ErrorCode.NULL_ERROR,"登录参数有空值");
        }
        User user = userService.userLogin(userAccount, userPassword, request);
        return ResultUtils.success(user,"用户登录成功");
    }

    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request){
        if (request == null){
            throw new BusinessException(ErrorCode.SYSTRM_ERROR,"request参数有问题");
        }
        int result = userService.userLogout(request);
        return ResultUtils.success(result,"用户注销成功");
    }

    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request){
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null){
            throw new BusinessException(ErrorCode.NO_LOGIN,"用户未登录");
        }
        long userId = currentUser.getId();
        // todo 校验用户是否合法
        User user = userService.getById(userId);
        User safetyUser = userService.getSafetyUser(user);
        return ResultUtils.success(safetyUser);
    }

    @GetMapping("/search")
    public BaseResponse<List<User>> searchUsers(String username,HttpServletRequest request){
        // 仅管理员可以查询
       if (!userService.isAdmin(request)){
           throw new BusinessException(ErrorCode.NO_AUTH,"用户无权限查询");
       }
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        if (StringUtils.isNotBlank(username)){
            queryWrapper.like("username",username);
        }
        List<User> userList = userService.list(queryWrapper);
        List<User> result = userList.stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());
        return ResultUtils.success(result);
    }

    @GetMapping("/search/tags")
    public BaseResponse<List<User>> searchUsersByTags(@RequestParam(required = false) List<String> tagNameList){
        if (CollectionUtils.isEmpty(tagNameList)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        List<User> userList = userService.searchUsersByTags(tagNameList);
        return ResultUtils.success(userList);
    }

    @GetMapping("/recommend")
    public BaseResponse<Page<User>> recommendUsers(long pageSize, long pageNum,HttpServletRequest request){
        User loginUser = userService.getLoginUser(request);
        String redisKey =String.format("xiban:user:recommend:%s", loginUser.getId());
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        // 如果有缓存，直接读缓存
        Page<User> userPage = (Page<User>) valueOperations.get(redisKey);
        if (userPage != null){
            return ResultUtils.success(userPage);
        }
        // 无缓存，查数据库
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        userPage = userService.page(new Page<>(pageNum, pageSize), queryWrapper);
        // 将查询结果写入缓存
        try {
            valueOperations.set(redisKey,userPage, 30000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            log.error("redis set key error", e);
        }
        return ResultUtils.success(userPage);
    }


    @PostMapping ("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestBody long id, HttpServletRequest request){
        // 仅管理员可以删除
        if (!userService.isAdmin(request)){
            throw new BusinessException(ErrorCode.NO_AUTH,"用户无权限删除");
        }
        if (id < 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户不存在");
        }
        boolean result = userService.removeById(id);
        return ResultUtils.success(result,"用户删除成功，返回删除的用户编号");
    }

    @PostMapping("/update")
    public BaseResponse<Integer> updateUser(@RequestBody User user, HttpServletRequest request){
        // 1、校验参数是否为空
        if (user == null){
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        User loginUser = userService.getLoginUser(request);
        if (loginUser == null){
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        Integer result = userService.updateUser(user,loginUser);
        return ResultUtils.success(result);

    }




}
