package com.example.xuchen.vision1;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class register extends AppCompatActivity {

    private user_database dbHepler;
    private static final String TAG = "register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button button = (Button) findViewById(R.id.btn_signup);

        final EditText names = (EditText) findViewById(R.id.input_name);
        final EditText email = (EditText) findViewById(R.id.input_email);
        final EditText username = (EditText) findViewById(R.id.input_username);
        final EditText password = (EditText) findViewById(R.id.input_password);


        dbHepler = new user_database(this,"user_base.db",null,1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHepler.getWritableDatabase();
                ContentValues values = new ContentValues();
                String Name = names.getText().toString();
                String Email = email.getText().toString();
                String Username = username.getText().toString();
                String Password = password.getText().toString();
                Log.d(TAG, "onClick_register : Username");


                values.put("username",Username);
                values.put("password",Password);
                values.put("name",Name);
                values.put("address",Email);

                db.insert("user_data",null,values);
                Toast.makeText(register.this,"register succeed!",Toast.LENGTH_SHORT).show();
                values.clear();

            }
        });

       Button button_login = (Button)findViewById(R.id.link_login);

       button_login.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(register.this,MainActivity.class);
               startActivity(intent);
           }
       });
    }

}
