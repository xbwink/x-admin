package com.xb;

import com.xb.entity.Page;
import com.xb.entity.User;
import com.xb.mapper.UserMapper;
import com.xb.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author xb
 * @description TODO
 * @create 2023-07-03 11:37
 * @vesion 1.0
 */
@SpringBootTest
public class TestUser {


    @Autowired
    UserMapper userMapper;
    @Autowired
    UserService userService;

    @Autowired
    RedisTemplate redisTemplate;

    @Test
    public void getAllUser(){
        List<User> users = userService.selectList();
        users.forEach(System.out::println);
    }

    @Test
    public void test(){
        User user = new User();
        user.setUsername("admin1");
        user.setPassword("123456");
        Map<String, Object> data = userService.login(user);
        System.out.println(data);
    }

    @Test
    public void testRedis(){
        User user = new User();
        user.setUsername("admin");
        user.setPassword("123456");
        redisTemplate.opsForValue().set("key1",user,30, TimeUnit.SECONDS);
        Object key1 = redisTemplate.opsForValue().get("key1");
        System.out.println(key1);
    }

    @Test
    public void test2(){
        List<String> rolesByUserId = userMapper.getRolesByUserId(1);
        System.out.println(rolesByUserId);
    }

    @Test
    public void testPage(){
        Page page = new Page("", "", 2, 3);
        List<User> users = userMapper.queryUsersBySql(page);
        System.out.println(users);
        Integer integer = userMapper.selectCount();
        System.out.println(integer);
    }

}
