package com.example.practicehomework;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.practicehomework.adapter.Main4Adapter;
import com.example.practicehomework.fragment.ChatFragment;
import com.example.practicehomework.fragment.HomeFragment;
import com.example.practicehomework.fragment.RankingFragment;
import com.example.practicehomework.fragment.VideoFragment;

import java.util.ArrayList;

public class Main4Activity extends AppCompatActivity {

    private TextView mTvToolMain4;
    private Toolbar mToolMain4;
    private ViewPager mVpMain4;
    private TabLayout mTabMain4;
    private NavigationView mNaMain4;
    private DrawerLayout mDlMain4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        initView();
    }

    private void initView() {
        mTvToolMain4 = (TextView) findViewById(R.id.tv_tool_main4);
        mToolMain4 = (Toolbar) findViewById(R.id.tool_main4);
        mVpMain4 = (ViewPager) findViewById(R.id.vp_main4);
        mTabMain4 = (TabLayout) findViewById(R.id.tab_main4);
        mNaMain4 = (NavigationView) findViewById(R.id.na_main4);
        mDlMain4 = (DrawerLayout) findViewById(R.id.dl_main4);
        mToolMain4.setTitle("");
        setSupportActionBar(mToolMain4);
        mTvToolMain4.setText("首页");
        mToolMain4.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();//关闭页面
            }
        });
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new VideoFragment());
        fragments.add(new ChatFragment());
        fragments.add(new RankingFragment());
        Main4Adapter adapter = new Main4Adapter(getSupportFragmentManager(),fragments);
        mVpMain4.setAdapter(adapter);
        mTabMain4.setupWithViewPager(mVpMain4);
        mTabMain4.getTabAt(0).setCustomView(R.layout.home);
        mTabMain4.getTabAt(1).setCustomView(R.layout.video);
        mTabMain4.getTabAt(2).setCustomView(R.layout.chat);
        mTabMain4.getTabAt(3).setCustomView(R.layout.ranking);
    }
}
