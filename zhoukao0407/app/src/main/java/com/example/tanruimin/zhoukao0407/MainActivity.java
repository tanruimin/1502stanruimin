package com.example.tanruimin.zhoukao0407;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import Untils.MyAnsycTask;


public class MainActivity extends AppCompatActivity {
    private String uri = "http://result.eolinker.com/KLn5hSP9f6fed196f92ec0148255a48aebb2c6cc5f97f0e?uri=user";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



            ListView lv  = (ListView) findViewById(R.id.lv);

            MyAnsycTask myAnsycTask = new MyAnsycTask(this,lv);
            myAnsycTask.execute(uri);

        }
    }
