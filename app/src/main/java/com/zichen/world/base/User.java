package com.zichen.world.base;

import android.os.Parcel;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/1.
 */

public class User implements Serializable {

    private int id ;
    private String name;
    private String loginName;
    private String loginPssd;

    public User(){

    }

    public User(String name,String loginName,String loginPssd){
        this.name = name;
        this.loginName = loginName;
        this.loginPssd = loginPssd;
    }

    public User(int id ,String name , String loginName,String loginPssd){
        this.id = id;
        this.name = name;
        this.loginName = loginName;
        this.loginPssd = loginPssd;
    }

    protected User(Parcel in) {
        id = in.readInt();
        name = in.readString();
        loginName = in.readString();
        loginPssd = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginPssd() {
        return loginPssd;
    }

    public void setLoginPssd(String loginPssd) {
        this.loginPssd = loginPssd;
    }

    public String toString(){
        return "user数据为："+this.id+","+this.name+","+this.getLoginName()+","+this.getLoginPssd();
    }

}
