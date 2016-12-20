package com.example.sheng.carpool.Dao;

/**
 * Created by sheng on 16-11-28.
 */
public class CarpoolInfo {
    private String account;     //账号
    private int CARPOOLID;
    private String name;
    private String date;          //日期
    private String departure;     //出发点
    private String destination;   //目的地
    private String departureTime;//出发时间
    private int price;             //价格
    private int totalNum;         //总人数
    private int haveNum;          //已有的人数
    private String phoneNum;      //手机号码
    private String detail;        //详细信息
    private String addID;         //要拼车的人的ID
    private String commentID;     //发表评论的人的ID

    public CarpoolInfo() {
    }

    @Override
    public String toString() {
        return "CarpoolInfo{" +
                "account='" + account + '\'' +
                ", CARPOOLID=" + CARPOOLID +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", departure='" + departure + '\'' +
                ", destination='" + destination + '\'' +
                ", departureTime='" + departureTime + '\'' +
                ", price=" + price +
                ", totalNum=" + totalNum +
                ", haveNum=" + haveNum +
                ", phoneNum='" + phoneNum + '\'' +
                ", detail='" + detail + '\'' +
                ", addID='" + addID + '\'' +
                ", commentID='" + commentID + '\'' +
                '}';
    }

    public CarpoolInfo(String account, int CARPOOLID, String name, String date, String departure, String destination,
                       String departureTime, int price, int totalNum, int haveNum,
                       String phoneNum, String detail, String addID, String commentID) {
        this.account = account;
        this.CARPOOLID = CARPOOLID;
        this.name = name;
        this.date = date;
        this.departure = departure;
        this.destination = destination;
        this.departureTime = departureTime;
        this.price = price;
        this.totalNum = totalNum;
        this.haveNum = haveNum;
        this.phoneNum = phoneNum;
        this.detail = detail;
        this.addID = addID;
        this.commentID = commentID;
    }


    public int getCARPOOLID() {
        return CARPOOLID;
    }

    public void setCARPOOLID(int CARPOOLID) {
        this.CARPOOLID = CARPOOLID;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public int getHaveNum() {
        return haveNum;
    }

    public void setHaveNum(int haveNum) {
        this.haveNum = haveNum;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
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
