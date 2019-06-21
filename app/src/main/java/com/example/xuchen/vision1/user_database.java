package com.example.xuchen.vision1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class user_database extends SQLiteOpenHelper {

    public static final String CREATE_USER = "create table user_data ("
            + "username text primary key,"
            +"password text,"
            +"name text,"
            +"address text)";

    private Context mContext;

    public user_database(Context context,String name,SQLiteDatabase.CursorFactory factory,int version){
        super(context,name,factory,version);
        mContext = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER);
        Toast.makeText(mContext,"登录成功！！",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
