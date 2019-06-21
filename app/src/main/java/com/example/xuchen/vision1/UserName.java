package com.example.xuchen.vision1;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserName extends AppCompatActivity {

    private user_database dbHelper;
    private Button button1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_name);
        dbHelper = new user_database(this,"user_base.db",null,1);

        button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }

    private String getUserName(){
        SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
        String username2 =pref.getString("user_name","");
        return username2;
    }

    public void onDialogClick(View v){
        new AlertDialog.Builder(UserName.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("注意")
                .setMessage("确定要更改用户名吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText editText1 = findViewById(R.id.message1);
                        String name2 = editText1.getText().toString();
                        String name1= getUserName();


                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        Cursor cursor = db.query("user_data",null,null,null,null,null,null);
                        if(cursor.moveToFirst()){
                            do{
                                String name_query = cursor.getString(cursor.getColumnIndex("username"));
                                if( name_query.equals(name1) )
                                {
                                    ContentValues values = new ContentValues();
                                    values.put("name",name2);
                                    db.update("user_data",values,"username = ?",new String[]{name1});
                                    String name_1 = cursor.getString(cursor.getColumnIndex("name"));
                                    Log.d("UserName","good job:"+ name_1);
                                    Intent intent1 = new Intent(UserName.this,Setting.class);
                                    startActivity(intent1);
                                }
                            }while(cursor.moveToNext());
                        }

                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create().show();
    }
}
