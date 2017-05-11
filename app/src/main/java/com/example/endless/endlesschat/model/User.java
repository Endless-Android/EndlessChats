package com.example.endless.endlesschat.model;

import cn.bmob.v3.BmobUser;

/**
 * Created by endless .
 */
public class User extends BmobUser {

    public User(String username,String password){
        setUsername(username);
        setPassword(password);
    }
}
