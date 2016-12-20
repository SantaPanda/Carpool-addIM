package com.example.sheng.carpool.ListViewHelp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.sheng.carpool.Dao.CarpoolInfo;
import com.example.sheng.carpool.Dao.CommentInfo;
import com.example.sheng.carpool.Dao.MyInfo;
import com.example.sheng.carpool.R;
import com.example.sheng.carpool.activities.ConversationActivity;
import com.example.sheng.carpool.activities.MyHaveFragment;
import com.example.sheng.carpool.activities.MyPrivateChat;

import java.util.List;

import io.rong.imkit.RongIM;

/**
 * Created by sheng on 16-12-7.
 */
public class CommentInfoListAdapter extends ArrayAdapter<CommentInfo> {
    private int resourceId;
    private Context context;
    private TextView case_in_name;
    private TextView case_in_phone;
    private Button case_in_button;
    private String str_case_in_name,str_case_in_phone;

    public CommentInfoListAdapter(Context context,int textViewRescourceId,
                                  List<CommentInfo> objects){
        super(context,textViewRescourceId,objects);
        resourceId = textViewRescourceId;
        this.context=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        final CommentInfo commentInfo = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,null);
        componentInit(view);
        case_in_name.setText(commentInfo.getName());
        case_in_phone.setText(commentInfo.getDetail());
        case_in_button.setTag(position);
        case_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MyPrivateChat.class);
                intent.putExtra("userid", commentInfo.getAccount());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        return view;
    }
    private void componentInit(View view){
        case_in_name = (TextView)view.findViewById(R.id.case_in_name);
        case_in_phone = (TextView)view.findViewById(R.id.case_in_phone);
        case_in_button = (Button)view.findViewById(R.id.begin_private_chat);
    }
    private void getVaule(){
        str_case_in_name = case_in_name.getText().toString();
        str_case_in_phone = case_in_phone.getText().toString();
    }
}
