package com.example.dell.myapplication;
//刘子硕 /2019/6/25/18:45

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.dell.myapplication.adapter.MainAdapter;
import com.example.dell.myapplication.fragment.CircleFragment;
import com.example.dell.myapplication.fragment.DataFragment;
import com.example.dell.myapplication.fragment.HomeFragment;
import com.example.dell.myapplication.fragment.MatchFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolMain;
    private ViewPager mVpMain;
    private TabLayout mTabMain;
    /**
     * 上篮
     */
    private TextView mTvToolMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mToolMain = (Toolbar) findViewById(R.id.tool_main);
        mVpMain = (ViewPager) findViewById(R.id.vp_main);
        mTabMain = (TabLayout) findViewById(R.id.tab_main);
        mTvToolMain = (TextView) findViewById(R.id.tv_tool_main);
        mToolMain.setTitle("");
        setSupportActionBar(mToolMain);
        mTvToolMain.setText("上篮");
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new MatchFragment());
        fragments.add(new CircleFragment());
        fragments.add(new DataFragment());
        MainAdapter adapter = new MainAdapter(getSupportFragmentManager(), fragments);
        mVpMain.setAdapter(adapter);
        mTabMain.setupWithViewPager(mVpMain);
        mTabMain.getTabAt(0).setCustomView(R.layout.home);
        mTabMain.getTabAt(1).setCustomView(R.layout.match);
        mTabMain.getTabAt(2).setCustomView(R.layout.circle);
        mTabMain.getTabAt(3).setCustomView(R.layout.data);
    }
}
