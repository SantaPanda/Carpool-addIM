package com.example.sheng.carpool.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.sheng.carpool.R;

/**
 * Created by Santa on 16/12/20.
 */

public class ConversationActivity extends FragmentActivity {

    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.conversation);
    }
}
