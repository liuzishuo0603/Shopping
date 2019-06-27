package com.example.practicehomework;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


public class TestActivity extends AppCompatActivity {

    private TextView test_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initView();
    }

    private void initView() {
        test_tv = (TextView) findViewById(R.id.test_tv);

        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        test_tv.setText(data);
    }
}
