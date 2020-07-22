package com.example.musicapplication;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "table_song")
public class Song implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int id;
    @ColumnInfo(name = "name")
    public String SongName;
    @ColumnInfo(name = "singer")
    public String Single;
    @ColumnInfo(name = "img")
    public int ImageUri;
    @ColumnInfo(name = "uri")
    public int SongUri;
    public Song()
    {}
    public Song(String songName, String single, int imageUri, int songUri) {
        SongName = songName;
        Single = single;
        ImageUri = imageUri;
        SongUri = songUri;
    }

    public String getSongName() {
        return SongName;
    }

    public void setSongName(String songName) {
        SongName = songName;
    }

    public String getSingle() {
        return Single;
    }

    public void setSingle(String single) {
        Single = single;
    }

    public int getImageUri() {
        return ImageUri;
    }

    public void setImageUri(int imageUri) {
        ImageUri = imageUri;
    }

    public int getSongUri() {
        return SongUri;
    }

    public void setSongUri(int songUri) {
        SongUri = songUri;
    }
}
