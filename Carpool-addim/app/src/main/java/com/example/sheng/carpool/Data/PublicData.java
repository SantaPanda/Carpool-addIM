package com.example.sheng.carpool.Data;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.renderscript.Type;
import android.text.InputFilter;
import android.text.Spanned;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sheng.carpool.activities.Login;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by sheng on 16-11-26.
 */
public class PublicData implements StatusCode{

    //private static final String server = "http://172.22.20.165:8080/_Server/servlet/";
    private static final String server = "http://101.200.39.155/_Server/servlet/";
   // private static final String server = "http://172.22.5.200:8080/CarpoolWeb_war_exploded/";
    //public static final String loginServer = server +"net-work";
    //public static final String registerServer = server+"net-work";
    public static final String loginServer = server+"Login";
    public static final String registerServer = server+"Register";
    public static final String changeMyInfoServer = server+"myInfo";
    public static final String myInfoServer = server+"CheckInfo";
    public static final String searchServer = server+"Search";
    public static final String forgetServer = server+"Forget";
    public static final String messageServer = server+"Message";
    public static final String myPublishServer = server+"MyPublish";
    public static final String myAddServer = server+"MyAdd";
    public static final String addServer = server+"Add";
    public static final String otherAccountServer = server+"OtherAddMember";
    public static final String publishServer = server+"Publish";
    public static final String commentMemberServer = server+"CommentMember";


    public static final String firstproject ="http://172.22.5.200:8080/firstProject_war_exploded/server-plus-demo";
    public static final int clientStoreNum =10;   //手机数据库存储10条信息
    public static final String TRUE_RETURN = "200";
    public static final String FALSE_RETURN = "false";
    public static final String NO_NETWORK = "请连接网络使用！";

//不包括FAILED_TO_SEARCH_RESULT,即115
    public static boolean returnNoInfo(String response){

        if(response.equals(FAILED)){
            return false;
        }
        else if(response.equals(WRONG_PASSWORD)){
            return false;
        }
        else if(response.equals(FAILED_TO_EXCUTE_SQL)){
            return false;
        }
        else if(response.equals(FAILED_TO_SEARCH_USERNAME)){
            return false;
        }
        else if(response.equals(HAD_IN)){
            return false;
        }
        else if(response.equals(WROONG_TYPE_OF_REQUEST)){
            return false;
        }
        else if(response.equals(ACCOUNT_EXISTED)){
            return false;
        }
        else if(response.equals(FAILED_TOCONNECT_DATABASE)){
            return false;
        }
        return true;
    }
    public static void returnToast(Context context,String response){

        if(response.equals(FAILED)){

        }
        else if(response.equals(WRONG_PASSWORD)){

        }
        else if(response.equals(FAILED_TO_EXCUTE_SQL)){

        }
        else if(response.equals(FAILED_TO_SEARCH_USERNAME)){

        }
        else if(response.equals(FAILED_TO_SEARCH_RESULT)){
            Toast.makeText(context,"搜索不到",Toast.LENGTH_SHORT).show();
        }
        else if(response.equals(HAD_IN)){

        }
        else if(response.equals(WROONG_TYPE_OF_REQUEST)){

        }
        else if(response.equals(ACCOUNT_EXISTED)){

        }
        else if(response.equals(FAILED_TOCONNECT_DATABASE)){

        }

    }

    //判断返回是否无效
    public static boolean returnFalse(String response){

        if(response.equals(FAILED)){
            return false;
        }
        else if(response.equals(WRONG_PASSWORD)){
            return false;
        }
        else if(response.equals(FAILED_TO_EXCUTE_SQL)){
            return false;
        }
        else if(response.equals(FAILED_TO_SEARCH_USERNAME)){

            return false;
        }
        else if(response.equals(FAILED_TO_SEARCH_RESULT)){
            return false;
        }
        else if(response.equals(HAD_IN)){
            return false;
        }
        else if(response.equals(WROONG_TYPE_OF_REQUEST)){
            return false;
        }
        else if(response.equals(ACCOUNT_EXISTED)){
            return false;
        }
        else if(response.equals(FAILED_TOCONNECT_DATABASE)){
            return false;
        }

        return true;
    }

/*
    public static <T> T gsonToObject(Class<T> c,String string){
        Gson gson = new Gson();

    }
    */
    //设置EditText是否可以编辑
    public static void changeEditState(boolean value, EditText editText) {
        if (value) {
            editText.setFocusable(true);
            editText.setFocusableInTouchMode(true);
            editText.setFilters(new InputFilter[] { new InputFilter() {
                @Override
                public CharSequence filter(CharSequence source, int start,
                                           int end, Spanned dest, int dstart, int dend) {
                    return null;
                }
            } });
        }
        else {
            editText.setFocusable(false);
            editText.setFocusableInTouchMode(false);
            //输入框无法输入新的内容
            /*
            editText.setFilters(new InputFilter[] { new InputFilter() {
                @Override
                public CharSequence filter(CharSequence source,
                                           int start, int end, Spanned dest, int dstart, int dend) {
                    return source.length() < 1 ? dest.subSequence(dstart, dend) : "";
                }
            } });
            */
        }
    }

    //判断EditText是否可编辑
    private boolean isEdit(){
        return false;
    }

    //SharedPreferences存储
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private String account;
    //获取账号
    private String getAccount(Context context){
        pref = context.getApplicationContext().getSharedPreferences("data",context.MODE_PRIVATE);
        String a = pref.getString("account","");
        return a;
    }
    public static boolean isEmpty(String str){
        if(str.equals("")){
            return false;
        }
        return true;
    }

    public static void login(Context context){
        Intent intent1 = new Intent();
        intent1.setClass(context,Login.class);
        context.startActivity(intent1);
    }
}
