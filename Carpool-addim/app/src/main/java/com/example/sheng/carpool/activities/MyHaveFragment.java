package com.example.sheng.carpool.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sheng.carpool.Dao.CarpoolInfo;
import com.example.sheng.carpool.Data.AnalyseJson;
import com.example.sheng.carpool.Data.PublicData;
import com.example.sheng.carpool.ListViewHelp.CarpoolInfoListAdapter;
import com.example.sheng.carpool.R;
import com.example.sheng.carpool.helpers.JsonOperation;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyHaveFragment extends Fragment {

    private RequestQueue mRequestQueue;
    private View view;
    private Button my_have_publish;
    private Button my_have_join;
   // private Button my_have_done;
    private TextView my_have_name;
    //ListView用的
    private CarpoolInfoListAdapter carpoolInfoListAdapter1,carpoolInfoListAdapter2,carpoolInfoListAdapter3;
    private List<CarpoolInfo> carpoolInfoArrayList1 = new ArrayList<>();
    private List<CarpoolInfo> carpoolInfoArrayList2 = new ArrayList<>();
    private List<CarpoolInfo> carpoolInfoArrayList3 = new ArrayList<>();
    private CarpoolInfo [] carpoolInfos;
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
            my_have_name.setText(a);
            account =a;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("response","response");
        view = inflater.inflate(R.layout.fragment_my_have, container, false);
        componentInit();
        getAccount();
        carpoolInfoListAdapter1 = new CarpoolInfoListAdapter(getContext(),
                R.layout.search_result,carpoolInfoArrayList1);
        ListView listView1 = (ListView)view.findViewById(R.id.my_have_publish_listView);
        listView1.setAdapter(carpoolInfoListAdapter1);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CarpoolInfo carpoolInfo = carpoolInfoArrayList1.get(i);
         //       Toast.makeText(getContext(),""+carpoolInfo.getCARPOOLID(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                //String carpool = JsonOperation.jsonObjectStructure(carpoolInfo);
                String carpool = new Gson().toJson(carpoolInfo);
                intent.putExtra("carpool",carpool);
                intent.setClass(getContext(),Search_case.class);
                getContext().startActivity(intent);
            }
        });

        carpoolInfoListAdapter2 = new CarpoolInfoListAdapter(getContext(),
                R.layout.search_result,carpoolInfoArrayList2);
        ListView listView2 = (ListView)view.findViewById(R.id.my_have_join_listView);
        listView2.setAdapter(carpoolInfoListAdapter2);
        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CarpoolInfo carpoolInfo = carpoolInfoArrayList2.get(i);
          //      Toast.makeText(getContext(),""+carpoolInfo.getCARPOOLID(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                //String carpool = JsonOperation.jsonObjectStructure(carpoolInfo);
                String carpool = new Gson().toJson(carpoolInfo);
                intent.putExtra("carpool",carpool);
                intent.setClass(getContext(),Search_case.class);
                getContext().startActivity(intent);
            }
        });
/*
        carpoolInfoListAdapter3 = new CarpoolInfoListAdapter(getContext(),
                R.layout.search_result,carpoolInfoArrayList3);
        ListView listView3 = (ListView)view.findViewById(R.id.my_have_done_listView);
        listView3.setAdapter(carpoolInfoListAdapter3);
        listView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CarpoolInfo carpoolInfo = carpoolInfoArrayList3.get(i);
                Toast.makeText(getContext(),""+carpoolInfo.getCARPOOLID(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                String carpool = JsonOperation.jsonObjectStructure(carpoolInfo);
                intent.putExtra("carpool",carpool);
                intent.setClass(getContext(),Search_case.class);
                getContext().startActivity(intent);
            }
        });
        */
        return view;

    }

    private void componentInit(){
        my_have_name = (TextView)view.findViewById(R.id.my_have_name);
        my_have_publish = (Button)view.findViewById(R.id.my_have_publish);
        my_have_publish.setOnClickListener(new buttonListener());
        my_have_join = (Button)view.findViewById(R.id.my_have_join);
        my_have_join.setOnClickListener(new buttonListener());
       // my_have_done = (Button)view.findViewById(R.id.my_have_done);
    //    my_have_done.setOnClickListener(new buttonListener());
    }


    //将response添加上去
    private void addCarpoolResponse(String response){
        if(carpoolInfoListAdapter2.getCount()!=0){
            carpoolInfoArrayList2.clear();
            carpoolInfoListAdapter2.notifyDataSetChanged();
        }
        else{
            getMyAddList(response);
            carpoolInfoListAdapter2.notifyDataSetChanged();
        }
    }
    private void myAdd(){
        final String url = PublicData.myAddServer;
        mRequestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
              //  Toast.makeText(getContext(),""+response,Toast.LENGTH_SHORT).show();
                //if(!response.equals(PublicData.FALSE_RETURN)){
                PublicData.returnToast(getContext(),response);
                if(PublicData.returnFalse(response)){
                    addCarpoolResponse(response);
                    /*
                    if(carpoolInfoListAdapter2.getCount()!=0){
                        carpoolInfoArrayList2.clear();
                        carpoolInfoListAdapter2.notifyDataSetChanged();
                    }
                    else{
                        getMyAddList(response);
                        carpoolInfoListAdapter2.notifyDataSetChanged();
                    }
                    */
                }
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Cache.Entry entry = mRequestQueue.getCache().get(url);
                if(entry!=null){
                    try {
                        String data = new String(entry.data, "UTF-8");
                      //  if(!data.equals(PublicData.FALSE_RETURN)){
                        if(PublicData.returnFalse(data)){
                            addCarpoolResponse(data);
                        }
                        else {
                            Toast.makeText(getContext(),PublicData.NO_NETWORK,Toast.LENGTH_SHORT).show();
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Toast.makeText(getContext(),PublicData.NO_NETWORK,Toast.LENGTH_SHORT).show();
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("type","myAdd");
                map.put("account",account);
                return map;
            }
        };
        mRequestQueue.add(stringRequest);
    }

    //将response添加上去
    private void publishCarpoolResponse(String response){
        if(carpoolInfoListAdapter1.getCount()!=0){
            carpoolInfoArrayList1.clear();
            carpoolInfoListAdapter1.notifyDataSetChanged();
        }
        else{
            getMyPublishList(response);
            carpoolInfoListAdapter1.notifyDataSetChanged();
        }
    }
    private void myPublish(){
        final String url= PublicData.myPublishServer;
        mRequestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
        //        Toast.makeText(getContext(),response,Toast.LENGTH_SHORT).show();
                PublicData.returnToast(getContext(),response);
                if(PublicData.returnFalse(response)){
                    publishCarpoolResponse(response);
                }
                /*
                if(!response.equals(PublicData.FALSE_RETURN)){
                    if(carpoolInfoListAdapter1.getCount()!=0){
                        carpoolInfoArrayList1.clear();
                        carpoolInfoListAdapter1.notifyDataSetChanged();
                    }
                    else{
                        getMyPublishList(response);
                        carpoolInfoListAdapter1.notifyDataSetChanged();
                    }
                }
                */
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

                Cache.Entry entry = mRequestQueue.getCache().get(url);
                if(entry!=null){
                    try {
                        String data = new String(entry.data, "UTF-8");
                        //if(!data.equals(PublicData.FALSE_RETURN)){
                        if(PublicData.returnFalse(data)){
                            publishCarpoolResponse(data);
                        }
                        else {
                            Toast.makeText(getContext(),PublicData.NO_NETWORK,Toast.LENGTH_SHORT).show();
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Toast.makeText(getContext(),PublicData.NO_NETWORK,Toast.LENGTH_SHORT).show();
                }
                Log.e("TAG", error.getMessage(), error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("type","myPublish");
                map.put("account",account);
                return map;
            }
        };
        mRequestQueue.add(stringRequest);
    }

    //ListView用的
    private void getMyPublishList(String response){
        List<CarpoolInfo> list = new ArrayList<CarpoolInfo>();
        list = AnalyseJson.getCarpoolList(response);
        if(list!=null&&!list.isEmpty()){
            for(int i=0;i<list.size();i++){
                carpoolInfoArrayList1.add(list.get(i));
            }
        }
    }
    private void getMyAddList(String response){
        List<CarpoolInfo> list = new ArrayList<CarpoolInfo>();
        list = AnalyseJson.getCarpoolList(response);
        if(list!=null&&!list.isEmpty()){
            for(int i=0;i<list.size();i++){
                carpoolInfoArrayList2.add(list.get(i));
            }
        }
    }
    /*
    private void initCarpoolInfoList1(){
        CarpoolInfo carpoolInfo1 = new CarpoolInfo("accountID1.1",1,"name1.1","date1.1","departure1.1",
                "destination1.1", "Time1.1", 111,114,1,"phoneNum1.1","detail1.1","addID1.1",
                "commentID");
        carpoolInfoArrayList1.add(carpoolInfo1);
        CarpoolInfo carpoolInfo2 = new CarpoolInfo("accountID1.2",2,"name1.2","date1.2","departure1.2",
                "destination1.2", "Time1.2", 112,224,2,"phoneNum1.2","detail1.2","addID1.2",
                "commentID");
        carpoolInfoArrayList1.add(carpoolInfo2);
    }
    private void initCarpoolInfoList2(){
        CarpoolInfo carpoolInfo1 = new CarpoolInfo("accountID2.1",3,"name2.1","date2.1","departure2.1",
                "destination2.1", "Time2.1",221,214,1,"phoneNum2.1","detail2.1","addID2.1",
                "commentID");
        carpoolInfoArrayList2.add(carpoolInfo1);
        CarpoolInfo carpoolInfo2 = new CarpoolInfo("accountID2.2",4,"name","date","departure",
                "destination", "departureTime", 100,4,1,"phoneNum","detail","addID",
                "commentID");
        carpoolInfoArrayList2.add(carpoolInfo2);
    }
    */
    private void initCarpoolInfoList3(){
        CarpoolInfo carpoolInfo1 = new CarpoolInfo("accountID3.1",5,"name","date","departure",
                "destination", "departureTime", 100,4,1,"phoneNum","detail","addID",
                "commentID");
        carpoolInfoArrayList3.add(carpoolInfo1);
        CarpoolInfo carpoolInfo2 = new CarpoolInfo("accountID3.2",6,"name","date","departure",
                "destination", "departureTime", 100,4,1,"phoneNum","detail","addID",
                "commentID");
        carpoolInfoArrayList3.add(carpoolInfo2);
    }

    class buttonListener implements View.OnClickListener {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.my_have_publish:
                    if(!account.equals("")){
                        myPublish();
                    }
                    else {
                        PublicData.login(getContext());
                    }

                    /*
                    if(carpoolInfoListAdapter1.getCount()!=0){
                        carpoolInfoArrayList1.clear();
                        carpoolInfoListAdapter1.notifyDataSetChanged();
                    }
                    else{
                        initCarpoolInfoList1();
                        carpoolInfoListAdapter1.notifyDataSetChanged();
                    }
                    */
                    break;
                case R.id.my_have_join:
                    if(!account.equals("")){
                        myAdd();
                    }
                    else {
                        PublicData.login(getContext());
                    }

                    /*
                    if(carpoolInfoListAdapter2.getCount()!=0){
                        carpoolInfoArrayList2.clear();
                        carpoolInfoListAdapter2.notifyDataSetChanged();
                    }
                    else{
                        initCarpoolInfoList2();
                        carpoolInfoListAdapter2.notifyDataSetChanged();
                    }
                    */
                    break;
                /*
                case R.id.my_have_done:
                    if(carpoolInfoListAdapter3.getCount()!=0){
                        carpoolInfoArrayList3.clear();
                        carpoolInfoListAdapter3.notifyDataSetChanged();
                    }
                    else{
                        initCarpoolInfoList3();
                        carpoolInfoListAdapter3.notifyDataSetChanged();
                    }
                    break;
                    */
                default:
                    break;
            }
        }
    }
}
