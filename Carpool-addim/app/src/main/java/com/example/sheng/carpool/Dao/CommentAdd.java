package com.example.sheng.carpool.Dao;

/**
 * Created by sheng on 16-12-8.
 */
public class CommentAdd {
    private String account;
    private String name;
    private String detail;


    public CommentAdd(String account, String name,String detail) {
        this.account = account;
        this.name = name;
        this.detail = detail;
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

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }


}
