package com.example.windows10.mp3player.activity;

import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.windows10.mp3player.R;
import com.example.windows10.mp3player.adapter.PlayerViewPagerAdapter;
import com.example.windows10.mp3player.fragment.PlayerSongDiskFragment;
import com.example.windows10.mp3player.fragment.PlayerSongListFragment;
import com.example.windows10.mp3player.model.BaiHat;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MusicPlayerActivity extends AppCompatActivity {
    Toolbar toolbarMusicPlayer;
    TextView txtTimeSong, txtTotalTime;
    SeekBar seekBarSong;
    ImageButton imgPlay, imgRepeat, imgPreview, imgNext, imgRandom;
    ViewPager viewPagerMusicPlayer;
    PlayerSongListFragment playerSongListFragment;
    PlayerSongDiskFragment playerSongDiskFragment;
    public static ArrayList<BaiHat> listBaiHat = new ArrayList<>();
    public static PlayerViewPagerAdapter playerViewPagerAdapter;
    MediaPlayer mediaPlayer = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        checkDataIntent();
        initControls();
        eventClick();
    }

    private void eventClick() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(playerViewPagerAdapter.getItem(1) != null){
                    if(listBaiHat.size() > 0){
                        playerSongDiskFragment.setImage(listBaiHat.get(0).getHinhBaiHat());
                        handler.removeCallbacks(this);
                    }else{
                        handler.postDelayed(this,300);
                    }
                }
            }
        },500);
        imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    playerSongDiskFragment.updateStatus(false);
                    imgPlay.setImageResource(R.drawable.iconplay);
                }else{
                    mediaPlayer.start();
                    playerSongDiskFragment.updateStatus(true);
                    imgPlay.setImageResource(R.drawable.iconpause);
                }
            }
        });
    }

    private void initControls() {
        viewPagerMusicPlayer = findViewById(R.id.viewPagerMusicPlayer);
        toolbarMusicPlayer = findViewById(R.id.toolBarMusicPlayer);
        txtTimeSong = findViewById(R.id.textViewTimeSong);
        txtTotalTime = findViewById(R.id.textViewTotalTimeSong);
        seekBarSong = findViewById(R.id.seekBarSong);
        imgPlay = findViewById(R.id.imageButtonPlay);
        imgRepeat = findViewById(R.id.imageButtonRepeat);
        imgPreview = findViewById(R.id.imageButtonPreview);
        imgNext = findViewById(R.id.imageButtonNext);
        imgRandom = findViewById(R.id.imageButtonRandom);

        playerSongDiskFragment = new PlayerSongDiskFragment();
        playerSongListFragment = new PlayerSongListFragment();
        playerViewPagerAdapter = new PlayerViewPagerAdapter(getSupportFragmentManager());
        playerViewPagerAdapter.addFragment(playerSongListFragment);
        playerViewPagerAdapter.addFragment(playerSongDiskFragment);
        viewPagerMusicPlayer.setAdapter(playerViewPagerAdapter);

        setSupportActionBar(toolbarMusicPlayer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarMusicPlayer.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        toolbarMusicPlayer.setTitle("Music Playerrrrrrr");
        toolbarMusicPlayer.setTitleTextColor(Color.WHITE);

        playerSongDiskFragment = (PlayerSongDiskFragment) playerViewPagerAdapter.getItem(1);
        Toast.makeText(this, listBaiHat.size()+"", Toast.LENGTH_SHORT).show();
        if(listBaiHat.size() > 0){
            getSupportActionBar().setTitle(listBaiHat.get(0).getTenBaiHat());
            new PlayMp3().execute(listBaiHat.get(0).getLinkBaiHat());
            imgPlay.setImageResource(R.drawable.iconpause);
        }

    }

    private void checkDataIntent() {
        Intent intent = getIntent();
        listBaiHat.clear();
        if (intent != null) {
            if (intent.hasExtra("baihat")) {
                BaiHat baiHat = intent.getParcelableExtra("baihat");
                listBaiHat.add(baiHat);
            }
            if (intent.hasExtra("tatcabaihat")) {
                ArrayList<BaiHat> danhSachBaiHat = intent.getParcelableArrayListExtra("tatcabaihat");
                Toast.makeText(this, danhSachBaiHat.get(0).getCaSi(), Toast.LENGTH_SHORT).show();
                listBaiHat = danhSachBaiHat;
            }
        }

    }

    class PlayMp3 extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            return strings[0];
        }

        @Override
        protected void onPostExecute(String baiHat) {
            super.onPostExecute(baiHat);
            try {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                    }
                });

                mediaPlayer.setDataSource(baiHat);
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.start();
            getTimeSong();
        }

    }

    private void getTimeSong() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        txtTotalTime.setText(simpleDateFormat.format(mediaPlayer.getDuration()));
        seekBarSong.setMax(mediaPlayer.getDuration());
    }
}


