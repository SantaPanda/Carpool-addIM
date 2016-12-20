package com.example.sheng.carpool.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.example.sheng.carpool.IM.App;
import com.example.sheng.carpool.R;

import io.rong.imkit.fragment.ConversationListFragment;

public class MainActivity extends AppCompatActivity {

    // 定义FragmentTabHost对象
    private FragmentTabHost mTabHost;
    //
    private RequestQueue mRequestQueue;
    // 定义一个布局
    private LayoutInflater layoutInflater;

    // 定义数组来存放Fragment界面
    private Class fragmentArray[] = { MainFragment.class,
            PublishFragment.class, MyHaveFragment.class, MyInfoFragment.class};

    // 定义数组来存放按钮图片
    private int mImageViewArray[] = { R.drawable.main_tab_item_home,
            R.drawable.main_tab_item_category, R.drawable.main_tab_item_down,
            R.drawable.main_tab_item_user};

    // Tab选项卡的文字
    private String mTextviewArray[] = { "搜索", "发布", "我的", "个人"};

    //token
    private String mytoken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
        mytoken = pref.getString("token", "");
        if(!mytoken.isEmpty()){
            App app = new App();
            app.connect(mytoken);
        }
        initView();
    }

    //创建选项菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=new MenuInflater(this);     //实例化一个MenuInflater对象
        inflater.inflate(R.menu.main_page_optionmenu, menu);      //解析菜单文件
        //getMenuInflater().inflate(R.menu.main_page_optionmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.myconversation){
            startActivity(new Intent(this, MyConversationActivity.class));
        }
        return true;
    }

    /**
     * 初始化组件
     */
    private void initView() {
        // 实例化布局对象
        layoutInflater = LayoutInflater.from(this);

        // 实例化TabHost对象，得到TabHost
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        // 得到fragment的个数
        int count = fragmentArray.length;

        for (int i = 0; i < count; i++) {
            // 为每一个Tab按钮设置图标、文字和内容
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(mTextviewArray[i])
                    .setIndicator(getTabItemView(i));
            // 将Tab按钮添加进Tab选项卡中
            mTabHost.addTab(tabSpec, fragmentArray[i], null);
            // 设置Tab按钮的背景
            mTabHost.getTabWidget().getChildAt(i)
                    .setBackgroundResource(R.drawable.main_tab_item_bg);
        }
    }

    /**
     * 给Tab按钮设置图标和文字
     */
    private View getTabItemView(int index) {
        View view = layoutInflater.inflate(R.layout.tab_item_view, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
        imageView.setImageResource(mImageViewArray[index]);

        TextView textView = (TextView) view.findViewById(R.id.textview);
        textView.setText(mTextviewArray[index]);

        return view;
    }

}
