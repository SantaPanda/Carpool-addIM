package com.example.sheng.carpool.activities;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.sheng.carpool.IM.App;
import com.example.sheng.carpool.R;

import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;

/**
 * Created by Santa on 16/12/20.
 */

public class MyConversationActivity extends FragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myconversation);
//        App app = new App();
//        app.connect("6naneFkCutuzp8Pxdse9N3lGw/5e1XlPkz5ZTNPegzPgRowQiGc+8JZ/caDQRRqkpfPe51lZpCd+bb/CJR32jQ==");
        Fragment mylistFragment = getRealContent();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.change_content, mylistFragment);
        transaction.commit();
    }

    public Fragment getRealContent(){
        ConversationListFragment listFragment = new ConversationListFragment();
        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationList")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false")//设置私聊会话是否聚合显示
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "true")
                .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")//设置私聊会话是否聚合显示
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")//设置私聊会是否聚合显示
                .build();
        listFragment.setUri(uri);
        return listFragment;
    }
}
