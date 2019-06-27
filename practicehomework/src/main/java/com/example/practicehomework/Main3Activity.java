package com.example.practicehomework;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.practicehomework.adapter.Main3Adapter;

import java.util.ArrayList;

public class Main3Activity extends AppCompatActivity {

    private ViewPager mVpMain3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        initView();
    }

    private void initView() {
        mVpMain3 = (ViewPager) findViewById(R.id.vp_main3);
        ArrayList<View> views = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.view_activity, null);
            ImageView iv = view.findViewById(R.id.iv_vp_main3);
            Button but = view.findViewById(R.id.but_vp_main3);
            if (i == 0) {
                iv.setImageResource(R.mipmap.a);
            } else if (i == 1) {
                iv.setImageResource(R.mipmap.b);
            } else if (i == 2) {
                iv.setImageResource(R.mipmap.c);
                but.setVisibility(View.VISIBLE);
                but.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      startActivity(new Intent(Main3Activity.this,Main4Activity.class));
                      finish();
                    }
                });
            }
            views.add(view);
        }
        Main3Adapter adapter = new Main3Adapter(views);
        mVpMain3.setAdapter(adapter);
    }
}
