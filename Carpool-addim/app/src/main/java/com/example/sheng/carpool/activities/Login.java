package com.example.sheng.carpool.activities;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sheng.carpool.Data.PublicData;
import com.example.sheng.carpool.IM.App;
import com.example.sheng.carpool.IM.CheckInternet;
import com.example.sheng.carpool.IM.GetToken;
import com.example.sheng.carpool.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Login extends Activity {

    private EditText login_num_input;
    private EditText login_pass_input;
    private Button login_sure;
    private Button login_forget;
    private Button login_register;
    private String str_login_num_input,str_login_pass_input;

    private String mytoken;
    private CheckInternet checkInternet = new CheckInternet();

    //SharedPreferences存储
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    //volly网络请求处理
    public static final String TAG = Login.class.getSimpleName();
    private RequestQueue mRequestQueue;
    private static Login mInstance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //注册网络接收器
        registerReceiver(checkInternet.receiver, checkInternet.intentFilter);

        componentInit();
        //之前有登陆，直接填写数据
        pref = getSharedPreferences("data",MODE_PRIVATE);
        String a = pref.getString("account","");
        String b = pref.getString("password","");
        if(!a.equals("")){
            login_num_input.setText(a);
            login_pass_input.setText(b);
            //login_pass_input.setSelection(a.length());
        }
    }
    //组件初始化，为组件监听
    private void componentInit(){
        login_num_input = (EditText) findViewById(R.id.login_num_input);
        login_pass_input = (EditText) findViewById(R.id.login_pass_input);
        login_sure = (Button) findViewById(R.id.login_sure);
        login_sure.setOnClickListener(new buttonListener());
        login_forget = (Button) findViewById(R.id.login_forget);
        login_forget.setOnClickListener(new buttonListener());
        login_register = (Button) findViewById(R.id.login_register);
        login_register.setOnClickListener(new buttonListener());
    }
    //存储账号密码到SharedPreferences
    private  void saveAccountPwToLoacl(){
        editor = pref.edit();
        editor.putString("account",login_num_input.getText().toString().trim());
        editor.putString("password",login_pass_input.getText().toString().trim());
        editor.commit();
    }

    //gettoken
    private void getTokenInLogin(){
        if(checkInternet.flag == 1) {
            GetToken getToken = new GetToken();
            mytoken = getToken.GetRongCloudToken(str_login_num_input, str_login_num_input, "portriat");
            if(!mytoken.isEmpty()){
                App app = new App();
                app.connect(mytoken);
            }
        }
        else {
            Toast.makeText(getApplicationContext(), "network break!", Toast.LENGTH_SHORT).show();;
        }
    }

    @Override
    protected void onStop(){
        super.onStop();
        unregisterReceiver(checkInternet.receiver);
    }

    //存储token到SharedPreference
    private void saveTokenToLocal(){
        editor = pref.edit();
        editor.putString("token", mytoken);
        editor.commit();
    }

    private void login(){
        str_login_num_input = login_num_input.getText().toString();
        str_login_pass_input = login_pass_input.getText().toString();
        final String url= PublicData.loginServer;
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(Login.this,response,Toast.LENGTH_SHORT).show();
                if(response.equals(PublicData.TRUE_RETURN)){
                    //保存账号和密码要本地
                    saveAccountPwToLoacl();
                    getTokenInLogin();
                    //保存token到本地
                    if(!mytoken.isEmpty()){
                        saveTokenToLocal();
                    }
                    else {
                        Toast.makeText(Login.this, "Cannot get the token from RongServer!", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }
                else {
                    Toast.makeText(Login.this,"请输入有效账号和密码或连接网络",Toast.LENGTH_SHORT).show();
                }
            }
            },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this,PublicData.NO_NETWORK,Toast.LENGTH_LONG).show();
                /*
                Cache.Entry entry = mRequestQueue.getCache().get(url);
                if(entry!=null){
                    try {
                        String data = new String(entry.data, "UTF-8");
                        if(data.equals(PublicData.TRUE_RETURN)){
                            finish();
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"请连接网络使用！",Toast.LENGTH_SHORT).show();
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    login_num_input.setText("请连接网络使用！");
                }
                */
            }
            }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //用HashMap来存储请求参数
                Map<String,String> map = new HashMap<String,String>();
                map.put("type","login");
                map.put("account",str_login_num_input);
                map.put("password",str_login_pass_input);
                return map;
            }
            };
        mRequestQueue.add(stringRequest);
    }
    /*get请求
            StringRequest stringRequest = new StringRequest(Request.Method.GET,"https://api.thinkpage.cn/v3/weather/now.json?key=rot2enzrehaztkdk&location=guangzhou&language=zh-Hans&unit=c",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            //String s即为服务器返回的数据
                            Log.d("cylog", s);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("cylog", error.getMessage(),error);
                }

            });
    */
/*
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                url2, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                String str = "";
                for(int i=0;i<response.length();i++){
                    JSONObject jsonObject = null;
                    try {
                         jsonObject= response.getJSONObject(i);
                        str += jsonObject.getString("a");
                        str += jsonObject.getString("b");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                login_num_input.setText(str);
                Toast.makeText(Login.this,str,Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mRequestQueue.add(jsonArrayRequest);
*/
/*
    public String parseJSON(String jsonStream){
        String str="";
        try {
            JSONArray jsonArray = new JSONArray(jsonStream);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                str += jsonObject.getString("a");
                str += jsonObject.getString("b");
                str += "\n";
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  str;
    }
*/
    class buttonListener implements View.OnClickListener {
        public void onClick(View v){
            switch (v.getId()){
                case R.id.login_sure:
                    login();
                    break;
                case R.id.login_forget:
                    Intent intent2 = new Intent();
                    intent2.setClass(Login.this,Forget.class);
                    Login.this.startActivity(intent2);
                    break;
                case R.id.login_register:
                    Intent intent3 = new Intent();
                    intent3.setClass(Login.this,Register.class);
                    Login.this.startActivity(intent3);
                   // finish();
                    break;
                default:
                    break;
            }
        }
    }
}
