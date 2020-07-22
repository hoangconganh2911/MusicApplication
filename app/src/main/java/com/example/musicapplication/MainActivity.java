package com.example.musicapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.MediaController;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnCLickListener{
RecyclerView rcvSong;
SongAdapter songAdapter;
List<Song> songList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rcvSong=(RecyclerView)findViewById(R.id.rcvMusic);
        LinearLayoutManager linearLayout=new LinearLayoutManager(this);
        rcvSong.setLayoutManager(linearLayout);
        Song a=new Song("Sai lầm của anh","Đình dũng",R.drawable.slca,R.raw.song_1);
        Song b=new Song("Mỹ nhân","Đinh Đại Vũ",R.drawable.mynhan,R.raw.song_2);
        Song c=new Song("Em ơi lên phố","Minh Vương 4U",R.drawable.eolp,R.raw.song3);
        Song d=new Song("Đi đu đưa đi","Bich Phương",R.drawable.dddd,R.raw.song4);
        Song e=new Song("Thuận theo ý trời","Bùi Anh Tuấn",R.drawable.ttyt,R.raw.song5);
        Song f=new Song("Do For Love","Amee,Bray,Masew",R.drawable.doforlove,R.raw.song6);
        Song g=new Song("Sóng Gió","Jack,K-ICM",R.drawable.songgio,R.raw.song_7);
        Song h=new Song("Trúc Xinh","Minh Vương",R.drawable.trucxinh,R.raw.song_8);
        Song i=new Song("Bạc Phận","Jack,K-ICM",R.drawable.bacphan,R.raw.song_9);
        Song k=new Song("Cây đàn sinh viên","Mỹ Tâm",R.drawable.caydansinhvien,R.raw.song_10);
        songList.add(a);
        songList.add(b);
        songList.add(c);
        songList.add(d);
        songList.add(e);
        songList.add(f);
        songList.add((g));
        songList.add(h);
        songList.add(i);
        songList.add(k);
        SongDB.getInstance(getApplicationContext(),"song_db").getSongDao().deleteCategorytAll();
        SongDB.getInstance(getApplicationContext(),"song_db").getSongDao().insert(songList);
        songAdapter=new SongAdapter(getApplicationContext(),songList);
        rcvSong.setAdapter(songAdapter);
        songAdapter.setOnCLickListener(this);
    }

    @Override
    public void onClickListener(int position) {
        Intent intent=new Intent(MainActivity.this,SongPlay.class);
        Song song= songList.get(position);
        Bundle bundle=new Bundle();
        bundle.putSerializable("song",song);
        bundle.putInt("id",position);
        intent.putExtra("data",bundle);
        startActivity(intent);
    }
}
