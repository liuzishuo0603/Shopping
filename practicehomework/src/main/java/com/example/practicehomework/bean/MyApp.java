package com.example.practicehomework.bean;

import android.app.Application;

import com.example.xts.greendaodemo.db.DaoMaster;
import com.example.xts.greendaodemo.db.DaoSession;

public class MyApp extends Application {

    private static DaoSession mDaoSession;

    public static DaoSession getmDaoSession() {
        return mDaoSession;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "user.db");
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        mDaoSession = daoMaster.newSession();
    }
}
