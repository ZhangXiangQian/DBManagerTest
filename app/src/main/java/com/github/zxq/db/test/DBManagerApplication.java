package com.github.zxq.db.test;

import android.app.Application;

import org.android.zxq.db.Controller;

/**
 * Created by zhang on 2016/7/7.
 */
public class DBManagerApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Controller.Ext.init(this);
    }
}
