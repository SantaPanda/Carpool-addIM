package com.example.sheng.carpool.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
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
import com.example.sheng.carpool.ListViewHelp.PeopleInfoListAdapter;
import com.example.sheng.carpool.R;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Register extends Activity {

    private EditText register_num_input;
    private EditText register_password_input;
    private EditText register_again_password_input;
    private EditText register_phone_input;
    private Button register_sure;
    private String str_register_num_input, str_register_password_input;
    private String str_register_again_password_input , str_register_phone_input;
    private RequestQueue mRequestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        componentInit();
    }
    //组件初始化，为组件监听
    private void componentInit(){
        register_num_input = (EditText)findViewById(R.id.register_num_input);
        register_password_input = (EditText)findViewById(R.id.register_password_input);
        register_again_password_input = (EditText)findViewById(R.id.register_again_password_input);
        register_phone_input = (EditText)findViewById(R.id.register_phone_input);
        register_sure = (Button)findViewById(R.id.register_sure);
        register_sure.setOnClickListener(new buttonListener1());
    }
    private void getValue(){
        str_register_num_input=register_num_input.getText().toString();
        str_register_password_input=register_password_input.getText().toString();
        str_register_again_password_input=register_again_password_input.getText().toString();
        str_register_phone_input=register_phone_input.getText().toString();
    }

    private boolean FillIn(){
        if(!str_register_num_input.equals("")&&!str_register_phone_input.equals("")&&
                !str_register_again_password_input.equals("")&&
                !str_register_password_input.equals("")){
            return true;
        }
        return false;
    }
    //
    private void register(){
        getValue();
        final String url= PublicData.registerServer;
        //final String url = PublicData.loginServer;
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals(PublicData.TRUE_RETURN)){
                    finish();
                }
       //         Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                Log.d("TAG", response);
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"请链接网络使用",Toast.LENGTH_LONG);
                Log.e("TAG", error.getMessage(), error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("type","register");
                map.put("account",str_register_num_input);
                map.put("password",str_register_password_input);
                map.put("phoneNum",str_register_phone_input);
                return map;
            }
        };
        mRequestQueue.add(stringRequest);
    }


    class buttonListener1 implements View.OnClickListener {
        public void onClick(View v){
            switch (v.getId()){
                case R.id.register_sure:
                    getValue();
                    if(FillIn()){
                        if(str_register_password_input.equals(str_register_again_password_input)){
                            register();
                        }
                        else {
                            Toast.makeText(Register.this,"两次输入的密码不一样",Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
