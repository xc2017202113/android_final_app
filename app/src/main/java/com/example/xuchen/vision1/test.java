package com.example.xuchen.vision1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class test extends AppCompatActivity {
    private static final String TAG = "test";
    public static String file_name;
    public String picname;
    private String height_data;
    private String weight_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        file_name = "man-180-65";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = new Intent(test.this,model_show.class);

        Intent pre_intent = getIntent();
        file_name = pre_intent.getStringExtra("label");
        picname = pre_intent.getStringExtra("picname");

        intent.putExtra("change",file_name);
        intent.putExtra("picname_second",picname);

        Log.d(TAG, "onCreate:asdfasdfasf "+file_name);
        startActivity(intent);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
