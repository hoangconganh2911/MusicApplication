package com.example.musicapplication;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;

public class App extends Application {
    public static final String CHANEL_ID="exampleServiceChanel";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChanel();
    }

    private void createNotificationChanel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel serviceApp=new NotificationChannel(
                    CHANEL_ID,"Example Service Chanel", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceApp);
        }
    }
}
