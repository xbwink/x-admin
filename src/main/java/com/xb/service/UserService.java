package com.xb.service;

import com.xb.entity.Page;
import com.xb.entity.User;

import java.util.List;
import java.util.Map;

/**
 * @author xb
 * @description TODO
 * @create 2023-07-03 11:34
 * @vesion 1.0
 */
public interface UserService {

    List<User> selectList();


    Map<String, Object> login(User user);

    Map<String, Object> getInfoByToken(String token);

    void logOut(String token);

    Map<String, Object> queryUsersBySql(Page page);

    void saveUser(User user);

    void removeById(Integer id);

    User getUserById(Integer id);

    void update(User user);
}
