package com.seagold.community;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommunityApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void contextLoads() {
        System.out.println(LocalDate.now().lengthOfYear());



        for (int i = 10001; i<10020; i++){
//            redisTemplate.opsForZSet().add("zset", "183-1610", i);
            redisTemplate.opsForZSet().add("183-1610", i    , i);
        }

        redisTemplate.opsForZSet().add("183-1610", 10002, 1);


    }

    @Test
    public void testCount(){
        Long count = redisTemplate.opsForZSet().count("183-1610", 10001, 12000);
        System.out.println(count);
    }

}
