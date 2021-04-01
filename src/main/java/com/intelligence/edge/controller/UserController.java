package com.intelligence.edge.controller;

import com.intelligence.edge.pojo.User;
import com.intelligence.edge.result.Result;
import com.intelligence.edge.result.ResultEnum;
import com.intelligence.edge.result.ResultUtil;
import com.intelligence.edge.result.StandardResult;
import com.intelligence.edge.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @description: 用户管理模块
 * @author: uu
 * @create: 10-28
 **/

@RestController
@CrossOrigin
@RequestMapping("/manage/user/")
@Slf4j(topic = "UserController")
public class UserController {

    @Autowired
    private UserService userService;

    /*
    *
     * @Description //TODO 用户注册
     * @Param [status]
     * @return com.intelligence.edge.result.Result
     **/
    @RequestMapping(value = "registered")
    public Result userRegister(@RequestBody  User user){

        int status = userService.insertUser(user);
        if(status ==1 ){
            log.info(user.getUsername()+ "注册成功");
            return ResultUtil.success(status);
        }else{
            return ResultUtil.error(ResultEnum.FAIL.getCode(),ResultEnum.FAIL.getMsg());
        }
    }


    @RequestMapping(value = "loginin")
    public Result userLogin(@RequestParam("username") String username, @RequestParam("password") String password){

        int status = userService.selectUser(username);
        String passswd = null;
        if(status > 0){
            String passwd = userService.getPasswordByUsername(username);

        }
        if(passswd  == password){
            log.info(username + "login in successfully");
            return ResultUtil.success(1);
        }else{
            return ResultUtil.error(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getMsg());
        }
    }

    @RequestMapping(value = "updateUser")
    public int updateUser(@RequestBody User user){
        log.info(user.getUsername() + "info has changed!");
        return userService.updateuUser(user);
    }

    @RequestMapping(value = "getUserInfo")
    public StandardResult getUserById(@RequestParam("Id") String Id){
        log.info("getUserInfo:"+Id);
        List<Map<String,Object>> UserInfo = userService.getUserById(Id);
        log.info("getUserInfo:"+ UserInfo);
        return StandardResult.success(UserInfo);
    }

    @RequestMapping(value = "securitylevel")
    public StandardResult getLevelById(@RequestParam("Id") String Id){
        log.info("getLevel:" + Id);
        return StandardResult.success(userService.getLevelById(Id));
    }

}
