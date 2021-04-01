package com.intelligence.edge.pojo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @description: 用户信息
 * @author: uu

 **/
@Data
public class User {
    @NotNull
    private String id;
    private String username;
    private String password;
    private String registtime;
    private String email;
    private String phonenumber;
    private String devicenumber;
    private int level;


    public User(String id, String username, String password, String registtime, String email, String phonenumber, String devicenumber, int level){
        this.id = id;
        this.username = username;
        this.password = password;
        this.registtime = registtime;
        this.email = email;
        this.phonenumber = phonenumber;
        this.devicenumber = devicenumber;
        this.level = level;
    }
}
