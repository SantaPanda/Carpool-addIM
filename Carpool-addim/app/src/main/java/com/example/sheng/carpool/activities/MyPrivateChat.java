package com.example.sheng.carpool.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import io.rong.imkit.RongIM;

/**
 * Created by Santa on 16/12/20.
 */

public class MyPrivateChat extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String userid = intent.getStringExtra("userid");
        Log.d("test1", userid);
        RongIM.getInstance().startPrivateChat(MyPrivateChat.this, userid, "No title");
    }

    @Override
    public void onStop(){
        super.onStop();
        finish();
    }
}
