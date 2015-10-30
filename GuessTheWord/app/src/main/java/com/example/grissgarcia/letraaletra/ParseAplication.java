package com.example.grissgarcia.letraaletra;

import android.app.Application;

import com.activeandroid.ActiveAndroid;
import com.parse.Parse;

/**
 * Created by Griss Garcia on 28/10/2015.
 */
public class ParseAplication extends com.activeandroid.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
        Parse.enableLocalDatastore(this);
        Parse.initialize(this);
    }
}
