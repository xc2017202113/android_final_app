package com.example.xuchen.vision1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.util.Log;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";

    private user_database dpHelper;


    @Override
    public Resources getResources() {
        return super.getResources();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final EditText editText_name = (EditText) findViewById(R.id.name);
        final EditText editText_password = (EditText) findViewById(R.id.password);

        Button button = (Button) findViewById(R.id.login_button);

       dpHelper = new user_database(this,"user_base.db",null,1);
        //dpHelper.getWritableDatabase();

        button.setOnClickListener(new View.OnClickListener() {
            //这里是得到用户名和密码
            @Override
            public void onClick(View v) {
                String name = editText_name.getText().toString();
                Log.d(TAG, "onClick: test:login_name"+ name );
                String password = editText_password.getText().toString();
                Log.d(TAG, "onClick: test:login_password"+ password );

                SQLiteDatabase db = dpHelper.getWritableDatabase();
                Cursor cursor = db.query("user_data",null,null,null,null,null,null);
                if(cursor.moveToFirst()){
                    do{
                        String name_query = cursor.getString(cursor.getColumnIndex("username"));
                        String password_query = cursor.getString(cursor.getColumnIndex("password"));

                        //szs
                        String name1=cursor.getString(cursor.getColumnIndex("username"));

                        if(name_query.equals(name) && password_query.equals(password))
                        {
                            //szs
                            SharedPreferences.Editor editor =getSharedPreferences("data",MODE_PRIVATE).edit();
                            editor.putString("user_name",name1);
                            editor.apply();
                            //szs

                            Intent intent2model_show = new Intent(MainActivity.this,model_show.class);
                            startActivity(intent2model_show);

                        }
                    }while(cursor.moveToNext());
                }

            }
        });


        Button button_register = (Button)findViewById(R.id.news);

        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,register.class);
                startActivity(intent);
            }
        });



    }

}

