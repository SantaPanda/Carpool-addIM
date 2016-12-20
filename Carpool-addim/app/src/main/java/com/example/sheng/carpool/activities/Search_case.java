package com.example.sheng.carpool.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ExpandedMenuView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.sheng.carpool.Dao.AddInfo;
import com.example.sheng.carpool.Dao.CarpoolInfo;
import com.example.sheng.carpool.Dao.CommentAdd;
import com.example.sheng.carpool.Dao.CommentInfo;
import com.example.sheng.carpool.Dao.MyInfo;
import com.example.sheng.carpool.Data.AnalyseJson;
import com.example.sheng.carpool.Data.PublicData;
import com.example.sheng.carpool.ListViewHelp.AddInfoListAdapter;
import com.example.sheng.carpool.ListViewHelp.CarpoolInfoListAdapter;
import com.example.sheng.carpool.ListViewHelp.CommentAddListAdapter;
import com.example.sheng.carpool.ListViewHelp.CommentInfoListAdapter;
import com.example.sheng.carpool.ListViewHelp.PeopleInfoListAdapter;
import com.example.sheng.carpool.R;
import com.example.sheng.carpool.helpers.JsonOperation;
import com.example.sheng.carpool.helpers.LogOut;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Search_case extends Activity {

    private RequestQueue mRequestQueue;
    private TextView case_public_name;
    private TextView case_people;
    private TextView case_good_show;
    private TextView case_bad_show;
    private TextView case_credit_show;
    private TextView case_start;
    private TextView case_time;
    private TextView case_end;
    private TextView case_day;
    private TextView case_phone;
    private TextView case_pay;
    private TextView case_show;
    private Button case_massage;
    private Button case_others;
    private Button case_send_massage;
    private Button case_join_in;
    //ListView用的
    //
    private PeopleInfoListAdapter peopleInfoListAdapter;
    private List<MyInfo>  peopleInfoArrayList = new ArrayList<>();
    private MyInfo[] myInfos;
    //
    private CommentInfoListAdapter commentInfoListAdapter;
    private List<CommentInfo> commentInfoArrayList = new ArrayList<>();
    private CommentInfo[]commentInfos;
    //
    private CommentAddListAdapter commentAddListAdapter;
    private List<CommentAdd> commentAddArrayList = new ArrayList<>();
    private CommentAdd[] commentAdds;
    //
    private AddInfoListAdapter addInfoListAdapter;
    private List<AddInfo> addInfoArrayList = new ArrayList<>();
    private AddInfo[] addInfos;

    //SharedPreferences存储
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private String account = "";
    private String carpoolID ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_case);
        final Intent intent = getIntent();
        String data = intent.getStringExtra("carpool");
        CarpoolInfo carpoolInfo = null;
        if(!data.equals("")){
            carpoolInfo = AnalyseJson.getInstance(data,CarpoolInfo.class);
            carpoolID = ""+ carpoolInfo.getCARPOOLID();
        }
        componentInit();
        if(!data.equals("")){
            setValue(carpoolInfo);
        }
        getAccount();
        //ListView中的内容
        //其他拼车成员
        peopleInfoListAdapter = new PeopleInfoListAdapter(getApplicationContext(),
                R.layout.case_in,peopleInfoArrayList);
        ListView listView = (ListView)findViewById(R.id.case_in_listview);
        listView.setAdapter(peopleInfoListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MyInfo myInfo = peopleInfoArrayList.get(i);
                String account = myInfo.getAccount();
                Intent intent = new Intent();
                intent.setClass(Search_case.this,SeeInfo.class);
                intent.putExtra("account",account);
                startActivity(intent);
            }
        });
        //留言
        commentInfoListAdapter = new CommentInfoListAdapter(getApplicationContext(),
                R.layout.case_in,commentInfoArrayList);
        ListView commentListView = (ListView)findViewById(R.id.case_have_massage);
        commentListView.setAdapter(commentInfoListAdapter);
        commentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CommentInfo commentInfo = commentInfoArrayList.get(i);
                String account = ""+commentInfo.getAccount();
                Intent intent = new Intent();
                intent.setClass(Search_case.this,SeeInfo.class);
                intent.putExtra("account",account);
                startActivity(intent);
            }
        });
    /*
        commentAddListAdapter = new CommentAddListAdapter(getApplicationContext(),
                R.layout.case_in,commentAddArrayList);
        ListView listView = (ListView)findViewById(R.id.case_in_listview);
        listView.setAdapter(commentAddListAdapter);
        addInfoListAdapter = new AddInfoListAdapter(getApplicationContext(),
                R.layout.case_in,addInfoArrayList);
        ListView commentListView = (ListView)findViewById(R.id.case_have_massage);
        commentListView.setAdapter(addInfoListAdapter);
*/
    }

    //获取账号
    private void getAccount(){
        //之前有登陆，直接填写数据
        pref = getSharedPreferences("data",MODE_PRIVATE);
        String a = pref.getString("account","");
        String b = pref.getString("password","");
        if(!a.equals("")){
            account = a;
        }
    }
    private void add(){
        getAccount();
        final String url= PublicData.addServer;
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals(PublicData.TRUE_RETURN)){
                    Toast.makeText(Search_case.this,"加入成功",Toast.LENGTH_SHORT).show();
                }
             //   Toast.makeText(Search_case.this,response,Toast.LENGTH_SHORT).show();
                Log.d("TAG", response);
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Search_case.this,"没网络",Toast.LENGTH_LONG).show();
                Log.e("TAG", error.getMessage(), error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("type","add");
                map.put("account",account);
                map.put("carpoolID",carpoolID);
                return map;
            }
        };
        mRequestQueue.add(stringRequest);
    }

    private void otherMember(){
        final String url= PublicData.otherAccountServer;
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                LogOut.printLog(getLocalClassName()+":"+response);
                PublicData.returnToast(getApplicationContext(),response);
               // Toast.makeText(Search_case.this,response,Toast.LENGTH_SHORT).show();
               // if(!response.equals(PublicData.FALSE_RETURN)){
                if(PublicData.returnFalse(response)){
                    if(peopleInfoListAdapter.getCount()!=0){
                        peopleInfoListAdapter.clear();
                        peopleInfoListAdapter.notifyDataSetChanged();
                    }
                    else {
                        //initPeopleInfo();
                        getMyInfoList(response);
                        peopleInfoListAdapter.notifyDataSetChanged();
                    }
                }
                else {
                //    Toast.makeText(getApplicationContext(),"没有人加入",Toast.LENGTH_SHORT).show();
                }
                Log.d("TAG", response);
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Search_case.this,"没网络",Toast.LENGTH_LONG).show();
                Log.e("TAG", error.getMessage(), error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //用HashMap来存储请求参数
                Map<String,String> map = new HashMap<String,String>();
                map.put("type","otherAddMember");
                map.put("carpoolID",carpoolID);
                return map;
            }
        };
        mRequestQueue.add(stringRequest);
    }

    private void commentMember(){
        final String url= PublicData.commentMemberServer;
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                LogOut.printLog(getLocalClassName()+":"+response);
                PublicData.returnToast(getApplicationContext(),response);
                if(PublicData.returnFalse(response)){
                    Log.d("response",response);
                    if(commentInfoListAdapter.getCount()!=0){
                        commentInfoListAdapter.clear();
                        commentInfoListAdapter.notifyDataSetChanged();
                    }
                    else {
                        //initCommentInfo();
                        getCommentInfoList(response);
                        commentInfoListAdapter.notifyDataSetChanged();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),"没有人留言",Toast.LENGTH_SHORT).show();
                }
                Log.d("TAG", response);

            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Search_case.this,"没网络",Toast.LENGTH_LONG).show();
                Log.e("TAG", error.getMessage(), error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //用HashMap来存储请求参数
                Map<String,String> map = new HashMap<String,String>();
                map.put("type","commentMember");
                map.put("carpoolID",carpoolID);
                return map;
            }
        };
        mRequestQueue.add(stringRequest);
    }

    private void initCommentAdd(){
        CommentAdd commentAdd1 = new CommentAdd("account1","name1","detail1");
        commentAddListAdapter.add(commentAdd1);
        CommentAdd commentAdd2 = new CommentAdd("account2","name2","detail2");
        commentAddListAdapter.add(commentAdd2);
    }
    private void initAddInfo(){
        AddInfo addInfo1 = new AddInfo("account1","name1","phone1");
        addInfoListAdapter.add(addInfo1);
        AddInfo addInfo2 = new AddInfo("account1","name1","phone1");
        addInfoListAdapter.add(addInfo2);
    }

    private void initPeopleInfo(){
        MyInfo myInfo1 = new MyInfo("account","password", "headPortrait", "name",
                "nickname", "sex", "phone", "wechat","qq", 5,3,"introduce","addID","commentID");
        peopleInfoArrayList.add(myInfo1);
        MyInfo myInfo2 = new MyInfo("account","password", "headPortrait", "name",
                "nickname", "sex", "phone", "wechat","qq", 5,3,"introduce","addID","commentID");
        peopleInfoArrayList.add(myInfo2);
    }
    private void initCommentInfo(){
        CommentInfo commentInfo1 = new CommentInfo(""+1,"zhou",11,111,"zhouzhouzhou");
        commentInfoListAdapter.add(commentInfo1);
        CommentInfo commentInfo2 = new CommentInfo(""+2,"rui",22,222,"ruiruiruirui");
        commentInfoListAdapter.add(commentInfo2);
    }
    //
    private void getCommentInfoList(String response){
        List<CommentInfo> list = new ArrayList<CommentInfo>();
        list = AnalyseJson.getCommentInfoList(response);
        if(list!=null&&!list.isEmpty()){
            for(int i=0;i<list.size();i++){
                commentInfoListAdapter.add(list.get(i));
            }
        }
    }
    private void getMyInfoList(String response){
        List<MyInfo> list = new ArrayList<MyInfo>();
        list = AnalyseJson.getMyInfoList(response);
        if(list!=null&&!list.isEmpty()){
            for(int i=0;i<list.size();i++){
                peopleInfoArrayList.add(list.get(i));
            }
        }
    }

    private void setValue(CarpoolInfo carpoolInfo){
        case_public_name.setText(carpoolInfo.getName());
        case_people.setText(carpoolInfo.getHaveNum()+"/"+carpoolInfo.getHaveNum());
        //case_good_show.setText(carpoolInfo.ge);
       // case_bad_show;
        //case_credit_show;
        case_start.setText(carpoolInfo.getDeparture());
        case_time.setText(carpoolInfo.getDepartureTime());
        case_end.setText(carpoolInfo.getDestination());
        case_day.setText(carpoolInfo.getDate());
        case_phone.setText(carpoolInfo.getPhoneNum());
        case_pay.setText(""+carpoolInfo.getPrice());
        case_show.setText(carpoolInfo.getDetail());
    }
    private void componentInit(){
        case_public_name = (TextView) findViewById(R.id.case_public_name);
        case_people = (TextView) findViewById(R.id.case_people);
        case_good_show = (TextView)findViewById(R.id.case_good_show);
        case_bad_show = (TextView)findViewById(R.id.case_bad_show);
        case_credit_show = (TextView)findViewById(R.id.case_credit_show);
        case_start = (TextView)findViewById(R.id.case_start);
        case_time = (TextView)findViewById(R.id.case_time);
        case_end = (TextView)findViewById(R.id.case_end);
        case_day = (TextView)findViewById(R.id.case_day);
        case_phone = (TextView)findViewById(R.id.case_phone);
        case_pay = (TextView)findViewById(R.id.case_pay);
        case_show = (TextView)findViewById(R.id.case_show);
        case_send_massage = (Button)findViewById(R.id.case_send_massage);
        case_send_massage.setOnClickListener(new buttonListener());
        case_join_in = (Button)findViewById(R.id.case_join_in);
        case_join_in.setOnClickListener(new buttonListener());
        case_others = (Button)findViewById(R.id.case_others);
        case_others.setOnClickListener(new buttonListener());
        case_massage = (Button)findViewById(R.id.case_massage);
        case_massage.setOnClickListener(new buttonListener());
    }
    private void dialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Search_case.this);
        builder.setMessage("请及时联系发布人,确认是否成功加入！");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                //加入处理。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。
                add();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    class buttonListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.case_send_massage:
                    if(!account.equals("")){
                        Intent intent = new Intent();
                        intent.setClass(Search_case.this,Message.class);
                        intent.putExtra("account",account);
                        intent.putExtra("carpoolID",carpoolID);
                        Search_case.this.startActivity(intent);
                        //finish();
                    }
                    else {
                        PublicData.login(Search_case.this);
                    }
                    break;
                case R.id.case_join_in:
                    if(!account.equals("")){
                        dialog();
                    }
                    else {
                        PublicData.login(Search_case.this);
                    }
                    break;
                //
                case R.id.case_others:
                    otherMember();
                    /*
                    if(peopleInfoListAdapter.getCount()!=0){
                        peopleInfoListAdapter.clear();
                        peopleInfoListAdapter.notifyDataSetChanged();
                    }
                    else {
                        initPeopleInfo();
                        peopleInfoListAdapter.notifyDataSetChanged();
                    }
                    */
                    break;
                case R.id.case_massage:
                    Log.d("message","点击");
                    if(!account.equals("")){
                        commentMember();
                    }
                    else {
                        PublicData.login(Search_case.this);
                    }

                    /*
                    if(commentInfoListAdapter.getCount()!=0){
                        commentInfoListAdapter.clear();
                        commentInfoListAdapter.notifyDataSetChanged();
                    }
                    else {
                        initCommentInfo();
                        commentInfoListAdapter.notifyDataSetChanged();
                    }
                    */
                    break;
                default:
                    break;
            }
        }
    }

}
