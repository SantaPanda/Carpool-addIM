package com.example.sheng.carpool.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sheng.carpool.Data.PublicData;
import com.example.sheng.carpool.R;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Forget extends Activity {

    private RequestQueue mRequestQueue;
    private EditText forget_num_input;
    private EditText forget_new_password_input;
    private EditText forget_password_again_input;
    private Button forget_sure;
    private String str_forget_num_input,str_forget_new_password_input,str_forget_password_again_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        componentInit();

    }
    private void componentInit(){
        forget_num_input = (EditText)findViewById(R.id.forget_num_input);
        forget_new_password_input = (EditText)findViewById(R.id.forget_new_password_input);
        forget_password_again_input = (EditText)findViewById(R.id.forget_password_again_input);
        forget_sure = (Button) findViewById(R.id.forget_sure);
        forget_sure.setOnClickListener(new buttonListener());
    }
    private void getValue(){
        str_forget_num_input = forget_num_input.getText().toString();
        str_forget_new_password_input = forget_new_password_input.getText().toString();
        str_forget_password_again_input = forget_password_again_input.getText().toString();

    }

    private void forget(){
        getValue();
        final String url= PublicData.forgetServer;
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                /*
                if(response.equals(PublicData.TRUE_RETURN)){
                    finish();
                }
                */
                Toast.makeText(Forget.this,response,Toast.LENGTH_SHORT).show();
                Log.d("TAG", response);
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Forget.this,"没网络",Toast.LENGTH_LONG).show();
                Log.e("TAG", error.getMessage(), error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //用HashMap来存储请求参数
                Map<String,String> map = new HashMap<String,String>();
                map.put("type","forget");
                map.put("account",str_forget_num_input);
                map.put("password",str_forget_new_password_input);
                return map;
            }
        };
        mRequestQueue.add(stringRequest);
    }
    /*
    //确保填写所有
    private boolean fillIn(){
        if()
        return true;
    }
    */
    class buttonListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.forget_sure:
                    getValue();
                    forget();
                    break;
                default:
                    break;
            }
        }
    }
}
