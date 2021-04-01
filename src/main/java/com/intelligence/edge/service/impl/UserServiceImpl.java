package com.intelligence.edge.service.impl;

import com.intelligence.edge.dao.UserMapper;
import com.intelligence.edge.pojo.User;
import com.intelligence.edge.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @description: 用户管理功能实现
 * @author: uu
 * @create: 10-27
 **/

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public String getPasswordByUsername(String username) {
        return userMapper.getPasswordByUsername(username);
    }

    @Override
    public int insertUser(User user) {
        int status = 0;
        try {
            status = userMapper.insertUser(user);
        }catch (Exception e){
            e.printStackTrace();
        }
        return status;
    }

    @Override
    public int updateuUser(User user) {
        return userMapper.updateUser(user);
    }

    @Override
    public int selectUser(String username) {
        int status =0;
        try {
            status = userMapper.selectUser(username);
        }catch (Exception e){
            e.printStackTrace();
        }
        return status;
    }

    @Override
    public List<Map<String,Object>> getUserById(String Id) {
        return userMapper.getUserByID(Id);
    }

    @Override
    public Map<String, Object>getLevelById(String Id) {
        return userMapper.getLevelById(Id);
    }
}
