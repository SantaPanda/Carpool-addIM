package com.example.sheng.carpool.localDatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by sheng on 2016/11/19.
 */
public class CreatDataBase extends SQLiteOpenHelper {

    public static final String MY_INFO = "create table myinfo (" +
            "id integer primary key autoincrement, " +
            "account text, " +
            "password text, " +
            "headPortrait text, " +
            "name text, " +
            "nickname text, " +
            "sex text, "+
            "phone text, " +
            "wechat text, " +
            "qq text, " +
            "good integer, " +
            "bad integer, " +
            "introduce text, " +
            "addID text, " +
            "commentID text)";
    public static final String CARPOOL_INFO = "create table carpool (" +
            "id integer primary key autoincrement, " +
            "account text, " +
            "date text, " +
            "departure text, " +
            "destination text, " +
            "departureTime text, " +
            "arriveTime text, "+
            "lackNum integer, " +
            "detail text, " +
            "phoneNum text, " +
            "issueTime text, " +
            "likeNum integer, " +
            "addID text, " +
            "commentID text)";

    private Context mContext;
    public CreatDataBase(Context context, String name, SQLiteDatabase.
            CursorFactory factory, int version){
        super(context,name,factory,version);
        mContext = context;

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CARPOOL_INFO);
        db.execSQL(MY_INFO);
        Toast.makeText(mContext,"数据库创建成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
