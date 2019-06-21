package com.example.xuchen.vision1;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class Password extends AppCompatActivity {

    private user_database dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        dbHelper = new user_database(this,"user_base.db",null,1);
    }

    private String getUserName(){
        SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
        String username2 =pref.getString("user_name","");
        return username2;
    }

    public void onDialogClick(View v){
        new AlertDialog.Builder(Password.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("注意")
                .setMessage("确定要更改密码吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText editText1 = findViewById(R.id.message1);
                        String password2 = editText1.getText().toString();
                        String name1= getUserName();


                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        Cursor cursor = db.query("user_data",null,null,null,null,null,null);
                        if(cursor.moveToFirst()){
                            do{
                                String name_query = cursor.getString(cursor.getColumnIndex("username"));
                                if( name_query.equals(name1) )
                                {
                                    ContentValues values = new ContentValues();
                                    values.put("password",password2);
                                    db.update("user_data",values,"username = ?",new String[]{name1});
                                    String name_1 = cursor.getString(cursor.getColumnIndex("password"));
                                    Log.d("UserName","good job:"+ name_1);
                                    Intent intent1 = new Intent(Password.this,Setting.class);
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
