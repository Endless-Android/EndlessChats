package com.example.endless.endlesschat.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by endless .
 */
@Entity
public class Contast {

    @Id
    public Long id;
    public String userName;
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 741484826)
    public Contast(Long id, String userName) {
        this.id = id;
        this.userName = userName;
    }
    @Generated(hash = 996801603)
    public Contast() {
    }

}
