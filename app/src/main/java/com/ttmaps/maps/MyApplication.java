package com.ttmaps.maps;

import android.app.Application;

/**
 * Created by ec186032 on 11/19/17.
 */

public class MyApplication extends Application {
    public static DBHandler db;
    public static DBHandler getApplication(){
        return db;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        db = new DBHandler(getApplicationContext());
    }
}
