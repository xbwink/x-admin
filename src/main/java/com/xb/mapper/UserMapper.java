package com.xb.mapper;

import com.xb.entity.Page;
import com.xb.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author xb
 * @description TODO
 * @create 2023-07-03 11:35
 * @vesion 1.0
 */
@Mapper
public interface UserMapper {

    void insert(User user);

    void update(User user);

    List<String> getRolesByUserId(Integer id);

    List<User> queryUsersBySql(Page page);

    @Select("select * from x_user")
    List<User> selectList();


    @Select("select count(id) from x_user")
    Integer selectCount();


    @Delete("delete from x_user where id = #{id}")
    void deleteById(Integer id);

    @Select("select * from x_user where id = #{id}")
    User getUserById(Integer id);


    @Select("select * from x_user where username = #{username}")
    User selectByName(String username);

}
