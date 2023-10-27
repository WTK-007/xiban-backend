package com.wtk.usercenterbackend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

@SpringBootTest
class UserCenterBackendApplicationTests {

    /**
     * 测试加密密码
     */
    @Test
    void testDigest(){
        String newPassword = DigestUtils.md5DigestAsHex(("abcd" + "123456789").getBytes());
        System.out.println(newPassword);

    }

    @Test
    void contextLoads() {
    }

}
