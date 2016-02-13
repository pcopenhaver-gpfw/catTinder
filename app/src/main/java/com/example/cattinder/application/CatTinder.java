package com.example.cattinder.application;

import com.example.cattinder.util.Logger;
import com.example.cattinder.util.Logger.AndroidTree;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class CatTinder extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        /**
         * Hook up the logger so it sends log calls
         * via Android's logger.
         */
        Logger.plant(new AndroidTree());
        RealmConfiguration config = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(config);

    }
}
