package com.example.sheng.carpool.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sheng.carpool.Dao.CarpoolInfo;
import com.example.sheng.carpool.Data.AnalyseJson;
import com.example.sheng.carpool.ListViewHelp.CarpoolInfoListAdapter;
import com.example.sheng.carpool.R;
import com.example.sheng.carpool.helpers.JsonOperation;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Search extends Activity {

    //ListView用的
    private CarpoolInfoListAdapter carpoolInfoListAdapter;
    private List<CarpoolInfo> carpoolInfoArrayList = new ArrayList<>();
    private CarpoolInfo [] carpoolInfos ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Intent intent = getIntent();
        String response = intent.getStringExtra("response");

        //ListView中的内容
        getCarpoolList(response);
      //  initCarpoolInfoList();
        carpoolInfoListAdapter = new CarpoolInfoListAdapter(getApplicationContext(),
                R.layout.search_result,carpoolInfoArrayList);
        ListView listView = (ListView)findViewById(R.id.search_listView);
        listView.setAdapter(carpoolInfoListAdapter);

        /**
         * listView 点击事件
         */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CarpoolInfo carpoolInfo = carpoolInfoArrayList.get(i);
               // Toast.makeText(getApplicationContext(),""+carpoolInfo.getAccountID(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                //String carpool = JsonOperation.jsonObjectStructure(carpoolInfo);
                String carpool = new Gson().toJson(carpoolInfo);
                //Toast.makeText(getApplicationContext(),carpool,Toast.LENGTH_SHORT).show();
                intent.putExtra("carpool",carpool);
                intent.setClass(Search.this,Search_case.class);
                Search.this.startActivity(intent);
               // finish();
            }
        });
    }

    private void getCarpoolList(String response){
        List<CarpoolInfo> list = new ArrayList<CarpoolInfo>();
        list = AnalyseJson.getCarpoolList(response);
        if(list!=null&&!list.isEmpty()){
            for(int i=0;i<list.size();i++){
                carpoolInfoArrayList.add(list.get(i));
            }
        }

    }
    private void initCarpoolInfoList(){
        CarpoolInfo carpoolInfo1 = new CarpoolInfo("accountID1",1,"name1","date1","departure1",
                "destination1", "Time1", 111,14,1,"phoneNum1","detail1","addID1",
                "commentID1");
        carpoolInfoArrayList.add(carpoolInfo1);
        CarpoolInfo carpoolInfo2 = new CarpoolInfo("accountID2",2,"name2","date2","departure2",
                "destination2", "Time2", 222,24,2,"phoneNum2","detail2","addID2",
                "commentID");
        carpoolInfoArrayList.add(carpoolInfo2);
    }

}
