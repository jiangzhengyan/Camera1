package com.ca.camera1;


import android.app.Application;

/**
 * 本类的主要功能是 :  application
 *
 * @author jiang_zheng_yan  2020/4/10 20:03
 */
public class AppApplication extends Application {
    private static AppApplication instance;
    private static final String TAG = "AppApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化 LitePal数据库
        instance = this;

    }



    public static AppApplication getInstance() {
        return instance;
    }


}
