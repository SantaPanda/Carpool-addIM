package com.example.sheng.carpool.helpers;

import com.example.sheng.carpool.Dao.CarpoolInfo;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sheng on 16-12-6.
 */
public class JsonOperation {

    public static String jsonObjectStructure(CarpoolInfo carpoolInfo){
        StringBuilder json = new StringBuilder();
        json.append('{');
        json.append("accountID:").append(carpoolInfo.getCARPOOLID()).append(",");
        json.append("name:").append(carpoolInfo.getName()).append(",");
        json.append("date:").append(carpoolInfo.getDate()).append(",");
        json.append("departure:").append(carpoolInfo.getDeparture()).append(",");
        json.append("destination:").append(carpoolInfo.getDestination()).append(",");
        json.append("departureTime:").append(carpoolInfo.getDepartureTime()).append(",");
        json.append("price:").append(carpoolInfo.getPrice()).append(",");
        json.append("totalNum:").append(carpoolInfo.getTotalNum()).append(",");
        json.append("haveNum:").append(carpoolInfo.getHaveNum()).append(",");
        json.append("phoneNum:").append(carpoolInfo.getPhoneNum()).append(",");
        json.append("detail:").append(carpoolInfo.getDetail()).append(",");
        json.append("addID:").append(carpoolInfo.getAddID()).append(",");
        json.append("commentID:").append(carpoolInfo.getCommentID());
        json.append("}");
        return json.toString();
    }

    public static CarpoolInfo jsonObjectAnalysis(String str){
        String accountID,name,date,departure,destination,departureTime;
        String phoneNum,detail,addID,commentID;
        int price,totalNum,haveNum;
        JSONObject jsonObject = null;
        CarpoolInfo carpoolInfo = null;
        String json = "";
        try {
            jsonObject = new JSONObject(str);
            accountID = jsonObject.getString("accountID");
            name = jsonObject.getString("name");
            date = jsonObject.getString("date");
            departure = jsonObject.getString("departure");
            destination = jsonObject.getString("destination");
            departureTime = jsonObject.getString("departureTime");
            price = jsonObject.getInt("price");
            totalNum = jsonObject.getInt("totalNum");
            haveNum = jsonObject.getInt("haveNum");
            phoneNum = jsonObject.getString("phoneNum");
            detail = jsonObject.getString("detail");
            addID = jsonObject.getString("addID");
            commentID = jsonObject.getString("commentID");
            carpoolInfo = new CarpoolInfo(accountID,1,name,date,departure,destination,
                    departureTime,price,totalNum, haveNum,phoneNum,detail,addID,commentID);
            json = jsonObjectStructure(carpoolInfo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return carpoolInfo;
    }
}
