package com.example.sheng.carpool.Dao;

/**
 * Created by sheng on 16-11-28.
 */
public class MyInfo {
    private String account;
    private String password;
    private String headPortrait;
    private String name;
    private String nickname;
    private String sex;
    private String phone;
    private String wechat;
    private String qq;
    private int good;
    private int bad;
    private String introduce;   //自我介绍
    private String addID;       //添加的拼车ID
    private String commentID;   //评论的拼车ID

    public MyInfo(String account, String password, String headPortrait, String name,
                  String nickname, String sex, String phone, String wechat, String qq,
                  int good, int bad, String introduce, String addID, String commentID) {
        this.account = account;
        this.password = password;
        this.headPortrait = headPortrait;
        this.name = name;
        this.nickname = nickname;
        this.sex = sex;
        this.phone = phone;
        this.wechat = wechat;
        this.qq = qq;
        this.good = good;
        this.bad = bad;
        this.introduce = introduce;
        this.addID = addID;
        this.commentID = commentID;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public int getGood() {
        return good;
    }

    public void setGood(int good) {
        this.good = good;
    }

    public int getBad() {
        return bad;
    }

    public void setBad(int bad) {
        this.bad = bad;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getAddID() {
        return addID;
    }

    public void setAddID(String addID) {
        this.addID = addID;
    }

    public String getCommentID() {
        return commentID;
    }

    public void setCommentID(String commentID) {
        this.commentID = commentID;
    }
}
