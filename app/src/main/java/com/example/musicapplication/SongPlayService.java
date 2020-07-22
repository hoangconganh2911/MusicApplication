package com.example.musicapplication;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.provider.SyncStateContract;
import android.util.Log;
import android.widget.SeekBar;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

import static com.example.musicapplication.App.CHANEL_ID;

public class SongPlayService extends Service {
    MediaPlayer mediaPlayer;
    public static final String ACTION_PLAY = "actionplay";
    private final IBinder binder = new SongPlayBinder();
    private List<Song> list = new ArrayList<>();

    public class SongPlayBinder extends Binder {
        public SongPlayService getService() {
            return SongPlayService.this;
        }
    }

    public SongPlayService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle = intent.getBundleExtra("dataService");
        int id = bundle.getInt("id");
        list = SongDB.getInstance(getApplicationContext(), "song_db").getSongDao().getAllSong();
        mediaPlayer = MediaPlayer.create(getApplicationContext(), list.get(id).getSongUri());
        mediaPlayer.start();
        createNot(list.get(id));
        return START_STICKY;
        //return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        mediaPlayer.stop();
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        mediaPlayer.reset();
        return super.onUnbind(intent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return this.binder;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNot(Song song) {
        Intent intentPlay = new Intent(this, MainActivity.class).setAction(ACTION_PLAY);
        PendingIntent pendingIntentPlay = PendingIntent.getActivity(this, 0, intentPlay, PendingIntent.FLAG_CANCEL_CURRENT);
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        Notification notification = new Notification.Builder(this, CHANEL_ID)
                .setContentTitle(song.getSongName())
                .setContentText(song.getSingle())
                .setSmallIcon(R.drawable.ic_audiotrack_black_24dp)
                .setContentIntent(pendingIntent)
                .addAction(R.drawable.pause, "Pause", pendingIntentPlay)
                .build();
        startForeground(1, notification);
        //CreateNotification.createNotification(this,song);
    }

    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    public void playMusic() {
        mediaPlayer.start();
    }

    public void pauseMusic() {
        mediaPlayer.pause();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void startMusic(int id) {
        mediaPlayer.stop();
        mediaPlayer = MediaPlayer.create(getApplicationContext(), list.get(id).getSongUri());
        mediaPlayer.start();
        createNot(list.get(id));
    }

    public void onForward() {
        int currentPosition = mediaPlayer.getCurrentPosition();
        if (currentPosition + 5000 <= mediaPlayer.getDuration()) {
            mediaPlayer.seekTo(currentPosition + 5000);
        } else {
            mediaPlayer.seekTo(mediaPlayer.getDuration());
        }
    }

    public void onRewind() {
        int currentPosition = mediaPlayer.getCurrentPosition();
        if (currentPosition - 5000 > 0) {
            mediaPlayer.seekTo(currentPosition - 5000);
        } else {
            mediaPlayer.seekTo(0);
        }
    }

    public void seekTo(int time) {
        mediaPlayer.seekTo(time);
    }
    public int getCurrentTime()
    {
        return mediaPlayer.getCurrentPosition();
    }
}
