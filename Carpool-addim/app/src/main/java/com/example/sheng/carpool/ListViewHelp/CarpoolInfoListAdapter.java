package com.example.sheng.carpool.ListViewHelp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.sheng.carpool.Dao.CarpoolInfo;
import com.example.sheng.carpool.R;

import java.util.List;

/**
 * Created by sheng on 16-11-29.
 */
public class CarpoolInfoListAdapter extends ArrayAdapter<CarpoolInfo> {
    private int resourceId;
    private Context context;
    private TextView search_result_name;
    private TextView search_result_day;
    private TextView search_result_per;
    private TextView search_start;
    private TextView search_time;
    private TextView search_end;
    private TextView search_pay_show;
    private TextView search_link_show;
    private String str_search_result_name,str_search_result_day,str_search_result_per;
    private String str_search_start,str_search_time,str_search_end,str_search_pay_show,str_search_link_show;
    public CarpoolInfoListAdapter(Context context, int textViewRescourceId,
                                  List<CarpoolInfo> objects){
        super(context,textViewRescourceId,objects);
        resourceId = textViewRescourceId;
        this.context=context;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        CarpoolInfo carpoolInfo = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,null);
        componentInit(view);

        search_result_name.setText(carpoolInfo.getName());
        search_result_day.setText(carpoolInfo.getDate());
        search_result_per.setText(carpoolInfo.getHaveNum()+"/"+carpoolInfo.getTotalNum());
        search_start.setText(carpoolInfo.getDeparture());
        search_time.setText(carpoolInfo.getDepartureTime());
        search_end.setText(carpoolInfo.getDestination());
        search_pay_show.setText(""+carpoolInfo.getPrice());
        search_link_show.setText(carpoolInfo.getPhoneNum());

        return view;
    }
    private void componentInit(View view){
        search_result_name = (TextView) view.findViewById(R.id.search_result_name);
        search_result_day = (TextView) view.findViewById(R.id.search_result_day);
        search_result_per = (TextView) view.findViewById(R.id.search_result_per);
        search_start = (TextView) view.findViewById(R.id.search_start);
        search_time = (TextView) view.findViewById(R.id.search_time);
        search_end = (TextView) view.findViewById(R.id.search_end);
        search_pay_show = (TextView) view.findViewById(R.id.search_pay_show);
        search_link_show = (TextView) view.findViewById(R.id.search_link_show);
    }
    private void getVaule(){
        str_search_result_name = search_result_name.getText().toString();
        str_search_result_day = search_result_day.getText().toString();
        str_search_result_per = search_result_per.getText().toString();
        str_search_start = search_start.getText().toString();
        str_search_time = search_time.getText().toString();
        str_search_end = search_end.getText().toString();
        str_search_pay_show = search_pay_show.getText().toString();
        str_search_link_show = search_link_show.getText().toString();
    }
    class buttonListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
            }
        }
    }
}
