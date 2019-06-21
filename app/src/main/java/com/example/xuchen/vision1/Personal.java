package com.example.xuchen.vision1;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Personal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
    }
    //更改第一组数据
    public void onFirstSize(View v){
        final EditText editText_h1 =findViewById(R.id.message1);
        final EditText editText_w1 =findViewById(R.id.message2);
        final EditText editText_wa1 = findViewById(R.id.message3);

        new AlertDialog.Builder(Personal.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("注意")
                .setMessage("确定要更改第一组数据吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences.Editor editor_s = getSharedPreferences("data",MODE_PRIVATE).edit();
                        String height1 = editText_h1.getText().toString();
                        String weight1 = editText_w1.getText().toString();
                        String waist1 = editText_wa1.getText().toString();
                        editor_s.putFloat("height1",Float.parseFloat(height1));
                        editor_s.putFloat("weight1",Float.parseFloat(weight1));
                        editor_s.putFloat("waist1",Float.parseFloat(waist1));
                        editor_s.apply();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create().show();

    }
    //更改第二组数据
    public void onSecondSize(View v){
        final EditText editText_h2 =findViewById(R.id.message4);
        final EditText editText_w2 =findViewById(R.id.message5);
        final EditText editText_wa2 = findViewById(R.id.message6);

        new AlertDialog.Builder(Personal.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("注意")
                .setMessage("确定要更改第一组数据吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences.Editor editor_s = getSharedPreferences("data",MODE_PRIVATE).edit();
                        String height2 = editText_h2.getText().toString();
                        String weight2 = editText_w2.getText().toString();
                        String waist2 = editText_wa2.getText().toString();
                        editor_s.putFloat("height2",Float.parseFloat(height2));
                        editor_s.putFloat("weight2",Float.parseFloat(weight2));
                        editor_s.putFloat("waist2",Float.parseFloat(waist2));
                        editor_s.apply();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create().show();
    }
}
