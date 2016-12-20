package com.example.sheng.carpool.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
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

public class Message extends Activity {

    private RequestQueue mRequestQueue;
    private EditText message_input;
    private Button message_send;
    private String str_message_input;
    private String account, carpoolID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        Intent intent = getIntent();
        account = intent.getStringExtra("account");
        carpoolID = intent.getStringExtra("carpoolID");
        componentInit();
    }
    private void componentInit(){
        message_input = (EditText)findViewById(R.id.message_input);
        message_send = (Button)findViewById(R.id.message_send);
        message_send.setOnClickListener(new buttonListener());
    }

    private void message(){
        str_message_input = message_input.getText().toString();
        final String url= PublicData.messageServer;
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
           //     Toast.makeText(Message.this,response,Toast.LENGTH_SHORT).show();
                if(response.equals(PublicData.TRUE_RETURN)){
                    finish();
                }
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Message.this,PublicData.NO_NETWORK,Toast.LENGTH_LONG).show();
                Log.e("TAG", error.getMessage(), error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("type","message");
                map.put("message",str_message_input);
                map.put("account",account);
                map.put("carpoolID",carpoolID);
                return map;
            }
        };
        mRequestQueue.add(stringRequest);
    }
    class buttonListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            str_message_input = message_input.getText().toString();
            message();
        }
    }
}
