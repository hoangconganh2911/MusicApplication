package com.example.musicapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class SongPlay extends AppCompatActivity {
    private SongPlayService songPlayService;
    private TextView txtSongName, txtCurrent, txtTotal;
    private ImageButton btnPlay, btnPre, btnRewind, btnNext, btnFormward;
    private SeekBar seekBar;
    private ImageView imgSong;
    private MediaPlayer mediaPlayer;
    private boolean mBound = false;
    private List<Song> songList = new ArrayList<>();
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            SongPlayService.SongPlayBinder songPlayBinder = (SongPlayService.SongPlayBinder) iBinder;
            songPlayService = songPlayBinder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_play);


        Intent i = getIntent();
        Bundle bundle = i.getBundleExtra("data");
        Song song = (Song) bundle.getSerializable("song");
        final int position = bundle.getInt("id");
        String songName = song.getSongName();
        final int songUri = song.getSongUri();
        final Intent intent = new Intent(SongPlay.this, SongPlayService.class);
        Bundle bundle1Service = new Bundle();
        bundle1Service.putSerializable("songService", song);
        bundle1Service.putInt("id", position);
        intent.putExtra("dataService", bundle1Service);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
        startService(intent);


        txtSongName = (TextView) findViewById(R.id.txtName1);
        btnPlay = (ImageButton) findViewById(R.id.btnPlay);
        btnPre = (ImageButton) findViewById(R.id.btnPre);
        btnRewind = (ImageButton) findViewById(R.id.btnRewind);
        btnNext = (ImageButton) findViewById(R.id.btnNext);
        btnFormward = (ImageButton) findViewById(R.id.btnFormwaed);
        seekBar = (SeekBar) findViewById(R.id.seekbar);
        txtTotal = (TextView) findViewById(R.id.txttimeEnd);
        txtCurrent=(TextView)findViewById(R.id.txttimePlay);
        imgSong=(ImageView)findViewById(R.id.img);
//        Glide.with(getApplicationContext()).load(R.drawable.pause).override(100, 100).into(btnPlay);
//        Glide.with(getApplicationContext()).load(R.drawable.pre).override(100, 100).into(btnPre);
//        Glide.with(getApplicationContext()).load(R.drawable.rewind).override(100, 100).into(btnRewind);
//        Glide.with(getApplicationContext()).load(R.drawable.formward).override(100, 100).into(btnFormward);
//        Glide.with(getApplicationContext()).load(R.drawable.next).override(100, 100).into(btnNext);
        songList = SongDB.getInstance(getApplicationContext(), "song_db").getSongDao().getAllSong();
        txtSongName.setText(songName);
        mediaPlayer = MediaPlayer.create(getApplicationContext(), songList.get(position).getSongUri());
        txtTotal.setText(Util.milliSecondToTimer(mediaPlayer.getDuration()));
        seekBar.setProgress(0);
        seekBar.setMax(mediaPlayer.getDuration());
        imgSong.setImageResource(song.getImageUri());
        UpdateTimeSong();
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBound) {
                    if (songPlayService.isPlaying()) {
                        songPlayService.pauseMusic();
                        //Glide.with(getApplicationContext()).load(R.drawable.play).override(100, 100).into(btnPlay);
                        btnPlay.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                    } else {
                        songPlayService.playMusic();
                        //Glide.with(getApplicationContext()).load(R.drawable.pause).override(100, 100).into(btnPlay);
                        btnPlay.setImageResource(R.drawable.ic_pause_black_24dp);
                    }
                }
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            int index = position + 1;

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if (index < songList.size()) {
                    txtSongName.setText(songList.get(index).getSongName());
                    songPlayService.startMusic(index);
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), songList.get(index).getSongUri());
                    seekBar.setMax(mediaPlayer.getDuration());
                    txtTotal.setText(Util.milliSecondToTimer(mediaPlayer.getDuration()));
                    imgSong.setImageResource(songList.get(index).getImageUri());
                    index++;
                } else {
                    txtSongName.setText(songList.get(0).getSongName());
                    songPlayService.startMusic(0);
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), songList.get(0).getSongUri());
                    seekBar.setMax(mediaPlayer.getDuration());
                    txtTotal.setText(Util.milliSecondToTimer(mediaPlayer.getDuration()));
                    imgSong.setImageResource(songList.get(0).getImageUri());
                    index = 1;
                }
                seekBar.setProgress(0);

            }
        });
        btnPre.setOnClickListener(new View.OnClickListener() {
            int index = position - 1;

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if (index >= 0) {
                    txtSongName.setText(songList.get(index).getSongName());
                    songPlayService.startMusic(index);
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), songList.get(index).getSongUri());
                    seekBar.setMax(mediaPlayer.getDuration());
                    txtTotal.setText(Util.milliSecondToTimer(mediaPlayer.getDuration()));
                    imgSong.setImageResource(songList.get(index).getImageUri());
                    index--;
                } else {
                    txtSongName.setText(songList.get(songList.size() - 1).getSongName());
                    songPlayService.startMusic(songList.size() - 1);
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), songList.get(songList.size() - 1).getSongUri());
                    seekBar.setMax(mediaPlayer.getDuration());
                    txtTotal.setText(Util.milliSecondToTimer(mediaPlayer.getDuration()));
                    imgSong.setImageResource(songList.get(songList.size()-1).getImageUri());
                    index = songList.size() - 2;
                }
                seekBar.setProgress(0);
            }
        });
        btnFormward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                songPlayService.onForward();
            }
        });
        btnRewind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                songPlayService.onRewind();
            }
        });
        seekBar.setMax(mediaPlayer.getDuration());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                songPlayService.seekTo(seekBar.getProgress());
            }
        });

    }
    private void UpdateTimeSong()
    {
        final Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                txtCurrent.setText(Util.milliSecondToTimer(songPlayService.getCurrentTime()));
                handler.postDelayed(this,300);
                seekBar.setProgress(songPlayService.getCurrentTime());
            }
        },100);
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(SongPlay.this, SongPlayService.class);
        stopService(intent);
        super.onDestroy();
    }
}
