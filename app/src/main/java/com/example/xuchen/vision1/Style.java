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
import android.widget.Button;
import android.widget.EditText;

public class Style extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_style);


    }

    public void onDialogClick1(View v){
        new AlertDialog.Builder(Style.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("注意")
                .setMessage("确定要更改风格吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences.Editor editor =getSharedPreferences("data",MODE_PRIVATE).edit();
                        editor.putString("style","style1");
                        editor.apply();
                        Intent intent1 = new Intent(Style.this,model_show.class);
                        startActivity(intent1);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create().show();
    }
    public void onDialogClick2(View v){
        new AlertDialog.Builder(Style.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("注意")
                .setMessage("确定要更改风格吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences.Editor editor =getSharedPreferences("data",MODE_PRIVATE).edit();
                        editor.putString("style","style2");
                        editor.apply();
                        Intent intent1 = new Intent(Style.this,model_show.class);
                        startActivity(intent1);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create().show();
    }

}
