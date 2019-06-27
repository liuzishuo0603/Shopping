package com.example.dell.myapplication;

import com.example.dell.myapplication.bean.SportsBannBean;
import com.example.dell.myapplication.bean.SportsBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiSerivce {
    String mUrl = "https://bkbapi.dqdgame.com/";
    String mBanner = "https://sport-data.dqdgame.com/";

    @GET("app/tabs/android/1.json?mark=gif&version=132")
    Observable<SportsBean> getData();

    @GET("sd/biz/live/index?platform=android&version=132")
    Observable<SportsBannBean> getDate();
}
