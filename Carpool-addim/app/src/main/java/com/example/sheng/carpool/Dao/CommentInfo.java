package com.example.sheng.carpool.Dao;

/**
 * Created by sheng on 16-11-28.
 */
public class CommentInfo {
    private String account;     //发表评论的人的ID
    private String name;
    private int carpoolID;     //所针对的拼车信息的ID
    private int aimCommentID;  //所回复的评论的ID
    private String detail;     //详细信息

    public CommentInfo() {
    }

    public CommentInfo(String account, String name, int carpoolID, int aimCommentID,
                       String detail) {
        this.account = account;
        this.name = name;
        this.carpoolID = carpoolID;
        this.aimCommentID = aimCommentID;
        this.detail = detail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getCarpoolID() {
        return carpoolID;
    }

    public void setCarpoolID(int carpoolID) {
        this.carpoolID = carpoolID;
    }

    public int getAimCommentID() {
        return aimCommentID;
    }

    public void setAimCommentID(int aimCommentID) {
        this.aimCommentID = aimCommentID;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
