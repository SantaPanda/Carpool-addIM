package com.example.sheng.carpool.activities;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sheng.carpool.Data.PublicData;
import com.example.sheng.carpool.Data.StatusCode;
import com.example.sheng.carpool.R;
import com.example.sheng.carpool.helpers.LogOut;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class MainFragment extends Fragment {

    private View view;
    private RequestQueue mRequestQueue;
    private EditText main_start_input;
    private EditText main_end_input;
    private EditText main_day_input;
    private Button main_sure;
    private String str_main_start_input, str_main_end_input, str_main_day_input;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        componentInit();
        return view;
    }

    private void componentInit(){
        main_start_input = (EditText)view.findViewById(R.id.main_start_input);
        main_end_input = (EditText)view.findViewById(R.id.main_end_input);
        main_day_input = (EditText)view.findViewById(R.id.main_day_input);
        main_day_input.setInputType(InputType.TYPE_NULL);//不显示输入键盘
        main_day_input.clearFocus();
        main_day_input.setOnFocusChangeListener(new textListener());
        main_sure = (Button)view.findViewById(R.id.main_sure);
        main_sure.setOnClickListener(new buttonListener());
    }
    private void getValue(){
        str_main_start_input =  main_start_input.getText().toString();
        str_main_end_input = main_end_input.getText().toString();
        str_main_day_input = main_day_input.getText().toString();
    }
    private boolean FillIn(){
        if(!str_main_start_input.equals("")&&!str_main_end_input.equals("")&&
                !str_main_day_input.equals("")){
            return true;
        }
        return false;
    }
    private void searchServer(){
        final String url= PublicData.searchServer;
        mRequestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                LogOut.printLog("MainFragment:"+response);
                PublicData.returnToast(getContext(),response);
                if(PublicData.returnFalse(response)){
                    LogOut.printLog("MainFragment2:"+response);
                    Intent intent = new Intent();
                    intent.putExtra("response", ""+response);
                    intent.setClass(getContext(),Search.class);
                    getContext().startActivity(intent);
                }
                else {
                    LogOut.printLog("MainFragment3:"+response);
                  //  Toast.makeText(getContext(),"查找不到数据，请重新输入有效地点和时间",Toast.LENGTH_SHORT).show();
                }
                Log.d("TAG", response);
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),"请连接网络使用",Toast.LENGTH_SHORT).show();
                Log.e("TAG", error.getMessage(), error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("type","search");
                map.put("main_start_input",str_main_start_input);
                map.put("main_end_input",str_main_end_input);
                map.put("main_day_input",str_main_day_input);
                return map;
            }
        };
        mRequestQueue.add(stringRequest);
    }

    class buttonListener implements View.OnClickListener {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.main_sure:
                    getValue();
                    if(FillIn()){
                        searchServer();
                    }
            }
        }
    }

    class textListener implements  View.OnFocusChangeListener {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                switch (v.getId()) {
                    case R.id.main_day_input:
                        Calendar c = Calendar.getInstance();
                        new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // TODO Auto-generated method stub
                                main_day_input.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
                            }
                        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
                        main_start_input.requestFocus();
                        break;
                    default:
                        break;
                }//switch
            }//if
        }
    }
}
