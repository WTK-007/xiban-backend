package com.wtk.xiban.once;

import com.wtk.xiban.mapper.UserMapper;
import com.wtk.xiban.model.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;

@Component
public class InsertUsers {

    @Resource
    private UserMapper userMapper;

   // @Scheduled(initialDelay = 5000, fixedRate = Long.MAX_VALUE)
    public void doInserUsers(){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        final int INSERT_NUM = 1000;
        for (int i = 0; i < INSERT_NUM; i++) {
            User user = new User();
            user.setUsername("假曦演");
            user.setUserAccount("fakexiyan");
            user.setAvatarUrl("https://img0.baidu.com/it/u=4024425805,2808286951&fm=253&fmt=auto&app=138&f=JPEG?w=506&h=500");
            user.setGender(0);
            user.setUserPassword("12345678");
            user.setPhone("123");
            user.setEmail("456@qq.com");
            user.setTags("[]");
            user.setUserStatus(0);
            user.setUserRole(0);
            user.setPlanetCode("1111111");
            userMapper.insert(user);
        }
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeMillis());
    }
}
