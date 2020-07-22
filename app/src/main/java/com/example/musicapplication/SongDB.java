package com.example.musicapplication;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Song.class}, version = 1)
public abstract class SongDB extends RoomDatabase {
    private static SongDB songDB;

    public static SongDB getInstance(Context context, String databasename) {
        if (songDB == null) {
            songDB = Room.databaseBuilder(context, SongDB.class, databasename)
                    .allowMainThreadQueries()
                    .build();
        }
        return songDB;
    }
    public abstract SongDao getSongDao();
}
