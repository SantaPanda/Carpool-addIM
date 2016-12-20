package com.example.sheng.carpool.Dao;

/**
 * Created by sheng on 16-12-8.
 */
public class AddInfo {
    private String account;
    private String name;
    private String phone;


    public AddInfo(String account, String name,String phone) {
        this.account = account;
        this.name = name;
        this.phone = phone;
    }
    public String getAccount() {
        return account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getphone() {
        return phone;
    }

    public void setphone(String phone) {
        this.phone = phone;
    }

}
