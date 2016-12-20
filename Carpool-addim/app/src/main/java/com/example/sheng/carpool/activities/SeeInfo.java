package com.example.sheng.carpool.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sheng.carpool.Dao.CarpoolInfo;
import com.example.sheng.carpool.Dao.MyInfo;
import com.example.sheng.carpool.Data.AnalyseJson;
import com.example.sheng.carpool.Data.PublicData;
import com.example.sheng.carpool.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SeeInfo extends Activity {

    private RequestQueue mRequestQueue;
    private TextView see_info_name;
    private TextView see_info_nickname;
    private TextView see_info_sex;
    private TextView see_info_phone;
    private TextView see_info_wechat;
    private TextView see_info_qq;
    private TextView see_info_good;
    private TextView see_info_bad;
    private TextView see_info_show_other;
    private String account;
    private MyInfo myInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_info);
        componentInit();
        Intent intent = getIntent();
        account = intent.getStringExtra("account");
        Log.d("SeeInfo",account);
        seePeopleInfoServer();
    }

    private void seePeopleInfoServer(){
        final String url= PublicData.myInfoServer;
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
     //           Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                if(!response.equals(PublicData.FALSE_RETURN)){
                    Log.d("SeeInfo",response);
                    getPeopleInfo(response);
                    setValue();
                }
                Log.d("TAG", response);
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),PublicData.NO_NETWORK,Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("type", "CheckInfo");
                map.put("account",account);
                return map;
            }
        };
        mRequestQueue.add(stringRequest);
    }

    private void getPeopleInfo(String response){
        myInfo = AnalyseJson.getInstance(response, MyInfo.class);
    }

    private void setValue(){
        see_info_name.setText(myInfo.getName());
        see_info_nickname.setText(myInfo.getNickname());
        see_info_sex.setText(myInfo.getSex());
        see_info_phone.setText(myInfo.getPhone());
        see_info_wechat.setText(myInfo.getWechat());
        see_info_qq.setText(myInfo.getQq());
        see_info_good.setText(""+myInfo.getGood());
        see_info_bad.setText(""+myInfo.getBad());
        see_info_show_other.setText(myInfo.getIntroduce());
    }

    private void componentInit(){
        see_info_name = (TextView)findViewById(R.id.see_info_name);
        see_info_nickname = (TextView)findViewById(R.id.see_info_nickname);
        see_info_sex = (TextView)findViewById(R.id.see_info_sex);
        see_info_phone = (TextView)findViewById(R.id.see_info_phone);
        see_info_wechat = (TextView)findViewById(R.id.see_info_wechat);
        see_info_qq = (TextView)findViewById(R.id.see_info_qq);
        see_info_good = (TextView)findViewById(R.id.see_info_good);
        see_info_bad = (TextView)findViewById(R.id.see_info_bad);
        see_info_show_other = (TextView)findViewById(R.id.see_info_show_other);
    }
}
