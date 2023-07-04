package com.xb.service.impl;

import com.xb.entity.Page;
import com.xb.entity.User;
import com.xb.mapper.UserMapper;
import com.xb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author xb
 * @description TODO
 * @create 2023-07-03 11:34
 * @vesion 1.0
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public List<User> selectList() {
        return userMapper.selectList();
    }

    @Override
    public Map<String, Object> login(User user) {
        //根据用户名查询用户
        User loginUser = userMapper.selectByName(user.getUsername());
        // 结果不为空，并且密码和传入密码匹配，则生成token，并将用户信息存入redis
        if(loginUser!=null && passwordEncoder.matches(user.getPassword(),loginUser.getPassword())){
            //生成token：user + uuid
            String key = "user:" + UUID.randomUUID();

            //将用户信息储存到redis
            redisTemplate.opsForValue().set(key,loginUser,30, TimeUnit.MINUTES);
            //返回数据
            HashMap<String, Object> data = new HashMap<>();
            data.put("token",key);
            return data;
        }

        return null;
    }

    /**
     * 根据token查询用户信息
     * @param token
     * @return
     */
    @Override
    public Map<String, Object> getInfoByToken(String token) {
        //通过token查询用户
        User user = (User)redisTemplate.opsForValue().get(token);
        if(user!=null){
            //构建返回数据
            HashMap<String, Object> data = new HashMap<>();
            //根据userId查询对应的角色
            List<String> roles = userMapper.getRolesByUserId(user.getId());
            data.put("roles",roles);
            data.put("name",user.getUsername());
            data.put("avatar",user.getAvatar());
            return data;
        }
        return null;
    }

    /**
     * 退出登录
     * @param token
     */
    @Override
    public void logOut(String token) {
        //删除redis中的用户信息
        redisTemplate.delete(token);
    }

    /**
     *
     * 根据条件分页查询
     */
    @Override
    public Map<String, Object> queryUsersBySql(Page page) {
        //计算分页参数
        int currentPage = (page.getPageNo()-1)*page.getPageSize();
        page.setPageNo(currentPage);
        List<User> users = userMapper.queryUsersBySql(page);
        //查总记录数
        Integer total = userMapper.selectCount();
        //构建返回数据
        HashMap<String, Object> map = new HashMap<>();

        map.put("total", total);
        map.put("data",users);
        return map;
    }

    @Override
    public void saveUser(User user) {
        //将用户密码转
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userMapper.insert(user);
    }

    @Override
    public void removeById(Integer id) {
        userMapper.deleteById(id);
    }

    @Override
    public User getUserById(Integer id) {
       return userMapper.getUserById(id);
    }

    @Override
    public void update(User user) {
        userMapper.update(user);
    }


}
