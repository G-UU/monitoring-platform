package com.intelligence.edge.service;

import com.intelligence.edge.pojo.User;

import java.util.List;
import java.util.Map;

/**
 * @description: 用户管理功能实现
 * @author: uu
 * @create: 10-27
 **/
public interface UserService {


    /**
     *查询用户密码
     *@Param [username]
     * @return java.lang.String
     **/
    String getPasswordByUsername(String username);

    /**
    * 用户注册 新增用户
    *@Param [user]
    * @return int
    **/
    int insertUser(User user);

    /**
     *@Description TODO 更新用户信息
     *@Param [user]
     * @return int
     **/
    int updateuUser(User user);

    /**
     *@Description TODO 是否存在该username
     *@Param [username]
     * @return int
     **/
    int selectUser(String username);

    List<Map<String, Object>> getUserById(String Id);

    Map<String, Object> getLevelById(String Id);
}
