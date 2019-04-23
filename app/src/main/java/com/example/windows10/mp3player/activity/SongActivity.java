package com.example.windows10.mp3player.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.StrictMode;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.windows10.mp3player.R;
import com.example.windows10.mp3player.adapter.SongAdapter;
import com.example.windows10.mp3player.model.BaiHat;
import com.example.windows10.mp3player.model.TheLoai;
import com.example.windows10.mp3player.service.APIService;
import com.example.windows10.mp3player.service.DataService;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SongActivity extends AppCompatActivity {
    TheLoai theLoai;
    CoordinatorLayout coordinatorLayoutSongList;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;
    RecyclerView recyclerViewSongList;
    ImageView imgSongList;
    FloatingActionButton floatingActionButton;
    ArrayList<BaiHat> listBaiHat;
    SongAdapter songAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        initControls();
        checkDataIntent();
        eventClickPlayAllSong();
        if(theLoai != null && !theLoai.getTenTheLoai().equals("")){
            setValueView(theLoai.getTenTheLoai(),theLoai.getHinhTheLoai(),theLoai.getHinhTheLoai());
            getDataTheLoai(theLoai.getIdTheLoai());

        }

    }

    private void eventClickPlayAllSong() {

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SongActivity.this,MusicPlayerActivity.class);
                intent.putExtra("tatcabaihat",listBaiHat);
                startActivity(intent);
            }
        });
    }


    private void getDataTheLoai(String idTheLoai) {

        DataService dataService = APIService.getService();
        Call<List<BaiHat>> callBack = dataService.getBaiHatFromTheLoai(idTheLoai);
        callBack.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                listBaiHat = (ArrayList<BaiHat>) response.body();
                songAdapter = new SongAdapter(SongActivity.this,listBaiHat);
                recyclerViewSongList.setLayoutManager(new LinearLayoutManager(SongActivity.this));
                recyclerViewSongList.setAdapter(songAdapter);
                if(listBaiHat.size() > 0)
                   floatingActionButton.setEnabled(true);

            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {

            }
        });


    }

    private void setValueView(String ten,String hinhQuangCao , String hinhBaiHat) {
        collapsingToolbarLayout.setTitle(ten);
        LoadImageToCollapsingToolbarLayout loadImageToCollapsingToolbarLayout = new LoadImageToCollapsingToolbarLayout();
        loadImageToCollapsingToolbarLayout.execute(hinhQuangCao);
        Picasso.get()
                .load(hinhBaiHat)
                .into(imgSongList);
    }



    private void checkDataIntent() {
        Intent intent = getIntent();
        if(intent != null){
            if(intent.hasExtra("theloai")){
                theLoai = (TheLoai) intent.getSerializableExtra("theloai");
            }
        }
    }

    private void initControls() {
        coordinatorLayoutSongList = findViewById(R.id.coordinatorLayoutSong);
        collapsingToolbarLayout = findViewById(R.id.collapsingToolbarLayoutSong);
        toolbar = findViewById(R.id.toolBarSong);
        recyclerViewSongList = findViewById(R.id.recyclerViewSong);
        floatingActionButton = findViewById(R.id.floatingActionButtonSong);
        imgSongList = findViewById(R.id.imageViewSong);
        floatingActionButton.setEnabled(false);
        listBaiHat = new ArrayList<>();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
    }

    private class LoadImageToCollapsingToolbarLayout extends AsyncTask<String,Void,Bitmap> {

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                return bitmap;
            } catch (MalformedURLException e) {
                return null;
            } catch (IOException e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if(bitmap != null){
                BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(),bitmap);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    collapsingToolbarLayout.setBackground(bitmapDrawable);
                }
                super.onPostExecute(bitmap);
            }
        }
    }


}
