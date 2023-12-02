package com.wtk.xiban.service;

import org.apache.commons.collections4.map.HashedMap;
import org.junit.jupiter.api.Test;
import org.redisson.api.RList;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class RedissonTest {

    @Resource
    private RedissonClient redissonClient;

    @Test
    void test(){

        // list
        List<String> list = new ArrayList<>();
        list.add("yupi");
        list.get(0);
        System.out.println("list:" + list.get(0));
        list.remove(0);

        RList<String> rList = redissonClient.getList("test-list");
//        rList.add("yupi");
        rList.get(0);
        System.out.println("rList:" + rList.get(0));
        rList.remove(0);

        // map
        Map<String, Integer> map = new HashedMap<>();
        map.put("yupi", 10);
        map.get("yupi");

        RMap<String, Integer> map1 = redissonClient.getMap("test-map");
        // set

        // stack
    }

    @Test
    void testWatchDog(){
        RLock lock = redissonClient.getLock("xiban:precachejob:docache:lock");
        try {
            // 只有一个线程获取到锁
            if (lock.tryLock(0, -1, TimeUnit.MILLISECONDS)){
                Thread.sleep(100000);
                System.out.println("getLock:" + Thread.currentThread().getId());
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        } finally {
            // 只能释放自己的锁
            if (lock.isHeldByCurrentThread()){
                System.out.println("unlock:" + Thread.currentThread().getId());
                lock.unlock();
            }
        }
    }
}
