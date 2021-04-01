package com.intelligence.edge.dao;


import com.intelligence.edge.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface UserMapper {

    List<Map<String, Object>>  getUserByID(String ID);

    String getPasswordByUsername(String username);

    int insertUser(User user);

    int updateUser(User user);

    int selectUser(String username);

    Map<String,Object> getLevelById(String Id);

}
