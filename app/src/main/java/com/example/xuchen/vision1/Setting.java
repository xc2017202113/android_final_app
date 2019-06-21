package com.example.xuchen.vision1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Setting extends AppCompatActivity {

    private  Button btn_help;
    private  Button btn_person;
    private  Button btn_style;
    private Button btn_name;
    private Button btn_pwd;
    private user_database dbHelper;
    private static final String TAG = "Setting";

    private String getUserName(){
        SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
        String username2 =pref.getString("user_name","");
        Log.d(TAG,"good:"+username2);
        return username2;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);


        //帮助界面  信息
        btn_help = findViewById(R.id.btn_help);
        btn_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_help = new Intent(Setting.this,Help_information.class);
                startActivity(intent_help);
            }
        });

        //设置名字 读取数据库里的名字
        btn_name=findViewById(R.id.button_username);
        String name1= getUserName();

        dbHelper = new user_database(this,"user_base.db",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("user_data",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                String name_query = cursor.getString(cursor.getColumnIndex("username"));
                String name2_query = cursor.getString(cursor.getColumnIndex("name"));
                if( name_query.equals(name1) )
                {
                    btn_name.setText(name2_query);
                    // Log.d(TAG,"suceess!");
                }
            }while(cursor.moveToNext());
        }

        btn_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_name= new Intent(Setting.this,UserName.class);
                startActivity(intent_name);
            }
        });
        btn_pwd=findViewById(R.id.btn_pwd);
        btn_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_name= new Intent(Setting.this,Password.class);
                startActivity(intent_name);
            }
        });

        //个人尺寸
        btn_person = findViewById(R.id.btn_person);
        btn_person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_person = new Intent(Setting.this,Personal.class);
                startActivity(intent_person);
            }
        });


        //个人风格
        btn_style = findViewById(R.id.btn_style);
        btn_style.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_style = new Intent(Setting.this,Style.class);
                startActivity(intent_style);
            }
        });






    }
}
