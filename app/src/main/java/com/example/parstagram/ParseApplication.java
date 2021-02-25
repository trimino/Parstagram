package com.example.parstagram;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Register Post Class with parse before parse initializer
        ParseObject.registerSubclass(Post.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.applicationID))
                .clientKey(getString(R.string.clientKey))
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
