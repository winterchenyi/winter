package com.yestic.winter.util;

import com.yestic.winter.Application;
import com.yestic.winter.component.RedisCache;
import com.yestic.winter.constant.Constants;
import com.yestic.winter.dto.JwtUser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/** 
* Redis Tester. 
* 
* @author <Authors name> 
* @since <pre>12/20/2017</pre> 
* @version 1.0 
*/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
public class RedisTest {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedisCache redisCache;


    @Test
    public void putRedis() throws Exception {
        JwtUser user=new JwtUser("chen1", "陈", "1", "127.0.0.1");
        redisCache.put("userKey",user, Constants.JWT_TTL);
    }

    @Test
    public void getRedis() throws Exception {
        JwtUser jwtUser = redisCache.get("userKey",JwtUser.class);
        System.out.println(jwtUser.toString());
    }


} 
