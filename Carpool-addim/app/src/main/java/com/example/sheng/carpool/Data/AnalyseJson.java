package com.example.sheng.carpool.Data;

import android.util.Log;
import android.widget.EditText;

import com.example.sheng.carpool.Dao.CarpoolInfo;
import com.example.sheng.carpool.Dao.CommentInfo;
import com.example.sheng.carpool.Dao.MyInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by sheng on 16-12-11.
 */
public class AnalyseJson {
    /*
    public static <T>T getInstance(){

    }
    */

    public static <T> List<T>getCarpoolList(String jsonString){
        System.out.println(jsonString);
        Log.d("AnalyseJson",jsonString);
        Gson gson= new Gson();
        List<T>list;
        list=gson.fromJson(jsonString,new TypeToken<List<CarpoolInfo>>(){

        }.getType());
        System.out.println(list);
        Log.d("AnalyseJson.List",""+list.toString());
        return list;
    }

    public static <T> List<T>getMyInfoList(String jsonString){
        System.out.println(jsonString);
        Gson gson= new Gson();
        List<T>list;
        list=gson.fromJson(jsonString,new TypeToken<List<MyInfo>>(){
        }.getType());
        System.out.println(list);
        return list;
    }

    public static <T> List<T>getCommentInfoList(String jsonString){
        System.out.println(jsonString);
        Gson gson= new Gson();
        List<T>list;
        list=gson.fromJson(jsonString,new TypeToken<List<CommentInfo>>(){
        }.getType());
        Log.d("Analyse.CommentInfoList",jsonString);
        System.out.println(list);
        return list;
    }
    /*
    public static <T> List<T>getJsonList(String jsonString,Class<T>cls){
        Log.d("AnalyseJson",""+jsonString);
        Gson gson= new Gson();
        List<T>list;
        list=gson.fromJson(jsonString,new TypeToken<List<T>>(){
        }.getType());
        System.out.println(list);
        Log.d("AnalyseJson.List",""+list.toString());
        return list;
    }
*/
    public static <T> T getInstance(String jsonString,Class<T>cls){
        T t = null;
        try {
            Gson gson = new Gson();
            t = gson.fromJson(jsonString,cls);
        }catch (Exception e){
            e.printStackTrace();
        }
        return t;

    }
}
