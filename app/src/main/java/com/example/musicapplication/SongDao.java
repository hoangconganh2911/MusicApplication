package com.example.musicapplication;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SongDao {
    @Query("SELECT*FROM table_song")
    List<Song> getAllSong();

    @Insert
    void insert(List<Song> songs);

    @Update
    void update(List<Song> songs);

    @Delete
    void delete(Song songs);

    @Query("DELETE FROM table_song")
    void deleteCategorytAll();
}
