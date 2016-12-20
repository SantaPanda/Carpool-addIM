package com.example.sheng.carpool.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
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
import com.example.sheng.carpool.Dao.MyInfo;
import com.example.sheng.carpool.Data.AnalyseJson;
import com.example.sheng.carpool.Data.PublicData;
import com.example.sheng.carpool.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;


public class MyInfoFragment extends Fragment {

    private View view;
    private RequestQueue mRequestQueue;
    private MyInfo myInfo;
    private EditText my_info_name;
    private EditText my_info_nickname;
    private EditText my_info_sex;
    private EditText my_info_phone;
    private EditText my_info_wechat;
    private EditText my_info_qq;
    private EditText my_info_show_me;
    private ImageButton my_info_change_ok;
    private ImageButton exit;
    private ImageButton my_info_change;
    private TextView my_info_good;
    private TextView my_info_bad;
    private TextView my_info_credit;
    private String str_my_info_name,str_my_info_nickname,str_my_info_sex,str_my_info_phone;
    private String str_my_info_wechat,str_my_info_qq,str_my_info_show_me;
    //SharedPreferences存储
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private String account="";

    //获取账号
    private void getAccount(){
        //之前有登陆，直接填写数据
        pref = getActivity().getSharedPreferences("data",getActivity().MODE_PRIVATE);
        String a = pref.getString("account","");
        String b = pref.getString("password","");
        if(!a.equals("")){
            account =a;
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_info, container, false);
        componentInit();
        getAccount();
        login();
        my_info_change_ok.setVisibility(View.INVISIBLE);
        changeFalseEdit();
        if(!account.equals("")){
            myInfoServer();
        }
        return view;
    }
    private void login(){
        //第一次强制登陆
        if(account.equals("")){
            //强制登陆
            Intent intent=new Intent();
            intent.setClass(getContext(),Login.class);
            getContext().startActivity(intent);
        }
    }
    //组件初始化，为组件监听
    private void componentInit(){
        my_info_good = (TextView)view.findViewById(R.id.my_info_good);
        my_info_bad = (TextView)view.findViewById(R.id.my_info_bad);
        my_info_credit = (TextView)view.findViewById(R.id.my_info_credit);
        my_info_name = (EditText) view.findViewById(R.id.my_info_name);
        my_info_nickname = (EditText) view.findViewById(R.id.my_info_nickname);
        my_info_sex = (EditText) view.findViewById(R.id.my_info_sex);
        my_info_phone = (EditText) view.findViewById(R.id.my_info_phone);
        my_info_wechat = (EditText) view.findViewById(R.id.my_info_wechat );
        my_info_qq = (EditText) view.findViewById(R.id.my_info_qq);
        my_info_show_me = (EditText) view.findViewById(R.id.my_info_show_me);
        my_info_change_ok = (ImageButton)view.findViewById(R.id.my_info_change_ok);
        my_info_change_ok.setOnClickListener(new buttonListener());
        exit = (ImageButton)view.findViewById(R.id.exit);
        exit.setOnClickListener(new buttonListener());
        my_info_change = (ImageButton) view.findViewById(R.id.my_info_change);
        my_info_change.setOnClickListener(new buttonListener());
    }
    private void getValue(){
        str_my_info_name=my_info_name.getText().toString();
        str_my_info_nickname=my_info_nickname.getText().toString();
        str_my_info_sex=my_info_sex.getText().toString();
        str_my_info_phone=my_info_phone.getText().toString();
        str_my_info_wechat=my_info_wechat.getText().toString();
        str_my_info_qq=my_info_qq.getText().toString();
        str_my_info_show_me=my_info_show_me.getText().toString();
    }

    //设置所有EditText是否可编辑
    private void changeEdit(boolean flag){
        /*
        PublicData.changeEditState(flag,my_info_name);
        PublicData.changeEditState(flag,my_info_nickname);
        PublicData.changeEditState(flag,my_info_sex);
        PublicData.changeEditState(flag,my_info_phone);
        PublicData.changeEditState(flag,my_info_wechat);
        PublicData.changeEditState(flag,my_info_qq);
        PublicData.changeEditState(flag,my_info_show_me);
        */

    }
    private void changeFalseEdit(){
        my_info_name.setFocusable(false);
        my_info_name.setFocusableInTouchMode(false);
        my_info_nickname.setFocusable(false);
        my_info_nickname.setFocusableInTouchMode(false);
        my_info_sex.setFocusable(false);
        my_info_sex.setFocusableInTouchMode(false);
        my_info_phone.setFocusable(false);
        my_info_phone.setFocusableInTouchMode(false);
        my_info_wechat.setFocusable(false);
        my_info_wechat.setFocusableInTouchMode(false);
        my_info_qq.setFocusable(false);
        my_info_qq.setFocusableInTouchMode(false);
        my_info_show_me.setFocusable(false);
        my_info_show_me.setFocusableInTouchMode(false);
    }
    private void changeTrueEdit(){
        my_info_name.setFocusableInTouchMode(true);
        my_info_name.setFocusable(true);
        my_info_name.requestFocus();
        my_info_nickname.setFocusableInTouchMode(true);
        my_info_nickname.setFocusable(true);
        my_info_nickname.requestFocus();
        my_info_sex.setFocusableInTouchMode(true);
        my_info_sex.setFocusable(true);
        my_info_sex.requestFocus();
        my_info_phone.setFocusableInTouchMode(true);
        my_info_phone.setFocusable(true);
        my_info_phone.requestFocus();
        my_info_wechat.setFocusableInTouchMode(true);
        my_info_wechat.setFocusable(true);
        my_info_wechat.requestFocus();
        my_info_qq.setFocusableInTouchMode(true);
        my_info_qq.setFocusable(true);
        my_info_qq.requestFocus();
        my_info_show_me.setFocusableInTouchMode(true);
        my_info_show_me.setFocusable(true);
        my_info_show_me.requestFocus();
    }

    private void getPeopleInfo(String response){
        myInfo = AnalyseJson.getInstance(response, MyInfo.class);
    }
    private void setValue(){
        my_info_name.setText(myInfo.getName());
        my_info_nickname.setText(myInfo.getNickname());
        my_info_sex.setText(myInfo.getSex());
        my_info_phone.setText(myInfo.getPhone());
        my_info_wechat.setText(myInfo.getWechat());
        my_info_qq.setText(myInfo.getQq());
        my_info_show_me.setText(myInfo.getIntroduce());
        int int_good=myInfo.getGood();
        int int_bad=myInfo.getBad();
        my_info_good.setText(""+int_good);
        my_info_bad.setText(""+int_bad);
        DecimalFormat df = new DecimalFormat(".00");
        Double double_credit= 100 *(double)int_good/(int_good+int_bad);
        my_info_credit.setText(""+df.format(double_credit)+"%");
    }
    /*
    //解析JsonObject
    private void parseJsonObject(JSONObject jsonObject){
        String str_good="", str_bad="";
        double double_credit = 0.0;
        try {
            my_info_name.setText(jsonObject.getString("name"));
            my_info_nickname.setText(jsonObject.getString("nickName"));
            my_info_sex.setText(jsonObject.getString("sex"));
            my_info_phone.setText(jsonObject.getString("phoneNum"));
            my_info_wechat.setText(jsonObject.getString("weChat"));
            my_info_qq.setText(jsonObject.getString("qq"));
            my_info_show_me.setText(jsonObject.getString("introduce"));
            str_good=jsonObject.getString("good");
            str_bad=jsonObject.getString("bad");
            my_info_good.setText(str_good);
            my_info_bad.setText(str_bad);
            DecimalFormat df = new DecimalFormat(".00");
            double_credit= 100*Double.parseDouble(str_good)/(Integer.parseInt(str_good)+Integer.parseInt(str_bad));
            my_info_credit.setText(""+df.format(double_credit)+"%");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
*/
    private void changeMyInfoServer(){
        final String url= PublicData.changeMyInfoServer;
        getAccount();
        getValue();
        mRequestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
     //           Toast.makeText(getContext(),response,Toast.LENGTH_SHORT).show();
                if(response.equals(PublicData.TRUE_RETURN)){
                    Toast.makeText(getContext(),"修改成功",Toast.LENGTH_SHORT).show();
                }
                Log.d("TAG", response);
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),"请连接网络！",Toast.LENGTH_SHORT).show();
                Log.e("TAG", error.getMessage(), error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //用HashMap来存储请求参数
                Map<String,String> map = new HashMap<String,String>();
                map.put("type","MyInfo");
                map.put("account",account);
                map.put("name",str_my_info_name);
                map.put("nickName",str_my_info_nickname);
                map.put("sex",str_my_info_sex);
                map.put("phone",str_my_info_phone);
                map.put("wechat",str_my_info_wechat);
                map.put("qq",str_my_info_qq);
                map.put("show_me",str_my_info_show_me);
                return map;
            }
        };
        mRequestQueue.add(stringRequest);
    }

    private void myInfoServer(){
        getAccount();
        final String url = PublicData.myInfoServer;
        mRequestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
             //   Toast.makeText(getContext(),response,Toast.LENGTH_SHORT).show();
                if(PublicData.returnFalse(response)){
                    getPeopleInfo(response);
                    setValue();
                }
                Log.d("TAG", response);
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Cache.Entry entry = mRequestQueue.getCache().get(url);
                if(entry!=null){
                    try {
                        String data = new String(entry.data, "UTF-8");
                        if(PublicData.returnFalse(data)){
                            getPeopleInfo(data);
                            setValue();
                        }
                        else {
                            Toast.makeText(getContext(),"请连接网络使用！",Toast.LENGTH_SHORT).show();
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
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
    /*
    private void myInfoServer(){
        getAccount();
        //final String url="http://172.22.5.200:8080/CarpoolWeb_war_exploded/test4";
        final String url = PublicData.myInfoServer;
        mRequestQueue = Volley.newRequestQueue(getContext());
        Map<String,String> map=new HashMap<String,String>();
        //传一个参数,type=myInfo
        map.put("type", "CheckInfo");
        map.put("account",account);
        JSONObject params=new JSONObject(map);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                parseJsonObject(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Cache.Entry entry = mRequestQueue.getCache().get(url);
                if(entry!=null){
                    try {
                        String data = new String(entry.data, "UTF-8");
                        JSONObject jsonObject = new JSONObject(data);
                        parseJsonObject(jsonObject);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Toast.makeText(getContext(),"Cache空的",Toast.LENGTH_SHORT).show();
                }
                Log.e("TAG", error.getMessage(), error);
            }
        });
        mRequestQueue.add(jsonObjectRequest);
/*
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                url, new Response.Listener<JSONArray>() {
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
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Cache.Entry entry = mRequestQueue.getCache().get(url);
                if(entry!=null){
                    try {
                        String data = new String(entry.data, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                Log.e("TAG", error.getMessage(), error);
            }
        });
        mRequestQueue.add(jsonArrayRequest);


    }
*/
    class buttonListener implements View.OnClickListener {
        public void onClick(View v){
            switch (v.getId()){
                case R.id.exit:
                    Intent intent = new Intent();
                    intent.setClass(getContext(),Login.class);
                    getContext().startActivity(intent);
                   // getActivity().finish();
                    break;
                case R.id.my_info_change_ok:
                    changeMyInfoServer();
                    my_info_change_ok.setVisibility(View.INVISIBLE);
                    my_info_change.setVisibility(View.VISIBLE);
                    changeFalseEdit();
                    break;
                case R.id.my_info_change:
                    changeTrueEdit();
                    my_info_change.setVisibility(View.INVISIBLE);
                    my_info_change_ok.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
        }
    }
}
